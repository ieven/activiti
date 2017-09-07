package com.hxkj.zncrm.controler;

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
import org.springframework.stereotype.Controller;

import com.hxkj.zncrm.service.ProLibService;
import com.hxkj.zncrm.util.JSONHelper;

@Controller
@Path("/pro_lib")
public class ProLibControler extends AbstractControler {

    @Autowired
    private ProLibService service;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getPhotoById(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            if (input.isEmpty()) {
                return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, "缺少查询条件")).build();
            }
            List libs = service.getProLib(input);
            String records = service.getProLibCount(input);
            Map result = new HashMap<>();
            result.put("iTotalRecords", records);
            result.put("iTotalDisplayRecords", records);
            result.put("result", libs);
            return Response.ok().entity(createResponeJson(ResponseConstant.LOGIN_OK, "", result)).build();
        }
        catch (Exception e) {
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }
}
