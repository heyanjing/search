package com.search.cap.main;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientTool {

    public class Method {
        public static final String GET = "get";
        public static final String POST = "post";
    }

    public static String invokeHttp(String method, String uri, Map<String, String> params)
            throws IOException {
        if (method.equals(HttpClientTool.Method.GET)) {
            if (params == null) {
                return invokeHttpGet(uri);
            } else {
                return invokeHttpGet(uri, params);
            }
        } else if (method.equals(HttpClientTool.Method.POST)) {
            return invokeHttpPost(uri, params);
        }
        return null;
    }

    public static String invokeHttp(String method, URI uri, Map<String, String> params)
            throws IOException, URISyntaxException {
        if (method.equals(HttpClientTool.Method.GET)) {
            if (params == null) {
                return invokeHttpGet(uri);
            } else {
                return invokeHttpGet(uri, params);
            }
        } else if (method.equals(HttpClientTool.Method.POST)) {
            return invokeHttpPost(uri, params);
        }
        return null;
    }

    public static String invokeHttp(String method, String scheme, String host, int port, String path, Map<String, String> params)
            throws ClientProtocolException, URISyntaxException, IOException {
        if (method.equals(HttpClientTool.Method.GET)) {
            return invokeHttpGet(scheme, host, port, path, params);
        } else if (method.equals(HttpClientTool.Method.POST)) {
            return invokeHttpPost(scheme, host, port, path, params);
        }
        return null;
    }

    public static final String invokeHttpGet(String uri)
            throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(uri);
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } finally {
            release(client, response);
        }
    }

    public static final String invokeHttpGet(String uri, Map<String, String> params)
            throws ClientProtocolException, IOException {
        String executeUri = uri + "?";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            executeUri += entry.getKey() + "=" + entry.getValue() + "&";
        }
        executeUri = executeUri.substring(0, executeUri.length() - 1);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(executeUri);
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } finally {
            release(client, response);
        }
    }

    public static final String invokeHttpGet(URI uri)
            throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(uri);
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } finally {
            release(client, response);
        }
    }

    public static final String invokeHttpGet(URI uri, Map<String, String> params)
            throws URISyntaxException, ClientProtocolException, IOException {
        URIBuilder builder = new URIBuilder()
                .setScheme(uri.getScheme())
                .setHost(uri.getHost())
                .setPort(uri.getPort())
                .setPath(uri.getPath());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.setParameter(entry.getKey(), entry.getValue());
        }
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(builder.build());
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } finally {
            release(client, response);
        }
    }

    public static final String invokeHttpGet(String scheme, String host, int port, String path, Map<String, String> params)
            throws URISyntaxException, ClientProtocolException, IOException {
        URIBuilder builder = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPort(port)
                .setPath(path);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.setParameter(entry.getKey(), entry.getValue());
        }
        URI uri = builder.build();
        return invokeHttpGet(uri);
    }

    public static final String invokeHttpPost(String uri, Map<String, String> params)
            throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(generatePostEntity(params));
        try {
            response = client.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity);
        } finally {
            release(client, response);
        }
    }

    public static final String invokeHttpPost(URI uri, Map<String, String> params)
            throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(generatePostEntity(params));
        try {
            response = client.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity);
        } finally {
            release(client, response);
        }
    }

    public static final String invokeHttpPost(String scheme, String host, int port, String path, Map<String, String> params)
            throws URISyntaxException, ClientProtocolException, IOException {
        URIBuilder builder = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPort(port)
                .setPath(path);
        URI uri = builder.build();
        return invokeHttpPost(uri, params);
    }

    private static UrlEncodedFormEntity generatePostEntity(Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return new UrlEncodedFormEntity(pairs, Consts.UTF_8);
    }

    private static void release(CloseableHttpClient client, CloseableHttpResponse response)
            throws IOException {
        if (client != null) {
            client.close();
        }
        if (response != null) {
            response.close();
        }
    }

}
