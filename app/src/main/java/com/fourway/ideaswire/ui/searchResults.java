package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_results);

        GridView gv = (GridView) findViewById(R.id.searchResultsGridView);

        msearchProfileAdapter = new SearchProfileListAdapter(this, loginUi.mProfileList);

        gv.setAdapter(msearchProfileAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(view.getId()==R.id.imgViewProfile) {
                    Profile p = loginUi.mProfileList.get(position);
                    GetProfileRequestData data = new GetProfileRequestData(p.getProfileId(), p);
                    GetProfileRequest request =
                            new GetProfileRequest(searchResults.this, data, searchResults.this);
                    request.executeRequest();
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
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            Profile p = data.getProfile();
            ArrayList<Page> pageList = p.getAllPages();

            MainActivity.initListOfPages();
            boolean bcanShowProfile = MainActivity.addPagesToList(pageList, false);

            if(bcanShowProfile == true) {
                shownLiveProfile();
            }else{
                Toast.makeText(getBaseContext(), "Error : Please Try Later", Toast.LENGTH_LONG).show();
            }

        }
    }
}
