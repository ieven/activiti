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
import com.hxkj.zncrm.dao.domain.ProjectEntity;
import com.hxkj.zncrm.po.BusCusMenuListEntity;
import com.hxkj.zncrm.service.BusCusService;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/m/bus_cus")
public class MBusCusControler extends AbstractControler {

    @Autowired
    private BusCusService service;

    @POST
    @Path("/menu_list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getBusCusMenuList(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            List<BusCusMenuListEntity> result = service.getBusCusMenuList(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.getMessage())).build();
        }
    }

    @POST
    @Path("/project_list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getProjectList(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        List<ProjectEntity> list = service.getProjectList(input);
        String records = service.getProjectCount(input);
        Map result = new HashMap<>();
        result.put("iTotalRecords", records);
        result.put("iTotalDisplayRecords", records);
        result.put("result", list);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
    }

    @POST
    @Path("/s_project_list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response searchProjectList(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        List<ProjectEntity> list = service.searchProjectList(input);
        String records = service.searchProjectListCount(input);
        Map result = new HashMap<>();
        result.put("iTotalRecords", records);
        result.put("iTotalDisplayRecords", records);
        result.put("result", list);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
    }

    @POST
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getProject(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        ProjectEntity result = service.getProject(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addProject(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        long num = service.addProject(input);
        if (num == -1) {
            return Response.ok().entity(createResponeJson(ResponseConstant.LOGIN_FAIL, "客户已经存在")).build();
        }
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
    }

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateProject(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        service.updateProject(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
    }

    @POST
    @Path("/log")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getProjectLog(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        List libs = service.getProjectLog(input);
        String records = service.getProjectLogCount(input);
        Map result = new HashMap<>();
        result.put("iTotalRecords", records);
        result.put("iTotalDisplayRecords", records);
        result.put("result", libs);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
    }

    @POST
    @Path("/add_log")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addProjectLog(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        service.addProjectLog(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
    }

    @POST
    @Path("/working_list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getWorkingList(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        List libs = service.searchWorking_list(input);
        String records = service.searchWorking_listCount(input);
        Map result = new HashMap<>();
        result.put("iTotalRecords", records);
        result.put("iTotalDisplayRecords", records);
        result.put("result", libs);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
    }
}
