package com.fourway.ideaswire.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.SignUpData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.LoginRequest;
import com.fourway.ideaswire.data.LoginData;
import com.fourway.ideaswire.request.SignUpRequest;

public class MainActivity extends AppCompatActivity implements LoginRequest.LoginResponseCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void TestLogin (View v){
        LoginData data = new LoginData("ramji", "ramji");
        LoginRequest req = new LoginRequest(this, data, this);
        req.executeRequest();
    }


    @Override
    public void onLoginResponse(CommonRequest.ResponseCode res, LoginData data) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }
}
