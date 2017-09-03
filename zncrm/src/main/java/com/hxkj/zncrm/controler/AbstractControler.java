package com.hxkj.zncrm.controler;

import java.util.HashMap;

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
}
