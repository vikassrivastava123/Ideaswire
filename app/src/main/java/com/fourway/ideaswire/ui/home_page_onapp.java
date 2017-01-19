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
import com.fourway.ideaswire.templates.HomePageDataTemplate;
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

public class home_page_onapp extends Activity implements SaveProfileData.SaveProfileResponseCallback, UploadImageForUrlRequest.UploadImageForUrlCallback  {
    TextView mTitle;
    EditText editTitle =null,
            editHeader =null,
            editSubheading =null,
            editParaGraph =null;

    NetworkImageView cardImage_1,cardImage_2;
    ImageView cardImageCrop_1;

    RelativeLayout cardImageLayout1;

    ImageView deleteTitle=null,
    deleteHeading=null,
    deleteSubHeading=null,
    deletePara=null,
    deleteCardImage_1=null,
    deleteCardImage_2=null;

    int cropRestart=0;

    public String TAG="home_page_onapp";

    HomePageDataTemplate dataObj;
    private boolean showPreview = false;

    void init_viewCampaign(){
        try {

            deleteTitle.setVisibility(View.GONE);
            deleteHeading.setVisibility(View.GONE);
            deleteSubHeading.setVisibility(View.GONE);
            deletePara.setVisibility(View.GONE);
            deleteCardImage_1.setVisibility(View.GONE);
            deleteCardImage_2.setVisibility(View.GONE);
        }catch (NullPointerException e){
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if (editTitle !=null){
            editTitle.setEnabled(false);
            editTitle.setKeyListener(null);
        }
        if (editHeader !=null){
            editHeader.setEnabled(false);
            editHeader.setKeyListener(null);
        }
        if (editSubheading !=null){
            editSubheading.setEnabled(false);
            editSubheading.setKeyListener(null);
        }
        if (editParaGraph !=null){
            editParaGraph.setEnabled(false);
            editParaGraph.setKeyListener(null);
        }
    }

    void init_editCampaign(){
        try {

            deleteTitle.setVisibility(View.VISIBLE);
            deleteHeading.setVisibility(View.VISIBLE);
            deleteSubHeading.setVisibility(View.VISIBLE);
            deletePara.setVisibility(View.VISIBLE);
            deleteCardImage_1.setVisibility(View.VISIBLE);
            deleteCardImage_2.setVisibility(View.VISIBLE);
        }catch (NullPointerException e){
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if (editTitle !=null){
            editTitle.setEnabled(true);
            editTitle.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
        if (editHeader !=null){
            editHeader.setEnabled(true);
            editHeader.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
        if (editSubheading !=null){
            editSubheading.setEnabled(true);
            editSubheading.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
        if (editParaGraph !=null){
            editParaGraph.setEnabled(true);
            editParaGraph.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
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

    public String name()
    {
        return "Home Page";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_onapp);
        dataObj = (HomePageDataTemplate)getIntent().getSerializableExtra("data");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        cardImage_1 =(NetworkImageView)findViewById(R.id.Home_CARD_IMAGE_1);
        cardImage_2 =(NetworkImageView)findViewById(R.id.Home_CARD_IMAGE_2);

        cardImageCrop_1 = (ImageView)findViewById(R.id.Home_STATIC_IMAGE);

        cardImageLayout1 =(RelativeLayout)findViewById(R.id.cardImageLayout1);

        deleteTitle =(ImageView)findViewById(R.id.deleteTitlehome);
        deleteHeading =(ImageView)findViewById(R.id.deleteHeadingHome);
        deleteSubHeading =(ImageView)findViewById(R.id.deleteSubHeadingHome);
        deletePara =(ImageView)findViewById(R.id.deleteParaHome);

        deleteCardImage_1 =(ImageView)findViewById(R.id.deleteCARD_IMAGE_1);
        deleteCardImage_2 =(ImageView)findViewById(R.id.deleteCARD_IMAGE_2);

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dynamicPages);
        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                int size = MainActivity.listOfTemplatePagesObj.size();
                Button[] btn = new Button[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(home_page_onapp.this);
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
                    btn[i] = new Button(home_page_onapp.this);

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
                    row.addView(btn[i]);
                    // Add the LinearLayout element to the ScrollView
                    i++;
                }
                //btn[1].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));
                //btn[1].setFocusable(true);
                // When adding another view, make sure you do it on the UI
                // thread.
                layout.post(new Runnable() {

                    public void run() {

                        layout.addView(row);
                    }
                });
            }
        }, 500);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });
        editTitle =(EditText)findViewById(R.id.Home_TITLE);
        editHeader = (EditText) findViewById(R.id.headingHome );
        editSubheading = (EditText) findViewById(R.id.subHeadingHome );
        editParaGraph = (EditText) findViewById(R.id.paraGraphHome );

        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        String title=dataObj.getTitle();
        if(title!=null){
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }
        else{
            editTitle.setVisibility(View.GONE);
        }

        String header=dataObj.getHeader();
        if(header!=null){
            editHeader.setText(header);
            editHeader.setTypeface(mycustomFont);
        }else{
            editHeader.setVisibility(View.GONE);
        }

        String subHeading=dataObj.getSubHeading();
        if(subHeading!=null){
            editSubheading.setText(subHeading);
            editSubheading.setTypeface(mycustomFont);
        }else{
            editSubheading.setVisibility(View.GONE);
        }

        String paraGraph=dataObj.getParaGraph();
        if(paraGraph!=null){
            editParaGraph.setText(paraGraph);
            editParaGraph.setTypeface(mycustomFont);
        }else{
            editParaGraph.setVisibility(View.GONE);
        }

        String urlOfProfile = dataObj.getUrlOfImage_1();

        if(urlOfProfile != null){
            Uri cardImageUri = Uri.parse(urlOfProfile);
            cardImage_1.setImageURI(cardImageUri);
            cardImageCrop_1.setVisibility(View.GONE);

        }else{
            cardImage_1.setVisibility(View.GONE);
            cardImageCrop_1.setImageResource(R.drawable.about_banner_1);

        }

        showImageForBackround();

        mProfileId = editCampaign.mCampaignIdFromServer;
        mPageName = ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE;

        mHomePageObj  = new Page(mProfileId,mPageName);
        mParentId = mHomePageObj.getPageId();



        if(dataObj.isDefaultDataToCreateCampaign() == false)
            init_viewCampaign();

    }

    Page  mHomePageObj;
    String mProfileId = null;
    String mPageName = null;
    String mParentId = null;
    Profile requestToMakeProfile;

    private void setAttribute(String name, String value){

        Attribute atrbtObj = new Attribute(mProfileId,mParentId,name,value);
        mHomePageObj.addAttribute(atrbtObj);
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

        //   RelativeLayout previewLayout = (RelativeLayout)findViewById(R.id.previewLayout);
        // previewLayout.setVisibility(View.VISIBLE);

    }

    public void pageTemplate(View view) {
        startActivity(new Intent(getApplicationContext(),about_us_page_template.class));
    }

    public void deleteTitleHome(View view){
        editTitle.setVisibility(View.GONE);
        deleteTitle.setVisibility(View.GONE);
    }

    public void deleteCardImage_1(View view){
        cardImage_1.setVisibility(View.GONE);
        cardImageLayout1.setVisibility(View.GONE);
        deleteCardImage_1.setVisibility(View.GONE);
    }
    public void deleteHeadingHome(View view){
        editHeader.setVisibility(View.GONE);
        deleteHeading.setVisibility(View.GONE);
    }
    public void deleteSubHeadingHome(View view){
        editSubheading.setVisibility(View.GONE);
        deleteSubHeading.setVisibility(View.GONE);
    }
    public void deleteParaGraphHome(View view){
        editParaGraph.setVisibility(View.GONE);
        deletePara.setVisibility(View.GONE);
    }

    public void deleteCardImage_2(View view){
        deleteCardImage_2.setVisibility(View.GONE);
        cardImage_2.setVisibility(View.GONE);
    }


    public void changeTitleTextHome(View view){
        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextHome" + title);
        if (title != null) {
            dataObj.setTitle(title);
        }
    }

    public void changeHeadingTextHome(View view){
        String header = String.valueOf(editHeader.getText());
        Log.d(TAG, "changeHeadingTxtHome" + header);
        if (header != null) {
            dataObj.setHeading(header);
        }
    }

    public void changeSubHeadingTextHome(View view){
        String subheader = String.valueOf(editSubheading.getText());
        Log.d(TAG, "changeSubHeadingHome" + subheader);
        if (subheader != null) {
            dataObj.setSubHeading(subheader);
        }
    }
    public void changeparaGraphTextHome(View view){
        String para = String.valueOf(editParaGraph.getText());
        Log.d(TAG, "changeParaHome" + para);
        if (para != null) {
            dataObj.setParaGraph(para);
        }
    }



    @Override
    public void onProfileSaveResponse(CommonRequest.ResponseCode res, Profile data) {
        Log.v(TAG,"ResponseCode = " + res);
        Toast.makeText(this, String.valueOf(res), Toast.LENGTH_SHORT).show();
        pd.dismiss();
    }

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {
        pbImage.hide();
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            String imageUrl = data.getResponseUrl();
            dataObj.setUrlOfImage_1(imageUrl);
            Log.v(TAG,"Url received" + imageUrl);
        }
    }

    ProgressDialog pd;
    public void GoLiveFloatingHome(View view) {
        pd=new ProgressDialog(this);
        pd.setMessage("please wait");
        pd.show();

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_HEADING, dataObj.getHeading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_SUBHEADING, dataObj.getSubHeading());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_TITLE, dataObj.getTitle());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_CARD_IMAGE_1, dataObj.getUrlOfImage_1());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_PARAGRAPH, dataObj.getParaGraph());


        requestToMakeProfile = new Profile(editCampaign.mCampaignIdFromServer, Profile.TemplateID.PROFILE_TEMPLATE_ID_T1);
        requestToMakeProfile.addPage(mHomePageObj);
        SaveProfileData req = new SaveProfileData(this,requestToMakeProfile,loginUi.mLogintoken,this, false);
        req.executeRequest();
    }

    ProgressDialog pbImage = null;
    private class PhotoAsyncTask extends AsyncTask<Void, Void, Void>
    {


        public void HomeImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = openFileInput(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            File sendFile = getFileObjectFromBitmap (bitmap);


            UploadImageForUrlData data =
                    new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "Home banner", 1);
            UploadImageForUrlRequest req = new UploadImageForUrlRequest(home_page_onapp.this, data, home_page_onapp.this);
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
                HomeImageUpload();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pbImage = new ProgressDialog(home_page_onapp.this);
            pbImage.setMessage("Uploading Image...");

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }



    private void showImageForBackround(){
        Log.v("editCampaign", "showImageCampaign");
        try {
            FileInputStream in = openFileInput(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_1);

            cardImage_1.setVisibility(View.GONE);
            cardImageCrop_1.setVisibility(View.VISIBLE);

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            cardImageCrop_1.setImageDrawable(new BitmapDrawable(getResources(), bitmap));


        }catch (FileNotFoundException e){
            Log.v("editCampaign","Home_TemplateImage_IMAGE_CROPED_NAME file not found");
        }

    }

    public void uploadToHomeOnApp(View view) {

        if(showPreview == false) {
            String campnName = null;
         /*
        * Need to open gallery directly from here
        * From Cropped image OK clicked editCampaign.java will be opened
        * In editCampaign.java this campaign name(campnName) will be used to set defualt text
        * ScreenName will be used by CropedImage as it will be used to open gallery by multiple classes
        * */

            Intent inf = new Intent(this, CropedImage.class);
            inf.putExtra("ScreenName", MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_1);
            inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_HOME_PAGE_ON_APP);

            inf.putExtra("CampaignName", "Choose Image");
            cropRestart=1;
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

}
