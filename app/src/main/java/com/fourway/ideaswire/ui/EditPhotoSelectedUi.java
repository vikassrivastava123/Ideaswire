package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.SearchProfileData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.SearchProfileRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    }

    void showImage(){

        Log.v("EditPhotoSelectedUi","showImage");
        try {
            FileInputStream in = openFileInput(MainActivity.SEARCH__IMAGE_CROPED_NAME);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            CroppedimageView.setImageDrawable(new BitmapDrawable(getResources(),bitmap));
            in.close();
        }catch (FileNotFoundException e){
            Log.v("EditPhotoSelectedUi","Imaged file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private File getFileObjectFromBitmap (Bitmap b) throws IOException {
        File f = new File(getApplicationContext().getCacheDir(), "Abc");
//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return f;
    }


    public void TestSearch(View view) {
        Log.v("TestSearch", "start");
       /* if(imgUri == null){
            Log.v("TestSearch","imgUri is null");
            return;
        }*/
        //File sendToSearch = new File()   //FileUtils.getFile(EditPhotoSelectedUi.this,imgUri);
        FileInputStream in = null;
        File fileObj = null;
        try {
            in = openFileInput(MainActivity.SEARCH__IMAGE_CROPED_NAME);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            fileObj = getFileObjectFromBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Bitmap bitmap = BitmapFactory.decodeStream(in);
        SearchProfileData data = new SearchProfileData(fileObj,"test",loginUi.mLogintoken);
        SearchProfileRequest req = new SearchProfileRequest(this, data,this);
        req.executeRequest();
    }

    public void onCancel(View view){
        finish();
    }

    @Override
    public void onSearchResponse(CommonRequest.ResponseCode res, SearchProfileData data) {
        Log.v("search results","res :"+res);

        if(CommonRequest.ResponseCode.COMMON_RES_SUCCESS == res){

            //Toast.makeText(getApplicationContext(), "Search waas sucess :)", Toast.LENGTH_SHORT).show();
            loginUi.mProfileList = data.getProfileList();
            int numberOfProfiles = loginUi.mProfileList.size();
            Log.v("SearchProfile","number of profiles searched"+numberOfProfiles);
            if(numberOfProfiles > 0) {
                Intent intent = new Intent(getApplicationContext(), searchResults.class);
                startActivity(intent);
            }


        }else{

            Toast.makeText(getApplicationContext(), "Fail to find ):", Toast.LENGTH_SHORT).show();
        }


        //TODO Vijay
        ///Intent iny = new Intent(this,TempActivity.class);

        //   Intent iny = new Intent(this,ChooseTemplate_Category.class);
       // startActivity(iny);


    }
}
