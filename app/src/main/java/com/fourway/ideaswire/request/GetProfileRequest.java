package com.fourway.ideaswire.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.fourway.ideaswire.data.CreateProfileData;
import com.fourway.ideaswire.data.GetProfileRequestData;
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
import static com.fourway.ideaswire.request.CreateProfileRequest.CREATE_PROFILE_JSON_TAG_CATEGORY;
import static com.fourway.ideaswire.request.CreateProfileRequest.CREATE_PROFILE_JSON_TAG_DEPT;
import static com.fourway.ideaswire.request.CreateProfileRequest.CREATE_PROFILE_JSON_TAG_NAME;
import static com.fourway.ideaswire.request.CreateProfileRequest.CREATE_PROFILE_JSON_TAG_TYPE;

/**
 * Created by Vikas on 8/26/2016.
 */

public class GetProfileRequest extends CommonRequest {
    public interface GetProfileResponseCallback {
        void onGetProfileResponse(CommonRequest.ResponseCode res, GetProfileRequestData data);
    }
    private GetProfileResponseCallback mGetProfileResponseCallback;

    private GetProfileRequestData mRequestData;
    private Context mContext;
    public GetProfileRequest(Context context, GetProfileRequestData data, GetProfileResponseCallback cb) {
        super(context, RequestType.COMMON_REQUEST_GET_PROFILE, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mRequestData = data; mGetProfileResponseCallback = cb; mContext = context;
        Map <String, String> param = new HashMap<>();
        param.put("x-image-profile-id", mRequestData.getProfileId());
        param.put("authorization", "bearer " + mRequestData.getAppKey());
        setParam(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        JSONObject profile = null;
        try {
            profile = response.getJSONObject("data");
            String id = profile.getString("id");
            String templateId = profile.getString("templateId");
            JSONObject data = profile.getJSONObject(CREATE_PROFILE_JSON_TAG_ATTR);
            String p_name = data.getString(CREATE_PROFILE_JSON_TAG_NAME);
            String p_cat = data.getString(CREATE_PROFILE_JSON_TAG_CATEGORY);
            String p_type = data.getString(CREATE_PROFILE_JSON_TAG_TYPE);
            String p_dept = data.getString(CREATE_PROFILE_JSON_TAG_DEPT);
            String p_img_url = profile.getString("downloadUrl");

            Profile p = new Profile(id, Profile.getTemplateIdFromString(templateId));
            p.setProfileName(p_name);
            p.setProfileCategory(p_cat);
            p.setProfileType(p_type);
            p.setProfileDepartment(p_dept);
            p.setImageUrl(p_img_url);

            JSONArray pages = profile.getJSONArray(Profile.PROFILE_PAGE_ARRAY_NAME);
            p.addAllPagesToList(pages);
            mRequestData.setProfile(p);
            mGetProfileResponseCallback.onGetProfileResponse(COMMON_RES_SUCCESS, mRequestData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorHandler(VolleyError error) {
        String errorMsg = VolleyErrorHelper.getMessage(error, mContext);
        Log.v("onErrorHandler","error is" + error);
        CommonRequest.ResponseCode resCode = COMMON_RES_INTERNAL_ERROR;
        if (error.networkResponse.statusCode == 404) {
            resCode = COMMON_RES_CONNECTION_TIMEOUT;
            mGetProfileResponseCallback.onGetProfileResponse (resCode, mRequestData);
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
        mGetProfileResponseCallback.onGetProfileResponse (resCode, mRequestData);
    }
}
