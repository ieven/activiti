package com.hxkj.zncrm.controler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Component
@Path("/bus_cus")
public class BusCusControler extends AbstractControler {

    @POST
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response test() {

        List list = new ArrayList<>();
        Map map = new HashMap<>();
        map.put("ok", "success");
        map.put("ok1", "success1");
        list.add(map);
        list.add(map);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", list)).build();
    }
}
