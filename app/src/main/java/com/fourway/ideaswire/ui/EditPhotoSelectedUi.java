package com.fourway.ideaswire.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.ImageUploadData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.ImageUploadRequest;

import java.io.File;

import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_IMAGE_NOT_FOUND;
import static com.fourway.ideaswire.request.CommonRequest.ResponseCode.COMMON_RES_SUCCESS;

public class EditPhotoSelectedUi extends AppCompatActivity implements ImageUploadRequest.SearchResponseCallback{

    Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo_selected_ui);

        Intent intent = getIntent();
        String image_path= intent.getStringExtra("imageUri");


        ImageView imageView = (ImageView) findViewById(R.id.capturedImage);
        if (imageView != null && image_path != null) {
            imgUri=Uri.parse(image_path);
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




    public void TestSearch(View view) {

        if(imgUri == null){
            Log.v("TestSearch","imgUri is null");
            return;
        }

        File sendToSearch = FileUtils.getFile(EditPhotoSelectedUi.this,imgUri);
        ImageUploadData data = new ImageUploadData(sendToSearch,"test",loginUi.mLogintoken);

        ImageUploadRequest req = new ImageUploadRequest(EditPhotoSelectedUi.this, data, this);
        req.executeRequest();



    }

    @Override
    public void onSearchResponse(CommonRequest.ResponseCode res, ImageUploadData data) {
        if (res == COMMON_RES_IMAGE_NOT_FOUND){
            //TODO: Go in create profile.
        }
        else if (res == COMMON_RES_SUCCESS)
        {
            //TODO: Show profile scren
        }
        else{
            //TODO: Do error handling
        }
    }
}
