package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import com.fourway.ideaswire.request.helper.VolleySingleton;
import com.fourway.ideaswire.templates.blogpageDataTemplate;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class activity_blogpage extends Activity implements SaveProfileData.SaveProfileResponseCallback, UploadImageForUrlRequest.UploadImageForUrlCallback{
    EditText heading=null,subheading=null,paraGraphBlog=null,heading_belo=null,subheading_below=null,paraGraphBlog_below=null;
    List<String> attName;
    TextView mTitle=null;
    EditText editTitle = null;

    int cropRestart=0;

    NetworkImageView cardImage;
    ImageView cardImageCrop;

    private static String TAG = "BlogOnApp";

    ImageView deleteTitleBlogBtnView = null,deleteCARD_IMAGEBtnView = null;
    ImageView deleteHeadingBlogBtnView = null;
    ImageView deleteSubHeaderBlogBtnView = null;
    ImageView deleteParaBlogBtnView = null;

    ImageView deleteHeadingBelowimgBlogBtnView = null;
    ImageView deleteSubHeaderBelowimgBlogBtnView = null;
    ImageView deleteParaBlogBelowimgBtnView = null;

    RelativeLayout cardRelativeLayout=null;

    blogpageDataTemplate dataObj;
    private boolean showPreview = false;

    void init_viewCampaign(){

        try {
            deleteTitleBlogBtnView.setVisibility(View.GONE);
            deleteCARD_IMAGEBtnView.setVisibility(View.GONE);
            deleteHeadingBlogBtnView.setVisibility(View.GONE);
            deleteSubHeaderBlogBtnView.setVisibility(View.GONE);
            deleteParaBlogBtnView.setVisibility(View.GONE);
            deleteHeadingBelowimgBlogBtnView.setVisibility(View.GONE);
            deleteSubHeaderBelowimgBlogBtnView.setVisibility(View.GONE);
            deleteParaBlogBelowimgBtnView.setVisibility(View.GONE);
        }catch (NullPointerException e){
            Log.v(TAG,"Null in init_viewCampaign");
        }
        if(editTitle !=null){
            editTitle.setEnabled(false);
            editTitle.setKeyListener(null);
        }

        if(heading !=null){
            heading.setEnabled(false);
            heading.setKeyListener(null);
        }

        if(subheading !=null){
            subheading.setEnabled(false);
            subheading.setKeyListener(null);
        }

        if(paraGraphBlog !=null){
            paraGraphBlog.setEnabled(false);
            paraGraphBlog.setKeyListener(null);
        }

        if(heading_belo != null){
            heading_belo.setEnabled(false);
            heading_belo.setKeyListener(null);
        }

        if(subheading_below != null){
            subheading_below.setEnabled(false);
            subheading_below.setKeyListener(null);
        }

        if(paraGraphBlog_below !=null){
            paraGraphBlog_below.setEnabled(false);
            paraGraphBlog_below.setKeyListener(null);
        }

    }

    public String name()
    {
        return "Blogs";
    }

    void showPreview(){

        init_viewCampaign();

    }

    @Override
    protected void onRestart() {
        super.onRestart();


        if(cropRestart==1) {
            showImageForBackround();
            PhotoAsyncTask obj = new PhotoAsyncTask();
            obj.execute();
            cropRestart=0;
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_blogpage);

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dynamicPages);
        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                int size = MainActivity.listOfTemplatePagesObj.size();
                final Button[] btn = new Button[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(activity_blogpage.this);
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

                    btn[i] = new Button(activity_blogpage.this);

                    btn[i].setLayoutParams(buttonLayoutParams);
                    btn[i].setText(name);
                    btn[i].setId(i);
                    btn[i].setBackgroundColor(getResources().getColor(R.color.card));
                    btn[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            if (!btn[v.getId()].getText().toString().equals("Blog")) {

                                changeText();

                                dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(v.getId()).getTemplateData(1, dataObj.isDefaultDataToCreateCampaign());
                                final int indexInList =  2;//data.getPositionInList();
                                Class intenetToLaunch = data.getIntentToLaunchPage();
                                Log.v(TAG, "5" + intenetToLaunch);
                                final Intent intent = new Intent(getApplicationContext(), intenetToLaunch);
                                intent.putExtra("data", data);

                                AlertDialog.Builder dialog=new AlertDialog.Builder(activity_blogpage.this);
                                dialog.setMessage("Want to add this page?");

                                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        blogPageToRequest();

                                        if(indexInList >=0 ){
                                            changeText();
                                            MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataObj);
                                        }

                                        startActivity(intent);
                                    }
                                });

                                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(intent);
                                    }
                                });

                                if (dataObj.isDefaultDataToCreateCampaign() == true) {
                                    dialog.show();
                                }else {
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    });

                    if (btn[i].getText().toString().equals("Blog"))
                        btn[i].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));

                    row.addView(btn[i]);
                    // Add the LinearLayout element to the ScrollView
                    i++;
                }
                //btn[2].setFocusable(true);
                //btn[2].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));
                // When adding another view, make sure you do it on the UI
                // thread.
                layout.post(new Runnable() {

                    public void run() {

                        layout.addView(row);
                    }
                });
            }
        }, 500);



        dataObj=(blogpageDataTemplate) getIntent().getSerializableExtra("data");

        int pos = dataObj.getPositionInList();
        //if(pos > -1)
        if(dataObj.isDefaultDataToCreateCampaign() == true){
            dataObj = (blogpageDataTemplate) MainActivity.listOfTemplatePagesObj.get(2).getTemplateData(1, dataObj.isDefaultDataToCreateCampaign());
        }


        deleteTitleBlogBtnView=(ImageView)findViewById(R.id.deleteTitleBlog);
        deleteCARD_IMAGEBtnView=(ImageView)findViewById(R.id.deleteCARD_IMAGE);
        deleteHeadingBlogBtnView=(ImageView)findViewById(R.id.deleteHeadingBlogPage);
        deleteSubHeaderBlogBtnView=(ImageView)findViewById(R.id.deleteSubHeaderBlogPage);
        deleteParaBlogBtnView=(ImageView)findViewById(R.id.deleteParaBlogPage);

        deleteHeadingBelowimgBlogBtnView=(ImageView)findViewById(R.id.deleteHeadingBelowimgBlogPage);
        deleteSubHeaderBelowimgBlogBtnView=(ImageView)findViewById(R.id.deleteSubHeaderBelowimgBlog);
        deleteParaBlogBelowimgBtnView=(ImageView)findViewById(R.id.deleteParaBelowimgBlogPage);

        cardRelativeLayout = (RelativeLayout)findViewById(R.id.cardImageLayout);

        cardImage = (NetworkImageView) findViewById(R.id.Blog_CARD_IMAGE);
        cardImageCrop =(ImageView) findViewById(R.id.Blog_STATIC_IMAGE);


        String urlOfProfile = dataObj.getUrlOfImage();

        if(urlOfProfile != null){
           // Uri cardImageUri = Uri.parse(urlOfProfile);
           // cardImage.setImageURI(cardImageUri);
            cardImage.setImageUrl(urlOfProfile, VolleySingleton.getInstance(this).getImageLoader());
            cardImageCrop.setVisibility(View.GONE);

        }else{
            cardImage.setVisibility(View.GONE);
            cardImageCrop.setImageResource(R.drawable.about_banner_1);

        }

        //showImageForBackround();


        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");

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
        attName = new ArrayList<String>();

        String title = dataObj.getTitle();
        editTitle = (EditText) findViewById(R.id.BLOG_TITLE);
        if(title != null && !title.equals("")) {
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else{
            editTitle.setVisibility(View.GONE);
            deleteTitleBlogBtnView.setVisibility(View.GONE);
        }

        String header = dataObj.getHeaderBlog();
        heading = (EditText) findViewById(R.id.blog_heading);
        if(header != null && !header.equals("")) {
            heading.setText(header);
            heading.setTypeface(mycustomFont);
        }else{
            heading.setVisibility(View.GONE  );
            deleteHeadingBlogBtnView.setVisibility(View.GONE);
        }

        String subHeading = dataObj.getSubHeader();
        subheading = (EditText) findViewById(R.id.blog_subheading);
        if(subHeading != null && !subHeading.equals("")) {
            subheading.setText(subHeading);
            subheading.setTypeface(mycustomFont);
        }else{
            subheading.setVisibility(View.GONE);
            deleteSubHeaderBlogBtnView.setVisibility(View.GONE);
        }

        String paraGraph =  dataObj.getText_Para();
        paraGraphBlog = (EditText) findViewById(R.id.blog_paraGraph);
        if(paraGraph != null && !paraGraph.equals("")){
            paraGraphBlog.setText(paraGraph);
            paraGraphBlog.setTypeface(mycustomFont);
        }else{
            paraGraphBlog.setVisibility(View.GONE);
            deleteParaBlogBtnView.setVisibility(View.GONE);
        }

        String headingBelow = dataObj.getHeaderBlogBlowing();
        heading_belo = (EditText) findViewById(R.id.blog_heading_belowing);
        if(headingBelow != null && !headingBelow.equals(""))
        {
            heading_belo.setText(headingBelow);
            heading_belo.setTypeface(mycustomFont);
        }
        else{
            heading_belo.setVisibility(View.GONE);
            deleteHeadingBelowimgBlogBtnView.setVisibility(View.GONE);
        }

        String subheadingBlow = dataObj.getSubHeaderBlowing();
        subheading_below = (EditText) findViewById(R.id.blog_subheading_belowing);
        if(subheadingBlow != null && !subheadingBlow.equals("")){
            subheading_below.setText(subheadingBlow);
            subheading_below.setTypeface(mycustomFont);
        }else {
            subheading_below.setVisibility(View.GONE);
            deleteSubHeaderBelowimgBlogBtnView.setVisibility(View.GONE);
        }

        paraGraphBlog_below = (EditText) findViewById(R.id.blog_paraGraph_belowing);
        String paraGraphBelow = dataObj.getText_ParaBlowing();
        if(paraGraphBelow != null && !paraGraphBelow.equals("")){
            paraGraphBlog_below.setText(paraGraphBelow);
            paraGraphBlog_below.setTypeface(mycustomFont);
        }else{
            paraGraphBlog_below.setVisibility(View.GONE);
            deleteParaBlogBelowimgBtnView.setVisibility(View.GONE);
        }

        // /setSupportActionBar(toolbar);


        mProfileId = editCampaign.mCampaignIdFromServer;
        mPageName = ProfileFieldsEnum.PROFILE_PAGE_BLOG;

        mBlogPageObj  = new Page(mProfileId,mPageName);
        mParentId = mBlogPageObj.getPageId();

        if(dataObj.isDefaultDataToCreateCampaign() == false) {
            toolbar.setVisibility(View.GONE);
            init_viewCampaign();
            showPreview=true;
        }

    }

    Page mBlogPageObj;
    String mProfileId = null;
    String mPageName = null;
    String mParentId = null;

    private void setAttribute(String name, String value){
        if(name != null && value != null) {

            Attribute atrbtObj = new Attribute(mProfileId, mParentId, name, value);
            mBlogPageObj.addAttribute(atrbtObj);
        }
    }

    void init_editCampaign(){

        try {
            deleteTitleBlogBtnView.setVisibility(View.VISIBLE);
            deleteCARD_IMAGEBtnView.setVisibility(View.VISIBLE);
            deleteHeadingBlogBtnView.setVisibility(View.VISIBLE);
            deleteSubHeaderBlogBtnView.setVisibility(View.VISIBLE);
            deleteParaBlogBtnView.setVisibility(View.VISIBLE);
        }catch (NullPointerException e){
            Log.v(TAG,"Null in init_viewCampaign");
        }
        if(editTitle !=null){
            editTitle.setEnabled(true);
            editTitle.setKeyListener(new EditText(getApplication()).getKeyListener());
        }

        if(heading !=null){
            heading.setEnabled(true);
            heading.setKeyListener(new TextView(getApplicationContext()).getKeyListener());
        }

        if(subheading !=null){
            subheading.setEnabled(true);
            subheading.setKeyListener(new TextView(getApplicationContext()).getKeyListener());
        }

        if(paraGraphBlog !=null){
            paraGraphBlog.setEnabled(true);
            paraGraphBlog.setKeyListener(new TextView(getApplicationContext()).getKeyListener());
        }

        if(heading_belo != null){
            heading_belo.setEnabled(true);
            heading_belo.setKeyListener(new TextView(getApplicationContext()).getKeyListener());
        }

        if(subheading_below != null){
            subheading_below.setEnabled(true);
            subheading_below.setKeyListener(new TextView(getApplicationContext()).getKeyListener());
        }

        if(paraGraphBlog_below !=null){
            paraGraphBlog_below.setEnabled(true);
            paraGraphBlog_below.setKeyListener(new TextView(getApplicationContext()).getKeyListener());
        }

    }


    public void previewTemplate(View view) {

        TextView textViewShowPreview = (TextView)findViewById(R.id.textShow_preview);

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.floatingForPreview);
        Button showPreviewBtn = (Button) findViewById(R.id.showPreview);


        if (showPreview == false) {
            textViewShowPreview.setText("Edit");
            btn.show();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_edit);
            init_viewCampaign();
            showPreview = true;
        } else {
            textViewShowPreview.setText("Preview");
            btn.hide();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_about);
            init_editCampaign();
            showPreview = false;
        }
    }


    public void GoLiveFloatingBlog(View view) {

        changeText();

        //TODO  below method called from changeText()
        /*
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_TITLE, dataObj.getTitle());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_1, dataObj.getHeaderBlog());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_2, dataObj.getHeaderBlogBlowing());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_1, dataObj.getSubHeader());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_2, dataObj.getSubHeaderBlowing());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_CARD_IMAGE, dataObj.getUrlOfImage());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_1, dataObj.getText_Para());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_2, dataObj.getText_ParaBlowing());

        */


        //requestToMakeProfile = new Profile(editCampaign.mCampaignIdFromServer, Profile.TemplateID.PROFILE_TEMPLATE_ID_T1);
        //requestToMakeProfile.addPage(mBlogPageObj);
        //SaveProfileData req = new SaveProfileData(this,requestToMakeProfile,loginUi.mLogintoken,this);
        //req.executeRequest();

        AlertDialog.Builder dialog=new AlertDialog.Builder(activity_blogpage.this);
        dialog.setMessage("Want to add this page?");

        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                blogPageToRequest();
                blogButtonAction();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                blogButtonAction();
            }
        });

        dialog.show();


    }

    private void blogPageToRequest(){

        Profile reqToMakeProfile =  MainActivity.getProfileObject();

        if(reqToMakeProfile.checkIfPageExist(mParentId) == false) {
            reqToMakeProfile.addPage(mBlogPageObj);
        }
    }

    private void blogButtonAction() {

        Profile reqToMakeProfile =  MainActivity.getProfileObject();
        int numOfPages = reqToMakeProfile.getTotalNumberOfPagesAdded();

        if(numOfPages > 0) {

            //blogPageToRequest(); //This function has check that ensures page is not added duplicate
            //Todo Need to show user popup to get his confirmation that this page will be added
            SaveProfileData req = new SaveProfileData(this, reqToMakeProfile, loginUi.mLogintoken, this);
            req.executeRequest();
        }else{
            Toast.makeText(this,"No page was added to your campaign",Toast.LENGTH_LONG).show();

            //blogPageToRequest();//Todo All this code will be removed once it is done with help of dialogbox
            SaveProfileData req = new SaveProfileData(this, reqToMakeProfile, loginUi.mLogintoken, this);
            req.executeRequest();
        }

    }

    ProgressDialog pbImage = null;
    private class PhotoAsyncTask extends AsyncTask<Void, Void, Void>
    {


        public void BlogImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = openFileInput(MainActivity.Blog_TemplateImage_IMAGE_CROPED_NAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            File sendFile = getFileObjectFromBitmap (bitmap);


            UploadImageForUrlData data =
                    new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "Blog banner", 1);
            UploadImageForUrlRequest req = new UploadImageForUrlRequest(activity_blogpage.this, data, activity_blogpage.this);
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
                BlogImageUpload();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pbImage = new ProgressDialog(activity_blogpage.this);
            pbImage.setMessage("Uploading Image...");

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }

    private void showImageForBackround(){
        Log.v("editCampaign", "showImageCampaign");
        try {
            FileInputStream in = openFileInput(MainActivity.Blog_TemplateImage_IMAGE_CROPED_NAME);

            cardImage.setVisibility(View.GONE);
            cardImageCrop.setVisibility(View.VISIBLE);

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            cardImageCrop.setImageDrawable(new BitmapDrawable(getResources(), bitmap));


        }catch (FileNotFoundException e){
            Log.v("editCampaign","Blog_TemplateImage_IMAGE_CROPED_NAME file not found");
        }

    }

    public void uploadToBlogOnApp(View view) {

        if(showPreview == false) {
            String campnName = null;
         /*
        * Need to open gallery directly from here
        * From Cropped image OK clicked editCampaign.java will be opened
        * In editCampaign.java this campaign name(campnName) will be used to set defualt text
        * ScreenName will be used by CropedImage as it will be used to open gallery by multiple classes
        * */

            Intent inf = new Intent(this, CropedImage.class);
            inf.putExtra("ScreenName", MainActivity.Blog_TemplateImage_IMAGE_CROPED_NAME);
            inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_BLOG_ON_APP);

            inf.putExtra("CampaignName", "Choose Image");
            cropRestart=1;
            startActivity(inf);


            //setImage for card  T

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



    public void pageTemplate(View view) {
        startActivity(new Intent(getApplicationContext(),about_us_page_template.class));
    }

    public void deleteCardImageBlogPage(View view) {

        cardImage.setVisibility(View.GONE);
        deleteCARD_IMAGEBtnView.setVisibility(View.GONE);
        cardRelativeLayout.setVisibility(View.GONE);

    }

    public void deleteTitleBlog(View view) {

        editTitle.setVisibility(View.GONE);
        editTitle.setText(null);
        deleteTitleBlogBtnView.setVisibility(View.GONE);

    }

    public void deleteHeadingBlogPage(View view) {

        heading.setVisibility(View.GONE);
        heading.setText(null);
        deleteHeadingBlogBtnView.setVisibility(View.GONE);
    }

    public void deleteSubHeadingBlogPage(View view) {
        subheading.setVisibility(View.GONE);
        subheading.setText(null);
        deleteSubHeaderBlogBtnView.setVisibility(View.GONE);
    }

    public void deleteParaBlog(View view) {
        paraGraphBlog.setVisibility(View.GONE);
        paraGraphBlog.setText(null);
        deleteParaBlogBtnView.setVisibility(View.GONE);
    }

    public void deleteHeadingBelowimgBlogPage(View view) {
        heading_belo.setVisibility(View.GONE);
        heading_belo.setText(null);
        deleteHeadingBelowimgBlogBtnView.setVisibility(View.GONE);
    }

    public void deleteSubHeadingBelowimgBlogPage(View view) {
        subheading_below.setVisibility(View.GONE);
        subheading_below.setText(null);
        deleteSubHeaderBelowimgBlogBtnView.setVisibility(View.GONE);
    }

    public void deleteParaBelowingBlog(View view) {
        paraGraphBlog_below.setVisibility(View.GONE);
        paraGraphBlog_below.setText(null);
        deleteParaBlogBelowimgBtnView.setVisibility(View.GONE);
    }


    void changeText(){
        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextBlog" + title);
        if (title != null) {
            dataObj.setTitle(title);
        }

        String header = String.valueOf(heading.getText());
        Log.d(TAG, "changeHeadingTxtBlog" + header);
        if (header != null) {
            dataObj.setHeaderBlog(header);
        }

        String subheader = String.valueOf(subheading.getText());
        Log.d(TAG, "changeSubHeadingBlog" + subheader);
        if (subheader != null) {
            dataObj.setSubHeader(subheader);
        }

        String para = String.valueOf(paraGraphBlog.getText());
        Log.d(TAG, "changeParaBlog" + para);
        if (para != null) {
            dataObj.setText_Para(para);
        }

        String headerBelow = String.valueOf(heading_belo.getText());
        Log.d(TAG, "changeHeadingBelowTxtBlog" + headerBelow);
        if (headerBelow != null) {
            dataObj.setHeaderBlogBlowing(headerBelow);
        }

        String subheaderBelow = String.valueOf(subheading_below.getText());
        Log.d(TAG, "changeSubHeadingBelowTxtBlog" + subheaderBelow);
        if (subheaderBelow != null) {
            dataObj.setSubHeaderBlowing(subheaderBelow);
        }

        String paraBelow = String.valueOf(paraGraphBlog_below.getText());
        Log.d(TAG, "changeSubHeadingBelowTxtBlog" + paraBelow);
        if (paraBelow != null) {
            dataObj.setText_ParaBlowing(paraBelow);
        }

        //For testing
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_TITLE, dataObj.getTitle());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_1, dataObj.getHeaderBlog());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_2, dataObj.getHeaderBlogBlowing());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_1, dataObj.getSubHeader());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_2, dataObj.getSubHeaderBlowing());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_CARD_IMAGE, dataObj.getUrlOfImage());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_1, dataObj.getText_Para());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_2, dataObj.getText_ParaBlowing());
    }

    /*
    *
    * TODO Vijay
     */
    public void changeTitleTextBlog(View view){

    }
    public void changeHeadingTxtBlog(View view){

    }

    public void changeSubHeadingBlog(View view){

    }

    public void changeParaBlog(View view){

    }

    public void changeHeadingTxtBelowBlog(View view){

    }

    public void changeSubHeadingTxtBelowBlog(View view){

    }

    public void changeParaTxtBelowBlog(View view){

    }



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
}
