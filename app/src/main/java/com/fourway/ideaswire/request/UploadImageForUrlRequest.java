package com.fourway.ideaswire.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fourway.ideaswire.data.UpdateImageRequestData;
import com.fourway.ideaswire.data.UploadImageForUrlData;
import com.fourway.ideaswire.request.helper.CommonFileUpload;
import com.fourway.ideaswire.request.helper.VolleyErrorHelper;

import org.json.JSONArray;
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

/**
 * Created by Vikas on 9/9/2016.
 */

public class UploadImageForUrlRequest {
    private Context mContext;
    private UploadImageForUrlData mImageData;
    private CommonFileUpload mFileUpload;

    public interface UploadImageForUrlCallback {
        void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data);
    }
    private UploadImageForUrlCallback mUploadImageForUrlCallback;

    public UploadImageForUrlRequest (Context c, UploadImageForUrlData img_data, UploadImageForUrlCallback cb) {
        mContext = c; mUploadImageForUrlCallback = cb; mImageData = img_data;
    }


    public void executeRequest(){
        String url = "http://ec2-52-40-240-149.us-west-2.compute.amazonaws.com:8090/4ways/api/image/content/upload/document";

        Response.Listener<NetworkResponse> listner = new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    String jsonStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    JSONObject json = new JSONObject(jsonStr);
                    int status = json.getInt("status");
                    if (status == 1){
                        JSONObject data = json.getJSONObject("data");
                        String url = data.getString("url");
                        mImageData.setResponseUrl(url);
                        mUploadImageForUrlCallback.onUploadImageForUrlResponse(CommonRequest.ResponseCode.COMMON_RES_SUCCESS, mImageData);
                    }
                    else
                    {
                        mUploadImageForUrlCallback.onUploadImageForUrlResponse(COMMON_RES_FAILED_TO_UPLOAD, mImageData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mUploadImageForUrlCallback.onUploadImageForUrlResponse(COMMON_RES_FAILED_TO_UPLOAD, mImageData);
                } catch (UnsupportedEncodingException e) {
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


        mFileUpload = new CommonFileUpload(mContext,
                mImageData.getImageData(),
                CommonFileUpload.FileType.COMMON_UPLOAD_FILE_TYPE_IMAGE,
                mImageData.getImageName(),
                url,
                mImageData.getAccessToken(),
                listner,
                errorListner);
        mFileUpload.setFileTag("content-document");

        Map<String, String> params = new HashMap<>();
        params.put("authorization", "bearer "+ mImageData.getAccessToken());
        params.put("x-image-profile-id", mImageData.getCampaignId());
        params.put("x-sequence-number", String.valueOf(mImageData.getUniqueSequenceNumber()));
        params.put("x-content-id", "no_id_available"); //Server do not accept null for this field. so a dummy id is needed
        mFileUpload.setHeader(params);

        mFileUpload.uploadFile();
    }

    public void onErrorHandler(VolleyError error) {
        String errorMsg = VolleyErrorHelper.getMessage(error, mContext);
        Log.v("onErrorHandler","error is" + error);
        CommonRequest.ResponseCode resCode = COMMON_RES_INTERNAL_ERROR;
        if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
            resCode = COMMON_RES_CONNECTION_TIMEOUT;
            mUploadImageForUrlCallback.onUploadImageForUrlResponse (resCode, mImageData);
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
        mUploadImageForUrlCallback.onUploadImageForUrlResponse (resCode, mImageData);
    }
}
