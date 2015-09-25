package com.android.desafioaudionews.volley;

import com.android.desafioaudionews.models.RequestResponse;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Lucho on 25/09/2015.
 */
public class CustomRequest <T> extends Request<T> {
    private JSONObject jsonObj = new JSONObject();
    private final Map<String, String> headers;
    private final Map<String, String> params;
    private final Response.Listener<T> listener;
    private final String tag;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param headers Map of request headers
     */
    public CustomRequest(String url, int method, Map<String, String> params, Map<String, String> headers,
                         Response.Listener<T> listener, Response.ErrorListener errorListener, String tag) {
        super(method, url, errorListener);
        this.headers = headers;
        this.listener = listener;
        this.params = params;
        this.tag = tag;
    }

    @Override
    protected Map<String, String> getParams() {
        return this.params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        if (listener != null) {
            listener.onResponse(response);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            jsonObj = new JSONObject(json);
            T requestResponse = (T) new RequestResponse(jsonObj, tag, response.statusCode);
            return Response.success(requestResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }


    private String getSessionId(String cookie) {
        String sessionId = "";
        if (cookie != null && !cookie.isEmpty()) {
            String[] setCookie = cookie.split(";");
            for (String t : setCookie) {
                String[] value = t.split("=");
                if (value[0].equals("sessionid")) {
                    sessionId = value[1];
                }

            }
        }
        return sessionId;
    }
}
