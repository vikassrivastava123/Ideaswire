package com.fourway.ideaswire.request;

import android.content.Context;
import android.util.Base64;
import android.util.JsonReader;
import android.util.JsonWriter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikas on 6/30/2016.
 */

public abstract class CommonRequest {
    /*------------------------- Constant Fields Definition ----------------------------*/
    private static final String DOMAIN = "http://ec2-52-40-240-149.us-west-2.compute.amazonaws.com:8080";

    private static final String LOGIN_REQUEST_URL = "http://4ways:4wayssecret@ec2-52-40-240-149.us-west-2.compute.amazonaws.com:8899"
            + "/4ways/userauth/oauth/token" + "?grant_type=password";
    private static final String SIGN_UP_REQUEST_URL = DOMAIN + "/4ways/api/user/register";

    public enum RequestType  {
        COMMON_REQUEST_LOGIN,
        COMMON_REQUEST_FORGET_PASSWORD,
        COMMON_REQUEST_SIGNUP,

        COMMON_REQUEST_END // WARNING: Add all request types above this line only
    }

    public enum ResponseCode  {
        COMMON_RES_SUCCESS,
        COMMON_RES_INTERNAL_ERROR,
        COMMON_RES_CONNECTION_TIMEOUT,
        COMMON_RES_FAILED_TO_CONNECT,
        COMMON_RES_SERVER_ERROR_WITH_MESSAGE,

        COMMON_REQUEST_END // WARNING: Add all request types above this line only
    }

    public enum CommonRequestMethod {
        COMMON_REQUEST_METHOD_GET,
        COMMON_REQUEST_METHOD_POST,

        COMMON_REQUEST_METHOD_END
    }

    /*---------------------------- Member variables -----------------------------------*/
    private String mURL;
    private CommonRequestMethod mMethod;
    private Map<String, String> mParams;
    private RequestType mRequestType;
    private Context mContext;


    public CommonRequest (Context context,RequestType type,
                          CommonRequestMethod reqMethod, Map<String, String> param){
        mContext = context; mRequestType = type; mMethod = reqMethod; mParams = param;
        mURL = getRequestTypeURL (mRequestType);
    }

    public void setURL (String url){
        mURL = url;
    }

    public void setHttpRequestMethod (CommonRequestMethod method){
        mMethod = method;
    }

    public void setParam (Map<String, String> params){
        mParams = params;
    }

    public abstract void onResponseHandler (JSONObject response);
    public abstract void onErrorHandler (VolleyError error);

    public String getRequestTypeURL (RequestType type){
        String url = null;
        switch (type){
            case COMMON_REQUEST_LOGIN:
                url = LOGIN_REQUEST_URL;
                break;
            case COMMON_REQUEST_SIGNUP:
                url = SIGN_UP_REQUEST_URL;
                break;
        }
        return url;
    }

    public String getURL (){
        return mURL;
    }

    public void executeRequest (){
        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onResponseHandler(response);
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onErrorHandler(error);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        CustomRequest jsObjRequest;
        if (mMethod == CommonRequestMethod.COMMON_REQUEST_METHOD_GET){
            jsObjRequest = new CustomRequest(mURL, null, listner, errorListner);
            requestQueue.add(jsObjRequest);
        }
        else
        {
            jsObjRequest = new CustomRequest(Request.Method.POST, mURL, mParams, listner, errorListner) {
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            requestQueue.add(jsObjRequest);
        }
    }
}