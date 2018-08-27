package com.search.common.base.web.core.util;

import com.search.common.base.core.utils.Guava;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * Created by heyanjing on 2018/2/24 11:04.
 */
public class Webs {
    /**
     * @param path 绝对路径
     * @param name 文件名称
     * @param response
     * @param request
     * @throws Exception
     */
    public static void download(String path, String name, HttpServletResponse response, HttpServletRequest request) throws Exception {
        String downLoadName = null;
        if (name == null) {
            String suffix = path.substring(path.lastIndexOf("."), path.length());
            name = System.currentTimeMillis() + suffix;
        } else {
            name = name.replaceAll(" ", "_");
        }
        String agent = request.getHeader("USER-AGENT");
        if (null != agent && -1 != agent.indexOf("Firefox")) { // Firefox
            downLoadName = new String(name.getBytes("UTF-8"), "iso-8859-1");
        } else if (null != agent && -1 != agent.indexOf("Mozilla")) { // IE
            downLoadName = URLEncoder.encode(name, "UTF-8");
        } else {
            downLoadName = URLEncoder.encode(name, "UTF-8");
        }
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + downLoadName);
        FileInputStream fis = null;
        OutputStream os = null;
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            fis = new FileInputStream(path);
            os = response.getOutputStream();
            while ((len = fis.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                os.close();
                fis.close();
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("accept") != null && request.getHeader("accept").indexOf("application/json") > -1) || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1);
    }

    public static void setContentType(HttpServletResponse response, String contentType) {
        response.setHeader("Content-Type", contentType);
    }

    public static void setJsonContentType(HttpServletResponse response) {
        setContentType(response, MediaTypes.APPLICATION_JSON_UTF_8_VALUE);
    }

    public static void setNoCacheHeader(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader(HttpHeaders.EXPIRES, 1L);
        response.addHeader(HttpHeaders.PRAGMA, "no-cache");
        // Http 1.1 header
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0");
    }

    public static void setStatus(HttpServletResponse response, int stateCode) {
        response.setStatus(stateCode);
    }

    public static void setCharacterEncoding(HttpServletResponse response, String characterEncoding) {
        response.setCharacterEncoding(characterEncoding);
    }

    public static void setCharacterEncodingUTF_8(HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
    }

    public static void setStatus403(HttpServletResponse response) {
        setStatus(response, 403);
    }

    public static void setStatus404(HttpServletResponse response) {
        setStatus(response, 404);
    }

    public static void setStatus500(HttpServletResponse response) {
        setStatus(response, 500);
    }

    public static void writeJsonData(HttpServletResponse response, int stateCode) throws IOException {
        writeJsonData(response, null, stateCode);
    }

    public static void writeJsonData(HttpServletResponse response, Object value, int stateCode) throws IOException {
        PrintWriter writer = null;
        try {
            String json = "";
            if (value != null) {
                json = Guava.toJson(value);
            }
            setCharacterEncodingUTF_8(response);
            setJsonContentType(response);
            setNoCacheHeader(response);
            setStatus(response, stateCode);
            writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    class MediaTypes {

        public static final String TEXT_PLAIN_VALUE = "text/plain";
        public static final String TEXT_PLAIN_UTF_8_VALUE = "text/plain;charset=UTF-8";

        public static final String TEXT_HTML_VALUE = "text/html";
        public static final String TEXT_HTML_UTF_8_VALUE = "text/html;charset=UTF-8";

        public static final String APPLICATION_JSON_VALUE = "application/json";
        public static final String APPLICATION_JSON_UTF_8_VALUE = "application/json;charset=UTF-8";

        public static final String TEXT_XML_VALUE = "text/xml";
        public static final String TEXT_XML_UTF_8_VALUE = "text/xml;charset=UTF-8";

        public static final String APPLICATION_XML_VALUE = "application/xml";
        public static final String APPLICATION_XML_UTF_8_VALUE = "application/xml; charset=UTF-8";

        public static final String APPLICATION_JAVASCRIPT_VALUE = "application/javascript";
        public static final String APPLICATION_JAVASCRIPT_UTF_8_VALUE = "application/javascript; charset=UTF-8";

        public static final String APPLICATION_XHTML_XML_VALUE = "application/xhtml+xml";
        public static final String APPLICATION_XHTML_XML_UTF_8_VALUE = "application/xhtml+xml; charset=UTF-8";

        public static final String APPLICATION_OCTET_STREAM_VALUE = "application/octet-stream";

    }
}
