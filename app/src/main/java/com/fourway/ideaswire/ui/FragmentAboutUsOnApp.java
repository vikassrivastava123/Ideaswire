package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.UploadImageForUrlData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.SaveProfileData;
import com.fourway.ideaswire.request.UploadImageForUrlRequest;
import com.fourway.ideaswire.request.helper.VolleySingleton;
import com.fourway.ideaswire.templates.AboutUsDataTemplate;
import com.fourway.ideaswire.templates.dataOfTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



/**
 * Created by 4way on 15-10-2016.
 */
public class FragmentAboutUsOnApp extends Fragment  implements SaveProfileData.SaveProfileResponseCallback  , UploadImageForUrlRequest.UploadImageForUrlCallback{

    ImageView deleteTitleAboutUsBtnView = null,deleteCARD_IMAGEBtnView = null;
    ImageView deleteHeadingAboutUsBtnView = null;
    ImageView deleteSubHeaderAboutUsBtnView = null;
    ImageView deleteParaAboutUsBtnView = null;

    EditText editTitle = null,editHeader = null,editSubHeading = null,editParaGraphAboutUs = null;

    NetworkImageView cardImage;
    ImageView cardImageCrop;
    Button submit_button;

    AboutUsDataTemplate dataObj = null;

    private static String TAG = "FragmentAboutUsOnApp";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_about, container, false);

        dataObj = (AboutUsDataTemplate)savedInstanceState.getSerializable("dataKey");

        deleteTitleAboutUsBtnView = (ImageView)view.findViewById(R.id.deleteTitleAboutUs);
        deleteCARD_IMAGEBtnView = (ImageView)view.findViewById(R.id.deleteCARD_IMAGE);
        deleteHeadingAboutUsBtnView = (ImageView)view.findViewById(R.id.deleteHeadingAboutUs);
        deleteSubHeaderAboutUsBtnView = (ImageView)view.findViewById(R.id.deleteSubHeaderAboutUs);
        deleteParaAboutUsBtnView = (ImageView)view.findViewById(R.id.deleteParaAboutUs);


        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        String title = dataObj.get_title();
        editTitle = (EditText) view.findViewById(R.id.ABOUT_TITLE);
        if(title != null) {
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else{
            editTitle.setVisibility(View.GONE);
        }

        String header = dataObj.get_heading();
        editHeader = (EditText) view.findViewById(R.id.ABOUT_US_HEADING);
        if(header != null) {
            editHeader.setText(header);
            editHeader.setTypeface(mycustomFont);
        }else{
            editHeader.setVisibility(View.GONE  );
        }

        String subHeading = dataObj.get_sub_heading();
        editSubHeading = (EditText) view.findViewById(R.id.ABOUT_US_SUBHEADING);
        if(subHeading != null) {
            editSubHeading.setText(subHeading);
            editSubHeading.setTypeface(mycustomFont);
        }else{
            editSubHeading.setVisibility(View.GONE);
        }

        String paraGraphAboutUs =  dataObj.get_text_para();
        editParaGraphAboutUs = (EditText) view.findViewById(R.id.paraGraphAboutUs);
        if(paraGraphAboutUs != null){
            editParaGraphAboutUs.setText(paraGraphAboutUs);
            editParaGraphAboutUs.setTypeface(mycustomFont);
        }else{
            editParaGraphAboutUs.setVisibility(View.GONE);
        }

        submit_button = (Button)  view.findViewById(R.id.buttonMainAbtUs);
        cardImage = (NetworkImageView)  view.findViewById(R.id.ABOUT_US_CARD_IMAGE);
        cardImageCrop = (ImageView) view.findViewById(R.id.ABOUT_US_STATIC_IMAGE);

        String urlOfProfile = dataObj.get_url();
        if(urlOfProfile != null && !urlOfProfile.equalsIgnoreCase("null")){
            cardImage.setImageUrl(urlOfProfile, VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());
            cardImageCrop.setVisibility(View.GONE);

        }else{
            cardImage.setVisibility(View.GONE);
            cardImageCrop.setImageResource(R.drawable.about_banner_1);
        }

        //TODO Vijay
        //showImageForBackround();
        submit_button.setTypeface(mycustomFont);
        submit_button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /*
                TODO Vijay

                if(showPreview == false) {

                    showMenuOptionToLinkPage();
                }else{

                    int posInListOfPage = dataObj.get_submit_button_link();

                    if(posInListOfPage >= 0) {
                        dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(posInListOfPage).getTemplateData(1,false);

                        Class intenetToLaunch = data.getIntentToLaunchPage();
                        Intent intent = new Intent(getApplicationContext(), intenetToLaunch);
                        intent.putExtra("data", data);
                        startActivity(intent);
                    }
                }*/


            }
        });

        String submitBtnText = dataObj.get_button_text();

        if(submitBtnText != null) {
            submit_button.setText(submitBtnText);
        }else{
            submit_button.setVisibility(View.GONE);
        }


        return view;
    }


    void init_viewCampaign(){

        try {
            deleteTitleAboutUsBtnView.setVisibility(View.GONE);
            deleteCARD_IMAGEBtnView.setVisibility(View.GONE);
            deleteHeadingAboutUsBtnView.setVisibility(View.GONE);
            deleteSubHeaderAboutUsBtnView.setVisibility(View.GONE);
            deleteParaAboutUsBtnView.setVisibility(View.GONE);
        }catch (NullPointerException e){
            Log.v(TAG,"Null in init_viewCampaign");
        }
        if(editTitle !=null){
            editTitle.setEnabled(false);
            editTitle.setKeyListener(null);
        }

        if(editHeader !=null){
            editHeader.setEnabled(false);
            editHeader.setKeyListener(null);
        }

        if(editSubHeading !=null){
            editSubHeading.setEnabled(false);
            editSubHeading.setKeyListener(null);
        }

        if(editParaGraphAboutUs !=null){
            editParaGraphAboutUs.setEnabled(false);
            editParaGraphAboutUs.setKeyListener(null);
        }

    }

    void init_editCampaign(){

        try {
            deleteTitleAboutUsBtnView.setVisibility(View.VISIBLE);
            deleteCARD_IMAGEBtnView.setVisibility(View.VISIBLE);
            deleteHeadingAboutUsBtnView.setVisibility(View.VISIBLE);
            deleteSubHeaderAboutUsBtnView.setVisibility(View.VISIBLE);
            deleteParaAboutUsBtnView.setVisibility(View.VISIBLE);
        }catch (NullPointerException e){
            Log.v(TAG,"Null in init_viewCampaign");
        }
        if(editTitle !=null){
            editTitle.setEnabled(true);
            editTitle.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(editHeader !=null){
            editHeader.setEnabled(true);
            editHeader.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(editSubHeading !=null){
            editSubHeading.setEnabled(true);
            editSubHeading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(editParaGraphAboutUs !=null){
            editParaGraphAboutUs.setEnabled(true);
            editParaGraphAboutUs.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

    }


    ProgressDialog pbImage = null;

    @Override
    public void onProfileSaveResponse(CommonRequest.ResponseCode res, Profile data) {

    }

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {

    }


    private File getFileObjectFromBitmap (Bitmap b) throws IOException {
        File f = new File(getActivity().getApplicationContext().getCacheDir(), "Abc");

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


    private class PhotoAsyncTask extends AsyncTask<Void, Void, Void>
    {


        public void AboutUsImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = getActivity().openFileInput(MainActivity.About_Us_TemplateImage_IMAGE_CROPED_NAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            File sendFile = getFileObjectFromBitmap (bitmap);


            UploadImageForUrlData data =
                    new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "About us banner", 1);
            UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity().getApplicationContext(), data, FragmentAboutUsOnApp.this);
            req.executeRequest();

            //todo set image
       /*     runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cardImage.setVisibility(View.GONE);
                    cardImageCrop.setVisibility(View.VISIBLE);
                    cardImageCrop.setImageDrawable(new BitmapDrawable(getResources(), bitmap));

                }
            });*/


        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                AboutUsImageUpload();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pbImage = new ProgressDialog(getActivity().getApplicationContext());
            pbImage.setMessage("Uploading Image...");

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }


    private void showImageForBackround(){
        Log.v("editCampaign", "showImageCampaign");
        try {
            FileInputStream in = getActivity().openFileInput(MainActivity.About_Us_TemplateImage_IMAGE_CROPED_NAME);

            cardImage.setVisibility(View.GONE);
            cardImageCrop.setVisibility(View.VISIBLE);

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            cardImageCrop.setImageDrawable(new BitmapDrawable(getResources(), bitmap));


        }catch (FileNotFoundException e){
            Log.v("editCampaign","About_Us_TemplateImage_IMAGE_CROPED_NAME file not found");
        }

    }






}
