package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
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
import com.fourway.ideaswire.templates.TeamDataTemplate;
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
 * Created by 4way on 25-10-2016.
 */
public class FragmentTeamOnApp extends Fragment implements View.OnClickListener, FragmenMainActivity.viewCampaign, UploadImageForUrlRequest.UploadImageForUrlCallback {

    private GridView gridView;
    ProgressBar progressBar;
    private GridViewAdapter gridViewAdapter;
    TextView mTitle,choose_cat;

    public String TAG="teamonapp";

    EditText editTitle=null,
            editHeading =null,
            editSubHeading =null,
            editParaGraph =null;

    ImageView deleteTeamImg;
    RelativeLayout teamImgLayout;

    ImageView deleteHeading=null,
            deleteSubHeading=null,
            deletePara=null;
    pages mthispage = null;
    int indexInList = -1;

    String teamImgUrl_1 = null;
    String teamImgUrl_2 = null;
    String teamImgUrl_3 = null;
    String teamImgUrl_4 = null;
    String teamImgUrl_5 = null;
    String teamImgUrl_6 = null;

    List<Drawable> defaultDrawableArrayList =new ArrayList<>(); //default drawable list


    int cropRestart=-1;
    private boolean showPreview = false;        /**Is in editable mode or privew/it is server data .
                                                    in case it false : editable, True means : Priview or server data*/


    TeamDataTemplate dataObj;

    //Variables to make request to server
    Page mTeamPageObj;
    String mProfileId = null;
    String mPageName = null;
    String mParentId = null;
    Profile requestToMakeProfile;

//    void showPreview(){
//        if(((FragmenMainActivity)getActivity()).checkPreview()){
//            init_ViewCampaign();
//            showPreview=true;
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);

        //mEditMode = getActivity().getIntent().getBooleanExtra(MainActivity.ExplicitEditModeKey, false);
        dataObj=(TeamDataTemplate)((FragmenMainActivity)getActivity()).getDatObject();

        if (dataObj.isEditDefaultOrUpdateData() == true){
            indexInList = (int)((FragmenMainActivity)getActivity()).getIndexOfPresentview();
            mthispage = MainActivity.listOfTemplatePagesObj.get(indexInList);
            mPageName = mthispage.nameis();
            showPreview = false;        //since editable
        }else {
            showPreview = true;          //in preview or server data
        }


        editTitle=(EditText)view.findViewById(R.id.Team_TITLE);
        editHeading =(EditText)view.findViewById(R.id.HeadingTeam);
        editSubHeading =(EditText)view.findViewById(R.id.SubHeadingTeam);
        editParaGraph =(EditText)view.findViewById(R.id.paraGraphTeam);

        editTitle.setText(dataObj.getTitle());
        editHeading.setText(dataObj.getHeaderTeam());
        editSubHeading.setText(dataObj.getSubHeadingTeam());
        editParaGraph.setText(dataObj.getParaGraphTeam());


        deleteHeading =(ImageView)view.findViewById(R.id.deleteHeadingTeam);
        deleteSubHeading =(ImageView)view.findViewById(R.id.deleteSubHeadingTeam);
        deletePara =(ImageView)view.findViewById(R.id.deleteParaGraphTeam);


        deleteHeading.setVisibility(View.GONE);
        deleteSubHeading.setVisibility(View.GONE);
        deletePara.setVisibility(View.GONE);


        deleteHeading.setOnClickListener(this);
        deleteSubHeading.setOnClickListener(this);
        deletePara.setOnClickListener(this);

        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        String title=dataObj.getTitle();
        if (title == null || !title.equals(CROSS_BUTTON_HIDE)){
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else {
            editTitle.setVisibility(View.GONE);
        }

        String heading=dataObj.getHeaderTeam();
        if (heading == null || !heading.equals(CROSS_BUTTON_HIDE)){
            editHeading.setText(heading);
            editHeading.setTypeface(mycustomFont);
        }else
        {
            editHeading.setVisibility(View.GONE);
        }

        String subHeading=dataObj.getSubHeadingTeam();
        if (subHeading == null || !subHeading.equals(CROSS_BUTTON_HIDE)){
            editSubHeading.setText(subHeading);
            editSubHeading.setTypeface(mycustomFont);
        }else {
            editSubHeading.setVisibility(View.GONE);
        }

        String para=dataObj.getParaGraphTeam();
        if(para == null || !para.equals(CROSS_BUTTON_HIDE)){
            editParaGraph.setText(para);
            editParaGraph.setTypeface(mycustomFont);
        }else {
            editParaGraph.setVisibility(View.GONE);
        }

        gridView = (GridView) view.findViewById(R.id.gridView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_team);

        final List<String> memberImageUrl=new ArrayList<>();
        final List<String> memberName=new ArrayList<>();
        final List<String> memberTitle =new ArrayList<>();

        Drawable defaultImageMember_1 = null;
        Drawable defaultImageMember_2 = null;
        try {
            defaultImageMember_1 = getResources().getDrawable( dataObj.getDefaultDrawableResourceId().get(0)); //default image drawable for member 1

            defaultImageMember_2= getResources().getDrawable( dataObj.getDefaultDrawableResourceId().get(1)); //default image drawable for member 2
        }catch (NullPointerException e) {

        }catch (IndexOutOfBoundsException e2){

        }



        for (int i=0; i<6; i++) {
            if (dataObj.getTeamImage(i) == null || !dataObj.getTeamImage(i).equals(CROSS_BUTTON_HIDE)) {
                memberName.add(dataObj.getTeamName(i));
                memberTitle.add(dataObj.getTeamTitle(i));
                memberImageUrl.add(dataObj.getTeamImage(i));

                Drawable saved = null;

                if (dataObj.isValInImageList(i) == true) {
                    saved = dataObj.dataOfImageDrawables.get(i);
                    defaultDrawableArrayList.add(saved); //add drawable in list
                }else {
                    if (i == 0 || i%2==0) {                                 // set default image into grid alternately
                        defaultDrawableArrayList.add(defaultImageMember_1);
                    }else {
                        defaultDrawableArrayList.add(defaultImageMember_2);
                    }
                    dataObj.dataOfImageDrawables.add(null);
                }
            }
        }


        gridViewAdapter=new GridViewAdapter(getActivity(),android.R.layout.simple_list_item_1,memberImageUrl,memberName, memberTitle, defaultDrawableArrayList);

        gridView.setAdapter(gridViewAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if (!showPreview && !FragmenMainActivity.isImageUploading) {

                    if (v.getId() == R.id.deleteTeamImage) {

                        int absPos = dataObj.getAbsoluteValueOfImagePosition(position);

                        dataObj.setTeamImage(CROSS_BUTTON_HIDE, absPos);
                        dataObj.setTeamName(CROSS_BUTTON_HIDE, absPos);
                        dataObj.setTeamTitle(CROSS_BUTTON_HIDE, absPos);

                        memberImageUrl.remove(position);
                        memberName.remove(position);
                        memberTitle.remove(position);

                        defaultDrawableArrayList.remove(position);

                        gridView.setAdapter(gridViewAdapter);
                        gridViewAdapter.notifyDataSetChanged();


                    } else if (v.getId() == R.id.memberImage || v.getId() == R.id.Team_STATIC_img) {
                        uploadToTeamOnApp(MainActivity.TEAM_MEMBER_IMAGE_CROPED_NAME_ + position, position);
                        gridPosition = position;
                    }
                }


            }});

        //showPreview();

        if(showPreview == false) {
            init_editCampaign();
        }else {
            init_viewCampaign();
        }



        return view;
    }

    int lastPositionInList = -1;
    void init_teamPage_request(){
        mProfileId = editCampaign.mCampaignIdFromServer;
        //mPageName = ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE;
        mTeamPageObj = MainActivity.getProfileObject().getPageByName(mPageName);


        if (mTeamPageObj != null) {
            lastPositionInList = MainActivity.getProfileObject().getIndexOfPageFromName(mPageName);
            MainActivity.getProfileObject().deletePageByName(mPageName);

        }

        mTeamPageObj = new Page(mProfileId, mPageName);
        mParentId = mTeamPageObj.getPageId();
    }

    public void uploadToTeamOnApp(String ImageName, int position) {

        if(!showPreview) {
            String campnName = null;
         /*
        * Need to open gallery directly from here
        * From Cropped image OK clicked editCampaign.java will be opened
        * In editCampaign.java this campaign name(campnName) will be used to set defualt text
        * ScreenName will be used by CropedImage as it will be used to open gallery by multiple classes
        * */

            Intent inf = new Intent(getActivity(), CropedImage.class);
            inf.putExtra("ScreenName", ImageName);
            inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_TEAM_PAGE_ON_APP);
            cropRestart=position;
            inf.putExtra("CampaignName", "Choose Image");
            startActivity(inf);


        }

    }

    int gridPosition = -1;
    private void showImageForBackround(String imageName){
        Log.v("editCampaign", "showImageCampaign");
        try {
            FileInputStream in = getActivity().openFileInput(imageName);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            if (gridPosition != -1) {
                BitmapDrawable bitFromGallary = new BitmapDrawable(bitmap);
                defaultDrawableArrayList.set(gridPosition, bitFromGallary); //set new drawable from gallery
                dataObj.dataOfImageDrawables.set(gridPosition, bitFromGallary);
                dataObj.posOfSavedImages.add(gridPosition);
//                gridViewAdapter.mThumbs[gridPosition] = new BitmapDrawable(bitmap);
                gridViewAdapter.notifyDataSetChanged();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
    private class PhotoAsyncTask extends AsyncTask<Void, Void, Void>
    {
        String ScreenName;
        String image_name;
        int unique_seq_number;

        public PhotoAsyncTask(String ScreenName,String image_name, int unique_seq_number){
            super();
            this.ScreenName = ScreenName;
            this.image_name = image_name;
            this.unique_seq_number = unique_seq_number;

        }

        public void ClientImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = getActivity().openFileInput(ScreenName);
                final Bitmap bitmap = BitmapFactory.decodeStream(in);
                File sendFile = getFileObjectFromBitmap (bitmap);


                UploadImageForUrlData data =
                        new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, image_name, unique_seq_number);
                UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity().getApplicationContext(), data, FragmentTeamOnApp.this);
                req.executeRequest();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
                FragmenMainActivity.isImageUploading = false;
            }



        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                ClientImageUpload();
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
            progressBar.setVisibility(View.VISIBLE);
            FragmenMainActivity.isImageUploading = true;

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }

    private class GridViewAdapter extends ArrayAdapter {

        Context context;
        List<String> memberImageUrl;
        List<String> memberNameList;
        List<String> memberTitleList;
        List<Drawable> defaultDrawableList = new ArrayList<>();


        public GridViewAdapter(Context context, int resource,List<String> memberImageUrl,List<String> memberNameList,List<String> memberPositionList,List<Drawable> defaultDrawableList) {
            super(context, resource, memberImageUrl);

            this.context=context;
            this.memberImageUrl=memberImageUrl;
            this.memberNameList=memberNameList;
            this.memberTitleList =memberPositionList;
            this.defaultDrawableList = defaultDrawableList;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            View gridView = LayoutInflater.from(context).inflate(R.layout.team_member_grid , null);

            NetworkImageView memberImg=(NetworkImageView) gridView.findViewById(R.id.memberImage);
            ImageView memberStaticImg = (ImageView)gridView.findViewById(R.id.Team_STATIC_img);
            teamImgLayout =(RelativeLayout)gridView.findViewById(R.id.team_img_layout);
            deleteTeamImg =(ImageView)gridView.findViewById(R.id.deleteTeamImage);

            EditText memberName =(EditText)gridView.findViewById(R.id.memberName);
            EditText memberTitle =(EditText)gridView.findViewById(R.id.memberPostion);

            if (showPreview){
                deleteTeamImg.setVisibility(View.GONE);
                memberName.setEnabled(false);
                memberTitle.setEnabled(false);
            }else {
                memberName.setEnabled(true);
                memberTitle.setEnabled(true);
            }



            deleteTeamImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView)parent).performItemClick(v, position,0);
                }
            });

            memberImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView)parent).performItemClick(v, position,0);
                }
            });

            memberStaticImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView)parent).performItemClick(v, position,0);
                }
            });


            memberName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String changeName=s.toString();
                    dataObj.setTeamName(changeName, position); memberNameList.set(position, changeName);

                }
            });

            memberTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String changeTitle=s.toString();
                    dataObj.setTeamTitle(changeTitle,position); memberTitleList.set(position, changeTitle);
                }

            });

            if(memberNameList.get(position)!=null) {
                memberName.setText(memberNameList.get(position));
            }else {
                if (showPreview){
                    memberName.setVisibility(View.GONE);
                }
            }

            if(memberTitleList.get(position)!=null) {
                memberTitle.setText(memberTitleList.get(position));
            }else {
                if (showPreview) {
                    memberTitle.setVisibility(View.GONE);
                }
            }

            String imageUrl = memberImageUrl.get(position);
            if( imageUrl!=null) {
                memberStaticImg.setVisibility(View.GONE);
                memberImg.setImageUrl(imageUrl, VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());
            }
            else {
                memberImg.setVisibility(View.GONE);
                if (dataObj.isEditOrUpdateMode()) {
                    memberStaticImg.setImageDrawable(defaultDrawableList.get(position));
                }else {
                    memberStaticImg.setVisibility(View.GONE);
                }
            }

            return gridView;
        }

    }




    @Override
    public void onClick(View v) {
        if (!FragmenMainActivity.isImageUploading) {
            switch (v.getId()) {

                case R.id.deleteHeadingTeam:
                    editHeading.setVisibility(View.GONE);
                    editHeading.setText(CROSS_BUTTON_HIDE);
                    dataObj.setHeaderTeam(CROSS_BUTTON_HIDE);
                    deleteHeading.setVisibility(View.GONE);
                    break;
                case R.id.deleteSubHeadingTeam:
                    editSubHeading.setVisibility(View.GONE);
                    deleteSubHeading.setVisibility(View.GONE);
                    editSubHeading.setText(CROSS_BUTTON_HIDE);
                    dataObj.setSubHeadingTeam(CROSS_BUTTON_HIDE);
                    break;
                case R.id.deleteParaGraphTeam:
                    editParaGraph.setVisibility(View.GONE);
                    editParaGraph.setText(CROSS_BUTTON_HIDE);
                    dataObj.setParaGraphTeam(CROSS_BUTTON_HIDE);
                    deletePara.setVisibility(View.GONE);
                    break;

            }
        }
    }

    private void setAttribute(String name, String value){
        if (name != null && value != null) {
            Attribute atrbtObj = new Attribute(mProfileId, mParentId, name, value);
            mTeamPageObj.addAttribute(atrbtObj);
        }
    }

    public  void changeText(){
        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextService" + title);
        if (!title.equals("")) {
            dataObj.setTitle(title);
        }

        String header = String.valueOf(editHeading.getText());
        Log.d(TAG, "changeHeadingTxtService" + header);
        if (!header.equals("")) {
            dataObj.setHeaderTeam(header);
        }

        String subheader = String.valueOf(editSubHeading.getText());
        Log.d(TAG, "changeSubHeadingService" + subheader);
        if (!subheader.equals("")) {
            dataObj.setSubHeadingTeam(subheader);
        }

        String para = String.valueOf(editParaGraph.getText());
        Log.d(TAG, "changeParaService" + para);
        if (!para.equals("")) {
            dataObj.setParaGraphTeam(para);
        }

        String stringImageUrl_1 = dataObj.getTeamImage(0);

        if (teamImgUrl_1!=null && (stringImageUrl_1 == null || !stringImageUrl_1.equals(CROSS_BUTTON_HIDE))){
            dataObj.setTeamImage(teamImgUrl_1,0);
        }

        String stringImageUrl_2 = dataObj.getTeamImage(1);
        if (teamImgUrl_2!=null && (stringImageUrl_2 == null || !stringImageUrl_2.equals(CROSS_BUTTON_HIDE))){
            dataObj.setTeamImage(teamImgUrl_2,1);
        }

        String stringImageUrl_3 = dataObj.getTeamImage(2);
        if (teamImgUrl_3!=null && (stringImageUrl_3 == null || !stringImageUrl_3.equals(CROSS_BUTTON_HIDE))){
            dataObj.setTeamImage(teamImgUrl_3,2);
        }

        String stringImageUrl_4 = dataObj.getTeamImage(3);
        if (teamImgUrl_4!=null && (stringImageUrl_4 == null || !stringImageUrl_4.equals(CROSS_BUTTON_HIDE))){
            dataObj.setTeamImage(teamImgUrl_4,3);
        }

        String stringImageUrl_5 = dataObj.getTeamImage(4);
        if (teamImgUrl_5!=null && (stringImageUrl_5 == null || !stringImageUrl_5.equals(CROSS_BUTTON_HIDE))){
            dataObj.setTeamImage(teamImgUrl_5,4);
        }

        String stringImageUrl_6 = dataObj.getTeamImage(5);
        if (teamImgUrl_6!=null && (stringImageUrl_6 == null || !stringImageUrl_6.equals(CROSS_BUTTON_HIDE))){
            dataObj.setTeamImage(teamImgUrl_6,5);
        }


    }


    void init_viewCampaign(){
        try {

            deleteHeading.setVisibility(View.GONE);
            deleteSubHeading.setVisibility(View.GONE);
            deletePara.setVisibility(View.GONE);
        }catch (NullPointerException e)
        {
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if (editTitle!=null){
            editTitle.setEnabled(false);
            editTitle.setKeyListener(null);
        }

        if (editHeading !=null){
            editHeading.setEnabled(false);
            editHeading.setKeyListener(null);
        }


        if (editSubHeading !=null){
            editSubHeading.setEnabled(false);
            editSubHeading.setKeyListener(null);
        }
        if (editParaGraph !=null){
            editParaGraph.setEnabled(false);
            editParaGraph.setKeyListener(null);
        }
    }

    void init_editCampaign(){
        try {
            if (dataObj.getHeaderTeam() == null || !dataObj.getHeaderTeam().equals(CROSS_BUTTON_HIDE)) {
                deleteHeading.setVisibility(View.VISIBLE);
            }
            if (dataObj.getSubHeadingTeam() == null || !dataObj.getSubHeadingTeam().equals(CROSS_BUTTON_HIDE)) {
                deleteSubHeading.setVisibility(View.VISIBLE);
            }
            if (dataObj.getParaGraphTeam() == null || !dataObj.getParaGraphTeam().equals(CROSS_BUTTON_HIDE)) {
                deletePara.setVisibility(View.VISIBLE);
            }






        }catch (NullPointerException e)
        {
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if (editTitle!=null){
            editTitle.setEnabled(true);
            editTitle.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if (editHeading !=null){
            editHeading.setEnabled(true);
            editHeading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }


        if (editSubHeading !=null){
            editSubHeading.setEnabled(true);
            editSubHeading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (editParaGraph !=null){
            editParaGraph.setEnabled(true);
            editParaGraph.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
    }

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {
        progressBar.setVisibility(View.GONE);
        FragmenMainActivity.isImageUploading = false;
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            int ImageId = data.getUniqueSequenceNumber();

            if (ImageId>=70) {
                String imageUrl = data.getResponseUrl();
                switch (ImageId){
                    case 70: teamImgUrl_1=imageUrl; deleteCropFile(0);
                        break;
                    case 71: teamImgUrl_2=imageUrl; deleteCropFile(1);
                        break;
                    case 72: teamImgUrl_3=imageUrl; deleteCropFile(2);
                        break;
                    case 73: teamImgUrl_4=imageUrl; deleteCropFile(3);
                        break;
                    case 74: teamImgUrl_5=imageUrl; deleteCropFile(4);
                        break;
                    case 75: teamImgUrl_6=imageUrl; deleteCropFile(5);
                        break;

                }
            }
        }
    }

    public void deleteCropFile(int imageNumber){
        String path = getActivity().getFilesDir().getAbsolutePath() + "/" + MainActivity.CLIENTS_LOGO_IMAGE_CROPED_NAME_+imageNumber;

        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(cropRestart!=-1) {
            showImageForBackround(MainActivity.TEAM_MEMBER_IMAGE_CROPED_NAME_+cropRestart);
            PhotoAsyncTask obj = new PhotoAsyncTask(MainActivity.TEAM_MEMBER_IMAGE_CROPED_NAME_+cropRestart,
                    "Team_Img_"+cropRestart, 70+cropRestart);
            obj.execute();
            cropRestart = -1;
        }else {
            //showBaseMenu();
        }
    }

    private void addPageToRequest(){
        init_teamPage_request();

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM, mthispage.nameis());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_STATUS, String.valueOf(mthispage.pageStatus()));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_TITLE, dataObj.getTitle());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_HEADING, dataObj.getHeaderTeam());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_SUBHEADING, dataObj.getSubHeadingTeam());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_PARAGRAPH, dataObj.getParaGraphTeam());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_1_IMAGE, dataObj.getTeamImage(0));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_2_IMAGE, dataObj.getTeamImage(1));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_3_IMAGE, dataObj.getTeamImage(2));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_4_IMAGE, dataObj.getTeamImage(3));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_5_IMAGE, dataObj.getTeamImage(4));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_6_IMAGE, dataObj.getTeamImage(5));

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_1_NAME, dataObj.getTeamName(0));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_2_NAME, dataObj.getTeamName(1));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_3_NAME, dataObj.getTeamName(2));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_4_NAME, dataObj.getTeamName(3));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_5_NAME, dataObj.getTeamName(4));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_6_NAME, dataObj.getTeamName(5));

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_1_TITLE, dataObj.getTeamTitle(0));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_2_TITLE, dataObj.getTeamTitle(1));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_3_TITLE, dataObj.getTeamTitle(2));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_4_TITLE, dataObj.getTeamTitle(3));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_5_TITLE, dataObj.getTeamTitle(4));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_6_TITLE, dataObj.getTeamTitle(5));


        Profile reqToMakeProfile;
        reqToMakeProfile = MainActivity.getProfileObject();

        //if(reqToMakeProfile.checkIfPageExist(mPageId)) {
        if(lastPositionInList == -1){
            reqToMakeProfile.addPage(mTeamPageObj);
        }else {
            reqToMakeProfile.addPageAtPosition(mTeamPageObj, lastPositionInList);
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
        }else {             //in priview mode
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
