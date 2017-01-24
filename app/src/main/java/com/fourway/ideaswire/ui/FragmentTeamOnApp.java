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

    ImageView deleteTitle=null,
            deleteHeading=null,
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

    String teamName_1 = null;
    String teamName_2 = null;
    String teamName_3 = null;
    String teamName_4 = null;
    String teamName_5 = null;
    String teamName_6 = null;

    String teamTitle_1 = null;
    String teamTitle_2 = null;
    String teamTitle_3 = null;
    String teamTitle_4 = null;
    String teamTitle_5 = null;
    String teamTitle_6 = null;


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
        editHeading.setText(dataObj.getHeader());
        editSubHeading.setText(dataObj.getSubHeadingTeam());
        editParaGraph.setText(dataObj.getParaGraphTeam());

        deleteTitle=(ImageView)view.findViewById(R.id.deleteTitleTeam);
        deleteHeading =(ImageView)view.findViewById(R.id.deleteHeadingTeam);
        deleteSubHeading =(ImageView)view.findViewById(R.id.deleteSubHeadingTeam);
        deletePara =(ImageView)view.findViewById(R.id.deleteParaGraphTeam);

        deleteTitle.setOnClickListener(this);
        deleteHeading.setOnClickListener(this);
        deleteSubHeading.setOnClickListener(this);
        deletePara.setOnClickListener(this);

        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        String title=dataObj.getTitle();
        if (title!=null && !title.equals("")){
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else {
            editTitle.setVisibility(View.GONE);
        }

        String heading=dataObj.getHeader();
        if (heading!=null && !heading.equals("")){
            editHeading.setText(heading);
            editHeading.setTypeface(mycustomFont);
        }else
        {
            editHeading.setVisibility(View.GONE);
        }

        String subHeading=dataObj.getSubHeadingTeam();
        if (subHeading!=null && !subHeading.equals("")){
            editSubHeading.setText(subHeading);
            editSubHeading.setTypeface(mycustomFont);
        }else {
            editSubHeading.setVisibility(View.GONE);
        }

        String para=dataObj.getParaGraphTeam();
        if(para!=null && !para.equals("")){
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



        memberName.add(dataObj.getTeam_1_name());
        memberName.add(dataObj.getTeam_2_name());
        memberName.add(dataObj.getTeam_3_name());
        memberName.add(dataObj.getTeam_4_name());
        memberName.add(dataObj.getTeam_5_name());
        memberName.add(dataObj.getTeam_6_name());

        memberTitle.add(dataObj.getTeam_1_title());
        memberTitle.add(dataObj.getTeam_2_title());
        memberTitle.add(dataObj.getTeam_3_title());
        memberTitle.add(dataObj.getTeam_4_title());
        memberTitle.add(dataObj.getTeam_5_title());
        memberTitle.add(dataObj.getTeam_6_title());

        memberImageUrl.add(dataObj.getTeam_1_image());
        memberImageUrl.add(dataObj.getTeam_2_image());
        memberImageUrl.add(dataObj.getTeam_3_image());
        memberImageUrl.add(dataObj.getTeam_4_image());
        memberImageUrl.add(dataObj.getTeam_5_image());
        memberImageUrl.add(dataObj.getTeam_6_image());


        gridViewAdapter=new GridViewAdapter(getActivity(),android.R.layout.simple_list_item_1,memberImageUrl,memberName, memberTitle);

        gridView.setAdapter(gridViewAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if (!showPreview) {

                    if (v.getId() == R.id.deleteTeamImage) {
                        memberImageUrl.remove(position);
                        memberName.remove(position);
                        memberTitle.remove(position);

                        gridView.setAdapter(gridViewAdapter);
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
                gridViewAdapter.mThumbs[gridPosition] = new BitmapDrawable(bitmap);
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


        public GridViewAdapter(Context context, int resource,List<String> memberImageUrl,List<String> memberNameList,List<String> memberPositionList) {
            super(context, resource, memberImageUrl);

            this.context=context;
            this.memberImageUrl=memberImageUrl;
            this.memberNameList=memberNameList;
            this.memberTitleList =memberPositionList;
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
                    switch (position){
                        case 0: dataObj.setTeam_1_name(changeName); memberNameList.set(0, changeName);
                            break;
                        case 1: dataObj.setTeam_2_name(changeName); memberNameList.set(1, changeName);
                            break;
                        case 2: dataObj.setTeam_3_name(changeName); memberNameList.set(2, changeName);
                            break;
                        case 3: dataObj.setTeam_4_name(changeName); memberNameList.set(3, changeName);
                            break;
                        case 4: dataObj.setTeam_5_name(changeName); memberNameList.set(4, changeName);
                            break;
                        case 5: dataObj.setTeam_6_name(changeName); memberNameList.set(5, changeName);
                            break;
                    }
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
                    switch (position){
                        case 0: dataObj.setTeam_1_title(changeTitle); memberTitleList.set(0, changeTitle);
                            break;
                        case 1: dataObj.setTeam_2_title(changeTitle); memberTitleList.set(1, changeTitle);
                            break;
                        case 2: dataObj.setTeam_3_title(changeTitle); memberTitleList.set(2, changeTitle);
                            break;
                        case 3: dataObj.setTeam_4_title(changeTitle); memberTitleList.set(3, changeTitle);
                            break;
                        case 4: dataObj.setTeam_5_title(changeTitle); memberTitleList.set(4, changeTitle);
                            break;
                        case 5: dataObj.setTeam_6_title(changeTitle); memberTitleList.set(5, changeTitle);
                            break;
                    }
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
                if (!showPreview || dataObj.isDefaultDataToCreateCampaign()) {
                    memberStaticImg.setImageDrawable(mThumbs[position]);
                }else {
                    memberStaticImg.setVisibility(View.GONE);
                }
            }

            return gridView;
        }

        Drawable myIcon = getResources().getDrawable( R.drawable.member_1 );
        Drawable[] mThumbs = {myIcon,myIcon,myIcon,myIcon,myIcon,myIcon};
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteTitleTeam:
                editTitle.setVisibility(View.GONE);
                editTitle.setText("");
                deleteTitle.setVisibility(View.GONE);
                break;
            case R.id.deleteHeadingTeam:
                editHeading.setVisibility(View.GONE);
                editHeading.setText("");
                deleteHeading.setVisibility(View.GONE);
                break;
            case R.id.deleteSubHeadingTeam:
                editSubHeading.setVisibility(View.GONE);
                deleteSubHeading.setVisibility(View.GONE);
                editSubHeading.setText("");
                break;
            case R.id.deleteParaGraphTeam:
                editParaGraph.setVisibility(View.GONE);
                editParaGraph.setText("");
                deletePara.setVisibility(View.GONE);
                break;

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
        if (title != null) {
            dataObj.setTitle(title);
        }

        String header = String.valueOf(editHeading.getText());
        Log.d(TAG, "changeHeadingTxtService" + header);
        if (header != null) {
            dataObj.setHeaderTeam(header);
        }

        String subheader = String.valueOf(editSubHeading.getText());
        Log.d(TAG, "changeSubHeadingService" + subheader);
        if (subheader != null) {
            dataObj.setSubHeadingTeam(subheader);
        }

        String para = String.valueOf(editParaGraph.getText());
        Log.d(TAG, "changeParaService" + para);
        if (para != null) {
            dataObj.setParaGraphTeam(para);
        }

        if (teamImgUrl_1!=null){
            dataObj.setTeam_1_image(teamImgUrl_1);
        }
        if (teamImgUrl_2!=null){
            dataObj.setTeam_2_image(teamImgUrl_2);
        }
        if (teamImgUrl_3!=null){
            dataObj.setTeam_3_image(teamImgUrl_3);
        }
        if (teamImgUrl_4!=null){
            dataObj.setTeam_4_image(teamImgUrl_4);
        }
        if (teamImgUrl_5!=null){
            dataObj.setTeam_5_image(teamImgUrl_5);
        }
        if (teamImgUrl_6!=null){
            dataObj.setTeam_6_image(teamImgUrl_6);
        }

//        if (teamName_1!=null){
//            dataObj.setTeam_1_name(teamName_1);
//        }
//        if (teamName_2!=null){
//            dataObj.setTeam_2_name(teamName_2);
//        }
//        if (teamName_3!=null){
//            dataObj.setTeam_3_name(teamName_3);
//        }
//        if (teamName_4!=null){
//            dataObj.setTeam_4_name(teamName_4);
//        }
//        if (teamName_5!=null){
//            dataObj.setTeam_5_name(teamName_5);
//        }
//        if (teamName_6!=null){
//            dataObj.setTeam_6_name(teamName_6);
//        }
//
//        if (teamTitle_1!=null){
//            dataObj.setTeam_1_title(teamTitle_1);
//        }
//        if (teamTitle_2!=null){
//            dataObj.setTeam_2_title(teamTitle_2);
//        }
//        if (teamTitle_3!=null){
//            dataObj.setTeam_3_title(teamTitle_3);
//        }
//        if (teamTitle_4!=null){
//            dataObj.setTeam_4_title(teamTitle_4);
//        }
//        if (teamTitle_5!=null){
//            dataObj.setTeam_5_title(teamTitle_5);
//        }
//        if (teamTitle_6!=null){
//            dataObj.setTeam_6_title(teamTitle_6);
//        }
    }


    void init_viewCampaign(){
        try {

            deleteTitle.setVisibility(View.GONE);
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

            deleteTitle.setVisibility(View.VISIBLE);
            deleteHeading.setVisibility(View.VISIBLE);
            deleteSubHeading.setVisibility(View.VISIBLE);
            deletePara.setVisibility(View.VISIBLE);
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

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_1_IMAGE, dataObj.getTeam_1_image());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_2_IMAGE, dataObj.getTeam_2_image());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_3_IMAGE, dataObj.getTeam_3_image());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_4_IMAGE, dataObj.getTeam_4_image());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_5_IMAGE, dataObj.getTeam_5_image());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_6_IMAGE, dataObj.getTeam_6_image());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_1_NAME, dataObj.getTeam_1_name());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_2_NAME, dataObj.getTeam_2_name());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_3_NAME, dataObj.getTeam_3_name());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_4_NAME, dataObj.getTeam_4_name());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_5_NAME, dataObj.getTeam_5_name());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_6_NAME, dataObj.getTeam_6_name());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_1_TITLE, dataObj.getTeam_1_title());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_2_TITLE, dataObj.getTeam_2_title());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_3_TITLE, dataObj.getTeam_3_title());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_4_TITLE, dataObj.getTeam_4_title());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_5_TITLE, dataObj.getTeam_5_title());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_TEAM_6_TITLE, dataObj.getTeam_6_title());


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
