package com.xtech.gobike.commons.net;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.xtech.gobike.commons.exception.XhException;
import com.xtech.gobike.commons.helper.LogUtil;
import com.xtech.gobike.commons.log.Log;
import com.xtech.gobike.commons.log.LogFactory;
import com.xtech.gobike.commons.net.protocol.BaseResponse;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 对象请求类
 */
public class XhObjectRequest<T extends BaseResponse> extends
        BaseObjectRequest<T> {

    private final String TAG = this.getClass().getName();

    private static final Log log = LogFactory.getLog(XhObjectRequest.class);
    private Class<T> resultClass;
    private final XhRequestListener<T> requestListener;
    private String url;

    @Override
    public String getUrl() {
        return url;
    }

    public Class<T> getResultClass() {
        return resultClass;
    }

    public XhRequestListener<T> getRequestListener() {
        return requestListener;
    }


    /**
     * @param method          请求方式：Method.POST, Method.GET, Method.DELETE,
     *                        Method.DEPRECATED_GET_OR_POST, Method.PUT
     * @param requestBean     请求的Bean
     * @param resultClazz     返回的结果类型
     * @param requestListener 请求的监听
     * @param tag             设置的标签
     */
    public XhObjectRequest(int method, String url, Object requestBean,
                           Class<T> resultClazz, final XhRequestListener<T> requestListener, Object tag) {
        this(method, url, requestBean, resultClazz, requestListener);
        setTag(tag);
    }

    public XhObjectRequest(final int method, final String url, final Object requestBean,
                            Class<T> result, final XhRequestListener<T> requestListener) {

        super(method, url,
                requestBean == null ? null : JSON.toJSONString(requestBean),
                new Response.Listener<T>() {
                    @Override
                    public void onResponse(T response) {
                        if (response == null) {
                            return;
                        }
                        if (response.getSuccess()) {
                            requestListener.onRequestSuccess(response);
                        } else {// 请求失败
                            requestListener.onRequestFail(response.getErrorCode(), response.getErrorMsg());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error == null || error.networkResponse == null) {// 未知错误
                            requestListener.onRequestFail("1", (error == null || error.getMessage() == null) ? "未知异常" : error.getMessage());
                            log.error(new XhException(error));
                        }
                    }
                });
        this.url = url;
        this.resultClass = result;
        this.requestListener = requestListener;

        String msg = "url:" + url + "," + "RequestBeanBase" + (requestBean == null ? "" : JSON.toJSONString(requestBean));
        LogUtil.i(TAG, "request: " + msg);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("version", "1");
        headers.put("os", "android_22");
        headers.put("screensize", "1920x1080");
        headers.put("accessToken", TokenHelper.getInstance().getAccessToken());
        return headers;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String jsonString;
        try {
            jsonString = new String(response.data, "UTF-8");
            LogUtil.i(TAG, "response: " + jsonString);
            T bean = JSON.parseObject(jsonString, resultClass);
            return Response.success(bean,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
    }
}
