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
import org.springframework.stereotype.Component;

import com.hxkj.zncrm.dao.domain.ProjectEntity;
import com.hxkj.zncrm.service.BusCusService;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/home")
public class HomeControler extends AbstractControler {

    @Autowired
    private BusCusService service;

    @POST
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

}
