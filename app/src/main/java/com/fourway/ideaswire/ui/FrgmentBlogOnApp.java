package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 4way on 15-10-2016.
 */
public class FrgmentBlogOnApp extends Fragment  implements SaveProfileData.SaveProfileResponseCallback  , UploadImageForUrlRequest.UploadImageForUrlCallback {

    EditText heading=null,subheading=null,paraGraphBlog=null,heading_belo=null,subheading_below=null,paraGraphBlog_below=null;
    List<String> attName;
    TextView mTitle=null;
    EditText editTitle = null;

    int cropRestart=0;
    int indexInList = -1;

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

    blogpageDataTemplate dataObj;
    private boolean showPreview = false;

    Page mBlogPageObj;
    String mProfileId = null;
    String mPageName = null;
    String mParentId = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_blog, container, false);

        dataObj = (blogpageDataTemplate)((FragmenMainActivity)getActivity()).getDatObject();//savedInstanceState.getSerializable("dataKey");
        if(dataObj.isDefaultDataToCreateCampaign() == false){
            showPreview = true;
        }else{
            indexInList = (int)((FragmenMainActivity)getActivity()).getIndexOfPresentview();
        }

        deleteTitleBlogBtnView=(ImageView)view.findViewById(R.id.deleteTitleBlog);
        deleteCARD_IMAGEBtnView=(ImageView)view.findViewById(R.id.deleteCARD_IMAGE);
        deleteHeadingBlogBtnView=(ImageView)view.findViewById(R.id.deleteHeadingBlogPage);
        deleteSubHeaderBlogBtnView=(ImageView)view.findViewById(R.id.deleteSubHeaderBlogPage);
        deleteParaBlogBtnView=(ImageView)view.findViewById(R.id.deleteParaBlogPage);

        deleteHeadingBelowimgBlogBtnView=(ImageView)view.findViewById(R.id.deleteHeadingBelowimgBlogPage);
        deleteSubHeaderBelowimgBlogBtnView=(ImageView)view.findViewById(R.id.deleteSubHeaderBelowimgBlog);
        deleteParaBlogBelowimgBtnView=(ImageView)view.findViewById(R.id.deleteParaBelowimgBlogPage);

        cardImage = (NetworkImageView) view.findViewById(R.id.Blog_CARD_IMAGE);
        cardImageCrop =(ImageView) view.findViewById(R.id.Blog_STATIC_IMAGE);


        String urlOfProfile = dataObj.getUrlOfImage();

        if(urlOfProfile != null){
            // Uri cardImageUri = Uri.parse(urlOfProfile);
            // cardImage.setImageURI(cardImageUri);
            cardImage.setImageUrl(urlOfProfile, VolleySingleton.getInstance(getActivity()).getImageLoader());
            cardImageCrop.setVisibility(View.GONE);

        }else{
            cardImage.setVisibility(View.GONE);
            cardImageCrop.setImageResource(R.drawable.about_banner_1);

        }

        //showImageForBackround();


        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        //todo mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        attName = new ArrayList<String>();

        String title = dataObj.getTitle();
        editTitle = (EditText) view.findViewById(R.id.BLOG_TITLE);
        if(title != null) {
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else{
            editTitle.setVisibility(View.GONE);
        }

        String header = dataObj.getHeaderBlog();
        heading = (EditText) view.findViewById(R.id.blog_heading);
        if(header != null) {
            heading.setText(header);
            heading.setTypeface(mycustomFont);
        }else{
            heading.setVisibility(View.GONE  );
        }

        String subHeading = dataObj.getSubHeader();
        subheading = (EditText) view.findViewById(R.id.blog_subheading);
        if(subHeading != null) {
            subheading.setText(subHeading);
            subheading.setTypeface(mycustomFont);
        }else{
            subheading.setVisibility(View.GONE);
        }

        String paraGraph =  dataObj.getText_Para();
        paraGraphBlog = (EditText) view.findViewById(R.id.blog_paraGraph);
        if(paraGraph != null){
            paraGraphBlog.setText(paraGraph);
            paraGraphBlog.setTypeface(mycustomFont);
        }else{
            paraGraphBlog.setVisibility(View.GONE);
        }

        String headingBelow = dataObj.getHeaderBlogBlowing();
        heading_belo = (EditText) view.findViewById(R.id.blog_heading_belowing);
        if(headingBelow != null)
        {
            heading_belo.setText(headingBelow);
            heading_belo.setTypeface(mycustomFont);
        }
        else{
            heading_belo.setVisibility(View.GONE);
        }

        String subheadingBlow = dataObj.getSubHeaderBlowing();
        subheading_below = (EditText) view.findViewById(R.id.blog_subheading_belowing);
        if(subheadingBlow != null){
            subheading_below.setText(subheadingBlow);
            subheading_below.setTypeface(mycustomFont);
        }else {
            subheading_below.setVisibility(View.GONE);
        }

        paraGraphBlog_below = (EditText) view.findViewById(R.id.blog_paraGraph_belowing);
        String paraGraphBelow = dataObj.getText_ParaBlowing();
        if(paraGraphBelow != null){
            paraGraphBlog_below.setText(paraGraphBelow);
            paraGraphBlog_below.setTypeface(mycustomFont);
        }else{
            paraGraphBlog_below.setVisibility(View.GONE);
        }



        return view;
    }

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
            Log.v(TAG, "Null in init_viewCampaign");
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

        mProfileId = editCampaign.mCampaignIdFromServer;
        mPageName = ProfileFieldsEnum.PROFILE_PAGE_BLOG;

        mBlogPageObj  = new Page(mProfileId,mPageName);
        mParentId = mBlogPageObj.getPageId();

        if(showPreview == true) {
            init_viewCampaign();
        }else{
            //init_editCampaign();
        }

    }

    public String name()
    {
        return "Blogs";
    }

    void showPreview(){

        init_viewCampaign();

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
    public void onProfileSaveResponse(CommonRequest.ResponseCode res, Profile data) {
        Log.v(TAG,"ResponseCode = " + res);
        Toast.makeText(getActivity(), String.valueOf(res), Toast.LENGTH_SHORT).show();
    }

    ProgressDialog pbImage = null;

    private class PhotoAsyncTask extends AsyncTask<Void, Void, Void>
    {


        public void BlogImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = getActivity().openFileInput(MainActivity.Blog_TemplateImage_IMAGE_CROPED_NAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            File sendFile = getFileObjectFromBitmap (bitmap);


            UploadImageForUrlData data =
                    new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "Blog banner", 1);
            UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity(), data, FrgmentBlogOnApp.this);
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

            pbImage = new ProgressDialog(getActivity());
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
            String imageUrl = data.getResponseUrl();
            dataObj.setUrlOfImage(imageUrl);
            Log.v(TAG,"Url received" + imageUrl);
        }
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
    }

    public void setAttribute(String name, String value){

        if(name != null && value != null) {
            Attribute atrbtObj = new Attribute(mProfileId, mParentId, name, value);
            mBlogPageObj.addAttribute(atrbtObj);
        }
    }


    private void addPageToRequest(){


        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_TITLE, dataObj.getTitle());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_1, dataObj.getHeaderBlog());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_2, dataObj.getHeaderBlogBlowing());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_1, dataObj.getSubHeader());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_2, dataObj.getSubHeaderBlowing());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_CARD_IMAGE, dataObj.getUrlOfImage());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_1, dataObj.getText_Para());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_2, dataObj.getText_ParaBlowing());



        Profile reqToMakeProfile =  MainActivity.getProfileObject();

        if(reqToMakeProfile.checkIfPageExist(mParentId) == false) {
            reqToMakeProfile.addPage(mBlogPageObj);
        }
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