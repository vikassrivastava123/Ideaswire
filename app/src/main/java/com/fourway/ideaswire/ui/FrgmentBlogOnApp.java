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
import com.fourway.ideaswire.templates.blogpageDataTemplate;
import com.fourway.ideaswire.templates.pages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.fourway.ideaswire.ui.MainActivity.CROSS_BUTTON_HIDE;

/**
 * Created by 4way on 15-10-2016.
 */
public class FrgmentBlogOnApp extends Fragment  implements  UploadImageForUrlRequest.UploadImageForUrlCallback ,View.OnClickListener, FragmenMainActivity.viewCampaign{

    EditText heading=null,subheading=null,paraGraphBlog=null,heading_belo=null,subheading_below=null,paraGraphBlog_below=null;
    List<String> attName;
    TextView mTitle=null;
    EditText editTitle = null;

    int cropRestart=0;
    int indexInList = -1;

    NetworkImageView cardImage;
    ImageView cardImageCrop;
    ProgressBar progressBar;
    pages mthispage = null;

    private static String TAG = "BlogOnApp";

    ImageView deleteCARD_IMAGEBtnView = null;
    ImageView deleteHeadingBlogBtnView = null;
    ImageView deleteSubHeaderBlogBtnView = null;
    ImageView deleteParaBlogBtnView = null;

    ImageView deleteHeadingBelowimgBlogBtnView = null;
    ImageView deleteSubHeaderBelowimgBlogBtnView = null;
    ImageView deleteParaBlogBelowimgBtnView = null;

    blogpageDataTemplate dataObj;
    private boolean showPreview = false;        /**Is in editable mode or privew/it is server data .
                                                in case it false : editable, True means : Priview or server data*/


    Page mBlogPageObj;
    String mProfileId = null;
    String mPageName = null;
    String mPageId = null;

    String cardImageUrl = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_blog, container, false);

       // mEditMode = getActivity().getIntent().getBooleanExtra(MainActivity.ExplicitEditModeKey, false);

        dataObj = (blogpageDataTemplate)((FragmenMainActivity)getActivity()).getDatObject();//savedInstanceState.getSerializable("dataKey");
        if (dataObj.isEditDefaultOrUpdateData() == true){

            indexInList = (int)((FragmenMainActivity)getActivity()).getIndexOfPresentview();
            mthispage = MainActivity.listOfTemplatePagesObj.get(indexInList);
            mPageName = mthispage.nameis();
            showPreview = false;         //since editable
        }else {
            showPreview = true;         //in preview or server data
        }


        deleteCARD_IMAGEBtnView=(ImageView)view.findViewById(R.id.deleteCARD_IMAGE);
        deleteHeadingBlogBtnView=(ImageView)view.findViewById(R.id.deleteHeadingBlogPage);
        deleteSubHeaderBlogBtnView=(ImageView)view.findViewById(R.id.deleteSubHeaderBlogPage);
        deleteParaBlogBtnView=(ImageView)view.findViewById(R.id.deleteParaBlogPage);

        deleteHeadingBelowimgBlogBtnView=(ImageView)view.findViewById(R.id.deleteHeadingBelowimgBlogPage);
        deleteSubHeaderBelowimgBlogBtnView=(ImageView)view.findViewById(R.id.deleteSubHeaderBelowimgBlog);
        deleteParaBlogBelowimgBtnView=(ImageView)view.findViewById(R.id.deleteParaBelowimgBlogPage);


        deleteCARD_IMAGEBtnView.setVisibility(View.GONE);
        deleteHeadingBlogBtnView.setVisibility(View.GONE);
        deleteSubHeaderBlogBtnView.setVisibility(View.GONE);
        deleteParaBlogBtnView.setVisibility(View.GONE);
        deleteHeadingBelowimgBlogBtnView.setVisibility(View.GONE);
        deleteSubHeaderBelowimgBlogBtnView.setVisibility(View.GONE);
        deleteParaBlogBelowimgBtnView.setVisibility(View.GONE);



        deleteCARD_IMAGEBtnView.setOnClickListener(this);
        deleteHeadingBlogBtnView.setOnClickListener(this);
        deleteSubHeaderBlogBtnView.setOnClickListener(this);
        deleteParaBlogBtnView.setOnClickListener(this);
        deleteHeadingBelowimgBlogBtnView.setOnClickListener(this);
        deleteSubHeaderBelowimgBlogBtnView.setOnClickListener(this);
        deleteParaBlogBelowimgBtnView.setOnClickListener(this);



        cardImage = (NetworkImageView) view.findViewById(R.id.Blog_CARD_IMAGE);
        cardImageCrop =(ImageView) view.findViewById(R.id.Blog_STATIC_IMAGE);

        progressBar =(ProgressBar)view.findViewById(R.id.progressBar_blog);

        cardImageCrop.setOnClickListener(this);
        cardImage.setOnClickListener(this);


        String urlOfProfile = dataObj.getUrlOfImage();

        if(urlOfProfile != null){
            if (!urlOfProfile.equals(CROSS_BUTTON_HIDE)) {
                cardImage.setImageUrl(urlOfProfile, VolleySingleton.getInstance(getActivity()).getImageLoader());
                cardImageCrop.setVisibility(View.GONE);
            }else {
                cardImage.setVisibility(View.GONE);
                cardImageCrop.setVisibility(View.GONE);
            }

        }else{
            cardImage.setVisibility(View.GONE);
            try {
                cardImageCrop.setImageResource(dataObj.getDefaultDrawableResourceId().get(0)); //set default image
            }catch (NullPointerException e) {

            }


        }

        //showImageForBackround();


        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        //todo mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        attName = new ArrayList<String>();

        String title = dataObj.getTitle();
        editTitle = (EditText) view.findViewById(R.id.BLOG_TITLE);
        if(title == null || !title.equals(CROSS_BUTTON_HIDE)) {
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else{
            editTitle.setVisibility(View.GONE);
        }

        String header = dataObj.getHeaderBlog();
        heading = (EditText) view.findViewById(R.id.blog_heading);
        if(header == null || !header.equals(CROSS_BUTTON_HIDE)) {
            heading.setText(header);
            heading.setTypeface(mycustomFont);
        }else{
            heading.setVisibility(View.GONE  );
        }

        String subHeading = dataObj.getSubHeader();
        subheading = (EditText) view.findViewById(R.id.blog_subheading);
        if(subHeading == null || !subHeading.equals(CROSS_BUTTON_HIDE)) {
            subheading.setText(subHeading);
            subheading.setTypeface(mycustomFont);
        }else{
            subheading.setVisibility(View.GONE);
        }

        String paraGraph =  dataObj.getText_Para();
        paraGraphBlog = (EditText) view.findViewById(R.id.blog_paraGraph);
        if(paraGraph == null || !paraGraph.equals(CROSS_BUTTON_HIDE)){
            paraGraphBlog.setText(paraGraph);
            paraGraphBlog.setTypeface(mycustomFont);
        }else{
            paraGraphBlog.setVisibility(View.GONE);
        }

        String headingBelow = dataObj.getHeaderBlogBlowing();
        heading_belo = (EditText) view.findViewById(R.id.blog_heading_belowing);
        if(headingBelow == null || !headingBelow.equals(CROSS_BUTTON_HIDE))
        {
            heading_belo.setText(headingBelow);
            heading_belo.setTypeface(mycustomFont);
        }
        else{
            heading_belo.setVisibility(View.GONE);
        }

        String subheadingBlow = dataObj.getSubHeaderBlowing();
        subheading_below = (EditText) view.findViewById(R.id.blog_subheading_belowing);
        if(subheadingBlow == null || !subheadingBlow.equals(CROSS_BUTTON_HIDE)){
            subheading_below.setText(subheadingBlow);
            subheading_below.setTypeface(mycustomFont);
        }else {
            subheading_below.setVisibility(View.GONE);
        }

        paraGraphBlog_below = (EditText) view.findViewById(R.id.blog_paraGraph_belowing);
        String paraGraphBelow = dataObj.getText_ParaBlowing();
        if(paraGraphBelow == null || !paraGraphBelow.equals(CROSS_BUTTON_HIDE)){
            paraGraphBlog_below.setText(paraGraphBelow);
            paraGraphBlog_below.setTypeface(mycustomFont);
        }else{
            paraGraphBlog_below.setVisibility(View.GONE);
        }

        //showPreview();

        if(showPreview == false) {
            init_editCampaign();
        }else {
            init_viewCampaign();
        }


        return view;
    }

    int lastPositionInList = -1;
    void init_blogPage_request(){
        mProfileId = editCampaign.mCampaignIdFromServer;
        //mPageName = ProfileFieldsEnum.PROFILE_PAGE_BLOG;

        mBlogPageObj = MainActivity.getProfileObject().getPageByName(mPageName);

        if (mBlogPageObj!=null) {
           lastPositionInList = MainActivity.getProfileObject().getIndexOfPageFromName(mPageName);
           MainActivity.getProfileObject().deletePageByName(mPageName);
        }
        mBlogPageObj = new Page(mProfileId, mPageName);
        mPageId = mBlogPageObj.getPageId();
    }

    void init_viewCampaign(){

        try {

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


    }

    void init_editCampaign(){

        try {

                if (dataObj.getUrlOfImage() == null || !dataObj.getUrlOfImage().equals(CROSS_BUTTON_HIDE)) {
                    deleteCARD_IMAGEBtnView.setVisibility(View.VISIBLE);
                }

                if (dataObj.getHeaderBlog() == null || !dataObj.getHeaderBlog().equals(CROSS_BUTTON_HIDE)) {
                    deleteHeadingBlogBtnView.setVisibility(View.VISIBLE);
                }

                if (dataObj.getSubHeader() == null || !dataObj.getSubHeader().equals(CROSS_BUTTON_HIDE)) {
                    deleteSubHeaderBlogBtnView.setVisibility(View.VISIBLE);
                }

                if (dataObj.getText_Para() == null || !dataObj.getText_Para().equals(CROSS_BUTTON_HIDE)) {
                    deleteParaBlogBtnView.setVisibility(View.VISIBLE);
                }

                if (dataObj.getHeaderBlogBlowing() == null || !dataObj.getHeaderBlogBlowing().equals(CROSS_BUTTON_HIDE)) {
                    deleteHeadingBelowimgBlogBtnView.setVisibility(View.VISIBLE);
                }

                if (dataObj.getSubHeaderBlowing() == null || !dataObj.getSubHeaderBlowing().equals(CROSS_BUTTON_HIDE)) {
                    deleteSubHeaderBelowimgBlogBtnView.setVisibility(View.VISIBLE);
                }

                if (dataObj.getText_ParaBlowing() == null || !dataObj.getText_ParaBlowing().equals(CROSS_BUTTON_HIDE)) {
                    deleteParaBlogBelowimgBtnView.setVisibility(View.VISIBLE);
                }

        }catch (NullPointerException e){
            Log.v(TAG,"Null in init_viewCampaign");
        }
        if(editTitle !=null){
            editTitle.setEnabled(true);
            editTitle.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(heading !=null){
            heading.setEnabled(true);
            heading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(subheading !=null){
            subheading.setEnabled(true);
            subheading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(paraGraphBlog !=null){
            paraGraphBlog.setEnabled(true);
            paraGraphBlog.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(heading_belo != null){
            heading_belo.setEnabled(true);
            heading_belo.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(subheading_below != null){
            subheading_below.setEnabled(true);
            subheading_below.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(paraGraphBlog_below !=null){
            paraGraphBlog_below.setEnabled(true);
            paraGraphBlog_below.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

    }

    public String name()
    {
        return "Blogs";
    }

//    void showPreview(){
//
//        if(((FragmenMainActivity)getActivity()).checkPreview()){
//            init_ViewCampaign();
//            showPreview=true;
//        }
//
//    }

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

    @Override
    public void onClick(View v) {
        if (!FragmenMainActivity.isImageUploading) {
            switch (v.getId()) {

                case R.id.deleteHeadingBlogPage:
                    heading.setVisibility(View.GONE);
                    heading.setText(CROSS_BUTTON_HIDE);
                    dataObj.setHeaderBlog(CROSS_BUTTON_HIDE);
                    deleteHeadingBlogBtnView.setVisibility(View.GONE);
                    break;
                case R.id.deleteSubHeaderBlogPage:
                    subheading.setVisibility(View.GONE);
                    subheading.setText(CROSS_BUTTON_HIDE);
                    dataObj.setSubHeader(CROSS_BUTTON_HIDE);
                    deleteSubHeaderBlogBtnView.setVisibility(View.GONE);
                    break;
                case R.id.deleteParaBlogPage:
                    paraGraphBlog.setVisibility(View.GONE);
                    paraGraphBlog.setText(CROSS_BUTTON_HIDE);
                    dataObj.setText_Para(CROSS_BUTTON_HIDE);
                    deleteParaBlogBtnView.setVisibility(View.GONE);
                    break;
                case R.id.deleteCARD_IMAGE:
                    cardImage.setVisibility(View.GONE);
                    cardImageCrop.setVisibility(View.GONE);
                    deleteCARD_IMAGEBtnView.setVisibility(View.GONE);
                    dataObj.setUrlOfImage(CROSS_BUTTON_HIDE);
                    cardImageUrl = CROSS_BUTTON_HIDE;
                    //cardRelativeLayout.setVisibility(View.GONE);
                    break;
                case R.id.deleteHeadingBelowimgBlogPage:
                    heading_belo.setVisibility(View.GONE);
                    heading_belo.setText(CROSS_BUTTON_HIDE);
                    dataObj.setHeaderBlogBlowing(CROSS_BUTTON_HIDE);
                    deleteHeadingBelowimgBlogBtnView.setVisibility(View.GONE);
                    break;
                case R.id.deleteSubHeaderBelowimgBlog:
                    subheading_below.setVisibility(View.GONE);
                    subheading_below.setText(CROSS_BUTTON_HIDE);
                    dataObj.setSubHeaderBlowing(CROSS_BUTTON_HIDE);
                    deleteSubHeaderBelowimgBlogBtnView.setVisibility(View.GONE);
                    break;
                case R.id.deleteParaBelowimgBlogPage:
                    paraGraphBlog_below.setVisibility(View.GONE);
                    paraGraphBlog_below.setText(CROSS_BUTTON_HIDE);
                    dataObj.setText_ParaBlowing(CROSS_BUTTON_HIDE);
                    deleteParaBlogBelowimgBtnView.setVisibility(View.GONE);
                    break;
                case R.id.Blog_STATIC_IMAGE:
                    uploadToBlogOnApp();
                    break;
                case R.id.Blog_CARD_IMAGE:
                    uploadToBlogOnApp();
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
        }else {              //in priview mode
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


        public void BlogImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = getActivity().openFileInput(MainActivity.Blog_TemplateImage_IMAGE_CROPED_NAME);
                final Bitmap bitmap = BitmapFactory.decodeStream(in);
                File sendFile = getFileObjectFromBitmap (bitmap);


                UploadImageForUrlData data =
                        new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "Blog banner", 4);
                UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity(), data, FrgmentBlogOnApp.this);
                req.executeRequest();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
                FragmenMainActivity.isImageUploading = false;
            }


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
            progressBar.setVisibility(View.VISIBLE);
            FragmenMainActivity.isImageUploading = true;

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }

    private void showImageForBackround(){
        Log.v("editCampaign", "showImageCampaign");
        try {
            FileInputStream in = getActivity().openFileInput(MainActivity.Blog_TemplateImage_IMAGE_CROPED_NAME);

            cardImage.setVisibility(View.GONE);
            cardImageCrop.setVisibility(View.VISIBLE);

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            cardImageCrop.setImageDrawable(new BitmapDrawable(getResources(), bitmap));


        }catch (FileNotFoundException e){
            Log.v("editCampaign","Blog_TemplateImage_IMAGE_CROPED_NAME file not found");
        }

    }

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {
        pbImage.hide();
        progressBar.setVisibility(View.GONE);
        FragmenMainActivity.isImageUploading = false;
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            String imageUrl = data.getResponseUrl();
            cardImageUrl = imageUrl;
            Log.v(TAG,"Url received" + imageUrl);
            deleteCropFile();
        }
    }

    public void deleteCropFile(){
        String path = getActivity().getFilesDir().getAbsolutePath() + "/" + MainActivity.Blog_TemplateImage_IMAGE_CROPED_NAME;

        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
    }

    public  void changeText(){
        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextBlog" + title);
        if (!title.equals("")) {
            dataObj.setTitle(title);
        }

        String header = String.valueOf(heading.getText());
        Log.d(TAG, "changeHeadingTxtBlog" + header);
        if (!header.equals("")) {
            dataObj.setHeaderBlog(header);
        }

        String subheader = String.valueOf(subheading.getText());
        Log.d(TAG, "changeSubHeadingBlog" + subheader);
        if (!subheader.equals("")) {
            dataObj.setSubHeader(subheader);
        }

        String para = String.valueOf(paraGraphBlog.getText());
        Log.d(TAG, "changeParaBlog" + para);
        if (!para.equals("")) {
            dataObj.setText_Para(para);
        }

        String headerBelow = String.valueOf(heading_belo.getText());
        Log.d(TAG, "changeHeadingBelowTxtBlog" + headerBelow);
        if (!headerBelow.equals("")) {
            dataObj.setHeaderBlogBlowing(headerBelow);
        }

        String subheaderBelow = String.valueOf(subheading_below.getText());
        Log.d(TAG, "changeSubHeadingBelowTxtBlog" + subheaderBelow);
        if (!subheaderBelow.equals("")) {
            dataObj.setSubHeaderBlowing(subheaderBelow);
        }

        String paraBelow = String.valueOf(paraGraphBlog_below.getText());
        Log.d(TAG, "changeSubHeadingBelowTxtBlog" + paraBelow);
        if (!paraBelow.equals("")) {
            dataObj.setText_ParaBlowing(paraBelow);
        }

        if (cardImageUrl!=null) {
            dataObj.setUrlOfImage(cardImageUrl);
        }
    }

    public void setAttribute(String name, String value){

        if(name != null && value != null) {
            Attribute atrbtObj = new Attribute(mProfileId, mPageId, name, value);
            mBlogPageObj.addAttribute(atrbtObj);
        }
    }


    private void addPageToRequest(){
        init_blogPage_request();

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG, mthispage.nameis());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_STATUS, String.valueOf(mthispage.pageStatus()));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_TITLE, dataObj.getTitle());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_1, dataObj.getHeaderBlog());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_2, dataObj.getHeaderBlogBlowing());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_1, dataObj.getSubHeader());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_2, dataObj.getSubHeaderBlowing());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_CARD_IMAGE, dataObj.getUrlOfImage());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_1, dataObj.getText_Para());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_2, dataObj.getText_ParaBlowing());



        Profile reqToMakeProfile;
        reqToMakeProfile =  MainActivity.getProfileObject();

        /*if(MainActivity.getProfileObject().getIndexOfPageFromName(mPageName) != -1) {
            int index = reqToMakeProfile.getIndexOfPage(mPageId);
            reqToMakeProfile.replacePage(index, mBlogPageObj);
        }else {
            reqToMakeProfile.addPage(mBlogPageObj);
        }*/

        if (lastPositionInList == -1){
            reqToMakeProfile.addPage(mBlogPageObj);
        }else {
            reqToMakeProfile.addPageAtPosition(mBlogPageObj, lastPositionInList);
        }
    }

    public void uploadToBlogOnApp() {

        if(showPreview == false && !FragmenMainActivity.isImageUploading) {
            String campnName = null;
         /*
        * Need to open gallery directly from here
        * From Cropped image OK clicked editCampaign.java will be opened
        * In editCampaign.java this campaign name(campnName) will be used to set defualt text
        * ScreenName will be used by CropedImage as it will be used to open gallery by multiple classes
        * */

            Intent inf = new Intent(getActivity(), CropedImage.class);
            inf.putExtra("ScreenName", MainActivity.Blog_TemplateImage_IMAGE_CROPED_NAME);
            inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_BLOG_ON_APP);

            inf.putExtra("CampaignName", "Choose Image");
            cropRestart=1;
            startActivity(inf);


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

}
