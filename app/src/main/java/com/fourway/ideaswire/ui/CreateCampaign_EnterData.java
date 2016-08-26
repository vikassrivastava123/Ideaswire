package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourway.ideaswire.R;

public class CreateCampaign_EnterData extends Activity {
    TextView tf1,mTitle;
    EditText etCampignName = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign__enter_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        tf1 = (TextView)findViewById(R.id.tVAddCampign);

        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        mTitle.setTypeface(mycustomFont);
        tf1.setTypeface(mycustomFont);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getApplicationContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });

        etCampignName = (EditText)findViewById(R.id.etCampaignName);
        etCampignName.setTypeface(mycustomFont);
    }

    public void uploadToCreateCampaign(View view) {
        String campnName = null;
        if(etCampignName !=null){
            campnName = etCampignName.getText().toString();
        }

        /*
        * Need to open gallery directly from here
        * From Cropped image OK clicked editCampaign.java will be opened
        * In editCampaign.java this campaign name(campnName) will be used to set defualt text
        * ScreenName will be used by CropedImage as it will be used to open gallery by multiple classes
        * */

        Intent inf = new Intent (this,CropedImage.class);
        inf.putExtra("ScreenName","Create Campaign");
        inf.putExtra("CampaignName",campnName);
        startActivity(inf);

      //  Intent intent = new Intent(this,editCampaign.class);
      //  startActivity(intent);

    }



    public void closeEditBox(View view) {
        ImageView closeCampaignTitle = (ImageView)findViewById(R.id.closeCamapignIcon);
        etCampignName.setVisibility(View.GONE);
        closeCampaignTitle.setVisibility(View.GONE);
        etCampignName = null;
    }


}
