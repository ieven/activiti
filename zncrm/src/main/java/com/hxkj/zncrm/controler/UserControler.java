package com.hxkj.zncrm.controler;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hxkj.zncrm.po.User;
import com.hxkj.zncrm.service.LoginService;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/")
public class UserControler {

    @Autowired
    private LoginService loginService;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes("application/x-www-form-urlencoded")
    public Response userTest(@FormParam("username") String username, @FormParam("password") String password) {

        User user = loginService.login(username, password);
        if (user == null) {
            return Response.status(404).entity("账号密码错误").build();
        }
        else {
            return Response.ok().entity(JSONHelper.toString(user)).build();
        }
    }

}
