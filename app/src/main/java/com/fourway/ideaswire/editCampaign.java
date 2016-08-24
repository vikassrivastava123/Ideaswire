package com.fourway.ideaswire;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.fourway.ideaswire.data.CreateProfileData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.CreateProfileRequest;
import com.fourway.ideaswire.ui.CreateCampain_Sucess;
import com.fourway.ideaswire.ui.loginUi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class editCampaign extends AppCompatActivity implements CreateProfileRequest.CreateProfileResponseCallback {

    ImageView selImage;
    RadioButton statusOn = null;
    RadioButton statusDraft = null;
    private EditText mEtCampnName = null;
    private String mCampaignNameReceived = null;
    private static String Tag = "editCampaign";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_campaign);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intentScrn = getIntent();
        mCampaignNameReceived = intentScrn.getStringExtra("CampaignName");
        mEtCampnName = (EditText)findViewById(R.id.etCampaignName);
        if(mEtCampnName != null){
            mEtCampnName.setText(mCampaignNameReceived);
        }
        selImage = (ImageView)findViewById(R.id.imageToEdit);
        statusDraft = (RadioButton)findViewById(R.id.StatusDraft);
        statusOn = (RadioButton)findViewById(R.id.StatusOn);
        showImageCampaign();
        //selImage.set

    }

   private void showImageCampaign(){

        Log.v("editCampaign", "showImageCampaign");
        try {
            FileInputStream in = openFileInput("Imaged");

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            selImage.setImageDrawable(new BitmapDrawable(getResources(), bitmap));

        }catch (FileNotFoundException e){
            Log.v("editCampaign","Imaged file not found");
        }

    }


    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        int id = view.getId();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.StatusOn:
                if (checked) {
                    // Pirates are the best
                }
                Log.v(Tag,"StausOn"+checked);
                statusOn.toggle();
                Log.v(Tag,"StatusOn after toggle"+statusOn.isChecked());
                    break;
            case R.id.StatusDraft:
                if (checked) {
                    // Ninjas rule
                }


                Log.v(Tag,"statusDraft"+checked);
                statusDraft.toggle();
                Log.v(Tag, "StatusOn after toggle" + statusDraft.isChecked());
                    break;
        }

    }

    public void createCamapignBtn(View view) {
        boolean liveCampain = false;

        RadioButton rbStatusOn = (RadioButton)findViewById(R.id.StatusOn);
        RadioButton rbDraftOn = (RadioButton)findViewById(R.id.StatusDraft);

        if(rbStatusOn.isChecked() == true){
            liveCampain = true;
        }

        String CamapingName = mEtCampnName.getText().toString();
        File sendFile = new File("Imaged");

        if(sendFile == null){
            Log.v(Tag,"file data is null");
        }else {
            Log.v(Tag, "Make request Now to create templae");
            Log.v(Tag,"LoginToken" + loginUi.mLogintoken);

            CreateProfileData data = new CreateProfileData(CreateProfileData.TemplateID.PROFILE_TEMPLATE_ID_T1, "default", "bussiness", "asASa", loginUi.mLogintoken, sendFile);
            CreateProfileRequest req = new CreateProfileRequest(editCampaign.this, data);
            req.executeRequest();
        }
       //make request here
    }

    @Override
    public void onCreateProfileResponse(CommonRequest.ResponseCode res, CreateProfileData data) {

        Log.v(Tag,"ResponseCode : "+res);

        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){

            Log.v(Tag,"Success ResponseCode : "+res);
            Intent inte = new Intent(this, CreateCampain_Sucess.class);
            startActivity(inte);


        }else
           Log.v(Tag,"Error ResponseCode : "+res);
//                COMMON_RES_INTERNAL_ERROR,
//                COMMON_RES_CONNECTION_TIMEOUT,
//                COMMON_RES_FAILED_TO_CONNECT,
//                COMMON_RES_IMAGE_NOT_FOUND,
//                COMMON_RES_SERVER_ERROR_WITH_MESSAGE

    }

/*    public void deleteCamapign(View view) {

        Intent inte = new Intent(this, CreateCampaign_homePage.class);
        startActivity(inte);
    }*/
}
