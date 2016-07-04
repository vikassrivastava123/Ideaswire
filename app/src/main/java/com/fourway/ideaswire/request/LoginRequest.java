package com.fourway.ideaswire.request;

import android.content.Context;

import com.android.volley.VolleyError;
import com.fourway.ideaswire.data.LoginData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikas on 6/30/2016.
 */

public class LoginRequest extends CommonRequest{
    private static final String JSON_FIELD_USERNAME = "username";
    private static final String JSON_FIELD_PASSWORD = "password";
    private Map<String, String> mParams;
    private LoginData mLoginData;


    public interface LoginResponseCallback {
        void onLoginResponse(ResponseCode res, LoginData data);
    }
    private LoginResponseCallback mLoginResponseCallback;

    public LoginRequest (Context context, LoginData data, LoginResponseCallback cb)
    {
        super(context, RequestType.COMMON_REQUEST_LOGIN, CommonRequestMethod.COMMON_REQUEST_METHOD_POST, null);
        mLoginData = data;
        mParams = new HashMap<>();
        mParams.put(JSON_FIELD_USERNAME, data.getUserName());
        mParams.put(JSON_FIELD_PASSWORD, data.getPassword());
        super.setParam(mParams);

        mLoginResponseCallback = cb;
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            //TODO: Need to change parsing as per response from server
            mLoginData.setAccessToken(response.getString("access_token"));
            mLoginData.setRefreshToken(response.getString("refresh_token"));
            mLoginResponseCallback.onLoginResponse (ResponseCode.COMMON_RES_SUCCESS, mLoginData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mLoginResponseCallback.onLoginResponse (ResponseCode.COMMON_RES_INTERNAL_ERROR, mLoginData);
    }
}
