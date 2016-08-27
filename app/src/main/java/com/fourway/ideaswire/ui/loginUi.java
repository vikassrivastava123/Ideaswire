package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.GetUserProfileRequestData;
import com.fourway.ideaswire.data.LoginData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.GetUserProfileRequest;
import com.fourway.ideaswire.request.LoginRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;



public class loginUi extends Activity implements LoginRequest.LoginResponseCallback{

    private static final String TAG = "loginUi";
    private static final int REQUEST_SIGNUP = 0;
    public static String mLogintoken = null;
    static ProgressDialog mProgressDialog;
    Button login_button;
    @InjectView(R.id.input_username)
    EditText _usernameText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.link_signup)
    Button _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ui);

         _loginButton = (Button) findViewById(R.id.btn_login);
        _loginButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                login();
            }
        });
                ButterKnife.inject(this);

        _passwordText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), signupUi.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        _loginButton.setEnabled(false);

        mProgressDialog = new ProgressDialog(loginUi.this,
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Authenticating...");
        mProgressDialog.show();

        String email = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        doLogin();

        // TODO: Implement your own authentication logic here.

      /*  new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        mProgressDialog.dismiss();
                    }
                }, 3000);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private void requestProfileList (){
        GetUserProfileRequestData data = new GetUserProfileRequestData(mLogintoken);
        GetUserProfileRequest request = new GetUserProfileRequest(this, data);
        request.executeRequest();
    }

    public void onLoginSuccess() {

        _loginButton.setEnabled(true);
        requestProfileList();
      //  finish();

        Intent intent = new Intent(getApplicationContext(), HomeScreenFirstLogin.class);
        startActivityForResult(intent, REQUEST_SIGNUP);

    }

    public void onLoginFailed(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public void doLogin() {

        String username = String.valueOf(_usernameText.getText());
        String password = String.valueOf(_passwordText.getText());

        Log.d(TAG, "username = "+username+",  Password = "+password);


        LoginData data = new LoginData(username,password);
        LoginRequest req = new LoginRequest(loginUi.this, data, this);
        req.executeRequest();
    }

    @Override
    public void onLoginResponse(CommonRequest.ResponseCode responseCode, LoginData data) {

        mProgressDialog.dismiss();

        Log.d(TAG, "responseCode ==" + responseCode);
        Log.d(TAG, "Error Message =="+data.getErrorMessage());

        switch(responseCode){
            case COMMON_RES_SUCCESS:
                mLogintoken = data.getAccessToken();
                Log.v(TAG,"LoginToken" + mLogintoken);
                onLoginSuccess();
                break;
            case COMMON_RES_INTERNAL_ERROR:
                onLoginFailed("Login Failed ,Please try again");
                break;
            case COMMON_RES_CONNECTION_TIMEOUT:
                onLoginFailed("Connection Timeout !");
                break;
            case COMMON_RES_FAILED_TO_CONNECT:
                onLoginFailed("Please check internet connection !");
                break;
            default:
                onLoginFailed("Login Failed ,Please try again");
                break;
        }

    }
}
