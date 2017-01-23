package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.SignUpData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.SignUpRequest;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class signupUi extends Activity implements SignUpRequest.SignUpResponseCallback{

    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name)
    EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.input_phonenumber) EditText _phonenumberText;
    @InjectView(R.id.confirm_password) EditText _confirm_password;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    @InjectView(R.id.link_login)
    TextView _loginLink;
    //Spinner spinner;
    String count_code;
    private String[] states;
    String mobile_num_new;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_ui);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        ButterKnife.inject(this);

        states = getResources().getStringArray(R.array.CountryCodes);
//        imgs = getResources().obtainTypedArray(R.array.countries_flag_list);

//        image = (ImageView) findViewById(R.id.country_image);
       // spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, states){

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(Color.WHITE);

                return v;
            }


            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);

                Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
                ((TextView) v).setTypeface(externalFont);
                v.setBackgroundColor(Color.WHITE);

                return v;
            }
        };
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        /*spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                image.setImageResource(imgs.getResourceId(
//                        spinner.getSelectedItemPosition(), -1));

                count_code = spinner.getSelectedItem().toString();
                Log.d("country is",count_code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });*/
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(signupUi.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirmPassword = _confirm_password.getText().toString();

        doSignUp();


        // TODO: Implement your own signup logic here.

       /*
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
                */
    }


    private void doSignUp(){

        String username = String.valueOf(_nameText.getText());
        String email = String.valueOf(_emailText.getText());
        String password = String.valueOf(_passwordText.getText());
        String mobile_num = String.valueOf(_phonenumberText.getText());
        String[] Code_is = count_code.split(",");
        String Code  = "+"+Code_is[0];
        mobile_num_new = Code+mobile_num;
        Log.d("here",mobile_num_new);
       SignUpData data = new SignUpData(username,password,username,username,email,mobile_num_new);
       SignUpRequest req = new SignUpRequest(signupUi.this, data, this);
        req.executeRequest();
    }
    public String GetCountryZipCode(){
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();
        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        //Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

public boolean phone_val(String ph_number)
{
    return android.util.Patterns.PHONE.matcher(ph_number).matches();
}

    private boolean isValidMobile(String phone2)
    {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone2))
        {
            if(phone2.length() < 12 || phone2.length() > 12)
            {
                check = false;

            }
            else
            {
                check = true;
            }
        }
        else
        {
            check=false;
        }
        return check;
    }

    public boolean validate() {
        boolean valid = true;
        boolean valid_num = true;
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirmPassword = _confirm_password.getText().toString();

        String ph_number = _phonenumberText.getText().toString();
//        String[] Code_is = count_code.split(",");
//        String Code  = "+"+Code_is[0];
//        mobile_num_new = Code+ph_number;

        count_code = GetCountryZipCode();
        String[] Code_is = count_code.split(",");
        String Code  = "+"+Code_is[0];
        mobile_num_new = Code+ph_number;
        Log.d("here",mobile_num_new);
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            // phone must begin with '+'
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(mobile_num_new, "");
            int countryCode = numberProto.getCountryCode();
            _phonenumberText.setError(null);
        } catch (NumberParseException e) {
            _phonenumberText.setError("Enter a valid Mobile re Exception");

            valid = false;
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        valid_num = phone_val(mobile_num_new);
        if (valid_num == true)
        {
            _phonenumberText.setError(null);
        }
        else
        {_phonenumberText.setError("Enter a valid Mobile Number");
            valid =false;}
        valid_num = isValidMobile(mobile_num_new);
        if (valid_num == true)
        {
            _phonenumberText.setError(null);
        }
        else
        {_phonenumberText.setError("Enter a valid Mobile Number");
        valid =false;}

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 16) {
            _passwordText.setError("between 6 and 16 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (confirmPassword.equals(password) == false) {
            _confirm_password.setError("Password not matched");
            valid = false;
        } else {
            _confirm_password.setError(null);
        }



        return valid;
    }

    public void onSignUpSuccess(String msg) {

        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);

    }

    public void onSignUpFail(String msg) {

        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onSignUpResponse(CommonRequest.ResponseCode responseCode, SignUpData data) {

        Log.d(TAG, "responseCode ==" + responseCode);
        Log.d(TAG, "Error message =="+data.getErrorMessage());
        progressDialog.dismiss();
        switch(responseCode){
            case COMMON_RES_SUCCESS:
                onSignUpSuccess("Successfully SignUp! You can login now");
                finish();
                break;
            case COMMON_RES_INTERNAL_ERROR:
                onSignUpSuccess("Please try again");
                break;
            case COMMON_RES_CONNECTION_TIMEOUT:
                onSignUpSuccess("Connection timeout !");
                break;
            case COMMON_RES_FAILED_TO_CONNECT:
                onSignUpSuccess("Check your connection !");
                break;
            default:
                onSignUpSuccess("Please try again");
                break;
        }

    }
}

