package com.fourway.ideaswire.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.LoginData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.LoginRequest;

public class MainActivity extends AppCompatActivity implements LoginRequest.LoginResponseCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), HomeScreenFirstLogin.class);
        startActivity(intent);
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
