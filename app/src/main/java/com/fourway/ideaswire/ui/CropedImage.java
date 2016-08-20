package com.fourway.ideaswire.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class CropedImage extends AppCompatActivity implements CropImageView.OnGetCroppedImageCompleteListener,  CropImageView.OnSetImageUriCompleteListener{


    private CropImageView mCropImageView;

    private View mProgressView;

    private Uri mCropImageUri;

    private TextView mProgressViewText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croped_image);


        mCropImageView = (CropImageView)  findViewById(R.id.CropImageView);
        mProgressView =  findViewById(R.id.ProgressView);
        mProgressViewText = (TextView)  findViewById(R.id.ProgressViewText);


        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_IMAGE_SELECTOR);



    }


    @Override
    protected void onStart() {
        super.onStart();
        mCropImageView.setOnSetImageUriCompleteListener(this);
        mCropImageView.setOnGetCroppedImageCompleteListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCropImageView.setOnSetImageUriCompleteListener(null);
        mCropImageView.setOnGetCroppedImageCompleteListener(null);
    }
    final int REQUEST_GALLERY_IMAGE_SELECTOR = 101;
//    public void onLoadImageClick(View view) {
//     //   CropImage.startPickImageActivity(this);
//
//        Intent intent = new Intent();
//        // Show only images, no videos or anything else
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        // Always show the chooser (if there are multiple options available)
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_IMAGE_SELECTOR);
//    }

    public void onCropImageClick(View view) {
        mCropImageView.getCroppedImageAsync(500, 500);
        mProgressViewText.setText("Cropping...");
        mProgressView.setVisibility(View.VISIBLE);

        Intent EditPhotoIntent = new Intent(this, EditPhotoSelectedUi.class);

        Log.e("Crop", "onCropImageClick to load image for cropping");
        String uriString  = mCropImageUri.getPath();

        Log.e("Crop", "onCropImageClick uri 1" + uriString);
        String test = FileUtils.getPath(this,mCropImageUri);
        Log.e("Crop", "onCropImageClick uri 2" + test);
        if(test == null){
            test = uriString;

        }else {
        }

        EditPhotoIntent.putExtra("imageUri", test);
        startActivity(EditPhotoIntent);

    }

    public void createImagefromBitmap(Bitmap bitmap){
        String fileName = "Imaged";
        try{

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }catch (Exception e){
            e.printStackTrace();
            fileName=null;
        }
    }

    @Override
    public void  onSetImageUriComplete(CropImageView cropImageView, Uri uri, Exception error) {
        mProgressView.setVisibility(View.INVISIBLE);
        if (error != null) {
            Log.e("Crop", "Failed to load image for cropping", error);
            Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_LONG).show();
        }else{
            Log.e("Crop", "Success to load image for cropping", error);
            mCropImageUri = uri;
        }
    }

    @Override
    public void  onGetCroppedImageComplete(CropImageView view, Bitmap bitmap, Exception error) {
        mProgressView.setVisibility(View.INVISIBLE);
        if (error == null) {
            if (bitmap != null) {
                mCropImageView.setImageBitmap(bitmap);
            }
        } else {
            Log.e("Crop", "Failed to crop image", error);
            createImagefromBitmap(bitmap);

            Intent EditPhotoIntent = new Intent(this, EditPhotoSelectedUi.class);
            startActivity(EditPhotoIntent);
            //Toast.makeText(this,  "Something went wrong, try again", Toast.LENGTH_LONG).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int  requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage,
            // but we don't know if we need to for the URI so the simplest is to try open the stream and see if we get error.
            boolean requirePermissions = false;
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {

                // request permissions and handle the result in onRequestPermissionsResult()
                requirePermissions = true;
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }

            if (!requirePermissions) {
                mCropImageView.setImageUriAsync(imageUri);
                mProgressViewText.setText("Loading...");
                mProgressView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mCropImageView.setImageUriAsync(mCropImageUri);
            mProgressViewText.setText("Loading...");
            mProgressView.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    public void onRotateImageClick(View view) {
        mCropImageView.rotateImage(90);
    }
}
