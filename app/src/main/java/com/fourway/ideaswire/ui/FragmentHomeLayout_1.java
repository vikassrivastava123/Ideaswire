package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Attribute;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.ProfileFieldsEnum;
import com.fourway.ideaswire.data.UploadImageForUrlData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.UploadImageForUrlRequest;
import com.fourway.ideaswire.request.helper.VolleySingleton;
import com.fourway.ideaswire.templates.HomePageLayout_1_DataTemplate;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.fourway.ideaswire.ui.MainActivity.CROSS_BUTTON_HIDE;


/**
 * Created by 4way on 15-10-2016.
 */
public class FragmentHomeLayout_1 extends Fragment  implements UploadImageForUrlRequest.UploadImageForUrlCallback , View.OnClickListener, FragmenMainActivity.viewCampaign {

    ImageView deleteCARD_IMAGEBtnView = null;
    ImageView deleteHeadingAboutUsBtnView = null;
    ImageView deleteSubHeaderAboutUsBtnView = null;
    ImageView deleteParaAboutUsBtnView = null;
    RelativeLayout cardImageRelativeLayout = null;

    EditText editTitle = null,editHeader = null,editSubHeading = null,editParaGraphAboutUs = null;

    NetworkImageView cardImage;
    ImageView cardImageCrop;
    Button submit_button;

    ProgressBar progressBar =null;

    HomePageLayout_1_DataTemplate dataObj = null;
    private boolean showPreview = false;   /**Is in editable mode or privew/it is server data .
                                             in case it false : editable, True means : Priview or server data*/


    String cardImageUrl = null;

    //Variables to make request to server
    Page  mAbtUsPageObj;
    String mProfileId = null;
    String mPageName = null;
    String mPageId = null;

    int indexInList = -1;
    int cropRestart=0;
    pages mthispage = null;

    private static String TAG = "FragmentHomeLayout_1";


    View  getViewBasedOnLayoutSelected(LayoutInflater inflater, ViewGroup container){
        View view = null;

        int selectedTemplate = dataObj.getLayoutSelected();

        switch (selectedTemplate){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                view = inflater.inflate(R.layout.fragment_about, container, false);
                break;
            default:
                view = inflater.inflate(R.layout.fragment_about, container, false);
                break;
        }

        return view;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dataObj = (HomePageLayout_1_DataTemplate)((FragmenMainActivity)getActivity()).getDatObject();//savedInstanceState.getSerializable("dataKey");

        View view= getViewBasedOnLayoutSelected(inflater,container);

       //mEditMode = getActivity().getIntent().getBooleanExtra(MainActivity.ExplicitEditModeKey, false);

        if (dataObj.isEditDefaultOrUpdateData() == true){

            indexInList = (int)((FragmenMainActivity)getActivity()).getIndexOfPresentview();
            mthispage = MainActivity.listOfTemplatePagesObj.get(indexInList);
            mPageName = mthispage.nameis();
            showPreview = false;   //since editable
        }else {
            showPreview = true;    //in preview or server data
        }



        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);


        deleteCARD_IMAGEBtnView = (ImageView)view.findViewById(R.id.deleteCARD_IMAGE);
        deleteHeadingAboutUsBtnView = (ImageView)view.findViewById(R.id.deleteHeadingAboutUs);
        deleteSubHeaderAboutUsBtnView = (ImageView)view.findViewById(R.id.deleteSubHeaderAboutUs);
        deleteParaAboutUsBtnView = (ImageView)view.findViewById(R.id.deleteParaAboutUs);


        deleteCARD_IMAGEBtnView.setVisibility(View.GONE);
        deleteHeadingAboutUsBtnView.setVisibility(View.GONE);
        deleteSubHeaderAboutUsBtnView.setVisibility(View.GONE);
        deleteParaAboutUsBtnView.setVisibility(View.GONE);


        cardImageRelativeLayout = (RelativeLayout)view.findViewById(R.id.cardImageLayout);


        deleteCARD_IMAGEBtnView .setOnClickListener(this);
        deleteHeadingAboutUsBtnView.setOnClickListener(this);
        deleteSubHeaderAboutUsBtnView.setOnClickListener(this);
        deleteParaAboutUsBtnView.setOnClickListener(this);


        cardImage = (NetworkImageView)  view.findViewById(R.id.ABOUT_US_CARD_IMAGE);
        cardImageCrop = (ImageView) view.findViewById(R.id.ABOUT_US_STATIC_IMAGE);

        cardImageCrop.setOnClickListener(this);
        cardImage.setOnClickListener(this);


        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        String title = dataObj.get_title();
        editTitle = (EditText) view.findViewById(R.id.ABOUT_TITLE);
        if(title == null || !title.equals(CROSS_BUTTON_HIDE)) {
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else{
            editTitle.setVisibility(View.GONE);
        }

        String header = dataObj.get_heading();
        editHeader = (EditText) view.findViewById(R.id.ABOUT_US_HEADING);
        if(header == null || !header.equals(CROSS_BUTTON_HIDE)) {
            editHeader.setText(header);
            editHeader.setTypeface(mycustomFont);
        }else{
            editHeader.setVisibility(View.GONE  );
        }

        String subHeading = dataObj.get_sub_heading();
        editSubHeading = (EditText) view.findViewById(R.id.ABOUT_US_SUBHEADING);
        if(subHeading == null || !subHeading.equals(CROSS_BUTTON_HIDE)) {
            editSubHeading.setText(subHeading);
            editSubHeading.setTypeface(mycustomFont);
        }else{
            editSubHeading.setVisibility(View.GONE);
        }

        String paraGraphAboutUs =  dataObj.get_text_para();
        editParaGraphAboutUs = (EditText) view.findViewById(R.id.paraGraphAboutUs);
        if(paraGraphAboutUs == null || !paraGraphAboutUs.equals(CROSS_BUTTON_HIDE)){
            editParaGraphAboutUs.setText(paraGraphAboutUs);
            editParaGraphAboutUs.setTypeface(mycustomFont);
        }else{
            editParaGraphAboutUs.setVisibility(View.GONE);
        }

        submit_button = (Button)  view.findViewById(R.id.buttonMainAbtUs);
        submit_button.setTypeface(mycustomFont);



        String urlOfProfile = dataObj.get_url();
        if(urlOfProfile != null && !urlOfProfile.equalsIgnoreCase("null")){
            if (!urlOfProfile.equals(CROSS_BUTTON_HIDE)) {
                cardImage.setImageUrl(urlOfProfile, VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());
                cardImageCrop.setVisibility(View.GONE);
            }else {
                cardImage.setVisibility(View.GONE);
                cardImageCrop.setVisibility(View.GONE);

            }

        }else{
            cardImage.setVisibility(View.GONE);
            try {
                cardImageCrop.setImageResource(dataObj.getDefaultDrawableResourceId().get(0));
            }catch (NullPointerException e){
                Log.e(TAG,e.getMessage());
            }

        }


        //TODO Vijay
        //showImageForBackround();
        submit_button.setTypeface(mycustomFont);
        submit_button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if(showPreview == false) { //it is editable and link page to button
                    showMenuOptionToLinkPage();
                }else{

                    int posInListOfPage = dataObj.get_submit_button_link();

                    if(posInListOfPage > 0) {
                        int listSize=MainActivity.listOfTemplatePagesObj.size();
                        dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(posInListOfPage).getTemplateData(true);
                        ((FragmenMainActivity)getActivity()).setDataObj(data); //set dataObj in FragmentMainActivity
                        ((FragmenMainActivity)getActivity()).setIndexOfPresentview(posInListOfPage); //set dataObj in FragmentMainActivity

                        Fragment fragmentToLaunch = data.getFragmentToLaunchPage();
                        ((FragmenMainActivity)getActivity()).setFragmentToLaunch(fragmentToLaunch); //set fragmentToLaunch in FragmentMainActivity

                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction transaction=fragmentManager.beginTransaction();

                        Bundle args = new Bundle();
                        args.putInt("IndexKey", posInListOfPage);
                        fragmentToLaunch.setArguments(args);
                        transaction.replace(R.id.mainRLayout,fragmentToLaunch);
                        transaction.commit();

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

        if(submitBtnText == null || !submitBtnText.equals(CROSS_BUTTON_HIDE)) {
            submit_button.setText(submitBtnText);
        }else{
            submit_button.setVisibility(View.GONE);
        }


   //   showPreview();

        if(showPreview == false) {
            init_editCampaign();
        }else {
            init_viewCampaign();
        }



        return view;
    }

//    void showPreview(){
//        if(((FragmenMainActivity)getActivity()).checkPreview()){    //true means in preview mode
//            init_ViewCampaign();
//            showPreview=true;
//        }
//
//    }

    int lastPositionInList = -1;
    void init_aboutUsPage_request(){
        mProfileId = editCampaign.mCampaignIdFromServer;
        //mPageName = ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US;

        mAbtUsPageObj = MainActivity.getProfileObject().getPageByName(mPageName);
        if(mAbtUsPageObj != null){
            lastPositionInList = MainActivity.getProfileObject().getIndexOfPageFromName(mPageName);
            MainActivity.getProfileObject().deletePageByName(mPageName);
        }
        mAbtUsPageObj = new Page(mProfileId, mPageName);
        mPageId = mAbtUsPageObj.getPageId();
    }


    void init_viewCampaign(){

        try {

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


                if (dataObj.get_url() == null || !dataObj.get_url().equals(CROSS_BUTTON_HIDE)) {
                    deleteCARD_IMAGEBtnView.setVisibility(View.VISIBLE);
                }
                if (dataObj.get_heading() == null || !dataObj.get_heading().equals(CROSS_BUTTON_HIDE)) {
                    deleteHeadingAboutUsBtnView.setVisibility(View.VISIBLE);
                }
                if (dataObj.get_sub_heading() == null || !dataObj.get_sub_heading().equals(CROSS_BUTTON_HIDE)) {
                    deleteSubHeaderAboutUsBtnView.setVisibility(View.VISIBLE);
                }
                if (dataObj.get_text_para() == null || !dataObj.get_text_para().equals(CROSS_BUTTON_HIDE)) {
                    deleteParaAboutUsBtnView.setVisibility(View.VISIBLE);
                }

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


    private void addPageToRequest(){

        init_aboutUsPage_request();

        setAttribute(ProfileFieldsEnum.PROFILE_THEME, String.valueOf(FragmenMainActivity.theme) );

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE, mthispage.nameis() );
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_STATUS, String.valueOf(mthispage.pageStatus()));

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_HEADING, dataObj.get_heading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_SUBHEADING, dataObj.get_sub_heading());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_TITLE, dataObj.get_title());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_CARD_IMAGE, dataObj.get_url());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_PARAGRAPH, dataObj.get_text_para());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_BUTTON_TEXT, dataObj.get_button_text());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_BUTTON_URL_TEXT, dataObj.get_buttonUrl());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_BUTTON_SUBNAME_LINKED_PAGE, String.valueOf(dataObj.get_submit_button_link()));


        Profile reqToMakeProfile =  null;
        reqToMakeProfile = MainActivity.getProfileObject();

        //if(reqToMakeProfile.checkIfPageExist(mPageId)) {
        /*if( MainActivity.getProfileObject().getIndexOfPageFromName(mPageName) != -1){
            int index = reqToMakeProfile.getIndexOfPage(mPageId);
            reqToMakeProfile.replacePage(index, mAbtUsPageObj);
        }else*/
        {
            if(lastPositionInList == -1){
                reqToMakeProfile.addPage(mAbtUsPageObj);
            }else {
                reqToMakeProfile.addPageAtPosition(mAbtUsPageObj, lastPositionInList);
            }

        }
    }


    public void uploadToAboutUsOnApp() {

        if(showPreview == false && !FragmenMainActivity.isImageUploading) {
            String campnName = null;
         /*
        * Need to open gallery directly from here
        * From Cropped image OK clicked editCampaign.java will be opened
        * In editCampaign.java this campaign name(campnName) will be used to set defualt text
        * ScreenName will be used by CropedImage as it will be used to open gallery by multiple classes
        * */

            Intent inf = new Intent(getActivity(), CropedImage.class);
            inf.putExtra("ScreenName", MainActivity.About_Us_TemplateImage_IMAGE_CROPED_NAME);
            inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_ABOUTUSPAGE_ON_APP);

            inf.putExtra("CampaignName", "Choose Image");
            cropRestart=1;
            startActivity(inf);


        }

    }


    ProgressDialog pbImage = null;

     @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {
         FragmenMainActivity.isImageUploading = false;
         progressBar.setVisibility(View.GONE);
        //pbImage.dismiss();
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {

            String ImageName = data.getImageName();
            if (ImageName.equals("About_us_banner")) {
                String imageUrl = data.getResponseUrl();
                cardImageUrl = imageUrl;
                //dataObj.set_url(imageUrl);
                Log.v(TAG, "Url received" + imageUrl);
               deleteCropFile();
            }
        }

    }

    public void deleteCropFile(){
            String path = getActivity().getFilesDir().getAbsolutePath() + "/" + MainActivity.About_Us_TemplateImage_IMAGE_CROPED_NAME;

            File file = new File(path);
            if (file.exists()){
                file.delete();
            }
    }

    public void setAttribute(String name, String value){

        if(name != null && value != null) {
            Attribute atrbtObj = new Attribute(mProfileId, mPageId, name, value);
            mAbtUsPageObj.addAttribute(atrbtObj);
        }
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

    void showLinkButtonToUrl(){

        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                getActivity());

        builderInner.setTitle("Enter Url to Link");

        View v= LayoutInflater.from(getActivity()).inflate(R.layout.url_to_link,null);
        final EditText addUrl = (EditText)v.findViewById(R.id.editTextAddUrl);
        final EditText addNameToButton = (EditText)v.findViewById(R.id.editTextButtonName);
        builderInner.setView(v);


        builderInner.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {

                        String linkUrlToButton = addUrl.getText().toString();
                        String addNameToButtonValue = addNameToButton.getText().toString();

                        if (!linkUrlToButton.equals("") && !addNameToButtonValue.equals("")) {
                            dataObj.set_buttonUrl(linkUrlToButton);

                            Button btn = (Button) getActivity().findViewById(R.id.buttonMainAbtUs);
                            btn.setText(addNameToButtonValue);
                            dataObj.set_button_text(addNameToButtonValue);


                            dialog.dismiss();
                        }else {
                            Toast.makeText(getActivity(), "Please enter valid text", Toast.LENGTH_SHORT).show();
                        }
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

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        //  builderSingle.setIcon(R.drawable.back_black);
        builderSingle.setTitle("Edit Link");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.select_dialog_singlechoice);

        for (int i=0;i<FragmenMainActivity.selectedPageList.size();i++){
            pages obj = MainActivity.listOfTemplatePagesObj.get(i);
            String[] nameStrings = obj.nameis().split(" ", 2);
            String name;


            if (nameStrings.length > 1) {
                name = nameStrings[1];
            } else {
                name = nameStrings[0];
            }
            arrayAdapter.add(name);

        }
        builderSingle.setIcon(R.drawable.logo);
        builderSingle.setTitle("Selected page list");
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
                                getActivity());

                        if(which == 0){

                            builderInner.setTitle("Enter Url to Link");
                            final EditText addUrl = new EditText(getActivity());
                            addUrl.setHint("Add Url");
                            builderInner.setView(addUrl);

                            final EditText addNameToButton = new EditText(getActivity());
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
                                            Button btn = (Button) getActivity().findViewById(R.id.buttonMainAbtUs);
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
                            final EditText edittext = new EditText(getActivity());
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
                                            Button btn = (Button) getActivity().findViewById(R.id.buttonMainAbtUs);
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

    private void showMenuOptionToLinkPage() {

        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(getActivity(), submit_button);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.set_template_button_action, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.connectUrl) {
                    showLinkButtonToUrl();
                } else if (item.getItemId() == R.id.connectPage){
                    showLinkButtonToActivity();
                }else {
                    dataObj.set_button_text(CROSS_BUTTON_HIDE);
                    submit_button.setVisibility(View.GONE);
                }
                return true;
            }
        });

        popup.show();//showing popup menu
    }

    @Override
    public void onClick(View v) {
        if (!FragmenMainActivity.isImageUploading) { //if isImageUploading TRUE disabled onClick
            switch (v.getId()) {
                case R.id.deleteCARD_IMAGE:
                    cardImage.setVisibility(View.GONE);
                    cardImageCrop.setVisibility(View.GONE);
                    dataObj.set_url(CROSS_BUTTON_HIDE);
                    cardImageUrl = CROSS_BUTTON_HIDE;
                    cardImageRelativeLayout.setVisibility(View.GONE);
                    deleteCARD_IMAGEBtnView.setVisibility(View.GONE);
                    break;
                case R.id.deleteHeadingAboutUs:
                    editHeader.setVisibility(View.GONE);
                    editHeader.setText(CROSS_BUTTON_HIDE);
                    dataObj.set_heading(CROSS_BUTTON_HIDE);
                    deleteHeadingAboutUsBtnView.setVisibility(View.GONE);
                    break;
                case R.id.deleteSubHeaderAboutUs:
                    editSubHeading.setVisibility(View.GONE);
                    editSubHeading.setText(CROSS_BUTTON_HIDE);
                    dataObj.set_sub_heading(CROSS_BUTTON_HIDE);
                    deleteSubHeaderAboutUsBtnView.setVisibility(View.GONE);
                    break;
                case R.id.deleteParaAboutUs:
                    editParaGraphAboutUs.setVisibility(View.GONE);
                    editParaGraphAboutUs.setText(CROSS_BUTTON_HIDE);
                    dataObj.set_text_para(CROSS_BUTTON_HIDE);
                    deleteParaAboutUsBtnView.setVisibility(View.GONE);
                    break;
                case R.id.ABOUT_US_STATIC_IMAGE:
                    uploadToAboutUsOnApp();
                    break;
                case R.id.ABOUT_US_CARD_IMAGE:
                    uploadToAboutUsOnApp();
                    break;

            }
        }
    }

        /**
        * What action should happen in case privew button is pressed by user
        * showPreview : if it is true that means it was in priview mode . change this to edit mode
        *  showPreview : if it is false that means it was in edit mode . change this to priview mode
        */


    @Override
    public void init_ViewCampaign() {
        if (showPreview==false){
            init_viewCampaign();
            showPreview = true;
        }else {  //in priview mode
            init_editCampaign();
            showPreview = false;
        }
    }

    @Override
    public void addLastPage() {
        changeText();
        addPageToRequest();
    }


    private class PhotoAsyncTask extends AsyncTask<Void, Void, Void>
    {


        public void AboutUsImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = getActivity().openFileInput(MainActivity.About_Us_TemplateImage_IMAGE_CROPED_NAME);
                final Bitmap bitmap = BitmapFactory.decodeStream(in);
                in.close();


                File sendFile = getFileObjectFromBitmap (bitmap);


                UploadImageForUrlData data =
                        new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "About_us_banner", 1);
                UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity().getApplicationContext(), data, FragmentHomeLayout_1.this);
                req.executeRequest();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
                FragmenMainActivity.isImageUploading = false;
            }


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

            pbImage = new ProgressDialog(getActivity());
            pbImage.setMessage("Uploading Image...");
            //pbImage.show();
            FragmenMainActivity.isImageUploading = true;
            progressBar.setVisibility(View.VISIBLE);

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
            in.close();
            cardImageCrop.setImageDrawable(new BitmapDrawable(getResources(), bitmap));


        }catch (FileNotFoundException e){
            Log.v("editCampaign","About_Us_TemplateImage_IMAGE_CROPED_NAME file not found");
        }catch (IOException e){
            Log.v("editCampaign","About_Us_TemplateImage_IMAGE_CROPED_NAME file not found");
        }

    }

    public  void changeText(){

        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextAbtUs" + title);
        if (!title.equals("")) {
            dataObj.set_title(title);
        }

        String header = String.valueOf(editHeader.getText());
        Log.d(TAG, "changeHeadingTxtAbtUs" + header);
        if (!header.equals("") ) {
            dataObj.set_heading(header);
        }

        String subheader = String.valueOf(editSubHeading.getText());
        Log.d(TAG, "changeSubHeadingAbtUs" + subheader);
        if (!subheader.equals("")) {
            dataObj.set_sub_heading(subheader);
        }

        String para = String.valueOf(editParaGraphAboutUs.getText());
        Log.d(TAG, "changeParaAbtUs" + para);
        if (!para.equals("")) {
            dataObj.set_text_para(para);
        }

        if (cardImageUrl !=null){
            dataObj.set_url(cardImageUrl);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if(indexInList >=0 )
        {
            changeText();
            MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataObj);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(cropRestart==1) {
            showImageForBackround();
            PhotoAsyncTask obj = new PhotoAsyncTask();
            obj.execute();
            cropRestart=0;
        }else {
            //showBaseMenu();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (indexInList >=0) {
            if (hidden) {
                changeText();
                addPageToRequest();
                MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataObj);
            } else {
                changeText();
                MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataObj);
            }
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            changeText();
            MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataObj);
        } else {
            changeText();
            MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataObj);
        }
    }

}
