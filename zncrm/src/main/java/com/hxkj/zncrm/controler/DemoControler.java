package com.hxkj.zncrm.controler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hxkj.zncrm.service.UserService;

@Component
@Path("user")
public class DemoControler {

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String userTest() {

        return "OK";
    }

    @Autowired
    private UserService userService;

    @GET
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    public String addUser() {

        int result = userService.addUser();
        System.out.println("Create user result = " + result);
        return result == 1 ? "OK" : "ERROR";
    }
}
