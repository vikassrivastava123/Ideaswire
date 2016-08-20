package com.fourway.ideaswire.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

        Intent inf = new Intent (this,CropedImage.class);
        startActivity(inf);

      //  Intent intent = new Intent(this,editCampaign.class);
      //  startActivity(intent);

    }



    public void closeEditBox(View view) {
        ImageView closeCampaignTitle = (ImageView)findViewById(R.id.closeCamapignIcon);
        EditText closeEDit = (EditText)findViewById(R.id.etCampaignName);
        closeEDit.setVisibility(View.GONE);
        closeCampaignTitle.setVisibility(View.GONE);

    }
}
