package com.fourway.ideaswire.request;

/**
 * Created by Vikas on 7/1/2016.
 */

public class LoginData {
    private String mUsername;
    private String mPassword;
    private String mAccessToken;
    private String mRefreshToken;

    public LoginData(String u_name, String u_password){
        mUsername = u_name; mPassword = u_password;
    }

    public String getUserName (){
        return mUsername;
    }

    public String getPassword (){
        return mPassword;
    }

    public String getAccessToken (){
        return mAccessToken;
    }

    public String getRefreshToken (){
        return mRefreshToken;
    }

    public void setAccessToken (String token){
        mAccessToken = token;
    }

    public void setRefreshToken (String token){
        mRefreshToken = token;
    }
}
