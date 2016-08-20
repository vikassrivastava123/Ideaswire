package com.fourway.ideaswire.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.fourway.ideaswire.R;

public class CreateCampaign_EnterData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign__enter_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void uploadToCreateCampaign(View view) {



    }

    ImageView closeCampaignTitle = (ImageView)findViewById(R.id.closeCamapign);

    public void closeEditBox(View view) {
     closeCampaignTitle.setVisibility(View.GONE);

    }
}
