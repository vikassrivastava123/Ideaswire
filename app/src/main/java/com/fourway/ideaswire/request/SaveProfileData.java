package com.fourway.ideaswire.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.request.helper.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_CONNECTION_TIMEOUT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_FAILED_TO_CONNECT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_INTERNAL_ERROR;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SUCCESS;

/**
 * Created by Vikas on 8/25/2016.
 */

public class SaveProfileData {
    private Context mContext;
    private Profile mProfile;
    private String mAppKey;
    private String mProfileId;
    private Boolean isUpdateRequest;

    public interface SaveProfileResponseCallback {
        void onProfileSaveResponse(CommonRequest.ResponseCode res, Profile data);
    }
    private SaveProfileResponseCallback mSaveProfileResponseCallback;

    public SaveProfileData (Context c, Profile p, String app_token, SaveProfileResponseCallback cb,Boolean isUpdate_Request){
        mContext = c; mProfile = p; mAppKey = app_token; mProfileId = mProfile.getProfileId();
        mSaveProfileResponseCallback = cb;
        isUpdateRequest = isUpdate_Request;
    }

    public void executeRequest () {
        String url = "http://ec2-52-40-240-149.us-west-2.compute.amazonaws.com:8090/4ways//api/image/content/add"; //update

        if (isUpdateRequest) {
            url = "http://ec2-52-40-240-149.us-west-2.compute.amazonaws.com:8090/4ways//api/image/content/add/temp";
        }

        JSONObject js = new JSONObject();
        try {
            js.put("contentType", "Text");
            JSONObject jsData = new JSONObject();
            jsData.put(Profile.PROFILE_DATA_JSON_TAG, mProfile.createJSONArray());
            js.put(Profile.PROFILE_PAGE_ARRAY_NAME, jsData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == 1){
                        mSaveProfileResponseCallback.onProfileSaveResponse(COMMON_RES_SUCCESS, mProfile);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onErrorHandler(error);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,listner, errorListner) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("authorization", "bearer " + mAppKey);
                headers.put("x-image-profile-id", mProfileId);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(jsonObjReq);
    }

    public void onErrorHandler(VolleyError error) {
        String errorMsg = VolleyErrorHelper.getMessage(error, mContext);
        Log.v("onErrorHandler","error is" + error);
        CommonRequest.ResponseCode resCode = COMMON_RES_INTERNAL_ERROR;
        if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
            resCode = COMMON_RES_CONNECTION_TIMEOUT;
            mSaveProfileResponseCallback.onProfileSaveResponse (resCode, mProfile);
        }
        if (errorMsg == VolleyErrorHelper.COMMON_NETWORK_ERROR_TIMEOUT)
        {
            resCode = COMMON_RES_CONNECTION_TIMEOUT;
        }
        else if (errorMsg == VolleyErrorHelper.COMMON_NETWORK_ERROR_UNKNOWN){
            resCode = COMMON_RES_INTERNAL_ERROR;
        }
        else if (errorMsg == VolleyErrorHelper.COMMON_NETWORK_ERROR_NO_INTERNET){
            resCode = COMMON_RES_FAILED_TO_CONNECT;
        }
        else
        {
            resCode = COMMON_RES_SERVER_ERROR_WITH_MESSAGE;
            mProfile.setErrorMessage(errorMsg);
        }
        mSaveProfileResponseCallback.onProfileSaveResponse (resCode, mProfile);
    }
}
