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

import com.hxkj.zncrm.service.TichengService;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/ticheng")
public class TichengControler extends AbstractControler {

    @Autowired
    private TichengService service;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getReceiveList(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            if (input.isEmpty()) {
                return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, "缺少查询条件")).build();
            }
            List libs = service.getTichengList(input);
            String records = service.getTichengCount(input);
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

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delProLib(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            service.delTicheng(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateProLib(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            service.updateTicheng(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @SuppressWarnings({ "unchecked" })
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addProLib(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            String id = service.addTicheng(input) + "";
            input.put("id", id);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", input)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

}
