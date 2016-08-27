package com.fourway.ideaswire.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fourway.ideaswire.data.CreateProfileData;
import com.fourway.ideaswire.data.UpdateImageRequestData;
import com.fourway.ideaswire.request.helper.CommonFileUpload;
import com.fourway.ideaswire.request.helper.VolleyErrorHelper;

import java.util.HashMap;
import java.util.Map;

import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_CONNECTION_TIMEOUT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_FAILED_TO_CONNECT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_INTERNAL_ERROR;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE;

/**
 * Created by Vikas on 8/27/2016.
 */

public class UpdateImageRequest {

    private Context mContext;
    private UpdateImageRequestData mImageData;
    private CommonFileUpload mFileUpload;

    public interface UpdateImageResponseCallback {
        void onUpdateImageResponse(CommonRequest.ResponseCode res, UpdateImageRequestData data);
    }
    private UpdateImageResponseCallback mUpdateImageResponseCallback;

    public UpdateImageRequest (Context c, UpdateImageRequestData img_data, UpdateImageResponseCallback cb) {
        mContext = c; mUpdateImageResponseCallback = cb; mImageData = img_data;
    }


    public void executeRequest(){
        String url = "http://ec2-52-40-240-149.us-west-2.compute.amazonaws.com:8090/4ways/api/image/profile/upload/image";

        Response.Listener<NetworkResponse> listner = new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
                mUpdateImageResponseCallback.onUpdateImageResponse(CommonRequest.ResponseCode.COMMON_RES_SUCCESS, mImageData);
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onErrorHandler(error);
            }
        };


        mFileUpload = new CommonFileUpload(mContext,
                mImageData.getImageData(),
                CommonFileUpload.FileType.COMMON_UPLOAD_FILE_TYPE_IMAGE,
                mImageData.getProfileName(),
                url,
                mImageData.getAccessToken(),
                listner,
                errorListner);
        mFileUpload.setFileTag("image");

        Map<String, String> params = new HashMap<>();
        params.put("content-type", "multipart/form-data");
        params.put("authorization", "bearer "+ mImageData.getAccessToken());
        params.put("x-image-profile-id", mImageData.getProfileId());

        mFileUpload.setHeader(params);

        mFileUpload.uploadFile();
    }

    public void onErrorHandler(VolleyError error) {
        String errorMsg = VolleyErrorHelper.getMessage(error, mContext);
        Log.v("onErrorHandler","error is" + error);
        CommonRequest.ResponseCode resCode = COMMON_RES_INTERNAL_ERROR;
        if (error.networkResponse.statusCode == 404) {
            resCode = COMMON_RES_CONNECTION_TIMEOUT;
            mUpdateImageResponseCallback.onUpdateImageResponse (resCode, mImageData);
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
            mImageData.setErrorMessage(errorMsg);
        }
        mUpdateImageResponseCallback.onUpdateImageResponse (resCode, mImageData);
    }
}