package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.GetProfileRequestData;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.GetProfileRequest;
import com.fourway.ideaswire.request.helper.VolleySingleton;
import com.fourway.ideaswire.templates.dataOfTemplate;

import java.util.ArrayList;

public class EditCampaignNew extends Activity implements GetProfileRequest.GetProfileResponseCallback{
    NetworkImageView editImageView;
    EditText edCampaignName;
    int profilePosition;

    public static String TAG = "EditCampaignNew";

    RadioButton statusOn = null;
    RadioButton statusDraft = null;
    private ArrayList<Profile> mProfileList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_campaign_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Typeface mycustomFont= Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        mProfileList = loginUi.mProfileList;
        profilePosition = getIntent().getIntExtra("profilePosition",0);

        editImageView = (NetworkImageView)findViewById(R.id.imageToEditNew);
        edCampaignName = (EditText)findViewById(R.id.etCampaignName);

        statusDraft = (RadioButton)findViewById(R.id.StatusDraft);
        statusOn = (RadioButton)findViewById(R.id.StatusOn);
        statusDraft.setTypeface(mycustomFont);
        statusOn.setTypeface(mycustomFont);

        Profile p = loginUi.mProfileList.get(profilePosition);
        GetProfileRequestData data = new GetProfileRequestData(loginUi.mLogintoken, p.getProfileId(), p);
        GetProfileRequest request =
                new GetProfileRequest(EditCampaignNew.this, data, EditCampaignNew.this);
        request.executeRequest();



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
                Log.v(TAG,"StausOn"+checked);
                statusOn.toggle();
                Log.v(TAG,"StatusOn after toggle"+statusOn.isChecked());
                break;
            case R.id.StatusDraft:
                if (checked) {
                    // Ninjas rule
                }


                Log.v(TAG,"statusDraft"+checked);
                statusDraft.toggle();
                Log.v(TAG, "StatusOn after toggle" + statusDraft.isChecked());
                break;
        }

    }

    public void editProfile(View view){
        if (!edCampaignName.getText().toString().equals("")){

            dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(0).getTemplateData(1, false);

            Class intenetToLaunch = data.getIntentToLaunchPage();
            Log.v(TAG, "5" + intenetToLaunch);
            //Intent intent = new Intent(this, FragmenMainActivity.class);
            Intent intent = new Intent(this, intenetToLaunch);
            intent.putExtra("data", data);
            intent.putExtra("pId",mProfileList.get(profilePosition).getProfileId());
            intent.putExtra(MainActivity.ExplicitEditModeKey, false);
            startActivity(intent);

        }else {
            Toast.makeText(EditCampaignNew.this, "Please Fill Campaign Name", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onGetProfileResponse(CommonRequest.ResponseCode res, GetProfileRequestData data) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            Profile p = data.getProfile();
            ArrayList<Page> pageList = p.getAllPages();

            MainActivity.initListOfPages();
            boolean bcanShowProfile = MainActivity.addPagesToList(pageList);

            if(bcanShowProfile == true) {
                //shoEditProfile();
                String url =  (mProfileList.get(profilePosition)).getImageUrl();
                if (url != null && !url.equalsIgnoreCase("null")){
                    editImageView.setImageUrl(url, VolleySingleton.getInstance(this).getImageLoader());
                }

                String cName = (mProfileList.get(profilePosition)).getProfileName();
                if (cName !=null && cName.equalsIgnoreCase("null")){
                    edCampaignName.setText(cName);
                }
            }else{
                Toast.makeText(getBaseContext(), "Error : Please Try Later", Toast.LENGTH_LONG).show();
            }
        }
    }
}
