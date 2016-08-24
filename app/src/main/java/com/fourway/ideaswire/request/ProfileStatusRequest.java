package com.fourway.ideaswire.request;

import android.content.Context;

import com.android.volley.VolleyError;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.ProfileStatusRequestData;
import com.fourway.ideaswire.data.SearchProfileData;
import com.fourway.ideaswire.request.helper.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_CONNECTION_TIMEOUT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_FAILED_TO_CONNECT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_IMAGE_NOT_FOUND;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_INTERNAL_ERROR;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE;

/**
 * Created by Vikas on 8/23/2016.
 */

public class ProfileStatusRequest extends CommonRequest {
    private Context mContext;
    private ProfileStatusRequestData mRequestData;

    public interface ProfileStatusResultCallback {
        void onProfileStatusResponse(CommonRequest.ResponseCode res, ProfileStatusRequestData data);
    }
    private ProfileStatusResultCallback mProfileStatuscb;



    public ProfileStatusRequest(Context context, ProfileStatusRequestData data, ProfileStatusResultCallback cb) {
        super(context, RequestType.COMMON_REQUEST_GET_PROFILE_STATUS, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mRequestData = data;
        mProfileStatuscb = cb;

        Map<String, String> param = new HashMap<>();
        param.put("content-type", "multipart/form-data");
        param.put("authorization", "bearer " + mRequestData.getToken());
        param.put("x-image-profile-id", mRequestData.getProfileId());
        setParam(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            String status = response.getString("message");
            mRequestData.setStatus(Profile.getStatusFromString(status));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mProfileStatuscb.onProfileStatusResponse(ResponseCode.COMMON_RES_SUCCESS, mRequestData);
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        onVolleyErrorHandler(error);
    }


    public void onVolleyErrorHandler(VolleyError error) {
        String errorMsg = VolleyErrorHelper.getMessage(error, mContext);
        CommonRequest.ResponseCode resCode = COMMON_RES_INTERNAL_ERROR;
        if (error.networkResponse.statusCode == 404) {
            resCode = COMMON_RES_IMAGE_NOT_FOUND;
            mProfileStatuscb.onProfileStatusResponse (resCode, mRequestData);
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
        mProfileStatuscb.onProfileStatusResponse (resCode, mRequestData);
    }
}
