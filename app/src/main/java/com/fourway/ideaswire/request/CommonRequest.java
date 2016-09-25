package com.fourway.ideaswire.request;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fourway.ideaswire.request.helper.CustomRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Vikas on 6/30/2016.
 */

public abstract class CommonRequest {
    /*------------------------- Constant Fields Definition ----------------------------*/
    private static final String LOGIN_REQUEST_URL = "http://4ways:4wayssecret@ec2-52-40-240-149.us-west-2.compute.amazonaws.com:8899"
            + "/4ways/userauth/oauth/token" + "?grant_type=password";
    private static final String SIGN_UP_REQUEST_URL =
            "http://ec2-52-66-99-210.ap-south-1.compute.amazonaws.com:8081" +
                    "/4ways/api/user/register";
    private static final String GET_PROFILE_DATA_URL =
            "http://ec2-52-66-99-210.ap-south-1.compute.amazonaws.com:8091" +
                    "/4ways/api/profile/search/profile/content";

    public enum RequestType  {
        COMMON_REQUEST_LOGIN,
        COMMON_REQUEST_FORGET_PASSWORD,
        COMMON_REQUEST_SIGNUP,
        COMMON_REQUEST_CREATE_PROFILE,

        COMMON_REQUEST_GET_PROFILE_STATUS,

        COMMON_REQUEST_GET_PROFILE, COMMON_REQUEST_GET_USER_PROFILE_LIST, COMMON_REQUEST_END // WARNING: Add all request types above this line only
    }

    public enum ResponseCode  {
        COMMON_RES_SUCCESS,
        COMMON_RES_INTERNAL_ERROR,
        COMMON_RES_CONNECTION_TIMEOUT,
        COMMON_RES_FAILED_TO_CONNECT,
        COMMON_RES_IMAGE_NOT_FOUND,
        COMMON_RES_SERVER_ERROR_WITH_MESSAGE,
        COMMON_RES_PROFILE_DATA_NO_CONTENT,
        COMMON_RES_FAILED_TO_UPLOAD,

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
    private Map<String, String> mPostHeader;
    private JSONObject mJSONParams;
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
    public void setPostHeader (Map<String, String> h){mPostHeader = h;}

    public void setParam (JSONObject params){
        mJSONParams = params;
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
            case COMMON_REQUEST_GET_PROFILE:
                url = GET_PROFILE_DATA_URL;
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
            jsObjRequest = new CustomRequest(mURL, null, listner, errorListner){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return ((mPostHeader != null)? mPostHeader : super.getHeaders());
                }
            };
            requestQueue.add(jsObjRequest);
        }
        else
        {
            jsObjRequest = new CustomRequest(Request.Method.POST, mURL, mParams, listner, errorListner) {
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return ((mPostHeader != null)? mPostHeader : super.getHeaders());
                }
            };
            requestQueue.add(jsObjRequest);
        }
    }
}