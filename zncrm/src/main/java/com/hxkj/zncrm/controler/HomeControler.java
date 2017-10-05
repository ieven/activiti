package com.hxkj.zncrm.controler;

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

import com.hxkj.zncrm.po.ChartEntity;
import com.hxkj.zncrm.service.HomeService;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/home")
public class HomeControler extends AbstractControler {

    @Autowired
    private HomeService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getProjectList(String json) {

        Map<String, String> input = JSONHelper.toObject(json, Map.class);
        List<ChartEntity> list = service.getChartEntityList(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", list)).build();
    }

}
