package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.AboutUsPage;
import com.fourway.ideaswire.templates.ClientPage;
import com.fourway.ideaswire.templates.HomePage;
import com.fourway.ideaswire.templates.ServicePage;
import com.fourway.ideaswire.templates.TeamPage;
import com.fourway.ideaswire.templates.blogpage;
import com.fourway.ideaswire.templates.contactDetails;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CreateCampain_Sucess extends Activity {

    ImageView selImage;
    TextView t1,t2,t3,mTitle;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campain__sucess);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(mycustomFont);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //setSupportActionBar(toolbar);
        t1 = (TextView) findViewById(R.id.greatText);
        t2 = (TextView) findViewById(R.id.greatTextFollowed);
        t3 = (TextView) findViewById(R.id.textInfo);
        b1 = (Button) findViewById(R.id.btn_addTemplate);
        t1.setTypeface(mycustomFont);
        t2.setTypeface(mycustomFont);
        t3.setTypeface(mycustomFont);
        b1.setTypeface(mycustomFont);
        selImage = (ImageView)findViewById(R.id.imageFinal);
        showImageCampaign();
    }

    private void showImageCampaign(){

        Log.v("CreateCampain_Sucess", "showImageCampaign");
        try {
            FileInputStream in = openFileInput(MainActivity.CREATE_CAMPAIGN_IMAGE_CROPED_NAME);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            selImage.setImageDrawable(new BitmapDrawable(getResources(), bitmap));

        }catch (FileNotFoundException e){
            Log.v("CreateCampain_Sucess","CREATE_CAMPAIGN_IMAGE_CROPED_NAME file not found");
        }

    }

    private void startCreateCampaignWithDefaultData(int typeOfTemplateSelected){

        MainActivity.listOfTemplatePagesObj = new ArrayList<pages>();


        pages abtusObj = new AboutUsPage();
        pages homeObj = new HomePage();
        pages blogpage = new blogpage();
        pages contactdetails = new contactDetails();
        pages ServicePage = new ServicePage();
        pages clientobj = new ClientPage();
        pages teamPages =new TeamPage();



        MainActivity.listOfTemplatePagesObj.add(0, abtusObj);
        abtusObj.setPageIndex(0);
        MainActivity.listOfTemplatePagesObj.add(1, homeObj);
        abtusObj.setPageIndex(1);
        MainActivity.listOfTemplatePagesObj.add(2, blogpage);
        abtusObj.setPageIndex(2);
        MainActivity.listOfTemplatePagesObj.add(3, contactdetails);
        abtusObj.setPageIndex(3);
        MainActivity.listOfTemplatePagesObj.add(4, ServicePage);
        abtusObj.setPageIndex(4);

        dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(0).getTemplateData(typeOfTemplateSelected, true);

       // Class intenetToLaunch = data.getIntentToLaunchPage();
      //  Log.v("Create homepage", "5" + intenetToLaunch);
      //  Intent intent = new Intent(getApplicationContext(), intenetToLaunch);

        Intent intent = new Intent(getApplicationContext(), FragmenMainActivity.class);
        intent.putExtra("data",data);
        startActivity(intent);


    }


    public void addTemplate(View view) {

        Log.v("CreateCampain_Sucess","Clicked to start choosing template");
//todo tempLoad Vijay
        startCreateCampaignWithDefaultData(1);

        //Intent iny = new Intent(this,TempActivity.class);


     //  Intent iny = new Intent(this,ChooseTemplate_Category.class);
       // startActivity(iny);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreateCampain_Sucess.this,CreateCampaign_homePage.class));
        finish();
    }
}
