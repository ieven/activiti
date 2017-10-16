package com.hxkj.zncrm.controler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hxkj.zncrm.po.RoleAuthEntity;
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

    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getUserList(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            if (input.isEmpty()) {
                return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, "缺少查询条件")).build();
            }
            List libs = loginService.getUserList(input);
            String records = loginService.getUserCount(input);
            Map result = new HashMap<>();
            result.put("iTotalRecords", records);
            result.put("iTotalDisplayRecords", records);
            result.put("result", libs);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @POST
    @Path("/user/get")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getUserByUsername(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            if (input.isEmpty()) {
                return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, "缺少查询条件")).build();
            }
            User result = loginService.getUserByUsername(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @PUT
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateUser(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            int result = loginService.updateUser(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @PUT
    @Path("/user/menu")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateUserMenu(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            String result = loginService.updateUserMenu(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @POST
    @Path("/user/menu")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addUserMenu(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            String result = loginService.addUserMenu(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @DELETE
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delUser(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            int result = loginService.delUser(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @POST
    @Path("/user/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addUser(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            String result = loginService.addUser(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @GET
    @Path("/job")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getJobList() {

        try {
            List libs = loginService.getJobList();
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", libs)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @GET
    @Path("/role")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getRoleList() {

        try {
            List libs = loginService.getRoleList();
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", libs)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @POST
    @Path("/role_list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getRoleList(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            if (input.isEmpty()) {
                return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, "缺少查询条件")).build();
            }
            List libs = loginService.getRoleListPage(input);
            String records = loginService.getRoleListPageCount(input);
            Map result = new HashMap<>();
            result.put("iTotalRecords", records);
            result.put("iTotalDisplayRecords", records);
            result.put("result", libs);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @POST
    @Path("/role_auth")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getRoleAuth(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            RoleAuthEntity result = loginService.getRoleAuth(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @POST
    @Path("/role_oper_auth")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getRoleOperAuth(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            List<String> result = loginService.getRoleOperAuth(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }
}
