package com.android.desafioaudionews.volley;

import android.content.Context;

import com.android.desafioaudionews.models.RequestResponse;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;

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


public void sendLogin(Response.Listener<RequestResponse> listenerOK, Response.ErrorListener listenerError, String UserMail,
        String UserPass, int rememberMe) {

       /* String url = UrlConstants.LOGIN_URL;
        String tag = Const.LOGIN_TAG;
        int method = Request.Method.POST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", UserMail);
        params.put("password", UserPass);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded");
        header.put("Cookie","DEVICE_TYPE=ANDROID");

        executeRequest(listenerOK, listenerError, url, tag, method, params, header);*/
        }



private void executeRequest(Response.Listener<RequestResponse> listenerOK, Response.ErrorListener listenerError,String sessionId,String url,String tag, int method){
        HashMap<String, String> params = new HashMap<>();

        Map<String, String> header = new HashMap<>();
        header.put("Cookie", "sessionid=" + sessionId);
        header.put("Content-Type", "application/x-www-form-urlencoded");
        header.put("DEVICE_TYPE", "ANDROID");

        CustomRequest<RequestResponse> customR = new CustomRequest<RequestResponse>(url, method, params, header,
        listenerOK, listenerError, tag);

        int socketTimeout = 30000;
/** The default number of retries */
final int DEFAULT_MAX_RETRIES = 1;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        customR.setRetryPolicy(policy);
        customR.setTag(tag);
        mRequestQueue.add(customR);
        }


private void executeRequest(Response.Listener<RequestResponse> listenerOK, Response.ErrorListener listenerError,String url,String tag, int method,HashMap<String, String> params,Map<String, String> header){

        CustomRequest<RequestResponse> customR = new CustomRequest<RequestResponse>(url, method, params, header,
        listenerOK, listenerError, tag);

        int socketTimeout = 30000;
/** The default number of retries */
final int DEFAULT_MAX_RETRIES = 1;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        customR.setRetryPolicy(policy);
        customR.setTag(tag);
        mRequestQueue.add(customR);
        }

        }
