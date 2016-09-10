package com.fourway.ideaswire.request;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fourway.ideaswire.data.CreateProfileData;
import com.fourway.ideaswire.data.UpdateImageRequestData;
import com.fourway.ideaswire.request.helper.CommonFileUpload;
import com.fourway.ideaswire.request.helper.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_CONNECTION_TIMEOUT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_FAILED_TO_CONNECT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_FAILED_TO_UPLOAD;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_INTERNAL_ERROR;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SUCCESS;

/**
 * Created by Vikas on 8/22/2016.
 */

public class CreateProfileRequest implements UpdateImageRequest.UpdateImageResponseCallback{
    private Context mContext;
    private CreateProfileData mProfileRequestData;
    private String mProfileId;
    CommonFileUpload mFileUpload;

    public static final String CREATE_PROFILE_JSON_TAG_NAME = "ProfileName";
    public static final String CREATE_PROFILE_JSON_TAG_CATEGORY = "ProfileCategory";
    public static final String CREATE_PROFILE_JSON_TAG_TYPE = "ProfileType";
    public static final String CREATE_PROFILE_JSON_TAG_DEPT = "ProfileDept";
    public static final String CREATE_PROFILE_JSON_TAG_ATTR = "imageAttributes";

    @Override
    public void onUpdateImageResponse(CommonRequest.ResponseCode res, UpdateImageRequestData data) {
        if (res != COMMON_RES_SUCCESS) {
            mProfileRequestData.setErrorMessage(data.getErrorMessage());
            mCreateProfileResponseCallback.onCreateProfileResponse(res, mProfileRequestData);
        }
        else {
            if (mProfileRequestData.getVideoFile() != null){
                uploadVideoFile();
            }
            else
            {
                mProfileRequestData.setErrorMessage(data.getErrorMessage());
                mCreateProfileResponseCallback.onCreateProfileResponse(res, mProfileRequestData);
            }
        }
    }

    public interface CreateProfileResponseCallback {
        void onCreateProfileResponse(CommonRequest.ResponseCode res, CreateProfileData data);
    }
    private CreateProfileResponseCallback mCreateProfileResponseCallback;

    public CreateProfileRequest(Context context, CreateProfileData data, CreateProfileResponseCallback cb) {
        mProfileRequestData = data;
        mContext = context;
        mCreateProfileResponseCallback = cb;
    }

    public void executeRequest () {
        String url = "http://ec2-52-40-240-149.us-west-2.compute.amazonaws.com:8090/4ways/api/image/profile/create";

        JSONObject js = new JSONObject();
        try {
            js.put("templateId", "t1");
            js.put("name", mProfileRequestData.getProfileName());

            JSONObject profileAttributes = new JSONObject();
            profileAttributes.put("ProfileName", mProfileRequestData.getProfileName());
            profileAttributes.put("ProfileCategory", mProfileRequestData.getProfileCategory());
            profileAttributes.put("ProfileType", "JPEG"); //TODO: Hardcoded JPEG type sent to server
            profileAttributes.put("ProfileDept", mProfileRequestData.getProfileDepartment());

            js.put("imageAttributes", profileAttributes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mProfileId = response.getString("data");
                    mProfileRequestData.setProfileId (mProfileId);
                    uploadProfileImage();
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
                headers.put("authorization", "bearer "+ mProfileRequestData.getAccessToken());
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(jsonObjReq);
    }

    private void uploadProfileImage (){
        UpdateImageRequestData data = new UpdateImageRequestData(
                mProfileRequestData.getAccessToken(),
                mProfileRequestData.getProfileId(),
                mProfileRequestData.getProfileName(),
                mProfileRequestData.getImageData());
        UpdateImageRequest req = new UpdateImageRequest(mContext,data, this);
        req.executeRequest();
    }

    private void uploadVideoFile(){
        String url = "http://ec2-52-40-240-149.us-west-2.compute.amazonaws.com:8090/4ways/api/image/content/upload/document";

        Response.Listener<NetworkResponse> listner = new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
               mCreateProfileResponseCallback.onCreateProfileResponse(COMMON_RES_FAILED_TO_UPLOAD, mProfileRequestData);
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onErrorResponse(error);
            }
        };


        mFileUpload = new CommonFileUpload(mContext,
                mProfileRequestData.getVideoFile(),
                CommonFileUpload.FileType.COMMON_UPLOAD_FILE_TYPE_VIDEO,
                mProfileRequestData.getProfileName(),
                url,
                mProfileRequestData.getAccessToken(),
                listner,
                errorListner);
        mFileUpload.setFileTag("video-data");

        Map<String, String> params = new HashMap<>();
        params.put("authorization", "bearer "+ mProfileRequestData.getAccessToken());
        params.put("x-image-profile-id", mProfileRequestData.getProfileId());
        mFileUpload.setHeader(params);
        mFileUpload.uploadFile();
    }

    public void onErrorHandler(VolleyError error) {
        String errorMsg = VolleyErrorHelper.getMessage(error, mContext);
        Log.v("onErrorHandler","error is" + error);
        CommonRequest.ResponseCode resCode = COMMON_RES_INTERNAL_ERROR;
        if (error.networkResponse.statusCode == 404) {
            resCode = COMMON_RES_CONNECTION_TIMEOUT;
            mCreateProfileResponseCallback.onCreateProfileResponse (resCode, mProfileRequestData);
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
            mProfileRequestData.setErrorMessage(errorMsg);
        }
        mCreateProfileResponseCallback.onCreateProfileResponse (resCode, mProfileRequestData);
    }
}
