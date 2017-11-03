package com.hxkj.zncrm.mcontroler;

import java.net.InetAddress;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import com.hxkj.zncrm.controler.AbstractControler;
import com.hxkj.zncrm.controler.ResponseConstant;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/m/workflow")
public class MWorkFlowControler extends AbstractControler {

    private CloseableHttpClient client = HttpClients.createDefault();

    @POST
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response startWorkflow(@Context HttpServletRequest request, String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            if (input.containsKey("workflow_name") && input.containsKey("initiator")
                    && input.containsKey("form_data")) {
                // Windows获取本地ip
                String host = InetAddress.getLocalHost().getHostAddress();
                HttpPost httpMethod = new HttpPost(
                        "http://" + host + ":8080/activiti-webapp-explorer2/zncrm_work_flow");
                StringEntity entity = null;
                entity = new StringEntity(json);
                entity.setContentEncoding("utf-8");
                entity.setContentType("application/json");
                httpMethod.setEntity(entity);

                client.execute(httpMethod);

                return Response.ok().entity(createResponeJson(ResponseConstant.OK, "发起成功")).build();
            }
            else {
                return Response.ok().entity(createResponeJson(ResponseConstant.LOGIN_FAIL, "缺少必要参数")).build();
            }
        }
        catch (Exception e) {
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.getMessage())).build();
        }
    }
}
