package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.GetUserProfileRequestData;
import com.fourway.ideaswire.data.LoginData;
import com.fourway.ideaswire.data.SessionManager;
import com.fourway.ideaswire.request.CommonRequest.ResponseCode;
import com.fourway.ideaswire.request.GetUserProfileRequest;
import com.fourway.ideaswire.request.RefreshTokenRequest;

import java.util.HashMap;

import static com.fourway.ideaswire.ui.loginUi.mLogintoken;
import static com.fourway.ideaswire.ui.loginUi.mRefreshToken;


public class SplashScreen extends Activity implements GetUserProfileRequest.GetUserProfilesResponseCallback,RefreshTokenRequest.RefreshTokenCallBack{

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* TokenUpdateService updateService = new TokenUpdateService(getApplicationContext(), 10*1000);
        updateService.onCreate();*/

        session = new SessionManager(this);
        setContentView(R.layout.activity_splash_screen);
        if (!session.isLoggedIn()){
            startApp();
        }else {
            HashMap<String, String> user = session.getUserDetails();


            mLogintoken = user.get(SessionManager.KEY_LOGIN_TOKEN);
            GetUserProfileRequestData data = new GetUserProfileRequestData(mLogintoken);
            GetUserProfileRequest request = new GetUserProfileRequest(this, data, this);
            request.executeRequest();
            /*String refreshToken = user.get(SessionManager.KEY_REFRESH_TOKEN);
            LoginData data = new LoginData(null, null);
            data.setRefreshToken(refreshToken);
            RefreshTokenRequest request = new RefreshTokenRequest(this,data,this);
            request.executeRequest();*/
        }
    }

    void startApp()
    {
        new Thread(){
            public void run(){
                try {
                    sleep(2000);
                    startActivity(new Intent(SplashScreen.this,HomepageBeforeLogin.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @Override
    public void onResponse(ResponseCode res, GetUserProfileRequestData data) {

        switch (res){
            case COMMON_RES_SUCCESS:
                loginUi.mProfileList = data.getProfileList();
                startActivity(new Intent(SplashScreen.this,HomepageBeforeLogin.class));
                finish();

                break;
           /* case COMMON_RES_SERVER_ERROR_WITH_MESSAGE:
                HashMap<String, String> user = session.getUserDetails();
                String refreshToken = user.get(SessionManager.KEY_REFRESH_TOKEN);
                RefreshTokenRequest request = new RefreshTokenRequest(this,refreshToken,this);
                request.executeRequest();

                break;*/
            default:
                session.logoutUser();
                finish();
        }


    }

    @Override
    public void onRefreshTokenResponse(ResponseCode res, LoginData mLoginData) {
        switch(res){
            case COMMON_RES_SUCCESS:
                mLogintoken = mLoginData.getAccessToken();
                mRefreshToken = mLoginData.getRefreshToken();
                session.createLoginSession(mLogintoken,mRefreshToken);
                GetUserProfileRequestData data = new GetUserProfileRequestData(mLogintoken);
                GetUserProfileRequest request = new GetUserProfileRequest(this, data, this);
                request.executeRequest();
                break;
            case COMMON_RES_INTERNAL_ERROR:
                //onLoginFailed("Login Failed ,Please try again");
                break;
            case COMMON_RES_CONNECTION_TIMEOUT:
                //onLoginFailed("Connection Timeout !");
                break;
            case COMMON_RES_FAILED_TO_CONNECT:
                //onLoginFailed("Please check internet connection !");
                break;
            default:
                //onLoginFailed("Login Failed ,Please try again");
                break;
        }
    }
}
