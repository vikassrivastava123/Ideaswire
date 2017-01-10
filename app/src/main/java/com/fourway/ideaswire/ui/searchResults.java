package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.GetProfileRequestData;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.GetProfileRequest;
import com.fourway.ideaswire.templates.dataOfTemplate;

import java.util.ArrayList;

public class searchResults extends Activity implements GetProfileRequest.GetProfileResponseCallback{

    SearchProfileListAdapter msearchProfileAdapter;
    private static String TAG = "SearchResults";
    ProgressDialog dialog;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
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

        GridView gv = (GridView) findViewById(R.id.searchResultsGridView);


        msearchProfileAdapter = new SearchProfileListAdapter(this, EditPhotoSelectedUi.mSearchProfileList);

        gv.setAdapter(msearchProfileAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog=new ProgressDialog(searchResults.this);
                dialog.setMessage("Please wait, loading...");
                if(view.getId()==R.id.imgViewProfile) {
                    Profile p = EditPhotoSelectedUi.mSearchProfileList.get(position);

                    //Clear page if Already have data in page
                    if (p.getTotalNumberOfPages() != 0){
                        p.clearPage();
                    }

                    GetProfileRequestData data = new GetProfileRequestData(p.getProfileId(), p);
                    GetProfileRequest request =
                            new GetProfileRequest(searchResults.this, data, searchResults.this);
                    request.executeRequest();
                    dialog.show();
                }
            }
        });


    }

    private void shownLiveProfile(){

        dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(0).getTemplateData(1,false);

        Class intenetToLaunch = data.getIntentToLaunchPage();
        Log.v(TAG, "5" + intenetToLaunch);
//        Intent intent = new Intent(this, intenetToLaunch);
        Intent intent = new Intent(this, FragmenMainActivity.class);
//        intent.putExtra(MainActivity.ExplicitEditModeKey, true);
        intent.putExtra("data",data);
        startActivity(intent);

    }

    @Override
    public void onGetProfileResponse(CommonRequest.ResponseCode res, GetProfileRequestData data) {

        dialog.dismiss();

        AlertDialog.Builder  errorDialog = new AlertDialog.Builder(this);
        errorDialog.setIcon(android.R.drawable.ic_dialog_alert);
        errorDialog.setPositiveButton("OK", null);
        errorDialog.setTitle("Error");
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            Profile p = data.getProfile();
            ArrayList<Page> pageList = p.getAllPages();

            if (pageList.size()>0) {
                MainActivity.initListOfPages();
                boolean bcanShowProfile = MainActivity.addPagesToList(pageList, false);

                if (bcanShowProfile == true) {
                    shownLiveProfile();
                } else {
//                    Toast.makeText(getBaseContext(), "Error : Please Try Later", Toast.LENGTH_LONG).show();
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
                    errorDialog.setMessage(""+res);
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
}
