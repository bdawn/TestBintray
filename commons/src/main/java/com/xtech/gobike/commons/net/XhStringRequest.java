package com.xtech.gobike.commons.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.xtech.gobike.commons.helper.LogUtil;
import com.xtech.gobike.commons.net.protocol.BaseResponse;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础请求
 */
public class XhStringRequest<T extends BaseResponse> extends Request<T> {
    private final String TAG = this.getClass().getName();
    private final Class<T> clazz;
    private final Map<String, String> params;
    private final Response.Listener<T> listener;

    public XhStringRequest(int method, String url, Map<String, String> params, Class<T> clazz,
                           final XhRequestListener<T> XhRequestListener) {

        super(method, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                XhRequestListener.onRequestFail("101", error.getMessage());
            }
        });
        this.params = params;
        this.clazz = clazz;

        listener = new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                if (response.getSuccess()) {
                    XhRequestListener.onRequestSuccess(response);

                } else {
                    XhRequestListener.onRequestFail(response.getErrorCode(), response.getErrorCode());
                }
            }
        };

        String msg = "url:" + url + "," + "RequestBeanBase" + (params == null ? "" : JSON.toJSONString(params));
        LogUtil.i(TAG, "request: " + msg);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return this.params == null ? null : JSON.toJSONString(this.params).getBytes("utf-8");
        } catch (UnsupportedEncodingException var2) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of params using %s", "utf-8");
            return null;
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        //SessionCookies.getInstance().addSessionCookie(headers);
        return headers;
    }

    @Override
    protected void deliverResponse(T response) {
        System.out.println("deliverResponse: " + response.toString());
        listener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        //SessionCookies.getInstance().checkSessionCookie(response.headers);
        try {
            LogUtil.i(TAG, "parseNetworkResponse: " + response.toString());
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(JSON.parseObject(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }
}
