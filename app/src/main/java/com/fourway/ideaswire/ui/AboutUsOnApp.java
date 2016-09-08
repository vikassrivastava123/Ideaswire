package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class AboutUsOnApp extends Activity implements SaveProfileData.SaveProfileResponseCallback{

    ViewPager mViewPager;
    PagerAdapter mAdapter;
    EditText myedit,myedit2;
    List<String> attName;
    TextView mTitle;
    Button b1,b2,b3,b4,submit_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_template1_on_app);
      // AboutUsDataTemplate dataObj = (AboutUsDataTemplate)getIntent().getSerializableExtra("data");
        AboutUsDataTemplate dataObj = new AboutUsDataTemplate(1,true);
//        int seltemplate = dataObj.getTemplateSelected();
//
//        if(seltemplate == 1) {
//            setContentView(R.layout.about_us_template1_on_app);
//        }else{
//            setContentView(R.layout.about_us_template1_on_app);
//        }
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });
        attName = new ArrayList<String>();
        myedit = (EditText) findViewById(R.id.input_password);
        myedit.setText(dataObj.get_heading());
        myedit2 = (EditText) findViewById(R.id.pass2);
        myedit2.setText(dataObj.get_heading2());
        b1 = (Button) findViewById(R.id.edit_text);
        b2 = (Button) findViewById(R.id.delete);
        b3 = (Button) findViewById(R.id.edit_text2);
        b4 = (Button) findViewById(R.id.delete2);
        submit_button = (Button) findViewById(R.id.button2);
//        if(myedit.requestFocus()) {
//            b1.setVisibility(View.VISIBLE);
//            b2.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            b1.setVisibility(View.GONE);
//            b2.setVisibility(View.GONE);
//
//        }
        submit_button.setText(dataObj.get_button_text());
        myedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("focus", "focus loosed");
                    b1.setVisibility(View.GONE);
           b2.setVisibility(View.GONE);
                   //Do whatever you want here
                } else {
                    Log.d("focus", "focused");
                    b1.setVisibility(View.VISIBLE);
           b2.setVisibility(View.VISIBLE);
                }
            }
        });
        myedit2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("focus", "focus loosed");
                    b3.setVisibility(View.GONE);
                    b4.setVisibility(View.GONE);
                    //Do whatever you want here
                } else {
                    Log.d("focus", "focused");
                    b3.setVisibility(View.VISIBLE);
                    b4.setVisibility(View.VISIBLE);
                }
            }
        });

//        mViewPager = (ViewPager) findViewById(R.id.viewpagerForFooter);
//        Log.v(TAG,"mViewPager" + mViewPager);
//        mAdapter = new footerAdapter(this);
//        mViewPager.setAdapter(mAdapter);
//        Log.v(TAG, "setAdapter");
//
        mProfileId = editCampaign.mCampaignIdFromServer;
//        mPageName = ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US;
//
        mAbtUsPageObj  = new Page(mProfileId,mPageName);
        mParentId = mAbtUsPageObj.getPageId();

       setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_HEADING, dataObj.getHeader());
//        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_TITLE, dataObj.get_profile_page_about_us_heading());
//

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
