package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.CreateProfileData;
import com.fourway.ideaswire.data.GetProfileRequestData;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.GetProfileRequest;
import com.fourway.ideaswire.request.helper.VolleySingleton;
import com.fourway.ideaswire.templates.AboutUsPage;
import com.fourway.ideaswire.templates.ClientPage;
import com.fourway.ideaswire.templates.HomePage;
import com.fourway.ideaswire.templates.ServicePage;
import com.fourway.ideaswire.templates.TeamPage;
import com.fourway.ideaswire.templates.blogpage;
import com.fourway.ideaswire.templates.contactDetails;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.util.ArrayList;

public class EditCampaignNew extends Activity implements GetProfileRequest.GetProfileResponseCallback{
    NetworkImageView editImageView;
    EditText edCampaignName;
    int profilePosition;

    public static String Tag = "EditCampaignNew";
    CheckBox statusBox=null;
    RadioButton statusOn = null;
    RadioButton statusDraft = null;
    private ArrayList<Profile> mProfileList;
    Button btn1;


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
        statusBox = (CheckBox)findViewById(R.id.checkBoxStatus);
        btn1 = (Button) findViewById(R.id.btn_createCampaignNew);

        statusDraft.setTypeface(mycustomFont);
        statusOn.setTypeface(mycustomFont);
        statusBox.setTypeface(mycustomFont);
        btn1.setTypeface(mycustomFont);

        statusBox.setOnCheckedChangeListener(statusChangeListener);


        Profile p = loginUi.mProfileList.get(profilePosition);
        //Clear page if Already have data in page
        if (p.getTotalNumberOfPages() != 0){
            p.clearPage();
        }

        GetProfileRequestData data = new GetProfileRequestData(loginUi.mLogintoken, p.getProfileId(), p);
        GetProfileRequest request =
                new GetProfileRequest(EditCampaignNew.this, data, EditCampaignNew.this);
        request.executeRequest();



    }

    private void showImageCampaign(){

        Toast.makeText(EditCampaignNew.this, "Image Not set", Toast.LENGTH_SHORT).show();

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

    public static Profile reqToEditProfile = null;

    public void editProfile(View view){
        if (!edCampaignName.getText().toString().equals("")){
            Profile p = loginUi.mProfileList.get(profilePosition);
            reqToEditProfile = p;
            editCampaign.mCampaignIdFromServer = p.getProfileId();
            dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(0).getTemplateData(1, false);
            addDefaultDataForAddPage();

//            Class intenetToLaunch = data.getIntentToLaunchPage();
//            Log.v(Tag, "5" + intenetToLaunch);
            Intent intent = new Intent(this, FragmenMainActivity.class);
//            Intent intent = new Intent(this, intenetToLaunch);
            intent.putExtra("data", data);
            intent.putExtra(MainActivity.ExplicitEditModeKey, true);
            startActivity(intent);

        }else {
            Toast.makeText(EditCampaignNew.this, "Please Fill Campaign Name", Toast.LENGTH_SHORT).show();
        }

    }

    public void addDefaultDataForAddPage()
    {
        select_layout_of_template.listOfTemplatePagesObjForAddPage = new ArrayList<pages>();

        pages abtusObj = new AboutUsPage();
        pages homeObj = new HomePage();
        pages blogpage = new blogpage();
        pages contactdetails = new contactDetails();
        pages ServicePage = new ServicePage();
        pages clientobj = new ClientPage();
        pages teamPages =new TeamPage();

        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(0, abtusObj);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(1, homeObj);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(2, blogpage);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(3, contactdetails);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(4, ServicePage);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(5, clientobj);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(6, teamPages);
    }

    @Override
    public void onGetProfileResponse(CommonRequest.ResponseCode res, GetProfileRequestData data) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            Profile p = data.getProfile();
            ArrayList<Page> pageList = p.getAllPages();

            MainActivity.initListOfPages();
            boolean bcanShowProfile = MainActivity.addPagesToList(pageList, true);

            if(bcanShowProfile == true) {
                //shoEditProfile();
                String url =  (mProfileList.get(profilePosition)).getImageUrl();
                if (url != null && !url.equalsIgnoreCase("null")){
                    editImageView.setImageUrl(url, VolleySingleton.getInstance(this).getImageLoader());
                }

                String cName = (mProfileList.get(profilePosition)).getProfileName();
                if (cName !=null){
                    edCampaignName.setText(cName);
                }
            }else{
                Toast.makeText(getBaseContext(), "Error : Please Try Later", Toast.LENGTH_LONG).show();
            }
        }
    }
}
