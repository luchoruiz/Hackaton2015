package com.android.desafioaudionews.volley;

import android.content.Context;

import com.android.desafioaudionews.api.UrlConstants;
import com.android.desafioaudionews.models.RequestResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucho on 25/09/2015.
 */
public class RequestConnector  {
    Context context;
    private RequestQueue mRequestQueue;

    public RequestConnector(Context context) {
        this.context = context;
        mRequestQueue = VolleyManager.getInstance(context).getRequestQueue();
    }

    public void stopRequest(Context context, String tag) {
        this.context = context;
        mRequestQueue = VolleyManager.getInstance(context).getRequestQueue();
        mRequestQueue.cancelAll(tag);
    }


    private void executeRequest(Response.Listener<RequestResponse> listenerOK, Response.ErrorListener listenerError,String url,String tag, int method){
        HashMap<String, String> params = new HashMap<>();
        Map<String, String> header = new HashMap<>();
        CustomRequest<RequestResponse> customR = new CustomRequest<RequestResponse>(url, method, params, header,
        listenerOK, listenerError, tag);

        //int socketTimeout = 30000;
        /** The default number of retries */
        //final int DEFAULT_MAX_RETRIES = 1;
        //RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DEFAULT_MAX_RETRIES,
        //DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        //customR.setRetryPolicy(policy);
        customR.setTag(tag);
        mRequestQueue.add(customR);
    }

    public void getNotes(Response.Listener<RequestResponse> listenerOK, Response.ErrorListener listenerError, String tag){
        executeRequest(listenerOK, listenerError, UrlConstants.LAST_NOTES, tag, Request.Method.GET);
    }




}
