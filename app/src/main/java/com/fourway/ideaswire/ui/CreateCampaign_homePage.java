package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fourway.ideaswire.R;

public class CreateCampaign_homePage extends Activity {
    TextView tf,mTitle;

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

    }

    public void createCamEditBtn(View view) {

        Intent intent = new Intent(this,CreateCampaign_EnterData.class);
        startActivity(intent);


    }
}
