package com.fourway.ideaswire.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.fourway.ideaswire.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CreateCampain_Sucess extends AppCompatActivity {

    ImageView selImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campain__sucess);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

}
