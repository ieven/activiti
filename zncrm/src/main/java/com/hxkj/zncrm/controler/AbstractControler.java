package com.hxkj.zncrm.controler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.hxkj.zncrm.util.JSONHelper;

public class AbstractControler {

    // code
    public enum RespCode {
        FAIL, SUCCESS, EXCEPTION
    }

    protected String createResponeJson(String respCode, String respMsg, Object respData) {

        HashMap<String, Object> resp = new HashMap<String, Object>();
        resp.put("CODE", respCode);
        resp.put("MSG", respMsg);
        resp.put("DATA", respData);
        return JSONHelper.toString(resp);
    }

    protected String createResponeJson(String respCode, String respMsg) {

        HashMap<String, Object> resp = new HashMap<String, Object>();
        resp.put("CODE", respCode);
        resp.put("MSG", respMsg);
        return JSONHelper.toString(resp);
    }

    protected byte[] getByteFromRequest(HttpServletRequest request) {

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
            if (!item.isFormField()) {
                return item.get();
            }
        }
        return null;
    }
}
