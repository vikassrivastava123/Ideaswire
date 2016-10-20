package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import com.fourway.ideaswire.templates.AboutUsDataTemplate;
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

public class AboutUsOnApp extends Activity implements SaveProfileData.SaveProfileResponseCallback, UploadImageForUrlRequest.UploadImageForUrlCallback{

    ViewPager mViewPager;
    PagerAdapter mAdapter;
    EditText editTitle = null,editHeader = null,editSubHeading = null,editParaGraphAboutUs = null;

    TextView mTitle;
    NetworkImageView cardImage;
    ImageView cardImageCrop;
    Button submit_button;
    int cropRestart=0;

    ImageView deleteTitleAboutUsBtnView = null,deleteCARD_IMAGEBtnView = null;
    ImageView deleteHeadingAboutUsBtnView = null;
    ImageView deleteSubHeaderAboutUsBtnView = null;
    ImageView deleteParaAboutUsBtnView = null;

    AboutUsDataTemplate dataObj;
    boolean inPreviewMode = false;
    private boolean showPreview = false;

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


    void showLinkButtonToUrl(){

        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                AboutUsOnApp.this);

        builderInner.setTitle("Enter Url to Link");

        View v= LayoutInflater.from(AboutUsOnApp.this).inflate(R.layout.url_to_link,null);
        final EditText addUrl = (EditText)v.findViewById(R.id.editTextAddUrl);
        final EditText addNameToButton = (EditText)v.findViewById(R.id.editTextButtonName);
        builderInner.setView(v);
        /*final EditText addUrl = new EditText(AboutUsOnApp.this);
        addUrl.setHint("Add Url");
        builderInner.setView(addUrl);

        final EditText addNameToButton = new EditText(AboutUsOnApp.this);
        addNameToButton.setHint("Edit Button Name");
        builderInner.setView(addNameToButton);*/

        builderInner.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {

                        String linkUrlToButton = addUrl.getText().toString();
                        dataObj.set_buttonUrl(linkUrlToButton);


                        String addNameToButtonValue = addNameToButton.getText().toString();
                        Button btn = (Button) findViewById(R.id.buttonMainAbtUs);
                        btn.setText(addNameToButtonValue);
                        dataObj.set_button_text(addNameToButtonValue);


                        dialog.dismiss();
                    }
                });

        builderInner.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        builderInner.show();

    }



    void showLinkButtonToActivity(){

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(AboutUsOnApp.this);
      //  builderSingle.setIcon(R.drawable.back_black);
        builderSingle.setTitle("Edit Link");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                AboutUsOnApp.this,
                android.R.layout.select_dialog_singlechoice);

        for(pages obj: MainActivity.listOfTemplatePagesObj){
            arrayAdapter.add(obj.nameis());

        }
        builderSingle.setIcon(R.drawable.logo);
        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        dataObj.set_submit_button_link(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                AboutUsOnApp.this);

                        if(which == 0){

                            builderInner.setTitle("Enter Url to Link");
                            final EditText addUrl = new EditText(AboutUsOnApp.this);
                            addUrl.setHint("Add Url");
                            builderInner.setView(addUrl);

                            final EditText addNameToButton = new EditText(AboutUsOnApp.this);
                            addNameToButton.setHint("Edit Button Name");
                            builderInner.setView(addNameToButton);

                            builderInner.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {

                                            String linkUrlToButton = addNameToButton.getText().toString();
                                            dataObj.set_buttonUrl(linkUrlToButton);


                                            String addNameToButtonValue = addNameToButton.getText().toString();
                                            Button btn = (Button) findViewById(R.id.buttonMainAbtUs);
                                            btn.setText(addNameToButtonValue);
                                            dataObj.set_button_text(addNameToButtonValue);





                                            dialog.dismiss();
                                        }
                                    });

                            builderInner.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });

                        }else {
                            builderInner.setTitle("Great ! \nNow Change text of Button");
                            final EditText edittext = new EditText(AboutUsOnApp.this);
                            edittext.setHint("Edit Button Name");
                            builderInner.setView(edittext);
                            builderInner.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            String YouEditTextValue = edittext.getText().toString();
                                            Button btn = (Button) findViewById(R.id.buttonMainAbtUs);
                                            btn.setText(YouEditTextValue);
                                            dataObj.set_button_text(YouEditTextValue);
                                            dialog.dismiss();
                                        }
                                    });

                            builderInner.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                        }



                        builderInner.show();
                    }
                });
        builderSingle.show();


    }


    public String name() {
        return "About us";
    }

    public void delete(EditText e) {
        e.setVisibility(View.GONE);
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        if(cropRestart==1) {
            showImageForBackround();
            PhotoAsyncTask obj = new PhotoAsyncTask();
            obj.execute();
            cropRestart=0;
        }else {
            loadPageTemplate();
        }

    }


    void showPreview(){

        init_viewCampaign();

    }

    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_template1_on_app);
        // AboutUsDataTemplate dataObj = new AboutUsDataTemplate(1,true);
        layout = (RelativeLayout) findViewById(R.id.dynamicPages);

        loadPageTemplate();

          //dataObj = (AboutUsDataTemplate) MainActivity.listOfTemplateDataObj;//
        dataObj=(AboutUsDataTemplate)getIntent().getSerializableExtra("data");
        inPreviewMode =getIntent().getBooleanExtra(MainActivity.ExplicitEditModeKey,false);
//        int seltemplate = dataObj.getTemplateSelected();
//
//// if(seltemplate == 1) {
//           setContentView(R.layout.about_us_template1_on_app);
//        }else{
//            setContentView(R.layout.about_us_template1_on_app);
//       }

         deleteTitleAboutUsBtnView = (ImageView)findViewById(R.id.deleteTitleAboutUs);
         deleteCARD_IMAGEBtnView = (ImageView)findViewById(R.id.deleteCARD_IMAGE);
         deleteHeadingAboutUsBtnView = (ImageView)findViewById(R.id.deleteHeadingAboutUs);
         deleteSubHeaderAboutUsBtnView = (ImageView)findViewById(R.id.deleteSubHeaderAboutUs);
         deleteParaAboutUsBtnView = (ImageView)findViewById(R.id.deleteParaAboutUs);


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

        String title = dataObj.get_title();
        editTitle = (EditText) findViewById(R.id.ABOUT_TITLE);
        if(title != null && !title.equals("")) {
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else{
            editTitle.setVisibility(View.GONE);
            deleteTitleAboutUsBtnView.setVisibility(View.GONE);
        }

        String header = dataObj.get_heading();
        editHeader = (EditText) findViewById(R.id.ABOUT_US_HEADING);
        if(header != null && !header.equals("")) {
            editHeader.setText(header);
            editHeader.setTypeface(mycustomFont);
        }else{
            editHeader.setVisibility(View.GONE  );
            deleteHeadingAboutUsBtnView.setVisibility(View.GONE);
        }

        String subHeading = dataObj.get_sub_heading();
        editSubHeading = (EditText) findViewById(R.id.ABOUT_US_SUBHEADING);
        if(subHeading != null && !subHeading.equals("")) {
            editSubHeading.setText(subHeading);
            editSubHeading.setTypeface(mycustomFont);
        }else{
            editSubHeading.setVisibility(View.GONE);
            deleteSubHeaderAboutUsBtnView.setVisibility(View.GONE);
        }

        String paraGraphAboutUs =  dataObj.get_text_para();
        editParaGraphAboutUs = (EditText) findViewById(R.id.paraGraphAboutUs);
        if(paraGraphAboutUs != null && !paraGraphAboutUs.equals("")){
            editParaGraphAboutUs.setText(paraGraphAboutUs);
            editParaGraphAboutUs.setTypeface(mycustomFont);
        }else{
            editParaGraphAboutUs.setVisibility(View.GONE);
            deleteParaAboutUsBtnView.setVisibility(View.GONE);
        }

        submit_button = (Button) findViewById(R.id.buttonMainAbtUs);
        cardImage = (NetworkImageView) findViewById(R.id.ABOUT_US_CARD_IMAGE);
        cardImageCrop = (ImageView)findViewById(R.id.ABOUT_US_STATIC_IMAGE);

        String urlOfProfile = dataObj.get_url();

        if(urlOfProfile != null && !urlOfProfile.equalsIgnoreCase("null")){
            //Uri cardImageUri = Uri.parse(urlOfProfile);
            //cardImage.setImageURI(cardImageUri);
            cardImage.setImageUrl(urlOfProfile, VolleySingleton.getInstance(this).getImageLoader());
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
               if(showPreview == false) {
                   //showLinkButtonToActivity();
                   showMenuOptionToLinkPage();
               }else{

                   int posInListOfPage = dataObj.get_submit_button_link();

                   if(posInListOfPage >0) {
                      // dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(posInListOfPage).getTemplateData(1,false);
                       dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(posInListOfPage).getTemplateData(1,showPreview);

                       Class intenetToLaunch = data.getIntentToLaunchPage();
                       Intent intent = new Intent(getApplicationContext(), intenetToLaunch);
                       intent.putExtra("data", data);
                       startActivity(intent);
                   }else {
                       String btnUrl= dataObj.get_buttonUrl();
                       if (btnUrl!=null) {
                           if (!btnUrl.startsWith("http://") && !btnUrl.startsWith("https://"))
                               btnUrl = "http://" + btnUrl;

                           Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(btnUrl));
                           startActivity(browserIntent);
                       }
                   }
               }


            }
        });

        String submitBtnText = dataObj.get_button_text();

        if(submitBtnText != null) {
            submit_button.setText(submitBtnText);
        }else{
            submit_button.setVisibility(View.GONE);
        }


        mProfileId = editCampaign.mCampaignIdFromServer;
        mPageName = ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US;

        mAbtUsPageObj  = new Page(mProfileId,mPageName);
        mParentId = mAbtUsPageObj.getPageId();



        //if(dataObj.isDefaultDataToCreateCampaign() == false && inPreviewMode == true)
     if(dataObj.isDefaultDataToCreateCampaign() == false) {
         toolbar.setVisibility(View.GONE);
         init_viewCampaign();
         showPreview = true;
     }
  }


    void loadPageTemplate()
    {
        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                int size = MainActivity.listOfTemplatePagesObj.size();
                final Button[] btn = new Button[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(AboutUsOnApp.this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT));
                for(pages obj: MainActivity.listOfTemplatePagesObj) {
                    String name = obj.nameis();


                     float   x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

                    float y =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());

                    LinearLayout.LayoutParams buttonLayoutParams =
                            new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                                    (int)x,
                                    (int)y));
                    buttonLayoutParams.setMargins(2,2, 0, 0);
                    btn[i] = new Button(AboutUsOnApp.this);

                    btn[i].setLayoutParams(buttonLayoutParams);
                    btn[i].setText(name);
                    btn[i].setId(i);
                    btn[i].setBackgroundColor(getResources().getColor(R.color.card));
                    btn[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if (!btn[v.getId()].getText().toString().equals("About")) {
                                changeText();

                                dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(v.getId()).getTemplateData(1, dataObj.isDefaultDataToCreateCampaign());


                                Class intenetToLaunch = data.getIntentToLaunchPage();
                                Log.v(TAG, "5" + intenetToLaunch);
                                final Intent intent = new Intent(AboutUsOnApp.this, intenetToLaunch);
                                intent.putExtra("data", data);

                                AlertDialog.Builder dialog=new AlertDialog.Builder(AboutUsOnApp.this);
                                dialog.setMessage("Want to add this page?");

                                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        addPageToRequest();
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
                                }


                                //Toast.makeText(getApplicationContext(),
                                //       "button is clicked" + v.getId(), Toast.LENGTH_LONG).show();



                            }
                        }
                    });
                    if (btn[i].getText().toString().equals("About"))
                        btn[i].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));

                        row.addView(btn[i]);
                    // Add the LinearLayout element to the ScrollView
                    i++;
                }
                //btn[0].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));
                //btn[0].setFocusable(true);
                // When adding another view, make sure you do it on the UI
                // thread.
                layout.post(new Runnable() {

                    public void run() {

                        layout.addView(row);
                    }
                });
            }
        }, 500);
    }

    Page  mAbtUsPageObj;
    String mProfileId = null;
    String mPageName = null;
    String mParentId = null;

    private void setAttribute(String name, String value){

        if(name != null && value != null) {
            Attribute atrbtObj = new Attribute(mProfileId, mParentId, name, value);
            mAbtUsPageObj.addAttribute(atrbtObj);
        }
   }

    private static String TAG = "AboutUsOnApp";

    private void addPageToRequest(){

        Profile reqToMakeProfile =  MainActivity.getProfileObject();

        if(reqToMakeProfile.checkIfPageExist(mParentId) == false) {
            reqToMakeProfile.addPage(mAbtUsPageObj);
        }
    }

    private void aboutUsButtonAction() {

        Profile reqToMakeProfile =  MainActivity.getProfileObject();
        int numOfPages = reqToMakeProfile.getTotalNumberOfPagesAdded();

        if(numOfPages > 0) {

            addPageToRequest(); //This function has check that ensures page is not added duplicate
                                //Todo Need to show user popup to get his confirmation that this page will be added
            SaveProfileData req = new SaveProfileData(this, reqToMakeProfile, loginUi.mLogintoken, this);
            req.executeRequest();
        }else{
            Toast.makeText(this,"No page was added to your campaign",Toast.LENGTH_LONG).show();

            addPageToRequest();//Todo All this code will be removed once it is done with help of dialogbox
            SaveProfileData req = new SaveProfileData(this, reqToMakeProfile, loginUi.mLogintoken, this);
            req.executeRequest();
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
            editTitle.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if(editHeader !=null){
            editHeader.setEnabled(true);
            editHeader.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if(editSubHeading !=null){
            editSubHeading.setEnabled(true);
            editSubHeading.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if(editParaGraphAboutUs !=null){
            editParaGraphAboutUs.setEnabled(true);
            editParaGraphAboutUs.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
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

     //   RelativeLayout previewLayout = (RelativeLayout)findViewById(R.id.previewLayout);
       // previewLayout.setVisibility(View.VISIBLE);

    }

    public void pageTemplate(View view) {
        startActivity(new Intent(getApplicationContext(),about_us_page_template.class));
    }

    //public void goLiveAbtUs(View view) {

        //requestToMakeProfile = new Profile(editCampaign.mCampaignIdFromServer, Profile.TemplateID.PROFILE_TEMPLATE_ID_T1);
        //requestToMakeProfile.addPage(mAbtUsPageObj);

     //   aboutUsButtonAction();

    //    SaveProfileData req = new SaveProfileData(this,requestToMakeProfile,loginUi.mLogintoken,this);
     //   req.executeRequest();

//    }

    public void hidePreviewAbtUs(View view) {

    //    RelativeLayout previewLayout = (RelativeLayout)findViewById(R.id.previewLayout);
      //  previewLayout.setVisibility(View.GONE);

    }

    ProgressDialog pd;
    public void GoLiveFloatingAbtUs(View view) {
        pd=new ProgressDialog(this);
        pd.setMessage("Please wait");
        pd.show();

        changeText();

        //TODO  below method called from changeText()
        /*
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_HEADING, dataObj.get_heading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_SUBHEADING, dataObj.get_sub_heading());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_TITLE, dataObj.get_title());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_CARD_IMAGE, dataObj.get_url());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_PARAGRAPH, dataObj.get_text_para());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_BUTTON_TEXT, dataObj.get_button_text());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_BUTTON_SUBNAME_LINKED_PAGE, String.valueOf(dataObj.get_submit_button_link()));
         */

        aboutUsButtonAction();


      //  requestToMakeProfile = new Profile(editCampaign.mCampaignIdFromServer, Profile.TemplateID.PROFILE_TEMPLATE_ID_T1);
     //   requestToMakeProfile.addPage(mAbtUsPageObj);
      //  SaveProfileData req = new SaveProfileData(this,requestToMakeProfile,loginUi.mLogintoken,this);
       // req.executeRequest();
    }
  ProgressDialog pbImage = null;
    private class PhotoAsyncTask extends AsyncTask<Void, Void, Void>
    {


        public void AboutUsImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = openFileInput(MainActivity.About_Us_TemplateImage_IMAGE_CROPED_NAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            File sendFile = getFileObjectFromBitmap (bitmap);


            UploadImageForUrlData data =
                    new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "About us banner", 1);
            UploadImageForUrlRequest req = new UploadImageForUrlRequest(AboutUsOnApp.this, data, AboutUsOnApp.this);
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

            pbImage = new ProgressDialog(AboutUsOnApp.this);
            pbImage.setMessage("Uploading Image...");

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }


    private void showImageForBackround(){
       Log.v("editCampaign", "showImageCampaign");
        try {
            FileInputStream in = openFileInput(MainActivity.About_Us_TemplateImage_IMAGE_CROPED_NAME);

            cardImage.setVisibility(View.GONE);
            cardImageCrop.setVisibility(View.VISIBLE);

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            cardImageCrop.setImageDrawable(new BitmapDrawable(getResources(), bitmap));


        }catch (FileNotFoundException e){
            Log.v("editCampaign","About_Us_TemplateImage_IMAGE_CROPED_NAME file not found");
        }

    }

   public void uploadToAboutUsOnApp(View view) {

       if(showPreview == false) {
           String campnName = null;
         /*
        * Need to open gallery directly from here
        * From Cropped image OK clicked editCampaign.java will be opened
        * In editCampaign.java this campaign name(campnName) will be used to set defualt text
        * ScreenName will be used by CropedImage as it will be used to open gallery by multiple classes
        * */

           Intent inf = new Intent(this, CropedImage.class);
           inf.putExtra("ScreenName", MainActivity.About_Us_TemplateImage_IMAGE_CROPED_NAME);
           inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_ABOUTUSPAGE_ON_APP);

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

    @Override
    public void onProfileSaveResponse(CommonRequest.ResponseCode res, Profile data) {
        Log.v(TAG,"ResponseCode = " + res);
        Toast.makeText(this, String.valueOf(res), Toast.LENGTH_SHORT).show();
        pd.dismiss();
    }


    /*
    *
    * set Url on receiving from server
    * */
    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {

        pbImage.hide();
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            String imageUrl = data.getResponseUrl();
            dataObj.set_url(imageUrl);
            Log.v(TAG,"Url received" + imageUrl);
        }
    }

    public void deleteCardImageAboutUs(View view) {

        cardImage.setVisibility(View.GONE);
        deleteCARD_IMAGEBtnView.setVisibility(View.GONE);
        cardImageCrop.setVisibility(View.GONE);


    }

    public void deleteTitleAboutUs(View view) {

        editTitle.setVisibility(View.GONE);
        editTitle.setText(null);
        deleteTitleAboutUsBtnView.setVisibility(View.GONE);

    }

    public void deleteHeadingAboutUs(View view) {

        editHeader.setVisibility(View.GONE);
        editHeader.setText(null);
        deleteHeadingAboutUsBtnView.setVisibility(View.GONE);
    }

    public void deleteSubHeadingAboutUs(View view) {
        editSubHeading.setVisibility(View.GONE);
        editSubHeading.setText(null);
        deleteSubHeaderAboutUsBtnView.setVisibility(View.GONE);
    }

    public void deleteParaAboutUs(View view) {
        editParaGraphAboutUs.setVisibility(View.GONE);
        editParaGraphAboutUs.setText(null);
        deleteParaAboutUsBtnView.setVisibility(View.GONE);
    }

    public void showMenuOptionToLinkPage() {

        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(this, submit_button);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.set_template_button_action, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.connectUrl) {
                        showLinkButtonToUrl();
                } else {
                        showLinkButtonToActivity();
                }
                return true;
               }
            });

        popup.show();//showing popup menu
        }

    void changeText(){

        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextAbtUs" + title);
        if (title != null) {
            dataObj.set_title(title);
        }

        String header = String.valueOf(editHeader.getText());
        Log.d(TAG, "changeHeadingTxtAbtUs" + header);
        if (header != null) {
            dataObj.set_heading(header);
        }

        String subheader = String.valueOf(editSubHeading.getText());
        Log.d(TAG, "changeSubHeadingAbtUs" + subheader);
        if (subheader != null) {
            dataObj.set_sub_heading(subheader);
        }

        String para = String.valueOf(editParaGraphAboutUs.getText());

        Log.d(TAG, "changeParaAbtUs" + para);
        if (para != null) {
            dataObj.set_text_para(para);
        }

        //For Testing
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_HEADING, dataObj.get_heading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_SUBHEADING, dataObj.get_sub_heading());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_TITLE, dataObj.get_title());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_CARD_IMAGE, dataObj.get_url());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_PARAGRAPH, dataObj.get_text_para());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_BUTTON_TEXT, dataObj.get_button_text());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_BUTTON_URL_TEXT, dataObj.get_buttonUrl());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_BUTTON_SUBNAME_LINKED_PAGE, String.valueOf(dataObj.get_submit_button_link()));
    }
            public void changeTitleTextAbtUs(View view) {

                //if (showPreview == false)
                {

                }
            }

            public void changeHeadingTxtAbtUs(View view) {

                //if (showPreview == false)
                {

                }
            }

            public void changeSubHeadingAbtUs(View view) {
                //if (showPreview == false)
                {

                }

            }

            public void changeParaAbtUs(View view) {
                //if (showPreview == false)
                {

                }

            }

        }
