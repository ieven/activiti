package com.hxkj.zncrm.controler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import org.springframework.stereotype.Component;

@Component
@Path("/upload")
public class UpLoadControler extends AbstractControler {

    @POST
    @Path("/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context HttpServletRequest request, @Context HttpServletResponse response)
            throws IllegalStateException, IOException {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
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
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String value = item.getString();
                request.setAttribute(fieldName, value);
            }
            else {
                String fileName = item.getName();
                int index = fileName.lastIndexOf("\\");
                fileName = fileName.substring(index + 1);
                request.setAttribute("realFileName", fileName);
                String basePath = request.getRealPath("/uploadFile");
                File dir = new File(basePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(basePath, fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                try {
                    item.write(file);
                    return Response.ok().entity(createResponeJson(ResponseConstant.OK, "")).build();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, "")).build();
    }

    @GET
    @Path("/file/{fileName}")
    public void download(@PathParam("fileName") String fileName, @Context HttpServletRequest request,
            @Context HttpServletResponse response) throws IllegalStateException, IOException {

        String dataDirectory = request.getRealPath("/uploadFile");
        String filename = fileName;
        File file = new File(dataDirectory, filename);
        if (file.exists()) {
            response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;

            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            finally {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            }
        }
    }
}
