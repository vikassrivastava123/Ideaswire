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
import com.fourway.ideaswire.data.GetUserProfileRequestData;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.request.helper.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_CONNECTION_TIMEOUT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_FAILED_TO_CONNECT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_INTERNAL_ERROR;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SUCCESS;
import static com.fourway.ideaswire.request.CreateProfileRequest.CREATE_PROFILE_JSON_TAG_ATTR;
import static com.fourway.ideaswire.request.CreateProfileRequest.CREATE_PROFILE_JSON_TAG_NAME;

/**
 * Created by Vikas on 8/27/2016.
 */

public class GetUserProfileRequest{
    private String GET_USER_PROFILE_LIST_URL =
            "http://ec2-52-66-99-210.ap-south-1.compute.amazonaws.com:8091" +
                    "/4ways/api/profile/search/user/profile/all";

    public interface GetUserProfilesResponseCallback {
        void onResponse(CommonRequest.ResponseCode res, GetUserProfileRequestData data);
    }
    private GetUserProfilesResponseCallback mOnUserProfileCallback;


    GetUserProfileRequestData mRequestData;
    Context mContext;
    public GetUserProfileRequest (Context c, GetUserProfileRequestData data, GetUserProfilesResponseCallback cb){
        mContext = c; mRequestData = data; mOnUserProfileCallback = cb;
        Map<String, String> param = new HashMap<>();
        param.put("authorization", "bearer " + mRequestData.getAccessToken());
    }

    public void executeRequest(){
        String url = GET_USER_PROFILE_LIST_URL;
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

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url, null,listner, errorListner) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("authorization", "bearer "+ mRequestData.getAccessToken());
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(jsonObjReq);
    }

    private void parseAndAddProfileResults (JSONObject res) throws JSONException {
        JSONArray profileList = res.getJSONArray("data");
        int size = profileList.length();
        for (int i=0; i<size; i++){
            JSONObject profile = profileList.getJSONObject(i);
            String id = profile.getString("id");
            String templateId = profile.getString("templateId");
            JSONObject data = profile.getJSONObject(CREATE_PROFILE_JSON_TAG_ATTR);
            String p_name = data.getString(CREATE_PROFILE_JSON_TAG_NAME);/* //Comment: Not needed as of now
            String p_cat = data.getString(CREATE_PROFILE_JSON_TAG_CATEGORY);
            String p_type = data.getString(CREATE_PROFILE_JSON_TAG_TYPE);
            String p_dept = data.getString(CREATE_PROFILE_JSON_TAG_DEPT);*/
            String p_img_url = profile.getString("downloadUrl");

            Profile p = new Profile(id, Profile.getTemplateIdFromString(templateId));
            p.setImageUrl(p_img_url);
            p.setProfileName(p_name);
            mRequestData.addProfile(p);
        }
    }


    public void onResponseHandler(JSONObject response) {
        try {
            parseAndAddProfileResults(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mOnUserProfileCallback.onResponse(COMMON_RES_SUCCESS, mRequestData);
    }


    public void onErrorHandler(VolleyError error) {
        String errorMsg = VolleyErrorHelper.getMessage(error, mContext);
        Log.v("onErrorHandler","error is" + error);
        CommonRequest.ResponseCode resCode = COMMON_RES_INTERNAL_ERROR;
        if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
            resCode = COMMON_RES_CONNECTION_TIMEOUT;
            mOnUserProfileCallback.onResponse (resCode, mRequestData);
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
            mRequestData.setErrorMessage(errorMsg);
        }
        mOnUserProfileCallback.onResponse (resCode, mRequestData);
    }
}
