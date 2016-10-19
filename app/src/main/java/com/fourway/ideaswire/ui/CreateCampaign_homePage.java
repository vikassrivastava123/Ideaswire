package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.ProgressDialog;
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
    Boolean campaignEditMode;
    int profilePosition;
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
            final ProgressDialog pd=new ProgressDialog(this);
            pd.setMessage("Profile Loading...");
            pd.show();
            new Thread(){
                public void run()
                {
                    try {
                        sleep(2000);
                        if (loginUi.mProfileList==null){
                            sleep(2000);
                            pd.dismiss();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            if(loginUi.mProfileList!=null) {
                mProfileAdapter = new MyProfileAdapter(this, loginUi.mProfileList);


                gv.setAdapter(mProfileAdapter);
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        campaignEditMode =true;
                        if(view.getId()==R.id.imgViewProfile) {
                            Profile p = loginUi.mProfileList.get(position);
                            GetProfileRequestData data = new GetProfileRequestData(loginUi.mLogintoken, p.getProfileId(), p);
                            GetProfileRequest request =
                                    new GetProfileRequest(CreateCampaign_homePage.this, data, CreateCampaign_homePage.this);
                            request.executeRequest();

                        }

                        if(view.getId()==R.id.editCampaign) {
                            campaignEditMode = false;
                            profilePosition = position;
                            Profile p = loginUi.mProfileList.get(position);
                            GetProfileRequestData profileRequestData = new GetProfileRequestData(loginUi.mLogintoken, p.getProfileId(), p);
                            GetProfileRequest request =
                                    new GetProfileRequest(CreateCampaign_homePage.this, profileRequestData, CreateCampaign_homePage.this);
                            request.executeRequest();
                        }

                    }
                });
            }
        }catch(NullPointerException e){
            Toast.makeText(getApplicationContext(), "no profile data received", Toast.LENGTH_SHORT).show();
        }
        }



    public void createCamEditBtn(View view) {

        Intent intent = new Intent(CreateCampaign_homePage.this,CreateCampaign_EnterData.class);
        startActivity(intent);


    }

    private void shownLiveProfile(){

        if (campaignEditMode) {
            dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(0).getTemplateData(1, false);

            Class intenetToLaunch = data.getIntentToLaunchPage();
            Log.v(TAG, "5" + intenetToLaunch);
            //Intent intent = new Intent(this, FragmenMainActivity.class);
            Intent intent = new Intent(this, intenetToLaunch);
            intent.putExtra("data", data);
            intent.putExtra(MainActivity.ExplicitEditModeKey, campaignEditMode);
            startActivity(intent);
        }else {

            Intent intent = new Intent(this, EditCampaignNew.class);
            intent.putExtra("profilePosition",profilePosition);
            intent.putExtra(MainActivity.ExplicitEditModeKey, campaignEditMode);
            startActivity(intent);

        }

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
