package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.CreateProfileData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.CreateProfileRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class editCampaign extends Activity implements CreateProfileRequest.CreateProfileResponseCallback {

    ImageView selImage;
    RadioButton statusOn = null;
    RadioButton statusDraft = null;
    private EditText mEtCampnName = null;
    private String mCampaignNameReceived = null;
    private static String Tag = "editCampaign";
    TextView mTitle,statusTextView;
    CheckBox statusBox=null;
    Button btn1;
    private int imageFrom;
    FileInputStream in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_campaign);
        Typeface mycustomFont= Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(mycustomFont);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });
//        setSupportActionBar(toolbar);

        statusTextView = (TextView)findViewById(R.id.tvStatus);
        statusTextView.setTypeface(mycustomFont);
        btn1 = (Button) findViewById(R.id.btn_createCampaign);
        btn1.setTypeface(mycustomFont);
//        t1_new.setTypeface(mycustomFont);
        Intent intentScrn = getIntent();
        imageFrom = intentScrn.getIntExtra(MainActivity.CREATE_CAMPAIGN_IMAGE_FROM, 2);

       mCampaignNameReceived = getIntent().getStringExtra("CampaignName");
        mEtCampnName = (EditText)findViewById(R.id.etCampaignName);
        if(mEtCampnName != null){
            mEtCampnName.setText(mCampaignNameReceived);
        }
        selImage = (ImageView)findViewById(R.id.imageToEdit);
        statusDraft = (RadioButton)findViewById(R.id.StatusDraft);
        statusOn = (RadioButton)findViewById(R.id.StatusOn);
        statusBox = (CheckBox)findViewById(R.id.checkBoxStatus);
        statusDraft.setTypeface(mycustomFont);
        statusOn.setTypeface(mycustomFont);
        statusBox.setTypeface(mycustomFont);
        statusBox.setOnCheckedChangeListener(statusChangeListener);
        showImageCampaign();
        //selImage.set


    }

   private void showImageCampaign(){

        Log.v("editCampaign", "showImageCampaign");
        try {
            switch (imageFrom){
                case MainActivity.IMAGE_CREATE_CAMPAIGN:
                    in = openFileInput(MainActivity.CREATE_CAMPAIGN_IMAGE_CROPED_NAME);
                    break;
                case MainActivity.IMAGE_SEARCH:
                    in = openFileInput(MainActivity.SEARCH__IMAGE_CROPED_NAME);
                    break;
            }

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            selImage.setImageDrawable(new BitmapDrawable(getResources(), bitmap));

        }catch (FileNotFoundException e){
            Log.v("editCampaign","CREATE_CAMPAIGN_IMAGE_CROPED_NAME file not found");
        }

    }

    CreateProfileData.ProfileStatus  mSetStatus = CreateProfileData.ProfileStatus.PROFILE_STATUS_ACTIVE;
    CompoundButton.OnCheckedChangeListener statusChangeListener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                mSetStatus = CreateProfileData.ProfileStatus.PROFILE_STATUS_ACTIVE;
            }else {
                mSetStatus = CreateProfileData.ProfileStatus.PROFILE_STATUS_DRAFT;
            }
        }
    };

    CreateProfileData.ProfileType mProfileType = CreateProfileData.ProfileType.PROFILE_TYPE_LOGO;
    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        int id = view.getId();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.StatusOn:
                Log.v(Tag,"StausOn"+checked);
                statusOn.toggle();
                if (checked) {
                    //mSetStatus = CreateProfileData.ProfileStatus.PROFILE_STATUS_DRAFT;
                    mProfileType = CreateProfileData.ProfileType.PROFILE_TYPE_INDIVIDUAL;
                }
                Log.v(Tag,"StatusOn after toggle"+statusOn.isChecked());
                    break;
            case R.id.StatusDraft:
                Log.v(Tag,"statusDraft"+checked);
                statusDraft.toggle();
                if (checked) {
                    //mSetStatus = CreateProfileData.ProfileStatus.PROFILE_STATUS_ACTIVE;
                    mProfileType = CreateProfileData.ProfileType.PROFILE_TYPE_LOGO;
                }
                Log.v(Tag, "StatusOn after toggle" + statusDraft.isChecked());
                    break;
        }

    }

    private File getFileObjectFromBitmap (Bitmap b) throws IOException {
        File f = new File(getApplicationContext().getCacheDir(), "Abc");

//Convert bitmap to byte array

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return f;
    }

    public void deleteCropFile(){
        String path = getFilesDir().getAbsolutePath() + "/" + MainActivity.CREATE_CAMPAIGN_IMAGE_CROPED_NAME;

        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
    }

    public static String mCampaignIdFromServer = null;

    ProgressDialog pd;
    public void createCamapignBtn(View view) throws IOException {
        if (!mEtCampnName.getText().toString().equals("")) {
            pd = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
            pd.setMessage("Please wait...");

            String campaignName = mEtCampnName.getText().toString();
            FileInputStream in = null;
            try {
                in = openFileInput(MainActivity.CREATE_CAMPAIGN_IMAGE_CROPED_NAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            File sendFile = getFileObjectFromBitmap(bitmap);

            if (sendFile == null) {
                Log.v(Tag, "file data is null");
            } else {
                Log.v(Tag, "Make request Now to create templae");
                Log.v(Tag, "LoginToken" + loginUi.mLogintoken);

                CreateProfileData data = new CreateProfileData (campaignName, "bussiness", "profile_department",
                        loginUi.mLogintoken, sendFile, mProfileType,mSetStatus );
                        //null;//new CreateProfileData(CamapingName, "bussiness", "asASa", loginUi.mLogintoken, sendFile);

                CreateProfileRequest req = new CreateProfileRequest(editCampaign.this, data, this);
                req.executeRequest();

                pd.show();

                //Intent inte = new Intent(this, CreateCampain_Sucess.class);
                //startActivity(inte);
            }
        }else {
            Toast.makeText(editCampaign.this, "Please Fill Campaign Name", Toast.LENGTH_SHORT).show();
        }
       //make request here
    }

    @Override
    public void onCreateProfileResponse(CommonRequest.ResponseCode res, CreateProfileData data) {

        Log.v(Tag,"ResponseCode : "+res);

        pd.dismiss();



        switch(res){
            case COMMON_RES_SUCCESS:
                Log.v(Tag,"Success ResponseCode : "+res);
                mCampaignIdFromServer = data.getProfileId();
                Intent inte = new Intent(this, CreateCampain_Sucess.class);
                startActivity(inte);
                finish();
                break;
            case COMMON_RES_INTERNAL_ERROR:
                showMessage("Failed ,Please try again");
                break;
            case COMMON_RES_CONNECTION_TIMEOUT:
                showMessage("Connection Timeout !");
                break;
            case COMMON_RES_FAILED_TO_CONNECT:
                showMessage("Please check internet connection !");
                break;
            case COMMON_RES_SERVER_ERROR_WITH_MESSAGE:
                String errorMsg = data.getErrorMessage();
                showMessage(errorMsg);
                break;
            default:
                showMessage("Failed ,Please try again");
                break;
        }


    }

    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

/*    public void deleteCamapign(View view) {

        Intent inte = new Intent(this, CreateCampaign_homePage.class);
        startActivity(inte);
    }*/
}
