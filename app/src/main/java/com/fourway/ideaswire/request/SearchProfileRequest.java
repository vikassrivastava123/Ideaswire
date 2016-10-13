package com.fourway.ideaswire.request;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.SearchProfileData;
import com.fourway.ideaswire.request.helper.CommonFileUpload;
import com.fourway.ideaswire.request.helper.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_CONNECTION_TIMEOUT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_FAILED_TO_CONNECT;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_IMAGE_NOT_FOUND;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_INTERNAL_ERROR;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SUCCESS;
import static com.fourway.ideaswire.request.CreateProfileRequest.CREATE_PROFILE_JSON_TAG_ATTR;
import static com.fourway.ideaswire.request.CreateProfileRequest.CREATE_PROFILE_JSON_TAG_CATEGORY;
import static com.fourway.ideaswire.request.CreateProfileRequest.CREATE_PROFILE_JSON_TAG_DEPT;
import static com.fourway.ideaswire.request.CreateProfileRequest.CREATE_PROFILE_JSON_TAG_NAME;
import static com.fourway.ideaswire.request.CreateProfileRequest.CREATE_PROFILE_JSON_TAG_TYPE;

/**
 * Created by Vikas on 7/23/2016.
 */

public class SearchProfileRequest {
    private static final String IMAGE_UPLOAD_SEARCH_PROFILE_URL =
            "http://ec2-52-66-99-210.ap-south-1.compute.amazonaws.com:8091" +
            "/4ways/api/profile/search/profile/search";
    Context mContext;
    SearchProfileData mImageData;
    CommonFileUpload mFileUpload;

    public interface SearchResponseCallback {
        void onSearchResponse(CommonRequest.ResponseCode res, SearchProfileData data);
    }
    private SearchResponseCallback mSearchResponseCallback;



    public SearchProfileRequest(Context context, SearchProfileData data, SearchResponseCallback cb){
        mContext = context; mImageData = data; mSearchResponseCallback = cb;
    }


    public void executeRequest(){
        String url = IMAGE_UPLOAD_SEARCH_PROFILE_URL;
        Response.Listener<NetworkResponse> listner = new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
                JSONObject obj = null;
                try {
                    String str = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    obj = new JSONObject(str);
                    onResponseHandler(obj);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
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


        mFileUpload = new CommonFileUpload(mContext,
                mImageData.getImageFile(),
                CommonFileUpload.FileType.COMMON_UPLOAD_FILE_TYPE_IMAGE,
                mImageData.getFileName(),
                url,
                mImageData.getAppKey(),
                listner,
                errorListner);
        mFileUpload.uploadFile();
    }


    public void onResponseHandler(JSONObject response) throws JSONException {
        parseAndAddProfileResults(response);
        mSearchResponseCallback.onSearchResponse(COMMON_RES_SUCCESS, mImageData);
    }

    private void parseAndAddProfileResults (JSONObject res) throws JSONException {
        JSONArray profileList = res.getJSONArray("data");
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
            mImageData.addProfile(p);
        }
    }

    public void onErrorHandler(VolleyError error) {
        String errorMsg = VolleyErrorHelper.getMessage(error, mContext);
        CommonRequest.ResponseCode resCode = COMMON_RES_INTERNAL_ERROR;
        if (error.networkResponse!= null && error.networkResponse.statusCode == 404) {
            resCode = COMMON_RES_IMAGE_NOT_FOUND;
            mSearchResponseCallback.onSearchResponse (resCode, mImageData);
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
        mSearchResponseCallback.onSearchResponse (resCode, mImageData);
    }

}
