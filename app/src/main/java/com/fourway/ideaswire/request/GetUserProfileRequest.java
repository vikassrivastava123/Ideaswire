package com.fourway.ideaswire.request;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fourway.ideaswire.data.GetUserProfileRequestData;
import com.fourway.ideaswire.data.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SUCCESS;

/**
 * Created by Vikas on 8/27/2016.
 */

public class GetUserProfileRequest{
    private String GET_USER_PROFILe_LIST_URL =
            "http://ec2-52-66-99-210.ap-south-1.compute.amazonaws.com:8091" +
                    "/4ways/api/profile/search/user/profile/all";

    GetUserProfileRequestData mRequestData;
    Context mContext;
    public GetUserProfileRequest (Context c, GetUserProfileRequestData data){
        mContext = c; mRequestData = data;
        Map<String, String> param = new HashMap<>();
        param.put("authorization", "bearer " + mRequestData.getAccessToken());
    }

    public void executeRequest(){
        String url = GET_USER_PROFILe_LIST_URL;
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
        JSONArray profileList = res.getJSONArray("Data");
        int size = profileList.length();
        for (int i=0; i<size; i++){
            JSONObject profile = profileList.getJSONObject(i);
            String id = profile.getString("id");
            String templateId = profile.getString("templateId");/* //Comment: Not needed as of now
            JSONObject data = profile.getJSONObject(CREATE_PROFILE_JSON_TAG_ATTR);
            String p_name = data.getString(CREATE_PROFILE_JSON_TAG_NAME);
            String p_cat = data.getString(CREATE_PROFILE_JSON_TAG_CATEGORY);
            String p_type = data.getString(CREATE_PROFILE_JSON_TAG_TYPE);
            String p_dept = data.getString(CREATE_PROFILE_JSON_TAG_DEPT);*/
            String p_img_url = profile.getString("downloadUrl");

            Profile p = new Profile(id, Profile.getTemplateIdFromString(templateId));
            p.setImageUrl(p_img_url);
            mRequestData.addProfile(p);
        }
    }


    public void onResponseHandler(JSONObject response) {
        try {
            parseAndAddProfileResults(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //mSearchResponseCallback.onSearchResponse(COMMON_RES_SUCCESS, mImageData);
    }


    public void onErrorHandler(VolleyError error) {
        Toast.makeText(mContext, "failed", Toast.LENGTH_SHORT);
    }
}
