/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.activiti.explorer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.Pusher;
import org.activiti.crystalball.simulator.SimulationDebugger;
import org.activiti.crystalball.simulator.SimulationEvent;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.explorer.identity.LoggedInUser;
import org.activiti.explorer.navigation.UriFragment;
import org.activiti.explorer.ui.ComponentFactory;
import org.activiti.explorer.ui.MainWindow;
import org.activiti.explorer.ui.content.AttachmentRendererManager;
import org.activiti.explorer.ui.form.FormPropertyRendererManager;
import org.activiti.explorer.ui.login.LoginHandler;
import org.activiti.explorer.ui.variable.VariableRendererManager;
import org.activiti.util.JSONHelper;
import org.activiti.util.PropertiesHelper;
import org.activiti.util.StringHelper;
import org.activiti.workflow.simple.converter.WorkflowDefinitionConversionFactory;
import org.activiti.workflow.simple.converter.json.SimpleWorkflowJsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;

/**
 * @author Joram Barrez
 */
public class ExplorerApp extends Application implements HttpServletRequestListener {

    private static final long serialVersionUID = -1L;

    // Thread local storage of instance for each user
    protected transient static ThreadLocal<ExplorerApp> current = new ThreadLocal<ExplorerApp>();

    protected String environment;
    protected boolean useJavascriptDiagram;
    protected MainWindow mainWindow;
    protected ViewManager viewManager;
    protected NotificationManager notificationManager;
    protected I18nManager i18nManager;
    protected AttachmentRendererManager attachmentRendererManager;
    protected FormPropertyRendererManager formPropertyRendererManager;
    protected VariableRendererManager variableRendererManager;
    protected LoginHandler loginHandler;
    protected ComponentFactories componentFactories;
    protected WorkflowDefinitionConversionFactory workflowDefinitionConversionFactory;
    protected SimpleWorkflowJsonConverter simpleWorkflowJsonConverter;

    // Flag to see if the session has been invalidated, when the application was closed
    protected boolean invalidatedSession = false;

    protected List<String> adminGroups;
    protected List<String> userGroups;

    protected String crystalBallCurrentDefinitionId = null;
    protected String crystalBallCurrentInstanceId = null;
    protected List<SimulationEvent> crystalBallSimulationEvents = null;
    protected transient SimulationDebugger crystalBallSimulationDebugger = null;

    // 添加推送
    protected Pusher mPusher;

    public Pusher getmPusher() {

        return mPusher;
    }

    public void setmPusher(Pusher mPusher) {

        this.mPusher = mPusher;
    }

    @Override
    public void init() {

        setMainWindow(mainWindow);
        mainWindow.showLoginPage();
    }

    /**
     * Required to support multiple browser windows/tabs, see
     * http://vaadin.com/web/joonas/wiki/-/wiki/Main/Supporting%20Multible%20Tabs
     */
    // public Window getWindow(String name) {
    // Window window = super.getWindow(name);
    // if (window == null) {
    // window = new Window("Activiti Explorer");
    // window.setName(name);
    // addWindow(window);
    // window.open(new ExternalResource(window.getURL()));
    // }
    //
    // return window;
    // }

    @Override
    public void close() {

        final LoggedInUser theUser = getLoggedInUser();

        // Clear the logged in user
        setUser(null);

        // Call loginhandler
        getLoginHandler().logout(theUser);

        invalidatedSession = false;
        super.close();
    }

    public static ExplorerApp get() {

        return current.get();
    }

    public LoggedInUser getLoggedInUser() {

        return (LoggedInUser) getUser();
    }

    public String getEnvironment() {

        return environment;
    }

    // Managers (session scoped)

    public ViewManager getViewManager() {

        return viewManager;
    }

    public I18nManager getI18nManager() {

        return i18nManager;
    }

    public NotificationManager getNotificationManager() {

        return notificationManager;
    }

    // Application-wide services

    public AttachmentRendererManager getAttachmentRendererManager() {

        return attachmentRendererManager;
    }

    public FormPropertyRendererManager getFormPropertyRendererManager() {

        return formPropertyRendererManager;
    }

    public void setFormPropertyRendererManager(FormPropertyRendererManager formPropertyRendererManager) {

        this.formPropertyRendererManager = formPropertyRendererManager;
    }

    public <T> ComponentFactory<T> getComponentFactory(Class<? extends ComponentFactory<T>> clazz) {

        return componentFactories.get(clazz);
    }

    public LoginHandler getLoginHandler() {

        return loginHandler;
    }

    public void setVariableRendererManager(VariableRendererManager variableRendererManager) {

        this.variableRendererManager = variableRendererManager;
    }

    public VariableRendererManager getVariableRendererManager() {

        return variableRendererManager;
    }

    public WorkflowDefinitionConversionFactory getWorkflowDefinitionConversionFactory() {

        return workflowDefinitionConversionFactory;
    }

    @Override
    public void setLocale(Locale locale) {

        super.setLocale(locale);
        if (i18nManager != null) {
            i18nManager.setLocale(locale);
        }
    }

    // HttpServletRequestListener -------------------------------------------------------------------

    protected static final Logger LOGGER = LoggerFactory.getLogger(ExplorerApp.class);

    @Override
    public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {

        // Set current application object as thread-local to make it easy accessible
        current.set(this);
        String username = "";
        if (request.getRequestURI().indexOf("zncrm_work_flow") > -1) {
            startWorkflow(request, response);
            return;
        }
        else if (request.getRequestURI().indexOf("zncrmpush") > -1) {
            try {
                BufferedReader br = request.getReader();
                String str, wholeStr = "";
                while ((str = br.readLine()) != null) {
                    wholeStr += str;
                }
                Map<String, String> input = JSONHelper.toObject(wholeStr, Map.class);
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return;
        }
        else if (request.getRequestURI().indexOf("zncrmlogout") > -1) {
            ExplorerApp.get().close();
            return;
        }
        else if (request.getRequestURI().indexOf("zncrmlogin") > -1) {
            ExplorerApp.get().close();
            String[] strs = request.getRequestURI().split("\\/");
            username = strs[strs.length - 1].split("=")[1];
        }
        if (!StringHelper.isEmpty(username)) {
            // Authentication: check if user is found, otherwise send to login page
            try {
                PropertiesHelper helper = new PropertiesHelper();
                String filePath = System.getProperty("hxkj.activiti.root") + "/../zncrm" + username + ".properties";
                Map<String, String> map = helper.ReadProperties(filePath);
                String userName = map.get("username");
                String password = map.get("password");
                // Delegate authentication to handler
                if ((!StringHelper.isEmpty(userName)) && (!StringHelper.isEmpty(password))) {

                    LoggedInUser loggedInUser = loginHandler.authenticate(userName, password);
                    ExplorerApp.get().setUser(loggedInUser);
                }
            }
            catch (Exception e) {
                LOGGER.error("Error at login");
            }
        }

        LoggedInUser user = (LoggedInUser) getUser();
        if (user == null) {
            // First, try automatic login
            user = loginHandler.authenticate(request, response);
            if (user == null) {
                if (mainWindow != null && !mainWindow.isShowingLoginPage()) {
                    viewManager.showLoginPage();
                }
            }
            else {
                setUser(user);
            }
        }

        if (user != null) {
            Authentication.setAuthenticatedUserId(user.getId());
            if (mainWindow != null && mainWindow.isShowingLoginPage()) {
                viewManager.showDefaultPage();
            }
        }

        // Callback to the login handler
        loginHandler.onRequestStart(request, response);
    }

    private void startWorkflow(HttpServletRequest request, HttpServletResponse response) {

        String json = null;
        try {
            // 获取上送参数
            BufferedReader br = request.getReader();
            String str, wholeStr = "";
            while ((str = br.readLine()) != null) {
                wholeStr += str;
            }
            json = wholeStr;
            Map input = JSONHelper.toObject(json, Map.class);
            JSONObject formData = (JSONObject) input.get("form_data");
            Map<String, String> map = JSONHelper.toObject(formData.toJSONString(), Map.class);
            // 获取已经部署流程列表
            List<ProcessDefinition> list = ProcessEngines.getDefaultProcessEngine().getRepositoryService()// 与流程定义和部署对象相关的Service
                    .createProcessDefinitionQuery().list();
            // 遍历所有已经部署的流程查找出符合条件的
            String processDefinitionId = "";
            for (ProcessDefinition definition : list) {
                String temp = definition.getId().split(":")[0];
                if (temp.equals(input.get("workflow_name"))) {
                    processDefinitionId = definition.getId();
                    break;
                }
            }
            FormService formService = ProcessEngines.getDefaultProcessEngine().getFormService();
            ProcessEngines.getDefaultProcessEngine().getIdentityService().setAuthenticatedUserId("admin");
            formService.submitStartFormData(processDefinitionId, map);

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {

        // Clean up thread-local app
        current.remove();

        // Clear authentication context
        Authentication.setAuthenticatedUserId(null);

        // Callback to the login handler
        loginHandler.onRequestEnd(request, response);

        if (!isRunning() && !invalidatedSession) {
            // Clear the session context, the application has been closed during this request, otherwise
            // the application will be stuck on the spring-session scope and will be reused on the next
            // request, which will lead to problems
            if (request.getSession(false) != null) {
                request.getSession().invalidate();
                invalidatedSession = true;
            }
        }
    }

    // Error handling ---------------------------------------------------------------------------------

    @Override
    public void terminalError(com.vaadin.terminal.Terminal.ErrorEvent event) {

        super.terminalError(event);

        // Look for an Activiti Exception, as it'll probably be more meaningful.
        // If not found, just show default
        Throwable exception = event.getThrowable().getCause();
        int depth = 0; // To avoid going too deep in the stack
        while (exception != null && depth < 20 && !(exception instanceof ActivitiException)) {
            exception = exception.getCause();
            depth++;
        }

        if (exception == null) {
            exception = event.getThrowable().getCause();
        }
        notificationManager.showErrorNotification(Messages.UNCAUGHT_EXCEPTION, exception.getMessage());
    }

    // URL Handling ---------------------------------------------------------------------------------

    public void setCurrentUriFragment(UriFragment fragment) {

        mainWindow.setCurrentUriFragment(fragment);
    }

    public UriFragment getCurrentUriFragment() {

        return mainWindow.getCurrentUriFragment();
    }

    // Injection setters

    public void setEnvironment(String environment) {

        this.environment = environment;
    }

    public boolean isUseJavascriptDiagram() {

        return useJavascriptDiagram;
    }

    public void setUseJavascriptDiagram(boolean useJavascriptDiagram) {

        this.useJavascriptDiagram = useJavascriptDiagram;
    }

    public void setApplicationMainWindow(MainWindow mainWindow) {

        this.mainWindow = mainWindow;
    }

    public void setViewManager(ViewManager viewManager) {

        this.viewManager = viewManager;
    }

    public void setNotificationManager(NotificationManager notificationManager) {

        this.notificationManager = notificationManager;
    }

    public void setI18nManager(I18nManager i18nManager) {

        this.i18nManager = i18nManager;
    }

    public void setAttachmentRendererManager(AttachmentRendererManager attachmentRendererManager) {

        this.attachmentRendererManager = attachmentRendererManager;
    }

    public void setComponentFactories(ComponentFactories componentFactories) {

        this.componentFactories = componentFactories;
    }

    public void setLoginHandler(LoginHandler loginHandler) {

        this.loginHandler = loginHandler;
    }

    public void setWorkflowDefinitionConversionFactory(
            WorkflowDefinitionConversionFactory workflowDefinitionConversionFactory) {

        this.workflowDefinitionConversionFactory = workflowDefinitionConversionFactory;
    }

    public List<String> getAdminGroups() {

        return adminGroups;
    }

    public void setAdminGroups(List<String> adminGroups) {

        this.adminGroups = adminGroups;
    }

    public List<String> getUserGroups() {

        return userGroups;
    }

    public void setUserGroups(List<String> userGroups) {

        this.userGroups = userGroups;
    }

    public SimpleWorkflowJsonConverter getSimpleWorkflowJsonConverter() {

        return simpleWorkflowJsonConverter;
    }

    public void setSimpleWorkflowJsonConverter(SimpleWorkflowJsonConverter simpleWorkflowJsonConverter) {

        this.simpleWorkflowJsonConverter = simpleWorkflowJsonConverter;
    }

    public String getCrystalBallCurrentDefinitionId() {

        return crystalBallCurrentDefinitionId;
    }

    public void setCrystalBallCurrentDefinitionId(String crystalBallCurrentDefinitionId) {

        this.crystalBallCurrentDefinitionId = crystalBallCurrentDefinitionId;
    }

    public String getCrystalBallCurrentInstanceId() {

        return crystalBallCurrentInstanceId;
    }

    public void setCrystalBallCurrentInstanceId(String crystalBallCurrentInstanceId) {

        this.crystalBallCurrentInstanceId = crystalBallCurrentInstanceId;
    }

    public List<SimulationEvent> getCrystalBallSimulationEvents() {

        return crystalBallSimulationEvents;
    }

    public void setCrystalBallSimulationEvents(List<SimulationEvent> crystalBallSimulationEvents) {

        this.crystalBallSimulationEvents = crystalBallSimulationEvents;
    }

    public SimulationDebugger getCrystalBallSimulationDebugger() {

        return crystalBallSimulationDebugger;
    }

    public void setCrystalBallSimulationDebugger(SimulationDebugger crystalBallSimulationDebugger) {

        this.crystalBallSimulationDebugger = crystalBallSimulationDebugger;
    }

}
