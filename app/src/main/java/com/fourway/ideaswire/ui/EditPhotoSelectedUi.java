package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.SearchProfileData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.SearchProfileRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EditPhotoSelectedUi extends Activity implements SearchProfileRequest.SearchResponseCallback {

    Uri imgUri;
    ImageView CroppedimageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo_selected_ui);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // back button pressed
//                finish();
//                //Intent intent = new Intent(getApplicationContext(),CreateCampaign_homePage.class);
//                //startActivity(intent);
//            }
//        });
//        Intent intent = getIntent();
//        String image_path= intent.getStringExtra("imageUri");

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
            FileInputStream in = openFileInput(MainActivity.SEARCH__IMAGE_CROPED_NAME);
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

        File sendToSearch = FileUtils.getFile(EditPhotoSelectedUi.this,imgUri);
        SearchProfileData data = new SearchProfileData(sendToSearch,"test",loginUi.mLogintoken);

        SearchProfileRequest req = new SearchProfileRequest(this, data,this);
        req.executeRequest();



    }

    @Override
    public void onSearchResponse(CommonRequest.ResponseCode res, SearchProfileData data) {
        Log.d("sera resulr","res :"+res);
    }
}
