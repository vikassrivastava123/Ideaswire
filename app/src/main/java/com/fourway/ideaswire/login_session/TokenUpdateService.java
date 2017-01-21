package com.fourway.ideaswire.login_session;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.fourway.ideaswire.data.LoginData;
import com.fourway.ideaswire.data.SessionManager;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.RefreshTokenRequest;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by 4way on 19-01-2017.
 */

public class TokenUpdateService extends Service implements RefreshTokenRequest.RefreshTokenCallBack {
    int milliseconds ;
    private static Timer timer = new Timer();
    Context context;

    SessionManager session;
    public TokenUpdateService() {

    }

    public TokenUpdateService(Context context, int time) {

        this.context = context;
        milliseconds = (time - (5*60)) *1000;
        session = new SessionManager(context);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        startService(milliseconds);
    }

    private void startService(int milliseconds) {
        timer.schedule(new mainTask(), milliseconds);
    }



    private class mainTask extends TimerTask{

        @Override
        public void run() {

            HashMap<String, String> user = session.getUserDetails();
            String refreshToken = user.get(SessionManager.KEY_REFRESH_TOKEN);
            LoginData data = new LoginData(null, null);
            data.setRefreshToken(refreshToken);
            RefreshTokenRequest request = new RefreshTokenRequest(context,data,TokenUpdateService.this);
            request.executeRequest();
        }
    }


    @Override
    public void onRefreshTokenResponse(CommonRequest.ResponseCode res, LoginData mLoginData) {
        switch (res) {
            case COMMON_RES_SUCCESS:

                String mLoginToken = mLoginData.getAccessToken();
                String mRefreshToken = mLoginData.getRefreshToken();
                int refreshTime = (mLoginData.getAccessTokenExpiry() - (5*60)) *1000;
                session.createLoginSession(mLoginToken, mRefreshToken);
                startService(refreshTime);
                break;
            default:
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
