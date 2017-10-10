package com.hxkj.zncrm.controler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hxkj.zncrm.dao.domain.ProLib;
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
    public Response getProLib(String json) {

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
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getProLibById(@PathParam("id") String id) {

        try {
            ProLib result = service.getProLibById(id);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", result)).build();
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
            String proId = service.addProLib(input) + "";
            input.put("pro_id", proId);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", input)).build();
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
            service.updateProLib(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
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
            service.delProLib(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @GET
    @Path("/get_pic/{id}")
    public void getProLibPic(@PathParam("id") String id, @Context HttpServletResponse response) {

        byte[] data = service.getProLibPic(id);
        if (data == null) {
            return;
        }
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("UTF-8");
        try {
            OutputStream outputSream = response.getOutputStream();
            InputStream in = new ByteArrayInputStream(data);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf, 0, 1024)) != -1) {
                outputSream.write(buf, 0, len);
            }
            outputSream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("/add_pic/{id}")
    public Response addProLibPic(@PathParam("id") String id, @Context HttpServletRequest request,
            @Context HttpServletResponse response) {

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = null;
        try {
            items = upload.parseRequest(request);
        }
        catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            byte[] bs = item.get();
            service.addProLibPic(id, bs);
        }
        return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
    }

    @POST
    @Path("/link_man")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getLinkMan(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            if (input.isEmpty()) {
                return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, "缺少查询条件")).build();
            }
            List libs = service.getLinkMan(input);
            String records = service.getLinkManCount(input);
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

    @POST
    @Path("/link_man/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addLinkMan(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            String proId = service.addLinkMan(input) + "";
            input.put("link_man_id", proId);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", input)).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @DELETE
    @Path("/link_man")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delLinkMan(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            service.delLinkMan(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }

    @PUT
    @Path("/link_man")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateLinkMan(String json) {

        try {
            Map<String, String> input = JSONHelper.toObject(json, Map.class);
            service.updateLinkMan(input);
            return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, e.toString())).build();
        }
    }
}
