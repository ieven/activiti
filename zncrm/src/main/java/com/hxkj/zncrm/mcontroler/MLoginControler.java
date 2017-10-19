package com.hxkj.zncrm.mcontroler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.hxkj.zncrm.po.User;
import com.hxkj.zncrm.service.LoginService;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/m")
public class MLoginControler extends AbstractControler {

    @Autowired
    private LoginService loginService;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response login(String userLoginInfoJson) {

        Map<String, String> input = JSONHelper.toObject(userLoginInfoJson, Map.class);
        String username = input.get("username");
        String password = input.get("password");
        User user = loginService.login(username, password);
        if (user == null) {
            return Response.status(404).entity(createResponeJson(ResponseConstant.LOGIN_FAIL, "账号密码错误")).build();
        }
        else {
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", user)).build();
        }
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response logout(String userLoginInfoJson) {

        Map<String, String> input = JSONHelper.toObject(userLoginInfoJson, Map.class);
        // 退出个JB，直接返回时间
        String username = input.get("username");
        Map<String, String> result = new HashMap<String, String>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.put("logout_time", df.format(new Date()));
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
    }
}
