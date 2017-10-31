package com.hxkj.zncrm.mcontroler;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hxkj.zncrm.controler.AbstractControler;
import com.hxkj.zncrm.controler.ResponseConstant;
import com.hxkj.zncrm.util.JSONHelper;
import com.hxkj.zncrm.util.Pusher;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

@Component
@Path("/m/push")
public class MPushControler extends AbstractControler {

    @Autowired
    public Pusher pusher;

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getBusCusMenuList(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            if (input.containsKey("tag") && input.containsKey("head") && input.containsKey("body")) {
                PushPayload payload = pusher.buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(input.get("tag"),
                        input.get("head"), input.get("body"));
                try {
                    PushResult result = pusher.getPushClient().sendPush(payload);
                    return Response.ok().entity(createResponeJson(ResponseConstant.OK, result.toString())).build();
                }
                catch (APIConnectionException e) {
                    // Connection error, should retry later
                    e.printStackTrace();
                    return Response.ok().entity(
                            createResponeJson(ResponseConstant.LOGIN_FAIL, "Connection error, should retry later"))
                            .build();

                }
                catch (APIRequestException e) {
                    e.printStackTrace();
                    return Response.ok().entity(createResponeJson(ResponseConstant.LOGIN_FAIL, e.getErrorMessage()))
                            .build();
                }
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
