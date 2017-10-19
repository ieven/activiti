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

import com.hxkj.zncrm.dao.domain.JobEntity;
import com.hxkj.zncrm.service.JobService;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/job")
public class JobControler extends AbstractControler {

    @Autowired
    private JobService jobService;

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getJobList(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        List<JobEntity> list = jobService.getJobList(input);
        String records = jobService.getJobCount(input);
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
    public Response addJob(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        long id = jobService.addJob(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delJob(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        int num = jobService.delJob(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateJob(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        int num = jobService.updateJob(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
    }

}
