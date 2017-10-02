package com.hxkj.zncrm.controler;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
        Map<String, Menu> map = service.getMeunByUsername(input);
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", map)).build();
    }
}
