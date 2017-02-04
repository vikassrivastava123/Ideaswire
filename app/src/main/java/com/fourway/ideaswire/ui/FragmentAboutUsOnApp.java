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
import android.widget.ProgressBar;
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
import com.fourway.ideaswire.templates.AboutUsDataTemplate;
import com.fourway.ideaswire.templates.pages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.fourway.ideaswire.ui.MainActivity.CROSS_BUTTON_HIDE;

/**
 * Created by 4way on 24-10-2016.
 */
public class FragmentAboutUsOnApp extends Fragment implements View.OnClickListener, FragmenMainActivity.viewCampaign, UploadImageForUrlRequest.UploadImageForUrlCallback{

    TextView mTitle;
    EditText editTitle =null,
            editHeader =null,
            editSubheading =null,
            editParaGraph =null;

    NetworkImageView cardImage_1,cardImage_2;
    ImageView cardImageCrop_1, cardImageCrop_2;
    ProgressBar progressBar1;
    ProgressBar progressBar2;

    RelativeLayout cardImageLayout1;

    ImageView deleteTitle=null,
            deleteHeading=null,
            deleteSubHeading=null,
            deletePara=null,
            deleteCardImage_1=null,
            deleteCardImage_2=null;

    int cropRestart=0;

    public String TAG="FragmentAboutUsOnApp";

    AboutUsDataTemplate dataObj;
    private boolean showPreview = false;
    //private boolean mEditMode = false;

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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);

        //mEditMode = getActivity().getIntent().getBooleanExtra(MainActivity.ExplicitEditModeKey, false);

        dataObj = (AboutUsDataTemplate)((FragmenMainActivity)getActivity()).getDatObject();

        if (dataObj.isEditDefaultOrUpdateData() == true ){

            indexInList = (int)((FragmenMainActivity)getActivity()).getIndexOfPresentview();
            mthispage = MainActivity.listOfTemplatePagesObj.get(indexInList);
            mPageName = mthispage.nameis();
            showPreview = false;
        }else {
            showPreview = true;
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        progressBar1 = (ProgressBar)view.findViewById(R.id.progressBar_home1);
        progressBar2 = (ProgressBar)view.findViewById(R.id.progressBar_home2);

        cardImage_1 =(NetworkImageView)view.findViewById(R.id.Home_CARD_IMAGE_1);
        cardImage_2 =(NetworkImageView)view.findViewById(R.id.Home_CARD_IMAGE_2);

        cardImageCrop_1 = (ImageView)view.findViewById(R.id.Home_STATIC_IMAGE_1);
        cardImageCrop_2 = (ImageView)view.findViewById(R.id.Home_STATIC_IMAGE_2);

        cardImage_1.setOnClickListener(this);
        cardImage_2.setOnClickListener(this);
        cardImageCrop_1.setOnClickListener(this);
        cardImageCrop_2.setOnClickListener(this);

        cardImageLayout1 =(RelativeLayout)view.findViewById(R.id.cardImageLayout1);

        deleteTitle =(ImageView)view.findViewById(R.id.deleteTitlehome);
        deleteHeading =(ImageView)view.findViewById(R.id.deleteHeadingHome);
        deleteSubHeading =(ImageView)view.findViewById(R.id.deleteSubHeadingHome);
        deletePara =(ImageView)view.findViewById(R.id.deleteParaHome);

        deleteCardImage_1 =(ImageView)view.findViewById(R.id.deleteCARD_IMAGE_1);
        deleteCardImage_2 =(ImageView) view.findViewById(R.id.deleteCARD_IMAGE_2);

        deleteTitle.setVisibility(View.GONE);
        deleteHeading.setVisibility(View.GONE);
        deleteSubHeading.setVisibility(View.GONE);
        deletePara.setVisibility(View.GONE);
        deleteCardImage_1.setVisibility(View.GONE);
        deleteCardImage_2.setVisibility(View.GONE);


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
        if(title == null || !title.equals(CROSS_BUTTON_HIDE)){
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }
        else{
            editTitle.setVisibility(View.GONE);
        }

        String header=dataObj.getHeading();
        if(header == null || !header.equals(CROSS_BUTTON_HIDE)){
            editHeader.setText(header);
            editHeader.setTypeface(mycustomFont);
        }else{
            editHeader.setVisibility(View.GONE);
        }

        String subHeading=dataObj.getSubHeading();
        if(subHeading == null || !subHeading.equals(CROSS_BUTTON_HIDE)){
            editSubheading.setText(subHeading);
            editSubheading.setTypeface(mycustomFont);
        }else{
            editSubheading.setVisibility(View.GONE);
        }

        String paraGraph=dataObj.getParaGraph();
        if(paraGraph == null || !paraGraph.equals(CROSS_BUTTON_HIDE)){
            editParaGraph.setText(paraGraph);
            editParaGraph.setTypeface(mycustomFont);
        }else{
            editParaGraph.setVisibility(View.GONE);
        }

        String urlOfProfile_1 = dataObj.getUrlOfImage_1();

        if(urlOfProfile_1 != null){
            if (!urlOfProfile_1.equals(CROSS_BUTTON_HIDE)) {
                cardImage_1.setImageUrl(urlOfProfile_1, VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());
                cardImageCrop_1.setVisibility(View.GONE);
            }else {
                cardImage_1.setVisibility(View.GONE);
                cardImageCrop_1.setVisibility(View.GONE);
            }

        }else{
            cardImage_1.setVisibility(View.GONE);
            try {
                cardImageCrop_1.setImageResource(dataObj.getDefaultDrawableResourceId().get(0)); //set default image
            }catch (NullPointerException e){
                Log.e(TAG,e.getMessage());
            }


        }

        String urlOfProfile_2 = dataObj.getUrlOfImage_2();
        if(urlOfProfile_2 != null){
            if (!urlOfProfile_2.equals(CROSS_BUTTON_HIDE)) {
                cardImage_2.setImageUrl(urlOfProfile_2, VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());
                cardImageCrop_2.setVisibility(View.GONE);
            }else {
                cardImage_2.setVisibility(View.GONE);
                cardImageCrop_2.setVisibility(View.GONE);
            }

        }else{
            cardImage_2.setVisibility(View.GONE);
            try {
                cardImageCrop_2.setImageResource(dataObj.getDefaultDrawableResourceId().get(0)); //set default image
            }catch (NullPointerException e){
                Log.e(TAG,e.getMessage());
            }


        }

        //showImageForBackround();

      //  showPreview();

        if(showPreview == false) {
            init_editCampaign();
        }else {
            init_viewCampaign();
        }
        return view;
    }

    int lastPositionInList = -1;
    void init_homeUsPage_request(){
        mProfileId = editCampaign.mCampaignIdFromServer;
        //mPageName = ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE;

       mHomePageObj = MainActivity.getProfileObject().getPageByName(mPageName);
       if (mHomePageObj != null) {
           lastPositionInList = MainActivity.getProfileObject().getIndexOfPageFromName(mPageName);
           MainActivity.getProfileObject().deletePageByName(mPageName);

        }
        mHomePageObj = new Page(mProfileId, mPageName);
        mParentId = mHomePageObj.getPageId();
    }

    @Override
    public void onClick(View v) {
        if (!FragmenMainActivity.isImageUploading) {
            switch (v.getId()) {
                case R.id.deleteTitlehome:
                    editTitle.setVisibility(View.GONE);
                    editTitle.setText(CROSS_BUTTON_HIDE);
                    dataObj.setTitle(CROSS_BUTTON_HIDE);
                    deleteTitle.setVisibility(View.GONE);
                    break;
                case R.id.deleteCARD_IMAGE_1:
                    cardImage_1.setVisibility(View.GONE);
                    cardImageLayout1.setVisibility(View.GONE);
                    deleteCardImage_1.setVisibility(View.GONE);
                    dataObj.setUrlOfImage_1(CROSS_BUTTON_HIDE);
                    cardImageUrl_1 = CROSS_BUTTON_HIDE;
                    break;
                case R.id.deleteHeadingHome:
                    editHeader.setVisibility(View.GONE);
                    editHeader.setText(CROSS_BUTTON_HIDE);
                    dataObj.setHeading(CROSS_BUTTON_HIDE);
                    deleteHeading.setVisibility(View.GONE);
                    break;
                case R.id.deleteSubHeadingHome:
                    editSubheading.setVisibility(View.GONE);
                    editSubheading.setText(CROSS_BUTTON_HIDE);
                    dataObj.setSubHeading(CROSS_BUTTON_HIDE);
                    deleteSubHeading.setVisibility(View.GONE);
                    break;
                case R.id.deleteParaHome:
                    editParaGraph.setVisibility(View.GONE);
                    editParaGraph.setText(CROSS_BUTTON_HIDE);
                    dataObj.setParaGraph(CROSS_BUTTON_HIDE);
                    deletePara.setVisibility(View.GONE);
                    break;
                case R.id.deleteCARD_IMAGE_2:
                    deleteCardImage_2.setVisibility(View.GONE);
                    cardImageCrop_2.setVisibility(View.GONE);
                    cardImage_2.setVisibility(View.GONE);
                    dataObj.setUrlOfImage_2(CROSS_BUTTON_HIDE);
                    cardImageUrl_2 = CROSS_BUTTON_HIDE;
                    break;
                case R.id.Home_STATIC_IMAGE_1:
                    uploadToHomeOnApp(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_1, 1);
                    break;
                case R.id.Home_CARD_IMAGE_1:
                    uploadToHomeOnApp(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_1, 1);
                    break;
                case R.id.Home_STATIC_IMAGE_2:
                    uploadToHomeOnApp(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_2, 2);
                    break;
                case R.id.Home_CARD_IMAGE_2:
                    uploadToHomeOnApp(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_2, 2);
                    break;
            }

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

        if(showPreview == false && !FragmenMainActivity.isImageUploading) {
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
                final Bitmap bitmap = BitmapFactory.decodeStream(in);
                File sendFile = getFileObjectFromBitmap (bitmap);


                UploadImageForUrlData data =
                        new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "Home_banner_1", 2);
                UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity().getApplicationContext(), data, FragmentAboutUsOnApp.this);
                req.executeRequest();
            } catch (FileNotFoundException e) {
                progressBar1.setVisibility(View.GONE);
                FragmenMainActivity.isImageUploading = false;
                e.printStackTrace();
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
            progressBar1.setVisibility(View.VISIBLE);
            FragmenMainActivity.isImageUploading = true;

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
                final Bitmap bitmap = BitmapFactory.decodeStream(in);
                File sendFile = getFileObjectFromBitmap (bitmap);


                UploadImageForUrlData data =
                        new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "Home_banner_2", 3);
                UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity().getApplicationContext(), data, FragmentAboutUsOnApp.this);
                req.executeRequest();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                progressBar2.setVisibility(View.GONE);
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
            progressBar2.setVisibility(View.VISIBLE);
            FragmenMainActivity.isImageUploading = true;

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {
        pbImage.hide();
        FragmenMainActivity.isImageUploading = false;
        progressBar1.setVisibility(View.GONE);
        progressBar2.setVisibility(View.GONE);

        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            String ImageName = data.getImageName();

            if (ImageName.equals("Home_banner_1")) {
                String imageUrl = data.getResponseUrl();
                cardImageUrl_1 = imageUrl;
                //dataObj.setUrlOfImage_1(imageUrl);
                Log.v(TAG, "Url received_1 " + imageUrl);
                deleteCropFile(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_1);
            }else if (ImageName.equals("Home_banner_2")){
                String imageUrl = data.getResponseUrl();
                cardImageUrl_2 = imageUrl;
                //dataObj.setUrlOfImage_2(imageUrl);
                Log.v(TAG, "Url received_2 " + imageUrl);
                deleteCropFile(MainActivity.Home_TemplateImage_IMAGE_CROPED_NAME_2);
            }
        }
    }

    public void deleteCropFile(String imageName){
        String path = getActivity().getFilesDir().getAbsolutePath() + "/" + imageName;

        File file = new File(path);
        if (file.exists()){
            file.delete();
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
           // addPageToRequest();
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

                if (dataObj.getTitle() == null || !dataObj.getTitle().equals(CROSS_BUTTON_HIDE)) {
                    deleteTitle.setVisibility(View.VISIBLE);
                }

                if (dataObj.getHeading() == null || !dataObj.getHeading().equals(CROSS_BUTTON_HIDE)) {
                    deleteHeading.setVisibility(View.VISIBLE);
                }

                if (dataObj.getSubHeading() == null || !dataObj.getSubHeading().equals(CROSS_BUTTON_HIDE)) {
                    deleteSubHeading.setVisibility(View.VISIBLE);
                }

                if (dataObj.getParaGraph() == null || !dataObj.getParaGraph().equals(CROSS_BUTTON_HIDE)) {
                    deletePara.setVisibility(View.VISIBLE);
                }

                if (dataObj.getUrlOfImage_1() == null || !dataObj.getUrlOfImage_1().equals(CROSS_BUTTON_HIDE)) {
                    deleteCardImage_1.setVisibility(View.VISIBLE);
                }

                if (dataObj.getUrlOfImage_2() == null || !dataObj.getUrlOfImage_2().equals(CROSS_BUTTON_HIDE)) {
                    deleteCardImage_2.setVisibility(View.VISIBLE);
                }

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

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US, mthispage.nameis());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_STATUS, String.valueOf(mthispage.pageStatus()));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_HEADING, dataObj.getHeading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_SUBHEADING, dataObj.getSubHeading());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_TITLE, dataObj.getTitle());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_CARD_IMAGE_1, dataObj.getUrlOfImage_1());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_CARD_IMAGE_2, dataObj.getUrlOfImage_2());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_PARAGRAPH, dataObj.getParaGraph());


        Profile reqToMakeProfile;
        reqToMakeProfile = MainActivity.getProfileObject();


        //if(reqToMakeProfile.checkIfPageExist(mPageId)) {
        if( lastPositionInList == -1){
            reqToMakeProfile.addPage(mHomePageObj);
        }else {
            reqToMakeProfile.addPageAtPosition(mHomePageObj, lastPositionInList);
        }
    }

//    void showPreview(){
//        if(((FragmenMainActivity)getActivity()).checkPreview()){
//            init_ViewCampaign();
//            showPreview=true;
//        }
//
//    }

    public  void changeText(){
        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextHome" + title);
        if (!title.equals("")) {
            dataObj.setTitle(title);
        }

        String header = String.valueOf(editHeader.getText());
        Log.d(TAG, "changeHeadingTxtHome" + header);
        if (!header.equals("")) {
            dataObj.setHeading(header);
        }
        String subheader = String.valueOf(editSubheading.getText());
        Log.d(TAG, "changeSubHeadingHome" + subheader);
        if (!subheader.equals("")) {
            dataObj.setSubHeading(subheader);
        }

        String para = String.valueOf(editParaGraph.getText());
        Log.d(TAG, "changeParaHome" + para);
        if (!para.equals("")) {
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
