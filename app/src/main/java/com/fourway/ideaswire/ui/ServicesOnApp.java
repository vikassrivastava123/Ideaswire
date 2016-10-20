package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Attribute;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.ProfileFieldsEnum;
import com.fourway.ideaswire.data.UploadImageForUrlData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.SaveProfileData;
import com.fourway.ideaswire.request.UploadImageForUrlRequest;
import com.fourway.ideaswire.templates.ServicesDataTemplate;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ServicesOnApp extends Activity implements SaveProfileData.SaveProfileResponseCallback, UploadImageForUrlRequest.UploadImageForUrlCallback{
    EditText editTitle, editHeading, editSubHeading, editParaGraph, editHeading_below, editSubHeading_below, editParaGraph_below;
    TextView mTitle=null;

    ImageView deleteTitle=null,
    deleteHeading=null,
    deleteSubHeading=null,
    deletePara=null,
    deleteCardImage=null,
    deleteHeadingBlow=null,
    deleteSubHeadingBlow=null,
    deleteParaBlow=null;

    NetworkImageView cardImage;
    ImageView cardImageCrop;

    int cropRestart=0;

    public static String TAG="ServiceOnApp";

    ServicesDataTemplate dataObj;
    private boolean showPreview = false;

    void init_viewCampaign(){

        try {
            deleteTitle.setVisibility(View.GONE);
            deleteHeading.setVisibility(View.GONE);
            deleteSubHeading.setVisibility(View.GONE);
            deletePara.setVisibility(View.GONE);
            deleteCardImage.setVisibility(View.GONE);
            deleteHeadingBlow.setVisibility(View.GONE);
            deleteSubHeadingBlow.setVisibility(View.GONE);
            deleteParaBlow.setVisibility(View.GONE);
        }catch (NullPointerException e)
        {
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if(editTitle !=null) {
            editTitle.setEnabled(false);
            editTitle.setKeyListener(null);
        }

        if(editHeading !=null) {
            editHeading.setEnabled(false);
            editHeading.setKeyListener(null);
        }
        if (editSubHeading !=null) {
            editSubHeading.setEnabled(false);
            editSubHeading.setKeyListener(null);
        }
        if (editParaGraph !=null) {
            editParaGraph.setEnabled(false);
            editParaGraph.setKeyListener(null);
        }

        if (editHeading_below !=null) {
            editHeading_below.setEnabled(false);
            editHeading_below.setKeyListener(null);
        }
        if (editSubHeading_below !=null){
            editSubHeading_below.setEnabled(false);
            editSubHeading_below.setKeyListener(null);
        }
        if (editParaGraph_below !=null){
            editParaGraph_below.setEnabled(false);
            editParaGraph_below.setKeyListener(null);
        }
    }


    public String toString()
    {
        return "Services";
    }

    void showPreview(){

        init_viewCampaign();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        showImageForBackround();
        if(cropRestart==1) {
            PhotoAsyncTask obj = new PhotoAsyncTask();
            obj.execute();
            cropRestart=0;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_on_app);

        deleteTitle =(ImageView)findViewById(R.id.deleteTitleService);
        deleteHeading =(ImageView)findViewById(R.id.deleteHeadingService);
        deleteSubHeading =(ImageView)findViewById(R.id.deleteSubHeadingService);
        deletePara =(ImageView)findViewById(R.id.deleteParaService);
        deleteCardImage =(ImageView)findViewById(R.id.deleteCardService);
        deleteHeadingBlow =(ImageView)findViewById(R.id.deleteHeadingBlowingService);
        deleteSubHeadingBlow =(ImageView)findViewById(R.id.deleteSubHeadingBlowingService);
        deleteParaBlow =(ImageView)findViewById(R.id.deleteParaBlowingService);

        cardImage =(NetworkImageView)findViewById(R.id.Service_CARD_IMAGE);
        cardImageCrop =(ImageView)findViewById(R.id.Service_STATIC_IMAGE);

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dynamicPages);
        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                int size = MainActivity.listOfTemplatePagesObj.size();
                Button[] btn = new Button[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(ServicesOnApp.this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT));
                for(pages obj: MainActivity.listOfTemplatePagesObj) {
                    String name = obj.nameis();



                    float x =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                    float y =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());

                    LinearLayout.LayoutParams buttonLayoutParams =
                            new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                                    (int)x,
                                    (int)y));
                    buttonLayoutParams.setMargins(2,2, 0, 0);
                    btn[i] = new Button(ServicesOnApp.this);

                    btn[i].setLayoutParams(buttonLayoutParams);
                    btn[i].setText(name);
                    btn[i].setId(i);
                    btn[i].setBackgroundColor(getResources().getColor(R.color.card));
                    btn[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //Toast.makeText(getApplicationContext(),
                            //       "button is clicked" + v.getId(), Toast.LENGTH_LONG).show();
                            dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(v.getId()).getTemplateData(1,dataObj.isDefaultDataToCreateCampaign());

                            Class intenetToLaunch = data.getIntentToLaunchPage();
                            Log.v(TAG, "5" + intenetToLaunch);
                            Intent intent = new Intent(getApplicationContext(), intenetToLaunch);
                            intent.putExtra("data",data);
                            startActivity(intent);
                        }
                    });
                    if (!name.equals("Service"))
                    row.addView(btn[i]);
                    // Add the LinearLayout element to the ScrollView
                    i++;
                }
               // btn[4].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));
                //btn[4].setFocusable(true);
                //btn[4].setFocusableInTouchMode(true);
                // When adding another view, make sure you do it on the UI
                // thread.
                layout.post(new Runnable() {

                    public void run() {

                        layout.addView(row);
                    }
                });
            }
        }, 500);

        dataObj = (ServicesDataTemplate)getIntent().getSerializableExtra("data");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });
        editTitle =(EditText)findViewById(R.id.Service_TITLE);
        editHeading = (EditText) findViewById(R.id.service_heading);
        editSubHeading = (EditText) findViewById(R.id.service_subheading);
        editParaGraph = (EditText) findViewById(R.id.service_para);
        editHeading_below = (EditText) findViewById(R.id.service_heading_belowimg);
        editSubHeading_below = (EditText) findViewById(R.id.service_subheading_belowimg);
        editParaGraph_below = (EditText) findViewById(R.id.service_para_belowing);


        String urlOfProfile = dataObj.getUrlOfImage();

        if(urlOfProfile != null){
            Uri cardImageUri = Uri.parse(urlOfProfile);
            cardImage.setImageURI(cardImageUri);
            cardImageCrop.setVisibility(View.GONE);

        }else{
            cardImage.setVisibility(View.GONE);
            cardImageCrop.setImageResource(R.drawable.about_banner_1);

        }

        showImageForBackround();

        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");

        String title=dataObj.getTitle();
        if (title!=null){
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else{
            editTitle.setVisibility(View.GONE);
        }

        String heading=dataObj.getHeading();
        if (heading!=null){
            editHeading.setText(heading);
            editHeading.setTypeface(mycustomFont);
        }else{
            editHeading.setVisibility(View.GONE);
        }

        String subHeading=dataObj.getSubHeading();
        if (subHeading!=null){
            editSubHeading.setText(subHeading);
            editSubHeading.setTypeface(mycustomFont);
        }else{
            editSubHeading.setVisibility(View.GONE);
        }

        String para=dataObj.getParaGraph();
        if (para!=null){
            editParaGraph.setText(para);
            editParaGraph.setTypeface(mycustomFont);
        }else{
            editParaGraph.setVisibility(View.GONE);
        }

        String headingBelow=dataObj.getHeading_below();
        if (headingBelow!=null){
            editHeading_below.setText(headingBelow);
            editHeading_below.setTypeface(mycustomFont);
        }else{
            editSubHeading_below.setVisibility(View.GONE);
        }

        String subHeadingBelow=dataObj.getSubHeading_below();
        if (subHeadingBelow!=null){
            editSubHeading_below.setText(subHeadingBelow);
            editSubHeading_below.setTypeface(mycustomFont);
        }else{
            editSubHeading_below.setVisibility(View.GONE);
        }

        String paraBelow=dataObj.getGetParaGraph_below();
        if (paraBelow!=null){
            editParaGraph_below.setText(paraBelow);
            editParaGraph_below.setTypeface(mycustomFont);
        }else{
            editParaGraph_below.setVisibility(View.GONE);
        }

        // /setSupportActionBar(toolbar);

        mProfileId = editCampaign.mCampaignIdFromServer;
        mPageName = ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US;

        mServicePageObj  = new Page(mProfileId,mPageName);
        mParentId = mServicePageObj.getPageId();



       /* if(dataObj.isDefaultDataToCreateCampaign() == false)
            init_viewCampaign();*/

    }

    Page  mServicePageObj;
    String mProfileId = null;
    String mPageName = null;
    String mParentId = null;

    private void setAttribute(String name, String value){

        Attribute atrbtObj = new Attribute(mProfileId,mParentId,name,value);
        mServicePageObj.addAttribute(atrbtObj);
    }

    Profile requestToMakeProfile;

    void init_editCampaign() {


        try {
            deleteTitle.setVisibility(View.VISIBLE);
            deleteHeading.setVisibility(View.VISIBLE);
            deleteSubHeading.setVisibility(View.VISIBLE);
            deletePara.setVisibility(View.VISIBLE);
            deleteCardImage.setVisibility(View.VISIBLE);
            deleteHeadingBlow.setVisibility(View.VISIBLE);
            deleteSubHeadingBlow.setVisibility(View.VISIBLE);
            deleteParaBlow.setVisibility(View.VISIBLE);
        }catch (NullPointerException e)
        {
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if(editTitle !=null) {
            editTitle.setEnabled(true);
            editTitle.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if(editHeading !=null) {
            editHeading.setEnabled(true);
            editHeading.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
        if (editSubHeading !=null) {
            editSubHeading.setEnabled(true);
            editSubHeading.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
        if (editParaGraph !=null) {
            editParaGraph.setEnabled(true);
            editParaGraph.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if (editHeading_below !=null) {
            editHeading_below.setEnabled(true);
            editHeading_below.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
        if (editSubHeading_below !=null){
            editSubHeading_below.setEnabled(true);
            editSubHeading_below.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
        if (editParaGraph_below !=null){
            editParaGraph_below.setEnabled(true);
            editParaGraph_below.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }


    }

    public void previewTemplate(View view) {
        TextView textViewShowPreview = (TextView)findViewById(R.id.textShow_preview);

        FloatingActionButton btn  = (FloatingActionButton)findViewById(R.id.floatingForPreview);
        Button showPreviewBtn  = (Button)findViewById(R.id.showPreview);


        if(showPreview == false) {
            textViewShowPreview.setText("Edit");
            btn.show();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_edit);
            init_viewCampaign();
            showPreview = true;
        }else {
            textViewShowPreview.setText("Preview");
            btn.hide();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_about);
            init_editCampaign();
            showPreview = false;
        }


    }

    public void pageTemplate(View view) {
        startActivity(new Intent(getApplicationContext(),about_us_page_template.class));
    }

    ProgressDialog pbImage = null;

    @Override
    public void onProfileSaveResponse(CommonRequest.ResponseCode res, Profile data) {
        Log.v(TAG,"ResponseCode = " + res);
        Toast.makeText(this, String.valueOf(res), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {
        pbImage.hide();
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            String imageUrl = data.getResponseUrl();
            dataObj.setUrlOfImage(imageUrl);
            Log.v(TAG,"Url received" + imageUrl);
        }
    }

    public void GoLiveFloatingService(View view) {
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_TITLE, dataObj.getTitle());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_HEADING_1, dataObj.getHeading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_HEADING_2, dataObj.getHeading_below());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_SUBHEADING_1, dataObj.getSubHeading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_HEADING_2, dataObj.getSubHeading_below());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_CARD_IMAGE, dataObj.getUrlOfImage());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_PARAGRAPH_1, dataObj.getParaGraph());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_PARAGRAPH_2, dataObj.getParaGraph_below);



        requestToMakeProfile = new Profile(editCampaign.mCampaignIdFromServer, Profile.TemplateID.PROFILE_TEMPLATE_ID_T1);
        requestToMakeProfile.addPage(mServicePageObj);
        SaveProfileData req = new SaveProfileData(this,requestToMakeProfile,loginUi.mLogintoken,this);
        req.executeRequest();
    }

    private class PhotoAsyncTask extends AsyncTask<Void, Void, Void>
    {


        public void ServiceImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = openFileInput(MainActivity.About_Us_TemplateImage_IMAGE_CROPED_NAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            File sendFile = getFileObjectFromBitmap (bitmap);


            UploadImageForUrlData data =
                    new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "Service us banner", 1);
            UploadImageForUrlRequest req = new UploadImageForUrlRequest(ServicesOnApp.this, data, ServicesOnApp.this);
            req.executeRequest();

            //todo set image
           /* runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cardImage.setVisibility(View.GONE);
                    cardImageCrop.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                    cardImageCrop.setVisibility(View.VISIBLE);
                }
            });*/


        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                ServiceImageUpload();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pbImage = new ProgressDialog(ServicesOnApp.this);
            pbImage.setMessage("Uploading Image...");

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }


    private void showImageForBackround(){
        Log.v("editCampaign", "showImageCampaign");
        try {
            FileInputStream in = openFileInput(MainActivity.Service_TemplateImage_IMAGE_CROPED_NAME);

            cardImage.setVisibility(View.GONE);
            cardImageCrop.setVisibility(View.VISIBLE);

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            cardImageCrop.setImageDrawable(new BitmapDrawable(getResources(), bitmap));



        }catch (FileNotFoundException e){
            Log.v("editCampaign","Service_TemplateImage_IMAGE_CROPED_NAME file not found");
        }

    }

    public void uploadToServiceOnApp(View view) {

        if(showPreview == false) {
            String campnName = null;
         /*
        * Need to open gallery directly from here
        * From Cropped image OK clicked editCampaign.java will be opened
        * In editCampaign.java this campaign name(campnName) will be used to set defualt text
        * ScreenName will be used by CropedImage as it will be used to open gallery by multiple classes
        * */

            Intent inf = new Intent(this, CropedImage.class);
            inf.putExtra("ScreenName", MainActivity.Service_TemplateImage_IMAGE_CROPED_NAME);
            inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_SERVICE_ON_APP);

            inf.putExtra("CampaignName", "Choose Image");
            startActivity(inf);


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


    public void deleteTitleService(View view) {
        editTitle.setVisibility(View.GONE);
        deleteTitle.setVisibility(View.GONE);
    }

    public void deleteHeadingService(View view){
        editHeading.setVisibility(View.GONE);
        deleteHeading.setVisibility(View.GONE);
    }
    public void deleteSubHeadingService(View view){
        editSubHeading.setVisibility(View.GONE);
        deleteSubHeading.setVisibility(View.GONE);
    }
    public void deleteParaService(View view){
        editParaGraph.setVisibility(View.GONE);
        deletePara.setVisibility(View.GONE);
    }
    public void deleteCardImageService(View view){
        cardImage.setVisibility(View.GONE);
        deleteCardImage.setVisibility(View.GONE);
    }
    public void deleteHeadingBelowimgService(View view){
        editHeading_below.setVisibility(View.GONE);
        deleteHeadingBlow.setVisibility(View.GONE);
    }

    public void deleteSubHeadingBelowimgService(View view){
        editSubHeading_below.setVisibility(View.GONE);
        deleteSubHeadingBlow.setVisibility(View.GONE);
    }

    public void deleteParaBelowingService(View view){
        editParaGraph_below.setVisibility(View.GONE);
        deleteParaBlow.setVisibility(View.GONE);
    }


    public void changeTitleTextService(View view){
        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextService" + title);
        if (title != null) {
            dataObj.setTitle(title);
        }
    }
    public void changeHeadingTxtService(View view){
        String header = String.valueOf(editHeading.getText());
        Log.d(TAG, "changeHeadingTxtService" + header);
        if (header != null) {
            dataObj.setHeading(header);
        }
    }

    public void changeSubHeadingService(View view){
        String subheader = String.valueOf(editSubHeading.getText());
        Log.d(TAG, "changeSubHeadingService" + subheader);
        if (subheader != null) {
            dataObj.setSubHeading(subheader);
        }
    }

    public void changeParaService(View view){
        String para = String.valueOf(editParaGraph.getText());
        Log.d(TAG, "changeParaService" + para);
        if (para != null) {
            dataObj.setParaGraph(para);
        }
    }

    public void changeHeadingTxtBelowService(View view){
        String headerBelow = String.valueOf(editHeading_below.getText());
        Log.d(TAG, "changeHeadingBelowTxtBlog" + headerBelow);
        if (headerBelow != null) {
            dataObj.setHeading_below(headerBelow);
        }
    }

    public void changeSubHeadingTxtBelowService(View view){
        String subheaderBelow = String.valueOf(editSubHeading_below.getText());
        Log.d(TAG, "changeSubHeadingBelowTxtBlog" + subheaderBelow);
        if (subheaderBelow != null) {
            dataObj.setSubHeading_below(subheaderBelow);
        }
    }

    public void changeParaTxtBelowService(View view){
        String paraBelow = String.valueOf(editParaGraph_below.getText());
        Log.d(TAG, "changeSubHeadingBelowTxtBlog" + paraBelow);
        if (paraBelow != null) {
            dataObj.setGetParaGraph_below(paraBelow);
        }
    }


}
