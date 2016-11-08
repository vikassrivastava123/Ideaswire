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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.fourway.ideaswire.templates.HomePageDataTemplate;
import com.fourway.ideaswire.templates.pages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 4way on 24-10-2016.
 */
public class FragmentHomeOnApp extends Fragment implements View.OnClickListener, FragmenMainActivity.viewCampaign, UploadImageForUrlRequest.UploadImageForUrlCallback{

    TextView mTitle;
    EditText editTitle =null,
            editHeader =null,
            editSubheading =null,
            editParaGraph =null;

    NetworkImageView cardImage_1,cardImage_2;
    ImageView cardImageCrop_1, cardImageCrop_2;

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

    String cardImageUrl_1 = null;
    String cardImageUrl_2 = null;

    //Variables to make request to server
    Page  mHomePageObj;
    String mProfileId = null;
    String mPageName = null;
    String mParentId = null;
    Profile requestToMakeProfile;

    int indexInList = -1;
    pages mthispage = null;



    public String name()
    {
        return "Home Page";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);

        dataObj = (HomePageDataTemplate)((FragmenMainActivity)getActivity()).getDatObject();

        if(dataObj.isDefaultDataToCreateCampaign() == false){
            showPreview = true;
        }else{
            indexInList = (int)((FragmenMainActivity)getActivity()).getIndexOfPresentview();
            mthispage = MainActivity.listOfTemplatePagesObj.get(indexInList);
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        cardImage_1 =(NetworkImageView)view.findViewById(R.id.Home_CARD_IMAGE_1);
        cardImage_2 =(NetworkImageView)view.findViewById(R.id.Home_CARD_IMAGE_2);

        cardImageCrop_1 = (ImageView)view.findViewById(R.id.Home_STATIC_IMAGE_1);
        cardImageCrop_2 = (ImageView)view.findViewById(R.id.Home_STATIC_IMAGE_2);

        cardImageCrop_1.setOnClickListener(this);
        cardImageCrop_2.setOnClickListener(this);

        cardImageLayout1 =(RelativeLayout)view.findViewById(R.id.cardImageLayout1);

        deleteTitle =(ImageView)view.findViewById(R.id.deleteTitlehome);
        deleteHeading =(ImageView)view.findViewById(R.id.deleteHeadingHome);
        deleteSubHeading =(ImageView)view.findViewById(R.id.deleteSubHeadingHome);
        deletePara =(ImageView)view.findViewById(R.id.deleteParaHome);

        deleteCardImage_1 =(ImageView)view.findViewById(R.id.deleteCARD_IMAGE_1);
        deleteCardImage_2 =(ImageView) view.findViewById(R.id.deleteCARD_IMAGE_2);


        cardImageCrop_1.setOnClickListener(this);
        deleteTitle.setOnClickListener(this);
        deleteHeading.setOnClickListener(this);
        deleteSubHeading.setOnClickListener(this);
        deletePara.setOnClickListener(this);
        deleteCardImage_1.setOnClickListener(this);
        deleteCardImage_2.setOnClickListener(this);

        editTitle =(EditText)view.findViewById(R.id.Home_TITLE);
        editHeader = (EditText) view.findViewById(R.id.headingHome );
        editSubheading = (EditText) view.findViewById(R.id.subHeadingHome );
        editParaGraph = (EditText) view.findViewById(R.id.paraGraphHome );

        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");
        String title=dataObj.getTitle();
        if(title!=null){
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }
        else{
            editTitle.setVisibility(View.GONE);
        }

        String header=dataObj.getHeading();
        if(header!=null && !header.equals("")){
            editHeader.setText(header);
            editHeader.setTypeface(mycustomFont);
        }else{
            editHeader.setVisibility(View.GONE);
        }

        String subHeading=dataObj.getSubHeading();
        if(subHeading!=null && !subHeading.equals("")){
            editSubheading.setText(subHeading);
            editSubheading.setTypeface(mycustomFont);
        }else{
            editSubheading.setVisibility(View.GONE);
        }

        String paraGraph=dataObj.getParaGraph();
        if(paraGraph!=null && !paraGraph.equals("")){
            editParaGraph.setText(paraGraph);
            editParaGraph.setTypeface(mycustomFont);
        }else{
            editParaGraph.setVisibility(View.GONE);
        }

        String urlOfProfile_1 = dataObj.getUrlOfImage_1();

        if(urlOfProfile_1 != null){
            cardImage_1.setImageUrl(urlOfProfile_1, VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());
            cardImageCrop_1.setVisibility(View.GONE);

        }else{
            cardImage_1.setVisibility(View.GONE);
            cardImageCrop_1.setImageResource(R.drawable.about_banner_1);

        }

        String urlOfProfile_2 = dataObj.getUrlOfImage_2();
        if(urlOfProfile_2 != null){
            cardImage_2.setImageUrl(urlOfProfile_2, VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());
            cardImageCrop_2.setVisibility(View.GONE);

        }else{
            cardImage_2.setVisibility(View.GONE);
            cardImageCrop_2.setImageResource(R.drawable.about_banner_1);

        }

        //showImageForBackround();

        if(showPreview == true) {
            init_viewCampaign();
        }else{
            init_editCampaign();
        }

        if (dataObj.isDefaultDataToCreateCampaign()) {
            showPreview();
        }
        return view;
    }

    void init_homeUsPage_request(){
        mProfileId = editCampaign.mCampaignIdFromServer;
        mPageName = ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE;

        mHomePageObj  = MainActivity.getProfileObject().getPageByName(mPageName);
        if (mHomePageObj == null) {
            mHomePageObj = new Page(mProfileId, mPageName);
        }
        mParentId = mHomePageObj.getPageId();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteTitlehome:
                editTitle.setVisibility(View.GONE);
                editTitle.setText("");
                deleteTitle.setVisibility(View.GONE);
                break;
            case R.id.deleteCARD_IMAGE_1:
                cardImage_1.setVisibility(View.GONE);
                cardImageLayout1.setVisibility(View.GONE);
                deleteCardImage_1.setVisibility(View.GONE);
                break;
            case R.id.deleteHeadingHome:
                editHeader.setVisibility(View.GONE);
                editHeader.setText("");
                deleteHeading.setVisibility(View.GONE);
                break;
            case R.id.deleteSubHeadingHome:
                editSubheading.setVisibility(View.GONE);
                editSubheading.setText("");
                deleteSubHeading.setVisibility(View.GONE);
                break;
            case R.id.deleteParaHome:
                editParaGraph.setVisibility(View.GONE);
                editParaGraph.setText("");
                deletePara.setVisibility(View.GONE);
                break;
            case R.id.deleteCARD_IMAGE_2:
                deleteCardImage_2.setVisibility(View.GONE);
                cardImage_2.setVisibility(View.GONE);
                break;
            case R.id.Home_STATIC_IMAGE_1:
                uploadToHomeOnApp(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_1, 1);
                break;
            case R.id.Home_STATIC_IMAGE_2:
                uploadToHomeOnApp(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_2, 2);
                break;
        }
    }

    private void setAttribute(String name, String value){
        if (name != null && value != null) {
            Attribute atrbtObj = new Attribute(mProfileId, mParentId, name, value);
            mHomePageObj.addAttribute(atrbtObj);
        }
    }

    private void showImageForBackround(String ImageName, NetworkImageView cardImageView, ImageView cardImageCrop){
        Log.v("editCampaign", "showImageCampaign");
        try {
            FileInputStream in = getActivity().openFileInput(ImageName);

            cardImageView.setVisibility(View.GONE);
            cardImageCrop.setVisibility(View.VISIBLE);

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            cardImageCrop.setImageDrawable(new BitmapDrawable(getResources(), bitmap));


        }catch (FileNotFoundException e){
            Log.v("editCampaign","Home_TemplateImage_IMAGE_CROPED_NAME file not found");
        }

    }

    public void uploadToHomeOnApp(String ImageName, int forWhichImage) {

        if(showPreview == false) {
            String campnName = null;
         /*
        * Need to open gallery directly from here
        * From Cropped image OK clicked editCampaign.java will be opened
        * In editCampaign.java this campaign name(campnName) will be used to set defualt text
        * ScreenName will be used by CropedImage as it will be used to open gallery by multiple classes
        * */

            Intent inf = new Intent(getActivity(), CropedImage.class);
            inf.putExtra("ScreenName", ImageName);
            inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_HOME_PAGE_ON_APP);
            inf.putExtra("CampaignName", "Choose Image");
            cropRestart=forWhichImage;
            startActivity(inf);


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

    ProgressDialog pbImage = null;
    private class PhotoAsyncTask_1 extends AsyncTask<Void, Void, Void>
    {


        public void HomeImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = getActivity().openFileInput(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            File sendFile = getFileObjectFromBitmap (bitmap);


            UploadImageForUrlData data =
                    new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "Home_banner_1", 2);
            UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity().getApplicationContext(), data, FragmentHomeOnApp.this);
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

            pbImage = new ProgressDialog(getActivity().getApplicationContext());
            pbImage.setMessage("Uploading Image...");

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }

    private class PhotoAsyncTask_2 extends AsyncTask<Void, Void, Void>
    {


        public void HomeImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = getActivity().openFileInput(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            File sendFile = getFileObjectFromBitmap (bitmap);


            UploadImageForUrlData data =
                    new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "Home_banner_2", 3);
            UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity().getApplicationContext(), data, FragmentHomeOnApp.this);
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

            pbImage = new ProgressDialog(getActivity().getApplicationContext());
            pbImage.setMessage("Uploading Image...");

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {
        pbImage.hide();
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            String ImageName = data.getImageName();

            if (ImageName.equals("Home_banner_1")) {
                String imageUrl = data.getResponseUrl();
                cardImageUrl_1 = imageUrl;
                //dataObj.setUrlOfImage_1(imageUrl);
                Log.v(TAG, "Url received_1 " + imageUrl);
            }else if (ImageName.equals("Home_banner_2")){
                String imageUrl = data.getResponseUrl();
                cardImageUrl_2 = imageUrl;
                //dataObj.setUrlOfImage_2(imageUrl);
                Log.v(TAG, "Url received_2 " + imageUrl);
            }
        }
    }

    @Override
    public void init_ViewCampaign() {
        if (showPreview==false){
            init_viewCampaign();
            showPreview = true;
        }else {
            init_editCampaign();
            showPreview = false;
        }
    }

    @Override
    public void addLastPage() {
        changeText();
        addPageToRequest();
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
        if(cropRestart>0) {
            if (cropRestart == 1) {
                showImageForBackround(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_1, cardImage_1, cardImageCrop_1);
                PhotoAsyncTask_1 obj1 = new PhotoAsyncTask_1();
                obj1.execute();
            }else {
                showImageForBackround(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_2, cardImage_2, cardImageCrop_2);
                PhotoAsyncTask_2 obj2 = new PhotoAsyncTask_2();
                obj2.execute();
            }

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
            editTitle.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (editHeader !=null){
            editHeader.setEnabled(true);
            editHeader.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (editSubheading !=null){
            editSubheading.setEnabled(true);
            editSubheading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (editParaGraph !=null){
            editParaGraph.setEnabled(true);
            editParaGraph.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
    }

    private void addPageToRequest(){
        init_homeUsPage_request();

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_HEADING, dataObj.getHeading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_SUBHEADING, dataObj.getSubHeading());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_TITLE, dataObj.getTitle());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_CARD_IMAGE_1, dataObj.getUrlOfImage_1());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_CARD_IMAGE_2, dataObj.getUrlOfImage_2());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_PARAGRAPH, dataObj.getParaGraph());


        Profile reqToMakeProfile =  MainActivity.getProfileObject();

        //if(reqToMakeProfile.checkIfPageExist(mPageId)) {
        if( MainActivity.getProfileObject().getIndexOfPageFromName(mPageName) != -1){
            int index = reqToMakeProfile.getIndexOfPage(mParentId);
            reqToMakeProfile.replacePage(index, mHomePageObj);
        }else {
            reqToMakeProfile.addPage(mHomePageObj);
        }
    }

    void showPreview(){
        if(((FragmenMainActivity)getActivity()).checkPreview()){
            init_ViewCampaign();
            showPreview=true;
        }

    }

    void changeText(){
        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextHome" + title);
        if (title != null) {
            dataObj.setTitle(title);
        }

        String header = String.valueOf(editHeader.getText());
        Log.d(TAG, "changeHeadingTxtHome" + header);
        if (header != null) {
            dataObj.setHeading(header);
        }
        String subheader = String.valueOf(editSubheading.getText());
        Log.d(TAG, "changeSubHeadingHome" + subheader);
        if (subheader != null) {
            dataObj.setSubHeading(subheader);
        }

        String para = String.valueOf(editParaGraph.getText());
        Log.d(TAG, "changeParaHome" + para);
        if (para != null) {
            dataObj.setParaGraph(para);
        }

        if (cardImageUrl_1!=null){
            dataObj.setUrlOfImage_1(cardImageUrl_1);
        }

        if (cardImageUrl_2!=null){
            dataObj.setUrlOfImage_2(cardImageUrl_2);
        }
    }
}
