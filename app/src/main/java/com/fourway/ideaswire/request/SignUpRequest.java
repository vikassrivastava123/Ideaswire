package com.fourway.ideaswire.request;

import android.content.Context;

import com.android.volley.VolleyError;
import com.fourway.ideaswire.data.SignUpData;
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
 * Created by Vikas on 7/4/2016.
 */

public class SignUpRequest extends CommonRequest {
    private static final String JSON_FIELD_USERNAME = "userId";
    private static final String JSON_FIELD_PASSWORD = "password";
    private static final String JSON_FIELD_FIRST_NAME = "firstName";
    private static final String JSON_FIELD_LAST_NAME = "lastName";
    private static final String JSON_FIELD_EMAIL_ID = "mailId";
    private static final String JSON_FIELD_MOBILE_NUMBER = "mobileNumber";
    Context mContext;

    private Map<String, String> mParams;
    private SignUpData mSignUpData;

    public interface SignUpResponseCallback {
        void onSignUpResponse(ResponseCode res, SignUpData data);
    }
    private SignUpResponseCallback mSignUpResponseCallback;

    public SignUpRequest(Context context, SignUpData data, SignUpResponseCallback cb) {
        super(context, RequestType.COMMON_REQUEST_SIGNUP,
                CommonRequestMethod.COMMON_REQUEST_METHOD_POST, null);

        mSignUpData = data;
        mParams = new HashMap<>();
        mParams.put(JSON_FIELD_USERNAME, data.getUserName());
        mParams.put(JSON_FIELD_PASSWORD, data.getPassword());
        mParams.put(JSON_FIELD_FIRST_NAME, data.getFirstName());
        mParams.put(JSON_FIELD_LAST_NAME, data.getLastName());
        mParams.put(JSON_FIELD_EMAIL_ID, data.getEmailId());
        mParams.put(JSON_FIELD_MOBILE_NUMBER, data.geMobileNumber());
        super.setParam(mParams);

        mSignUpResponseCallback = cb;
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            //TODO: Need to change parsing as per response from server
            mSignUpData.seResponseMessage(response.getString("message"));
            mSignUpResponseCallback.onSignUpResponse (ResponseCode.COMMON_RES_SUCCESS, mSignUpData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorHandler(VolleyError error) {
        String errorMsg = VolleyErrorHelper.getMessage(error, mContext);
        ResponseCode resCode = COMMON_RES_INTERNAL_ERROR;
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
            mSignUpData.setErrorMessage(errorMsg);
        }
        mSignUpResponseCallback.onSignUpResponse(resCode, mSignUpData);
    }
}
