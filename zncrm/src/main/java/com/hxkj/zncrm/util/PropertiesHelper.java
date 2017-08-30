package com.hxkj.zncrm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class PropertiesHelper {

    public Map<String, String> ReadProperties(String filePath) throws IOException {

        Properties pps = new Properties();
        InputStream in = new BufferedInputStream(new FileInputStream(filePath));
        pps.load(in);
        @SuppressWarnings("rawtypes")
        Enumeration en = pps.propertyNames();

        Map<String, String> result = new HashMap<>();
        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = pps.getProperty(strKey);
            result.put(strKey, strValue);
        }
        return result;
    }

    public void WriteProperties(String filePath, String pKey, String pValue) throws IOException {

        Properties pps = new Properties();
        File file = new File(filePath);
        InputStream in;
        if (file.exists()) {

            in = new FileInputStream(filePath);
        }
        else {
            file.createNewFile();
            in = new FileInputStream(file);
        }
        pps.load(in);
        OutputStream out = new FileOutputStream(filePath);
        pps.setProperty(pKey, pValue);
        pps.store(out, "Update " + pKey + " name");
    }
}
