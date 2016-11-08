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
import com.fourway.ideaswire.templates.ServicesDataTemplate;
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
public class FragmentServiceOnApp extends Fragment implements UploadImageForUrlRequest.UploadImageForUrlCallback, View.OnClickListener, FragmenMainActivity.viewCampaign{
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

    //Variables to make request to server
    Page mServicePageObj;
    String mProfileId = null;
    String mPageName = null;
    String mParentId = null;

    int cropRestart=0;

    public static String TAG="ServiceOnApp";

    ServicesDataTemplate dataObj;
    private boolean showPreview = false;
    pages mthispage = null;
    int indexInList = -1;

    String cardImageUrl = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_service,container,false);

        dataObj = (ServicesDataTemplate) ((FragmenMainActivity)getActivity()).getDatObject();//savedInstanceState.getSerializable("dataKey");

        if(dataObj.isDefaultDataToCreateCampaign() == false){
            showPreview = true;
        }else{
            indexInList = (int)((FragmenMainActivity)getActivity()).getIndexOfPresentview();
            mthispage = MainActivity.listOfTemplatePagesObj.get(indexInList);
            //mPageName = mthispage.nameis();
        }

        deleteTitle =(ImageView)view.findViewById(R.id.deleteTitleService);
        deleteHeading =(ImageView)view.findViewById(R.id.deleteHeadingService);
        deleteSubHeading =(ImageView)view.findViewById(R.id.deleteSubHeadingService);
        deletePara =(ImageView)view.findViewById(R.id.deleteParaService);
        deleteCardImage =(ImageView)view.findViewById(R.id.deleteCardService);
        deleteHeadingBlow =(ImageView)view.findViewById(R.id.deleteHeadingBlowingService);
        deleteSubHeadingBlow =(ImageView)view.findViewById(R.id.deleteSubHeadingBlowingService);
        deleteParaBlow =(ImageView)view.findViewById(R.id.deleteParaBlowingService);

        deleteTitle.setOnClickListener(this);
        deleteHeading.setOnClickListener(this);
        deleteSubHeading.setOnClickListener(this);
        deletePara.setOnClickListener(this);
        deleteCardImage.setOnClickListener(this);
        deleteHeadingBlow.setOnClickListener(this);
        deleteSubHeadingBlow.setOnClickListener(this);
        deleteParaBlow.setOnClickListener(this);


        editTitle =(EditText)view.findViewById(R.id.Service_TITLE);
        editHeading = (EditText) view.findViewById(R.id.service_heading);
        editSubHeading = (EditText) view.findViewById(R.id.service_subheading);
        editParaGraph = (EditText) view.findViewById(R.id.service_para);
        editHeading_below = (EditText) view.findViewById(R.id.service_heading_belowimg);
        editSubHeading_below = (EditText) view.findViewById(R.id.service_subheading_belowimg);
        editParaGraph_below = (EditText) view.findViewById(R.id.service_para_belowing);

        cardImage =(NetworkImageView)view.findViewById(R.id.Service_CARD_IMAGE);
        cardImageCrop =(ImageView)view.findViewById(R.id.Service_STATIC_IMAGE);

        cardImageCrop.setOnClickListener(this);

        String urlOfProfile = dataObj.getUrlOfImage();

        if(urlOfProfile != null){
            // Uri cardImageUri = Uri.parse(urlOfProfile);
            // cardImage.setImageURI(cardImageUri);
            cardImage.setImageUrl(urlOfProfile, VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());
            cardImageCrop.setVisibility(View.GONE);

        }else{
            cardImage.setVisibility(View.GONE);
            cardImageCrop.setImageResource(R.drawable.services_banner);
        }

        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        String title=dataObj.getTitle();
        if (title!=null && !title.equals("")){
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else{
            editTitle.setVisibility(View.GONE);
        }

        String heading=dataObj.getHeading();
        if (heading!=null && !heading.equals("")){
            editHeading.setText(heading);
            editHeading.setTypeface(mycustomFont);
        }else{
            editHeading.setVisibility(View.GONE);
        }

        String subHeading=dataObj.getSubHeading();
        if (subHeading!=null && !subHeading.equals("")){
            editSubHeading.setText(subHeading);
            editSubHeading.setTypeface(mycustomFont);
        }else{
            editSubHeading.setVisibility(View.GONE);
        }

        String para=dataObj.getParaGraph();
        if (para!=null && !para.equals("")){
            editParaGraph.setText(para);
            editParaGraph.setTypeface(mycustomFont);
        }else{
            editParaGraph.setVisibility(View.GONE);
        }

        String headingBelow=dataObj.getHeading_below();
        if (headingBelow!=null && ! headingBelow.equals("")){
            editHeading_below.setText(headingBelow);
            editHeading_below.setTypeface(mycustomFont);
        }else{
            editSubHeading_below.setVisibility(View.GONE);
        }

        String subHeadingBelow=dataObj.getSubHeading_below();
        if (subHeadingBelow!=null && !subHeadingBelow.equals("")){
            editSubHeading_below.setText(subHeadingBelow);
            editSubHeading_below.setTypeface(mycustomFont);
        }else{
            editSubHeading_below.setVisibility(View.GONE);
        }

        String paraBelow=dataObj.getGetParaGraph_below();
        if (paraBelow!=null && !paraBelow.equals("")){
            editParaGraph_below.setText(paraBelow);
            editParaGraph_below.setTypeface(mycustomFont);
        }else{
            editParaGraph_below.setVisibility(View.GONE);
        }


        if(showPreview == true) {
            init_viewCampaign();
        }else{
            init_editCampaign();
        }

        if (dataObj.isDefaultDataToCreateCampaign()){
            showPreview();
        }


        return view;
    }

    int lastPositionInList = -1;
    void init_servicePage_request(){
        mProfileId = editCampaign.mCampaignIdFromServer;
        mPageName = ProfileFieldsEnum.PROFILE_PAGE_SERVICES;
        mServicePageObj  =  MainActivity.getProfileObject().getPageByName(mPageName);
        if (mServicePageObj==null) {
            lastPositionInList = MainActivity.getProfileObject().getIndexOfPageFromName(mPageName);
            MainActivity.getProfileObject().deletePageByName(mPageName);
        }
        mServicePageObj = new Page(mProfileId, mPageName);
        mParentId = mServicePageObj.getPageId();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteTitleService:
                editTitle.setVisibility(View.GONE);
                deleteTitle.setVisibility(View.GONE);
                break;
            case R.id.deleteHeadingService:
                editHeading.setVisibility(View.GONE);
                deleteHeading.setVisibility(View.GONE);
                break;
            case R.id.deleteSubHeadingService:
                editSubHeading.setVisibility(View.GONE);
                deleteSubHeading.setVisibility(View.GONE);
                break;
            case R.id.deleteParaService:
                editParaGraph.setVisibility(View.GONE);
                deletePara.setVisibility(View.GONE);
                break;
            case R.id.deleteCardService:
                cardImage.setVisibility(View.GONE);
                deleteCardImage.setVisibility(View.GONE);
                break;
            case R.id.deleteHeadingBlowingService:
                editHeading_below.setVisibility(View.GONE);
                deleteHeadingBlow.setVisibility(View.GONE);
                break;
            case R.id.deleteSubHeadingBlowingService:
                editSubHeading_below.setVisibility(View.GONE);
                deleteSubHeadingBlow.setVisibility(View.GONE);
                break;
            case R.id.deleteParaBlowingService:
                editParaGraph_below.setVisibility(View.GONE);
                deleteParaBlow.setVisibility(View.GONE);
                break;
            case R.id.Service_STATIC_IMAGE:
                uploadToServiceOnApp();
                break;

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


    private class PhotoAsyncTask extends AsyncTask<Void, Void, Void>
    {


        public void ServiceImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = getActivity().openFileInput(MainActivity.Service_TemplateImage_IMAGE_CROPED_NAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            File sendFile = getFileObjectFromBitmap (bitmap);


            UploadImageForUrlData data =
                    new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "Service banner", 5);
            UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity().getApplicationContext(), data, FragmentServiceOnApp.this);
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
            FileInputStream in = getActivity().openFileInput(MainActivity.Service_TemplateImage_IMAGE_CROPED_NAME);

            cardImage.setVisibility(View.GONE);
            cardImageCrop.setVisibility(View.VISIBLE);

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            cardImageCrop.setImageDrawable(new BitmapDrawable(getResources(), bitmap));



        }catch (FileNotFoundException e){
            Log.v("editCampaign","Service_TemplateImage_IMAGE_CROPED_NAME file not found");
        }

    }

    public void uploadToServiceOnApp() {

        if(showPreview == false) {
            String campnName = null;
         /*
        * Need to open gallery directly from here
        * From Cropped image OK clicked editCampaign.java will be opened
        * In editCampaign.java this campaign name(campnName) will be used to set defualt text
        * ScreenName will be used by CropedImage as it will be used to open gallery by multiple classes
        * */

            Intent inf = new Intent(getActivity(), CropedImage.class);
            inf.putExtra("ScreenName", MainActivity.Service_TemplateImage_IMAGE_CROPED_NAME);
            inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_SERVICE_ON_APP);
            cropRestart=1;
            inf.putExtra("CampaignName", "Choose Image");
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

    @Override
    public void onPause() {
        super.onPause();
        if(indexInList >=0 ){
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Do your Work
        } else {
            changeText();
            addPageToRequest();
            MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataObj);
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

    ProgressDialog pbImage = null;
    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {
        pbImage.hide();
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            String imageUrl = data.getResponseUrl();
            cardImageUrl = imageUrl;
            Log.v(TAG,"Url received" + imageUrl);
        }
    }

    void changeText(){
        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextService" + title);
        if (title != null) {
            dataObj.setTitle(title);
        }

        String header = String.valueOf(editHeading.getText());
        Log.d(TAG, "changeHeadingTxtService" + header);
        if (header != null) {
            dataObj.setHeading(header);
        }

        String subheader = String.valueOf(editSubHeading.getText());
        Log.d(TAG, "changeSubHeadingService" + subheader);
        if (subheader != null) {
            dataObj.setSubHeading(subheader);
        }

        String para = String.valueOf(editParaGraph.getText());
        Log.d(TAG, "changeParaService" + para);
        if (para != null) {
            dataObj.setParaGraph(para);
        }

        String headerBelow = String.valueOf(editHeading_below.getText());
        Log.d(TAG, "changeHeadingBelowTxtService" + headerBelow);
        if (headerBelow != null) {
            dataObj.setHeading_below(headerBelow);
        }

        String subheaderBelow = String.valueOf(editSubHeading_below.getText());
        Log.d(TAG, "changeSubHeadingBelowTxService" + subheaderBelow);
        if (subheaderBelow != null) {
            dataObj.setSubHeading_below(subheaderBelow);
        }

        String paraBelow = String.valueOf(editParaGraph_below.getText());
        Log.d(TAG, "changeSubHeadingBelowTxtService" + paraBelow);
        if (paraBelow != null) {
            dataObj.setGetParaGraph_below(paraBelow);
        }

        if (cardImageUrl!=null) {
            dataObj.setUrlOfImage(cardImageUrl);
        }
    }

    private void setAttribute(String name, String value){
        if (name != null && value != null) {
            Attribute atrbtObj = new Attribute(mProfileId, mParentId, name, value);
            mServicePageObj.addAttribute(atrbtObj);
        }
    }

    void addPageToRequest(){
        init_servicePage_request();

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_TITLE, dataObj.getTitle());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_HEADING_1, dataObj.getHeading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_HEADING_2, dataObj.getHeading_below());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_SUBHEADING_1, dataObj.getSubHeading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_SUBHEADING_2, dataObj.getSubHeading_below());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_CARD_IMAGE, dataObj.getUrlOfImage());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_PARAGRAPH_1, dataObj.getParaGraph());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_SERVICES_PARAGRAPH_2, dataObj.getParaGraph_below);

        Profile reqToMakeProfile =  MainActivity.getProfileObject();

        if (lastPositionInList == -1){
            reqToMakeProfile.addPage(mServicePageObj);
        }else {
            reqToMakeProfile.addPageAtPosition(mServicePageObj, lastPositionInList);
        }
    }
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
            editTitle.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(editHeading !=null) {
            editHeading.setEnabled(true);
            editHeading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (editSubHeading !=null) {
            editSubHeading.setEnabled(true);
            editSubHeading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (editParaGraph !=null) {
            editParaGraph.setEnabled(true);
            editParaGraph.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if (editHeading_below !=null) {
            editHeading_below.setEnabled(true);
            editHeading_below.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (editSubHeading_below !=null){
            editSubHeading_below.setEnabled(true);
            editSubHeading_below.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (editParaGraph_below !=null){
            editParaGraph_below.setEnabled(true);
            editParaGraph_below.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }


    }


    public String toString()
    {
        return "Services";
    }

    void showPreview(){

        if(((FragmenMainActivity)getActivity()).checkPreview()){
            init_ViewCampaign();
            showPreview=true;
        }

    }
}
