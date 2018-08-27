package com.search.common.base.core.utils;

import com.search.common.base.core.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

public class Https {

    public static final String QUESTION_MARK = "?";
    public static final String AND_MARK = "&";
    public static final String EQUAL_MARK = "=";
    public static final String SEPARATOR = "/";
    public static final String GET = "GET";
    public static final String POST = "POST";

    public static String encode2Url(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, Constants.UTF_8_STRING);
    }

    public static String toQueryString(String url) throws Exception {
        return toQueryString(url, null);
    }

    public static String toQueryString(Map<String, Object> queryMap) throws Exception {
        return toQueryString(null, queryMap);
    }

    public static String toQueryString(String url, Map<String, Object> queryMap) throws Exception {
        return toQueryString(url,queryMap,false);
    }
    public static String toQueryString(String url, Map<String, Object> queryMap, boolean simple) throws Exception {
        StringBuilder query = new StringBuilder();
        if (url != null) {
            query.append(url);
        }
        if (queryMap != null) {
            // 是否需要添加？号
            boolean isAppendWithUrl = true;
            if (StringUtils.isNotBlank(url)) {
                String lastSeperator = StringUtils.substringAfterLast(url, SEPARATOR);
                if (StringUtils.contains(lastSeperator, QUESTION_MARK)) {
                    isAppendWithUrl = false;
                }
            }
            Set<String> keys = queryMap.keySet();
            for (String key : keys) {
                if (StringUtils.isNotBlank(key)) {
                    String value = String.valueOf(queryMap.get(key));
                    if (isAppendWithUrl) {
                        query.append(QUESTION_MARK);
                        isAppendWithUrl = false;
                    } else {
                        query.append(AND_MARK);
                    }

                    query.append(simple ? key : encode2Url(key)).append(EQUAL_MARK).append(simple ? value : encode2Url(value));
                }
            }
        }
        return query.toString();
    }

    public static String get(String urlStr) throws Exception {
        return get(urlStr, null);
    }

    public static String get(String urlStr, Map<String, Object> params) throws Exception {
        return get(urlStr, params, Constants.UTF_8_CHARSET);
    }

    public static String get(String urlStr, Map<String, Object> params, String charset) throws Exception {
        return get(urlStr, params, Charset.forName(charset));
    }

    public static String get(String urlStr, Map<String, Object> params, Charset charset) throws Exception {
        URL url;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            url = new URL(toQueryString(urlStr, params));
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(GET);
            connection.setUseCaches(false);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String post(String url) throws Exception {
        return post(url, Constants.UTF_8_CHARSET);
    }

    public static String post(String url, Charset charset) throws Exception {
        return post(url, null, charset);
    }

    public static String post(String url, String charset) throws Exception {
        return post(url, null, Charset.forName(charset));
    }

    public static String post(String url, Map<String, Object> params) throws Exception {
        return post(url, params, Constants.UTF_8_CHARSET);
    }

    public static String post(String urlStr, Map<String, Object> params, Charset charset) throws Exception {
        URL url;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        DataOutputStream out = null;

        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(POST);
            connection.setUseCaches(false);
            connection.connect();
            out = new DataOutputStream(connection.getOutputStream());
            if (params != null) {
                out.writeBytes(toQueryString(params));
            }
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * // MEINFO:2018/3/1 11:39 待验证
     */
    public static String upload(String url, Map<String, Object> params, File file) throws Exception {
        if (params == null) {
            params = Guava.newHashMap();
        }
        url = toQueryString(url, params);

        URL urlObj = new URL(url);
        // 连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);

        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        // 结尾部分
        // 定义最后数据分隔线
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }

    public static String upload(String url, Map<String, Object> params, String path) throws Exception {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        return upload(url, params, file);
    }

}
