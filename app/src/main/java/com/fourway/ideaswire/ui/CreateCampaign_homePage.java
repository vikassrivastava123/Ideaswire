package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.GetProfileRequestData;
import com.fourway.ideaswire.data.GetUserProfileRequestData;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.SessionManager;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.GetProfileRequest;
import com.fourway.ideaswire.request.GetUserProfileRequest;
import com.fourway.ideaswire.templates.dataOfTemplate;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fourway.ideaswire.ui.loginUi.mLogintoken;

public class CreateCampaign_homePage extends Activity implements GetProfileRequest.GetProfileResponseCallback,GetUserProfileRequest.GetUserProfilesResponseCallback {
    TextView tf,mTitle;
    MyProfileAdapter mProfileAdapter;
    Boolean campaignEditMode;
    int profilePosition;
    ImageButton menuButton;
    ProgressDialog mProgressDialog;
    GridView profileGridView;
    SessionManager session;
    private static String TAG = "CreateCampaign_homePage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign_home_page);
        menuButton = (ImageButton)findViewById(R.id.menu_button);
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

        session = new SessionManager(this);
        profileGridView = (GridView) findViewById(R.id.profileGridView);
        try {

            if(loginUi.mProfileList!=null) {

                mProfileAdapter = new MyProfileAdapter(this, loginUi.mProfileList);
                profileGridView.setAdapter(mProfileAdapter);
                profileGridView.setOnItemClickListener(profileGridClickListener);

            }else {

                HashMap<String, String> user = session.getUserDetails();
                mLogintoken = user.get(SessionManager.KEY_LOGIN_TOKEN);

                GetUserProfileRequestData data = new GetUserProfileRequestData(mLogintoken);
                GetUserProfileRequest request = new GetUserProfileRequest(this, data, this);
                request.executeRequest();
                mProgressDialog = new ProgressDialog(CreateCampaign_homePage.this,
                        R.style.AppTheme_Dark_Dialog);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Please wait...  ");
                mProgressDialog.show();
            }
        }catch(NullPointerException e){
            Toast.makeText(getApplicationContext(), "no profile data received", Toast.LENGTH_SHORT).show();
        }



        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMenu();
            }
        });
    }

    AdapterView.OnItemClickListener profileGridClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            campaignEditMode = false;
            if(view.getId()==R.id.imgViewProfile) {
                Profile p = loginUi.mProfileList.get(position);

                //Clear page if Already have data in page
                if (p.getTotalNumberOfPages() != 0){
                    p.clearPage();
                }

                GetProfileRequestData data = new GetProfileRequestData(loginUi.mLogintoken, p.getProfileId(), p);
                GetProfileRequest request =
                        new GetProfileRequest(CreateCampaign_homePage.this, data, CreateCampaign_homePage.this);
                request.executeRequest();
                mProgressDialog = new ProgressDialog(CreateCampaign_homePage.this,
                        R.style.AppTheme_Dark_Dialog);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Getting data...  ");
                mProgressDialog.show();

            }

            if(view.getId()==R.id.editCampaign) {
                campaignEditMode = true;
                profilePosition = position;



                shownLiveProfile();
            }
        }
    };



    public void createCamEditBtn(View view) {

        Intent intent = new Intent(CreateCampaign_homePage.this,CreateCampaign_EnterData.class);
        startActivity(intent);


    }

    private void shownLiveProfile(){

        if (!campaignEditMode) {
            dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(0).getTemplateData(1, false);
            data.setEditMode(false);
            data.setIsInUpdateProfileMode(false);
            // Log.v(TAG, "5" + intentToLaunch);
            Intent intent = new Intent(this, FragmenMainActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        } else {

            Intent intent = new Intent(this, EditCampaignNew.class);
            intent.putExtra("profilePosition",profilePosition);
            intent.putExtra(MainActivity.ExplicitEditModeKey, campaignEditMode);
            startActivity(intent);

        }

    }


    void onClickMenu(){
        PopupMenu popup = new PopupMenu(this,menuButton);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.menu_search, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.search_menu){
                    startActivity(new Intent(CreateCampaign_homePage.this,HomepageBeforeLogin.class));
                }
                return false;
            }
        });

        popup.show();
    }


    @Override
    public void onGetProfileResponse(CommonRequest.ResponseCode res, GetProfileRequestData data) {
        mProgressDialog.dismiss();
        AlertDialog.Builder  errorDialog = new AlertDialog.Builder(this);
        errorDialog.setIcon(android.R.drawable.ic_dialog_alert);
        errorDialog.setPositiveButton("OK", null);
        errorDialog.setTitle("Error");

        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            Profile p = data.getProfile();
            ArrayList<Page> pageList = p.getAllPages();

            if (pageList.size() > 0) {
                MainActivity.initListOfPages();
                boolean bcanShowProfile = MainActivity.addPagesToList(pageList, true);

                if (bcanShowProfile == true) {
                    shownLiveProfile();
                } else {
//                Toast.makeText(getBaseContext(), "Error : Please Try Later", Toast.LENGTH_LONG).show();
                    errorDialog.setMessage("Something went wrong in data");
                    errorDialog.show();
                }
            }else {
                errorDialog.setMessage("Data not found");
                errorDialog.show();
            }

        }else {
            switch (res){
                case COMMON_RES_CONNECTION_TIMEOUT:
                    errorDialog.setMessage("Connection time out");
                    errorDialog.show();
                    break;
                case COMMON_RES_INTERNAL_ERROR:
                    errorDialog.setMessage("Internal error");
                    errorDialog.show();
                    break;
                case COMMON_RES_FAILED_TO_CONNECT:
                    errorDialog.setMessage("failed to connect");
                    errorDialog.show();
                    break;
                case COMMON_RES_SERVER_ERROR_WITH_MESSAGE:
                    errorDialog.setMessage(data.getErrorMessage());
                    errorDialog.show();
                    break;
                case COMMON_RES_PROFILE_DATA_NO_CONTENT:
                    errorDialog.setMessage("Profile data no content");
                    errorDialog.show();
                    break;
                default:errorDialog.setMessage("Fail to find ):");
                    errorDialog.show();

            }
        }
    }

    @Override
    public void onResponse(CommonRequest.ResponseCode res, GetUserProfileRequestData data) {
        mProgressDialog.dismiss();
        switch (res){
            case COMMON_RES_SUCCESS:
                loginUi.mProfileList = data.getProfileList();
                mProfileAdapter = new MyProfileAdapter(this, loginUi.mProfileList);
                profileGridView.setAdapter(mProfileAdapter);
                profileGridView.setOnItemClickListener(profileGridClickListener);
                break;
        }
    }
}
