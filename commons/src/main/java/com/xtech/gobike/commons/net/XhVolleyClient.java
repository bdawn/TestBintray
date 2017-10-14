package com.xtech.gobike.commons.net;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.xtech.gobike.commons.helper.CommonContext;


/**
 * 处理client
 */
public class XhVolleyClient {

    private static XhVolleyClient mClient;
    RequestQueue mQueue;

    public static synchronized XhVolleyClient getInstance() {
        if (mClient == null) {
            mClient = new XhVolleyClient(CommonContext.getContext());
        }
        return mClient;
    }

    private XhVolleyClient(Context context) {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        HTTPSTrustManager.allowAllSSL();
        mQueue = Volley.newRequestQueue(context,new OkHttpStack(client));
        //mQueue = Volley.newRequestQueue(context);
    }

    /**
     * 添加一个请求
     *
     * @param request 请求对象
     */
    public void addRequest(Request<?> request) {
        int socketTimeout = 15000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        mQueue.add(request);
    }

    /**
     * 取消任务
     *
     * @param tag 请求的标签
     */
    public void cancelAll(Object tag) {
        mQueue.cancelAll(tag);
    }

}
