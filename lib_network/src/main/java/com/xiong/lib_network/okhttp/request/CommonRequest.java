package com.xiong.lib_network.okhttp.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author vision
 * @function build the request
 */
public class CommonRequest {
    /**
     * create the key-value Request
     *
     * @param url
     * @param params
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params) {
        return createPostRequest(url, params, null);
    }

    /**
     * 可以带请求头的Post请求
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params, RequestParams headers) {
        FormBody.Builder mFormBodyBuild = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                mFormBodyBuild.add(entry.getKey(), entry.getValue());
            }
        }
        //添加请求头
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeaderBuild.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody mFormBody = mFormBodyBuild.build();
        Headers mHeader = mHeaderBuild.build();
        Request request = new Request.Builder().url(url).
                post(mFormBody).
                headers(mHeader)
                .build();
        return request;
    }

    /**
     * ressemble the params to the url
     *
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params) {

        return createGetRequest(url, params, null);
    }

    /**
     * 可以带请求头的Get请求
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params, RequestParams headers) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        //添加请求头
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeaderBuild.add(entry.getKey(), entry.getValue());
            }
        }
        Headers mHeader = mHeaderBuild.build();
        return new Request.Builder().
                url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .get()
                .headers(mHeader)
                .build();
    }

    /**
     * 文件上传请求
     *
     * @return
     */
    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");

    public static Request createMultiPostRequest(String url, RequestParams params) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof File) {
                    String TYPE = "application/octet-stream";
                    File file = (File) entry.getValue();
                    RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE),file);
                    requestBody.addFormDataPart(entry.getKey(),file.getName(),fileBody);
                }
            }
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                requestBody.addFormDataPart(entry.getKey(),entry.getValue());
            }
        }
        return new Request.Builder().url(url).post(requestBody.build()).build();
    }
}