package com.search.common.base.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * Created by heyanjing on 2018/6/28 11:16.
 */
public class https2 {
    private static final Logger log = LoggerFactory.getLogger(https2.class);
    public static final String GET = "GET";
    public static final String POST = "POST";
    private static final String UTF_8 = "UTF-8";
    private static final String QUESTION_MARK = "?";
    private static final String AND_MARK = "&";
    private static final String EQUAL_MARK = "=";

    /**
     * @param queryParams 查询参数
     * @param simple      参数值是否需要编码 true：不需要编码，false：需要编码
     * @param charset     编码
     * @return 返回所有参数编码后的字符串
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    private static String encodeParams(Map<String, String> queryParams, boolean simple, String charset) throws UnsupportedEncodingException {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                if (first) {
                    sb.append(key).append(EQUAL_MARK);
                    sb = simple ? sb.append(value) : sb.append(URLEncoder.encode(value, charset));
                    first = false;
                } else {
                    sb.append(AND_MARK).append(key).append(EQUAL_MARK);
                    sb = simple ? sb.append(value) : sb.append(URLEncoder.encode(value, charset));
                }
            }
        }
        String encodeParams = sb.toString();
        log.info("编码参数为：{}", encodeParams);
        return encodeParams;
    }

//    public static String encodeParams(Map<String, String> queryParams) throws UnsupportedEncodingException {
//        return encodeParams(queryParams, false, UTF_8);
//    }
//
//    public static String encodeParamsSimple(Map<String, String> queryParams) throws UnsupportedEncodingException {
//        return encodeParams(queryParams, true, null);
//    }

    /**
     * @param url         网络路径基地址 get请求需要，post请求不需要
     * @param queryParams 查询参数
     * @param simple      参数值是否需要编码
     * @param charset     编码
     * @return 返回url和所有参数编码后的字符串
     * @throws Exception Exception
     */
    private static String toQueryString(String url, Map<String, String> queryParams, boolean simple, String charset) throws Exception {
        StringBuilder query = new StringBuilder();
        if (StringUtils.isNotBlank(url)) {
            //get 请求
            query.append(url);
            if (queryParams != null) {
                if (!url.contains(QUESTION_MARK)) {
                    query.append(QUESTION_MARK);
                }
                if (url.contains(AND_MARK)) {
                    query.append(AND_MARK);
                }
                query.append(encodeParams(queryParams, simple, charset));
            }
        } else {
            //post 请求
            query.append(encodeParams(queryParams, simple, charset));
        }
        String queryString = query.toString();
        log.info("查询字符串为：{}", queryString);
        return queryString;
    }

    /**
     * 简单的拼装get请求
     */
    public static String toGetQueryStringSimple(String url, Map<String, String> queryParams) throws Exception {
        return toQueryString(url, queryParams, true, null);
    }

    /**
     * utf-8编码的拼装get请求
     */
    public static String toGetDefaultQueryString(String url, Map<String, String> queryParams) throws Exception {
        return toQueryString(url, queryParams, false, UTF_8);
    }

    public static String toGetQueryString(String url, Map<String, String> queryParams, String charset) throws Exception {
        return toQueryString(url, queryParams, false, charset);
    }

    /**
     * utf-8编码的拼装post请求参数
     */
    public static String toPostQueryString(Map<String, String> queryParams) throws Exception {
        return toQueryString(null, queryParams, false, UTF_8);
    }

    private static String toPostQueryString(Map<String, String> queryParams, String charset) throws Exception {
        return toQueryString(null, queryParams, false, charset);
    }

    public static String postDefault(String urlStr, Map<String, String> queryParams) throws Exception {
        return post(urlStr, queryParams, UTF_8);
    }

    private static String post(String urlStr, Map<String, String> queryParams, String charset) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod(POST);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        connection.connect();
        DataOutputStream out = null;
        BufferedReader reader = null;
        try {
            out = new DataOutputStream(connection.getOutputStream());
            if (queryParams != null) {
                out.writeBytes(toPostQueryString(queryParams, charset));
            }
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            String result = sb.toString();
            log.info("返回结果为：{}", result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
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

    public static String getDefault(String urlStr, Map<String, String> queryParams) throws Exception {
        return get(urlStr, queryParams, false, UTF_8);
    }

    public static String getSimple(String urlStr, Map<String, String> queryParams) throws Exception {
        return get(urlStr, queryParams, true, null);
    }

    private static String get(String urlStr, Map<String, String> queryParams, boolean simple, String charset) throws Exception {
        String queryString;
        if (simple) {
            queryString = toGetQueryStringSimple(urlStr, queryParams);
        } else {
            queryString = toGetQueryString(urlStr, queryParams, charset);
        }
        URL url = new URL(queryString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = null;
        try {
            connection.setDoInput(true);
            connection.setRequestMethod(GET);
            connection.setUseCaches(false);
            connection.connect();
            StringBuilder sb = new StringBuilder();
            String line;
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String result = sb.toString();
            log.info("返回结果为：{}", result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
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
}
