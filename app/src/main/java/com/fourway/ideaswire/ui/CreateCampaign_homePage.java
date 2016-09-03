package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Attribute;
import com.fourway.ideaswire.data.GetProfileRequestData;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.GetProfileRequest;

import java.util.ArrayList;

public class CreateCampaign_homePage extends Activity implements GetProfileRequest.GetProfileResponseCallback {
    TextView tf,mTitle;
    MyProfileAdapter mProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        mTitle.setTypeface(mycustomFont);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),HomeScreenFirstLogin.class);
                //startActivity(intent);
            }
        });
        tf = (TextView) findViewById(R.id.tVAddCampign);
        tf.setTypeface(mycustomFont);
        if (toolbar != null) {
           // toolbar.setTitle("Create Campaign");
           // toolbar.setNavigationIcon(R.drawable.ic_menu_send);
           // setSupportActionBar(toolbar);
        }

        GridView gv = (GridView) findViewById(R.id.profileGridView);
//        mProfileAdapter = new MyProfileAdapter(this, loginUi.mProfileList);
//        gv.setAdapter(mProfileAdapter);
//        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Profile p = loginUi.mProfileList.get(position);
//                GetProfileRequestData data = new GetProfileRequestData(loginUi.mLogintoken, p.getProfileId());
//                GetProfileRequest request =
//                        new GetProfileRequest(CreateCampaign_homePage.this, data, CreateCampaign_homePage.this);
//            }
//        });
    }

    public void createCamEditBtn(View view) {

        Intent intent = new Intent(CreateCampaign_homePage.this,CreateCampaign_EnterData.class);
        startActivity(intent);


    }

    @Override
    public void onGetProfileResponse(CommonRequest.ResponseCode res, GetProfileRequestData data) {
        Profile p = data.getProfile();
        ArrayList<Page> pageList= p.getAllPages();
        int num_of_pages = pageList.size();

        ArrayList<Attribute> Page_0_attribute = pageList.get(0).getAttributes();
    }
}
