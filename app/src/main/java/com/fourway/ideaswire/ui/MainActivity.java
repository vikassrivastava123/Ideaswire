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

public class MainActivity extends AppCompatActivity implements SignUpRequest.SignUpResponseCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void TestLogin (View v){
        SignUpData data = new SignUpData("test", "test", "f_name", "l_name", "abc@abc.com", "9818869437");
        SignUpRequest req = new SignUpRequest(this, data, this);
        req.executeRequest();
    }


    @Override
    public void onSignUpResponse(CommonRequest.ResponseCode res, SignUpData data) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }
}
