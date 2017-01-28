package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.SessionManager;

public class ImageNotFoundUi extends Activity {
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_not_found_ui);

        session = new SessionManager(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void createApp(View view){
        if (session.isLoggedIn()){
            Intent intent = new Intent(this, editCampaign.class);
            intent.putExtra(MainActivity.CREATE_CAMPAIGN_IMAGE_FROM,MainActivity.IMAGE_SEARCH);
            startActivity(intent);
            finish();
        }else {
            startActivity(new Intent(this,loginUi.class));
        }
    }

}
