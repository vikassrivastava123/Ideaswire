package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.GetProfileRequestData;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.GetProfileRequest;
import com.fourway.ideaswire.templates.dataOfTemplate;

import java.util.ArrayList;

public class CreateCampaign_homePage extends Activity implements GetProfileRequest.GetProfileResponseCallback {
    TextView tf,mTitle;
    MyProfileAdapter mProfileAdapter;
private static String TAG = "CreateCampaign_homePage";
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

        try {
            GridView gv = (GridView) findViewById(R.id.profileGridView);
            mProfileAdapter = new MyProfileAdapter(this, loginUi.mProfileList);
            gv.setAdapter(mProfileAdapter);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Profile p = loginUi.mProfileList.get(position);
                    GetProfileRequestData data = new GetProfileRequestData(loginUi.mLogintoken, p.getProfileId(), p);
                    GetProfileRequest request =
                            new GetProfileRequest(CreateCampaign_homePage.this, data, CreateCampaign_homePage.this);
                    request.executeRequest();
                }
            });
        }catch(Exception e){
            Log.d(TAG,"Error in showing profile data"+e);
            Toast.makeText(this,"No data received for your Profile this time",Toast.LENGTH_LONG).show();
        }
    }

    public void createCamEditBtn(View view) {

        Intent intent = new Intent(CreateCampaign_homePage.this,CreateCampaign_EnterData.class);
        startActivity(intent);


    }

    private void shownLiveProfile(){

        dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(0).getTemplateData(1,false);

        Class intenetToLaunch = data.getIntentToLaunchPage();
        Log.v(TAG, "5" + intenetToLaunch);
        Intent intent = new Intent(this, intenetToLaunch);
        intent.putExtra("data",data);
        startActivity(intent);

    }

    @Override
    public void onGetProfileResponse(CommonRequest.ResponseCode res, GetProfileRequestData data) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            Profile p = data.getProfile();
            ArrayList<Page> pageList = p.getAllPages();

            MainActivity.initListOfPages();
            boolean bcanShowProfile = MainActivity.addPagesToList(pageList);

            if(bcanShowProfile == true) {
                shownLiveProfile();
            }else{
                Toast.makeText(getBaseContext(), "Error : Please Try Later", Toast.LENGTH_LONG).show();
            }

        }
    }
}
