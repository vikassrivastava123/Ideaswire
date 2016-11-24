package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.GetUserProfileRequestData;
import com.fourway.ideaswire.data.SessionManager;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.GetUserProfileRequest;

import java.util.HashMap;


public class SplashScreen extends Activity implements GetUserProfileRequest.GetUserProfilesResponseCallback{

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        setContentView(R.layout.activity_splash_screen);
        startApp();
        if (!session.isLoggedIn()){
            startApp();
        }else {
            HashMap<String, String> user = session.getUserDetails();


            loginUi.mLogintoken = user.get(SessionManager.KEY_LOGIN_TOKEN);
            GetUserProfileRequestData data = new GetUserProfileRequestData(loginUi.mLogintoken);
            GetUserProfileRequest request = new GetUserProfileRequest(this, data, this);
            request.executeRequest();
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
    public void onResponse(CommonRequest.ResponseCode res, GetUserProfileRequestData data) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            loginUi.mProfileList = data.getProfileList();
            finish();
        }else {
            session.logoutUser();
            finish();
        }
    }

}
