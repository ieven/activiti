package com.hxkj.zncrm.mcontroler;

import java.util.HashMap;
import java.util.List;
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
import com.hxkj.zncrm.service.LoginService;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/m/user")
public class MUserControler extends AbstractControler {

    @Autowired
    private LoginService loginService;

    @POST
    @Path("/get_users_by_department")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response get_users_by_department(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            if (input.isEmpty()) {
                return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, "缺少查询条件")).build();
            }
            List libs = loginService.getUserListByDepartment(input);
            String records = loginService.getUserDepartmentCount(input);
            Map result = new HashMap<>();
            result.put("iTotalRecords", records);
            result.put("iTotalDisplayRecords", records);
            result.put("result", libs);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.getMessage())).build();
        }
    }

    @POST
    @Path("/get_users_by_status")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response get_users_by_status(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            if (input.isEmpty()) {
                return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, "缺少查询条件")).build();
            }
            List libs = loginService.getUserListByStatus(input);
            String records = loginService.getUserStatusCount(input);
            Map result = new HashMap<>();
            result.put("iTotalRecords", records);
            result.put("iTotalDisplayRecords", records);
            result.put("result", libs);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.getMessage())).build();
        }
    }
}
