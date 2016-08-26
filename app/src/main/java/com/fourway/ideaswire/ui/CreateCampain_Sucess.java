package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourway.ideaswire.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CreateCampain_Sucess extends AppCompatActivity {

    ImageView selImage;
    TextView t1,t2,t3;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campain__sucess);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
            FileInputStream in = openFileInput("Imaged");
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            selImage.setImageDrawable(new BitmapDrawable(getResources(), bitmap));

        }catch (FileNotFoundException e){
            Log.v("CreateCampain_Sucess","Imaged file not found");
        }

    }

    public void addTemplate(View view) {

        Log.v("CreateCampain_Sucess","Clicked to start choosing template");
        Intent iny = new Intent(this,ChooseTemplate_Category.class);
        startActivity(iny);
    }
}
