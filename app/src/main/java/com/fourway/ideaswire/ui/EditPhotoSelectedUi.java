package com.fourway.ideaswire.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fourway.ideaswire.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EditPhotoSelectedUi extends AppCompatActivity {

    Uri imgUri;
    ImageView CroppedimageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo_selected_ui);

     //   Intent intent = getIntent();
     //   String image_path= intent.getStringExtra("imageUri");

        Log.v("EditPhotoSelectedUi","start");
        CroppedimageView = (ImageView) findViewById(R.id.capturedImage);
        showImage();
        /*if (CroppedimageView != null && image_path != null) {
            imgUri=Uri.parse(image_path);
            if(imgUri != null) {

                Log.v("EditPhotoSelectedUi", "image_path" + image_path);
                Log.v("EditPhotoSelectedUi","imgUri" + imgUri);

                CroppedimageView.setImageURI(null);
                CroppedimageView.setImageURI(imgUri);
            }else{
                Log.v("EditPhotoSelectedUi","imageView not available");
            }
        }else{
            Log.v("EditPhotoSelectedUi","imageView not available");

        }*/


    }

    void showImage(){

        Log.v("EditPhotoSelectedUi","showImage");
        try {
            FileInputStream in = openFileInput("ImagedA");
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            CroppedimageView.setImageDrawable(new BitmapDrawable(getResources(),bitmap));
        }catch (FileNotFoundException e){
            Log.v("EditPhotoSelectedUi","Imaged file not found");
        }

    }


    public void TestSearch(View view) {

        Log.v("TestSearch","start");

        if(imgUri == null){
            Log.v("TestSearch","imgUri is null");
            return;
        }

  /*      File sendToSearch = FileUtils.getFile(EditPhotoSelectedUi.this,imgUri);
        ImageUploadData data = new ImageUploadData(sendToSearch,"test",loginUi.mLogintoken);

        ImageUploadRequest req = new ImageUploadRequest(EditPhotoSelectedUi.this, data);
        req.executeRequest();*/



    }
}
