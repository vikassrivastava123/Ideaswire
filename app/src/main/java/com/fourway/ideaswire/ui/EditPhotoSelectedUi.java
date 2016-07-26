package com.fourway.ideaswire.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.fourway.ideaswire.R;

public class EditPhotoSelectedUi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo_selected_ui);

        Intent intent = getIntent();
        String image_path= intent.getStringExtra("imageUri");


        ImageView imageView = (ImageView) findViewById(R.id.capturedImage);
        if (imageView != null && image_path != null) {
            Uri imgUri=Uri.parse(image_path);
            if(imgUri != null) {

                Log.d("EditPhotoSelectedUi","image_path" + image_path);
                Log.d("EditPhotoSelectedUi","imgUri" + imgUri);

                imageView.setImageURI(null);
                imageView.setImageURI(imgUri);
            }else{
                Log.d("EditPhotoSelectedUi","imageView not available");
            }
        }else{
            Log.d("EditPhotoSelectedUi","imageView not available");

        }


    }

}
