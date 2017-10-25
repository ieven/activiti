package org.activiti;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.explorer.ExplorerApp;
import org.activiti.explorer.form.UserFormType;
import org.activiti.util.StringHelper;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

public class IOSPushListener implements ExecutionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private FixedValue msg;

    public FixedValue getMsg() {

        return msg;
    }

    public void setMsg(FixedValue msg) {

        this.msg = msg;
    }

    @Override
    public void notify(DelegateExecution execution) throws Exception {

        FormService formService = ProcessEngines.getDefaultProcessEngine().getFormService();
        StartFormData startFormData = formService.getStartFormData(execution.getProcessDefinitionId());
        List<FormProperty> formProperties = startFormData.getFormProperties();
        List<String> usernames = new ArrayList<>();
        for (FormProperty temp : formProperties) {
            if (temp.getType().getName().equals("user")) {
                UserFormType userFormType = (UserFormType) temp.getType();
                usernames.add(userFormType.getUsername());
            }
        }
        // String processName = startFormData.getProcessDefinition().getName();
        Pusher pusher = ExplorerApp.get().getmPusher();
        String content = "";
        if (!StringHelper.isEmpty(msg.getValue(null).toString())) {
            content = msg.getValue(null).toString();
        }
        for (String str : usernames) {

            PushPayload payload = pusher.buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(str, "流程处理通知", content);
            try {
                PushResult result = pusher.getPushClient().sendPush(payload);
                System.out.println(result);
            }
            catch (APIConnectionException e) {
                // Connection error, should retry later
                e.printStackTrace();
                System.out.println("Connection error, should retry later");

            }
            catch (APIRequestException e) {
                // Should review the error, and fix the request
                e.printStackTrace();
                System.out.println("HTTP Status: " + e.getStatus());
                System.out.println("Error Code: " + e.getErrorCode());
                System.out.println("Error Message: " + e.getErrorMessage());
                System.out.println("上送tag：" + str);
                System.out.println("上送内容为：" + content);
            }
        }
    }

}
