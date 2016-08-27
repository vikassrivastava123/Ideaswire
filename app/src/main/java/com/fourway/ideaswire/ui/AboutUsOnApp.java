package com.fourway.ideaswire.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Attribute;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.ProfileFieldsEnum;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.SaveProfileData;
import com.fourway.ideaswire.templates.AboutUsDataTemplate;

import java.util.ArrayList;
import java.util.List;

public class AboutUsOnApp extends AppCompatActivity implements SaveProfileData.SaveProfileResponseCallback{

    ViewPager mViewPager;
    PagerAdapter mAdapter;

    List<String> attName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutUsDataTemplate dataObj = (AboutUsDataTemplate)getIntent().getSerializableExtra("data");
        int seltemplate = dataObj.getTemplateSelected();

        if(seltemplate == 1) {
            setContentView(R.layout.about_us_template1_on_app);
        }else{
            setContentView(R.layout.about_us_template1_on_app);
        }

        attName = new ArrayList<String>();



        mViewPager = (ViewPager) findViewById(R.id.viewpagerForFooter);
        Log.v(TAG,"mViewPager" + mViewPager);
        mAdapter = new footerAdapter(this);
        mViewPager.setAdapter(mAdapter);
        Log.v(TAG, "setAdapter");

        mProfileId = editCampaign.mCampaignIdFromServer;
        mPageName = ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US;

        mAbtUsPageObj  = new Page(mProfileId,mPageName);
        mParentId = mAbtUsPageObj.getPageId();

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_HEADING, dataObj.getHeader());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_TITLE, dataObj.get_profile_page_about_us_heading());


       // TextView test = (TextView)findViewById(R.id.tvHeaderAbtUstplte);
       // test.setText(dataObj.getHeader());

    }

    Page  mAbtUsPageObj;
    String mProfileId = null;
    String mPageName = null;
    String mParentId = null;

    private void setAttribute(String name, String value){

        Attribute atrbtObj = new Attribute(mProfileId,mParentId,name,value);
        mAbtUsPageObj.addAttribute(atrbtObj);
   }

    Profile requestToMakeProfile;
    private static String TAG = "AboutUsOnApp";
    public void aboutUsButtonAction(View view) {

        requestToMakeProfile = new Profile(editCampaign.mCampaignIdFromServer, Profile.TemplateID.PROFILE_TEMPLATE_ID_T1);
        requestToMakeProfile.addPage(mAbtUsPageObj);
        SaveProfileData req = new SaveProfileData(this,requestToMakeProfile,loginUi.mLogintoken,this);
        req.executeRequest();

    }

    @Override
    public void onProfileSaveResponse(CommonRequest.ResponseCode res, Profile data) {

        Log.v(TAG,"ResponseCode = " + res);
        Toast.makeText(this, String.valueOf(res), Toast.LENGTH_SHORT).show();


    }
}
