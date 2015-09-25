package com.android.desafioaudionews.models;

import org.json.JSONObject;

/**
 * Created by Lucho on 25/09/2015.
 */
public class RequestResponse {
    private JSONObject jsonResponse;
    private String tag;
    private int httpStatusCode;

    public RequestResponse(JSONObject jsonResponse, String tag, int statusCode) {
        super();
        this.jsonResponse = jsonResponse;
        this.tag = tag;
        this.httpStatusCode = statusCode;
    }

    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public String getTag() {
        return tag;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
