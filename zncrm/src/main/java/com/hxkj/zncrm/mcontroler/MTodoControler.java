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
import com.hxkj.zncrm.dao.domain.TodoEntity;
import com.hxkj.zncrm.service.TodoService;
import com.hxkj.zncrm.util.JSONHelper;

@Component
@Path("/m/todo")
public class MTodoControler extends AbstractControler {

    @Autowired
    private TodoService service;

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getBusCusMenuList(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            service.addTodo(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", input)).build();
        }
        catch (Exception e) {
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.getMessage())).build();
        }
    }

    @POST
    @Path("/get_todo_by_id")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getTodoById(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            TodoEntity result = service.getTodoById(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.getMessage())).build();
        }
    }

    @POST
    @Path("/update_todo_by_id")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateTodoById(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            int result = service.updateTodoById(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
        }
        catch (Exception e) {
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.getMessage())).build();
        }
    }

    @POST
    @Path("/del_todo_by_id")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delTodoById(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            int result = service.delTodoById(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
        }
        catch (Exception e) {
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.getMessage())).build();
        }
    }

    @POST
    @Path("/get_todo_list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getTodoList(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            List libs = service.getTodoList(input);
            String records = service.getTodoCount(input);
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
