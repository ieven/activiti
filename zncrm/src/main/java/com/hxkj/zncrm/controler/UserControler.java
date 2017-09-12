package com.hxkj.zncrm.controler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hxkj.zncrm.po.User;
import com.hxkj.zncrm.service.LoginService;
import com.hxkj.zncrm.service.PicService;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/")
public class UserControler extends AbstractControler {

    @Autowired
    private LoginService loginService;

    @Autowired
    private PicService picService;

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

    @GET
    @Path("get_pic/{id}")
    public void getPhotoById(@PathParam("id") String id, @Context HttpServletResponse response) {

        byte[] data = picService.getPicById(id);
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("UTF-8");
        try {
            OutputStream outputSream = response.getOutputStream();
            InputStream in = new ByteArrayInputStream(data);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf, 0, 1024)) != -1) {
                outputSream.write(buf, 0, len);
            }
            outputSream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
