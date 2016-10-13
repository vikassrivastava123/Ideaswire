package com.fourway.ideaswire.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

public class searchResults extends AppCompatActivity implements GetProfileRequest.GetProfileResponseCallback{

    SearchProfileListAdapter msearchProfileAdapter;

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
                Profile p = loginUi.mProfileList.get(position);
                GetProfileRequestData data = new GetProfileRequestData(p.getProfileId(), p);
                GetProfileRequest request =
                        new GetProfileRequest(searchResults.this, data, searchResults.this);
                request.executeRequest();
            }
        });


    }

    @Override
    public void onGetProfileResponse(CommonRequest.ResponseCode res, GetProfileRequestData data) {
        /*if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            Profile p = data.getProfile();
            ArrayList<Page> pageList = p.getAllPages();

            MainActivity.initListOfPages();
            boolean bcanShowProfile = MainActivity.addPagesToList(pageList);

            if(bcanShowProfile == true) {
                shownLiveProfile();
            }else{
                Toast.makeText(getBaseContext(), "Error : Please Try Later", Toast.LENGTH_LONG).show();
            }

        }*/
    }
}