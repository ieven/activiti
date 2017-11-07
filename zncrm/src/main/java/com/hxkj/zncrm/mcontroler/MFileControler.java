package com.hxkj.zncrm.mcontroler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hxkj.zncrm.controler.AbstractControler;
import com.hxkj.zncrm.controler.ResponseConstant;
import com.hxkj.zncrm.dao.domain.FileEntity;
import com.hxkj.zncrm.service.FileService;
import com.hxkj.zncrm.util.EncodeHelper;

@Component
@Path("/m/file")
public class MFileControler extends AbstractControler {

    @Autowired
    private FileService service;

    private String BASEPATH = System.getProperty("hxkj.zncrm.root") + "/../uploadFile";

    @POST
    @Path("/add")
    public Response addProjectFlie(@Context HttpServletRequest request, @Context HttpServletResponse response)
            throws IOException {

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
                FileEntity entity = new FileEntity();
                entity.setAuthor("");
                entity.setMenu_id("");
                entity.setTitle(fileName);
                service.addFile(entity);
                String[] fileSuffix = fileName.split("\\.");
                String fileId = entity.getFile_id() + "." + fileSuffix[fileSuffix.length - 1];
                entity.setFile_name(fileId);
                request.setAttribute("realFileName", fileId);
                String basePath = BASEPATH;
                File dir = new File(basePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(basePath, fileId);
                if (!file.exists()) {
                    file.createNewFile();
                }
                try {
                    item.write(file);
                    service.setFileName(entity);
                    return Response.ok().entity(createResponeJson(ResponseConstant.OK, "", entity)).build();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return Response.ok().entity(createResponeJson(ResponseConstant.EXCEPTION, "")).build();
    }

    @GET
    @Path("/{file_id}")
    public void download(@PathParam("file_id") String fileId, @Context HttpServletRequest request,
            @Context HttpServletResponse response) throws IllegalStateException, IOException {

        FileEntity entity = service.getFileById(fileId);
        String basePath = BASEPATH;
        String filename = entity.getFile_name();
        File file = new File(basePath, filename);
        if (file.exists()) {
            response.addHeader("Content-Disposition",
                    "attachment; filename=\"" + EncodeHelper.urlEncode(entity.getTitle()) + "\"");
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
