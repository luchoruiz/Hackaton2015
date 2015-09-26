package com.android.desafioaudionews.volley;

import com.android.desafioaudionews.models.RequestResponse;
import com.android.desafioaudionews.utils.StreamToStringConverter;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
             String UTF8String = StreamToStringConverter.convert(response.data);
             String json = new String(UTF8String);

            jsonObj = new JSONObject(json);
            T requestResponse = (T) new RequestResponse(jsonObj, tag, response.statusCode);
            return Response.success(requestResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.error(new ParseError(new Exception()));
    }



}
