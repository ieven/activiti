package com.hxkj.zncrm.controler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hxkj.zncrm.dao.domain.Menu;
import com.hxkj.zncrm.service.MenuService;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/menu")
public class MenuControler extends AbstractControler {

    @Autowired
    private MenuService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getMeunByUsername(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        List<Menu> list = service.getMeunByUsername(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", list)).build();
    }

    @POST
    @Path("/parent_id")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getMeunByParentId(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        List<Menu> list = service.getMeunByParentId(input);
        String records = service.getMenuCount(input);
        Map result = new HashMap<>();
        result.put("iTotalRecords", records);
        result.put("iTotalDisplayRecords", records);
        result.put("result", list);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addMeun(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        service.addMenu(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateMeun(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        service.updateMenu(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delMeun(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        service.delMenu(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
    }
}
