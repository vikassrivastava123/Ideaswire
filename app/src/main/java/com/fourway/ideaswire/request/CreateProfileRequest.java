package com.fourway.ideaswire.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fourway.ideaswire.data.CreateProfileData;
import com.fourway.ideaswire.request.helper.CommonFileUpload;
import com.fourway.ideaswire.request.helper.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_CONNECTION_TIMEOUT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_FAILED_TO_CONNECT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_INTERNAL_ERROR;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE;

/**
 * Created by Vikas on 8/22/2016.
 */

public class CreateProfileRequest{
    private Context mContext;
    private CreateProfileData mProfileRequestData;
    private String mProfileId;
    CommonFileUpload mFileUpload;

    public interface CreateProfileResponseCallback {
        void onCreateProfileResponse(CommonRequest.ResponseCode res, CreateProfileData data);
    }
    private CreateProfileResponseCallback mCreateProfileResponseCallback;

    public CreateProfileRequest(Context context, CreateProfileData data) {
        mProfileRequestData = data;
        mContext = context;
    }

    public void executeRequest () {
        String url = "http://ec2-52-40-240-149.us-west-2.compute.amazonaws.com:8090/4ways/api/image/profile/create";

        JSONObject js = new JSONObject();
        try {
            js.put("authorization", "bearer " + mProfileRequestData.getAccessToken());
            js.put("templateId", mProfileRequestData.getTemplateId());

            JSONObject imageAttributes = new JSONObject();
            imageAttributes.put("imagename", mProfileRequestData.getImageName());
            imageAttributes.put("imagecategory", mProfileRequestData.getImageCategory());
            imageAttributes.put("imagetype", "JPEG"); //TODO: Hardcoded JPEG type sent to server
            imageAttributes.put("imagedept", mProfileRequestData.getImageDepartment());

            js.put("imageAttributes", imageAttributes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mProfileId = response.getString("data");
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
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(jsonObjReq);
    }

    private void uploadProfileImage (){
        String url = "http://ec2-52-40-240-149.us-west-2.compute.amazonaws.com:8090/4ways/api/image/profile/upload/image";

        Response.Listener<NetworkResponse> listner = new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
                mCreateProfileResponseCallback.onCreateProfileResponse(CommonRequest.ResponseCode.COMMON_RES_SUCCESS, mProfileRequestData);
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onErrorHandler(error);
            }
        };


        mFileUpload = new CommonFileUpload(mContext,
                mProfileRequestData.getImageData(),
                CommonFileUpload.FileType.COMMON_UPLOAD_FILE_TYPE_IMAGE,
                mProfileRequestData.getImageName(),
                url,
                mProfileRequestData.getAccessToken(),
                listner,
                errorListner);
        mFileUpload.setFileTag("image");

        Map<String, String> params = new HashMap<>();
        params.put("content-type", "multipart/form-data");
        params.put("authorization", "bearer "+ mProfileRequestData.getAccessToken());
        params.put("x-image-profile-id", mProfileId);

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
