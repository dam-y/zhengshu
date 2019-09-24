package com.cxria.ceshi.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xinwu-yang
 * @ClassName: HttpXml4Client
 * @Description: HttpClient4.5 发送http请求
 * @date 2016年1月4日
 */
public class HttpXml4Client {
    private static final Logger LOGGER = LogManager.getLogger(HttpXml4Client.class.getName());

    public static String get(String url, Map<String, String> headers) {
        if (StringUtils.isEmpty(url)) {
            LOGGER.error("URL is Empty");
            return "";
        }
        HttpGet httpGet = new HttpGet(url);
        setHeaders(httpGet, headers);
        setTimeout(httpGet, 15000, 15000, 15000);
        return getResponse(httpGet);
    }

    public static String post(String url, StringEntity se, Map<String, String> headers) {
        if (StringUtils.isEmpty(url)) {
            LOGGER.error("URL is Empty");
            return "";
        }
        HttpPost httpPost = new HttpPost(url);
        setHeaders(httpPost, headers);
        setTimeout(httpPost, 15000, 15000, 15000);
        try {
            httpPost.setEntity(se);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "";
        }
        return getResponse(httpPost);
    }

    public static String post(String url, List<NameValuePair> params, Map<String, String> headers) {
        if (StringUtils.isEmpty(url)) {
            LOGGER.error("URL is Empty");
            return "";
        }
        HttpPost httpPost = new HttpPost(url);
        setHeaders(httpPost, headers);
        setTimeout(httpPost, 15000, 15000, 15000);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return getResponse(httpPost);
    }

    public static String delete(String url, Map<String, String> headers) {
        if (StringUtils.isEmpty(url)) {
            LOGGER.error("URL is Empty");
            return "";
        }
        HttpDelete delete = new HttpDelete(url);
        setHeaders(delete, headers);
        setTimeout(delete, 15000, 15000, 15000);
        return getResponse(delete);
    }

    private static String getResponse(HttpRequestBase httpRequestBase) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String body;
        try (CloseableHttpResponse resp = httpClient.execute(httpRequestBase)) {
            HttpEntity httpEntity = resp.getEntity();
            body = EntityUtils.toString(httpEntity, "UTF-8");
            // 关闭流
            EntityUtils.consume(httpEntity);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return "";
        }
        return body;
    }

    private static void setHeaders(HttpRequestBase httpRequestBase, Map<String, String> headers) {
        if (headers != null) {
            Set<String> headerKeys = headers.keySet();
            for (String headerKey : headerKeys) {
                httpRequestBase.setHeader(headerKey, headers.get(headerKey));
            }
        }
    }

    private static void setTimeout(HttpRequestBase httpRequestBase, int connectTimeout, int connectionRequestTimeout, int socketTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout).build();
        httpRequestBase.setConfig(requestConfig);
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}