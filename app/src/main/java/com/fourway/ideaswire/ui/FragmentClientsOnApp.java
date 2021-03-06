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
import com.fourway.ideaswire.templates.ClientDataTemplate;
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
public class FragmentClientsOnApp extends Fragment
        implements View.OnClickListener, FragmenMainActivity.viewCampaign, UploadImageForUrlRequest.UploadImageForUrlCallback {
    private GridView gridView;
    ProgressBar progressBar;
    TextView mTitle,choose_cat;
    public String TAG="ClientOnApp";

    EditText editTitle =null,
            editHeading =null,
            editSubHeading =null,
            editParaGraph =null;

    ImageView deleteHeading=null,
            deleteSubHeading=null,
            deleteParaGraph=null;

    pages mthispage = null;
    int indexInList = -1;

    ImageView deleteClientLogo;

    RelativeLayout clientLogoLayout;

    GridViewAdapter gridViewAdapter;
    List<String> clientsLogoUrl =new ArrayList<>();

    String LogoUrl_1 = null;
    String LogoUrl_2 = null;
    String LogoUrl_3 = null;
    String LogoUrl_4 = null;
    String LogoUrl_5 = null;
    String LogoUrl_6 = null;

    List<Drawable> defaultArrayList =new ArrayList<>(); //default drawable list


    int cropRestart=-1;

    ClientDataTemplate dataObj;
    private boolean showPreview = false;
                                        /**Is in editable mode or privew/it is server data .
                                         in case it false : editable, True means : Priview or server data*/
    //private boolean mEditMode = false;


    //Variables to make request to server
    Page mClientsPageObj;
    String mProfileId = null;
    String mPageName = null;
    String mParentId = null;
    Profile requestToMakeProfile;



//
//    void showPreview(){
//        if(((FragmenMainActivity)getActivity()).checkPreview()){
//            init_ViewCampaign();
//            showPreview=true;
//        }
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_clients, container, false);
     //   mEditMode = getActivity().getIntent().getBooleanExtra(MainActivity.ExplicitEditModeKey, false);

        dataObj = (ClientDataTemplate) ((FragmenMainActivity)getActivity()).getDatObject();

        if (dataObj.isEditDefaultOrUpdateData() == true){

            indexInList = (int)((FragmenMainActivity)getActivity()).getIndexOfPresentview();
            mthispage = MainActivity.listOfTemplatePagesObj.get(indexInList);
            mPageName = mthispage.nameis();
            showPreview = false;    //since editable
        }else {
            showPreview = true;     //in preview or server data
        }

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar_client);
        editTitle =(EditText)view.findViewById(R.id.Client_TITLE);
        editHeading = (EditText)view.findViewById(R.id.ClientHeading);
        editSubHeading =(EditText)view.findViewById(R.id.ClientSubHeading);
        editParaGraph =(EditText)view.findViewById(R.id.ClientParaGraph);


        deleteHeading =(ImageView)view.findViewById(R.id.deleteHeadingClient);
        deleteSubHeading =(ImageView)view.findViewById(R.id.deleteSubHeadingClient);
        deleteParaGraph =(ImageView)view.findViewById(R.id.deleteParaClient);


        deleteHeading.setVisibility(View.GONE);
        deleteSubHeading.setVisibility(View.GONE);
        deleteParaGraph.setVisibility(View.GONE);

        deleteHeading.setOnClickListener(this);
        deleteSubHeading.setOnClickListener(this);
        deleteParaGraph.setOnClickListener(this);

        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        String title=dataObj.getTitle();
        if (title == null || !title.equals(CROSS_BUTTON_HIDE)){
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else {
            editTitle.setVisibility(View.GONE);
        }

        String heading=dataObj.getHeaderClient();
        if (heading == null || !heading.equals(CROSS_BUTTON_HIDE)){
            editHeading.setText(heading);
            editHeading.setTypeface(mycustomFont);
        }else
        {
            editHeading.setVisibility(View.GONE);
        }

        String subHeading=dataObj.getSubHeaderClient();
        if (subHeading == null || !subHeading.equals(CROSS_BUTTON_HIDE)){
            editSubHeading.setText(subHeading);
            editSubHeading.setTypeface(mycustomFont);
        }else {
            editSubHeading.setVisibility(View.GONE);
        }

        String para=dataObj.getParaClient();
        if(para == null || !para.equals(CROSS_BUTTON_HIDE)){
            editParaGraph.setText(para);
            editParaGraph.setTypeface(mycustomFont);
        }else {
            editParaGraph.setVisibility(View.GONE);
        }


        gridView = (GridView) view.findViewById(R.id.ClientGridView);

        Drawable defaultLogo = getResources().getDrawable(R.drawable.clients_logo);

     //   int  sizeOfLogoList = dataObj.sizeOfLogoList();

        //if(sizeOfLogoList<=0)
        {
            //todo null check
            if ((dataObj.getClient_logo(0) == null || !dataObj.getClient_logo(0).equals(CROSS_BUTTON_HIDE))) {
                clientsLogoUrl.add(dataObj.getClient_logo(0));
                Drawable saved = null;


                if(dataObj.isValInLogoList(0) == true){
                    saved = dataObj.dataOfLogoDrawables.get(0);
                    defaultArrayList.add(saved); //add drawable in list
                }else{
                    defaultArrayList.add(defaultLogo); //add drawable in list
                    dataObj.dataOfLogoDrawables.add(null);
                }

                //  dataObj.dataOfLogoDrawables.add(defaultLogo); //add drawable in list

               // dataObj.dataOfLogoDrawables.add(0, defaultLogo);
            }

            if ((dataObj.getClient_logo(1) == null || !dataObj.getClient_logo(1).equals(CROSS_BUTTON_HIDE))) {
                clientsLogoUrl.add(dataObj.getClient_logo(1));

                Drawable saved = null;
                if(dataObj.isValInLogoList(1) == true){
                    saved = dataObj.dataOfLogoDrawables.get(1);
                    defaultArrayList.add(saved); //add drawable in list
                }else{
                    defaultArrayList.add(defaultLogo); //add drawable in list
                    dataObj.dataOfLogoDrawables.add(null);
                }

                //dataObj.dataOfLogoDrawables.add(defaultLogo); //add drawable in list
                //dataObj.dataOfLogoDrawables.add(1, defaultLogo);
            }
            if ((dataObj.getClient_logo(2) == null || !dataObj.getClient_logo(2).equals(CROSS_BUTTON_HIDE))) {
                clientsLogoUrl.add(dataObj.getClient_logo(2));

                Drawable saved = null;

                if(dataObj.isValInLogoList(2) == true){
                    saved = dataObj.dataOfLogoDrawables.get(2);
                    defaultArrayList.add(saved); //add drawable in list
                }else{
                    defaultArrayList.add(defaultLogo); //add drawable in list
                    dataObj.dataOfLogoDrawables.add(null);
                }

                //dataObj.dataOfLogoDrawables.add(defaultLogo); //add drawable in list
                //dataObj.dataOfLogoDrawables.add(2, defaultLogo);
            }
            if ((dataObj.getClient_logo(3) == null || !dataObj.getClient_logo(3).equals(CROSS_BUTTON_HIDE))) {
                clientsLogoUrl.add(dataObj.getClient_logo(3));


                Drawable saved = null;

                if(dataObj.isValInLogoList(3) == true){
                    saved = dataObj.dataOfLogoDrawables.get(3);
                    defaultArrayList.add(saved); //add drawable in list
                }else{
                    defaultArrayList.add(defaultLogo); //add drawable in list
                    dataObj.dataOfLogoDrawables.add(null);
                }

                //dataObj.dataOfLogoDrawables.add(defaultLogo); //add drawable in list
                //dataObj.dataOfLogoDrawables.add(3, defaultLogo);
            }
            if ((dataObj.getClient_logo(4) == null || !dataObj.getClient_logo(4).equals(CROSS_BUTTON_HIDE))) {
                clientsLogoUrl.add(dataObj.getClient_logo(4));

                Drawable saved = null;
                if(dataObj.isValInLogoList(4) == true){
                    saved = dataObj.dataOfLogoDrawables.get(4);
                    defaultArrayList.add(saved); //add drawable in list
                }else{
                    defaultArrayList.add(defaultLogo); //add drawable in list
                    dataObj.dataOfLogoDrawables.add(null);
                }
                //dataObj.dataOfLogoDrawables.add(defaultLogo); //add drawable in list
                //dataObj.dataOfLogoDrawables.add(4, defaultLogo);
            }
            if ((dataObj.getClient_logo(5) == null || !dataObj.getClient_logo(5).equals(CROSS_BUTTON_HIDE))) {
                clientsLogoUrl.add(dataObj.getClient_logo(5));

                Drawable saved = null;
                if(dataObj.isValInLogoList(5) == true){
                    saved = dataObj.dataOfLogoDrawables.get(5);
                    defaultArrayList.add(saved); //add drawable in list
                }else{
                    defaultArrayList.add(defaultLogo); //add drawable in list
                    dataObj.dataOfLogoDrawables.add(null);
                }

                //dataObj.dataOfLogoDrawables.add(defaultLogo); //add drawable in list
                //dataObj.dataOfLogoDrawables.add(5, defaultLogo);
            }
        }

    //    dataObj.dataOfLogoDrawables.add(defaultLogo); //add drawable in list
        // use your custom layout
        gridViewAdapter=new GridViewAdapter(getActivity(),android.R.layout.simple_list_item_1, clientsLogoUrl, defaultArrayList);
        gridView.setAdapter(gridViewAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if (!showPreview && !FragmenMainActivity.isImageUploading) {

                    if (v.getId() == R.id.deleteClientLogo) {

                        int absPos = dataObj.getAbsoluteValueOfLogoPosition(position);
                        dataObj.setClient_logo(CROSS_BUTTON_HIDE, absPos);

                        clientsLogoUrl.remove(position);
                        //gridViewAdapter.mThumbs[position].
                        //dataObj.dataOfLogoDrawables.remove(position); //delete drawable from list acoording grid position

                        defaultArrayList.remove(position);

                        gridView.setAdapter(gridViewAdapter);
                        gridViewAdapter.notifyDataSetChanged();


                    } else if (v.getId() == R.id.imgvTemplateCatrgory || v.getId() == R.id.Client_STATIC_LOGO) {

                        uploadToClientsOnApp(MainActivity.CLIENTS_LOGO_IMAGE_CROPED_NAME_ + position, position);
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
    void init_clientsPage_request(){
        mProfileId = editCampaign.mCampaignIdFromServer;
        //mPageName = ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE;
        mClientsPageObj = MainActivity.getProfileObject().getPageByName(mPageName);

        if (mClientsPageObj != null) {
                lastPositionInList = MainActivity.getProfileObject().getIndexOfPageFromName(mPageName);
                MainActivity.getProfileObject().deletePageByName(mPageName);

        }
        mClientsPageObj = new Page(mProfileId, mPageName);
        mParentId = mClientsPageObj.getPageId();
    }

    public void uploadToClientsOnApp(String ImageName, int position) {

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
            inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_CLIENTS_PAGE_ON_APP);
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
                //gridViewAdapter.mThumbs[gridPosition] = new BitmapDrawable(bitmap);

                BitmapDrawable bitFromGallrey = new BitmapDrawable(bitmap);
                defaultArrayList.set(gridPosition, bitFromGallrey);//set new drable from gallary
                dataObj.dataOfLogoDrawables.set(gridPosition,bitFromGallrey);
                dataObj.posOfSavedImagesInLogo.add(gridPosition);
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
                UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity().getApplicationContext(), data, FragmentClientsOnApp.this);
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
            FragmenMainActivity.isImageUploading = true;
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }


    private class GridViewAdapter extends ArrayAdapter{
        Context context;
        List<String> clientLogoUrl=new ArrayList<>();
        List<Drawable> defaultDrawableList = new ArrayList<>();

        public GridViewAdapter(Context context, int resource,List<String> clientLogoUrl,List<Drawable> defaultDrawableList) {
            super(context, resource, clientLogoUrl);
            this.context=context;
            this.clientLogoUrl=clientLogoUrl;
            this.defaultDrawableList = defaultDrawableList;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            View gridView=LayoutInflater.from(context).inflate(R.layout.cl, null);

            NetworkImageView clientLogo = (NetworkImageView) gridView.findViewById(R.id.imgvTemplateCatrgory);
            final ImageView clientStaticLogo = (ImageView)gridView.findViewById(R.id.Client_STATIC_LOGO);

            clientLogoLayout =(RelativeLayout)gridView.findViewById(R.id.client_logo_layout);
            deleteClientLogo =(ImageView)gridView.findViewById(R.id.deleteClientLogo);

           if (showPreview){
                deleteClientLogo.setVisibility(View.GONE);
            }

            deleteClientLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView) parent).performItemClick(v, position, 0);

                }
            });

            clientLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView) parent).performItemClick(v, position, 0);
                }
            });

            clientStaticLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView) parent).performItemClick(v, position, 0);
                }
            });

            String logoImageUrl =clientLogoUrl.get(position);

            if( logoImageUrl!=null )/*In case no url from server*/
            {
                clientStaticLogo.setVisibility(View.GONE);
                clientLogo.setImageUrl(logoImageUrl, VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());
            }
            else {
                clientLogo.setVisibility(View.GONE);
                if (dataObj.isEditOrUpdateMode()) {
                       clientStaticLogo.setImageDrawable(defaultDrawableList.get(position));
                }else {
                       clientStaticLogo.setVisibility(View.GONE);
                }
            }

            return gridView;
        }

    }

    @Override
    public void onClick(View v) {
        if (!FragmenMainActivity.isImageUploading) {
            switch (v.getId()) {

                case R.id.deleteHeadingClient:
                    editHeading.setVisibility(View.GONE);
                    editHeading.setText(CROSS_BUTTON_HIDE);
                    dataObj.setHeaderClient(CROSS_BUTTON_HIDE);
                    deleteHeading.setVisibility(View.GONE);
                    break;
                case R.id.deleteSubHeadingClient:
                    editSubHeading.setVisibility(View.GONE);
                    editSubHeading.setText(CROSS_BUTTON_HIDE);
                    dataObj.setSubHeaderClient(CROSS_BUTTON_HIDE);
                    deleteSubHeading.setVisibility(View.GONE);
                    break;
                case R.id.deleteParaClient:
                    editParaGraph.setVisibility(View.GONE);
                    editParaGraph.setText(CROSS_BUTTON_HIDE);
                    dataObj.setParaClient(CROSS_BUTTON_HIDE);
                    deleteParaGraph.setVisibility(View.GONE);
                    break;

            }
        }
    }

    private void setAttribute(String name, String value){
        if (name != null && value != null) {
            Attribute atrbtObj = new Attribute(mProfileId, mParentId, name, value);
            mClientsPageObj.addAttribute(atrbtObj);
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
            dataObj.setHeaderClient(header);
        }

        String subheader = String.valueOf(editSubHeading.getText());
        Log.d(TAG, "changeSubHeadingService" + subheader);
        if (!subheader.equals("")) {
            dataObj.setSubHeaderClient(subheader);
        }

        String para = String.valueOf(editParaGraph.getText());
        Log.d(TAG, "changeParaService" + para);
        if (!para.equals("")) {
            dataObj.setParaClient(para);
        }

        if (LogoUrl_1!=null){
            dataObj.setClient_logo(LogoUrl_1,0);
        }
        if (LogoUrl_2!=null){
            dataObj.setClient_logo(LogoUrl_2,1);
        }
        if (LogoUrl_3!=null){
            dataObj.setClient_logo(LogoUrl_3,2);
        }
        if (LogoUrl_4!=null){
            dataObj.setClient_logo(LogoUrl_4,3);
        }
        if (LogoUrl_5!=null){
            dataObj.setClient_logo(LogoUrl_5,4);
        }
        if (LogoUrl_6!=null){
            dataObj.setClient_logo(LogoUrl_6,5);
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

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {
        pbImage.hide();
        FragmenMainActivity.isImageUploading = false;
        progressBar.setVisibility(View.GONE);
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            int ImageId = data.getUniqueSequenceNumber();

            if (ImageId>=60) {
                String imageUrl = data.getResponseUrl();
                switch (ImageId){
                    case 60: LogoUrl_1=imageUrl; deleteCropFile(0);
                        break;
                    case 61: LogoUrl_2=imageUrl; deleteCropFile(1);
                        break;
                    case 62: LogoUrl_3=imageUrl; deleteCropFile(2);
                        break;
                    case 63: LogoUrl_4=imageUrl; deleteCropFile(3);
                        break;
                    case 64: LogoUrl_5=imageUrl; deleteCropFile(4);
                        break;
                    case 65: LogoUrl_6=imageUrl; deleteCropFile(5);
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
            showImageForBackround(MainActivity.CLIENTS_LOGO_IMAGE_CROPED_NAME_+cropRestart);
            PhotoAsyncTask obj = new PhotoAsyncTask(MainActivity.CLIENTS_LOGO_IMAGE_CROPED_NAME_+cropRestart,
                    "Clients_Logo_"+cropRestart, 60+cropRestart);
            obj.execute();
            cropRestart = -1;
        }else {
            //showBaseMenu();
        }
    }

    private void addPageToRequest(){
        init_clientsPage_request();

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT, mthispage.nameis());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT_STATUS, String.valueOf(mthispage.pageStatus()));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT_TITLE, dataObj.getTitle());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT_HEADING, dataObj.getHeaderClient());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT_SUBHEADING, dataObj.getSubHeaderClient());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT_PARAGRAPH, dataObj.getParaClient());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_1, dataObj.getClient_logo(0));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_2, dataObj.getClient_logo(1));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_3, dataObj.getClient_logo(2));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_4, dataObj.getClient_logo(3));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_5, dataObj.getClient_logo(4));
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_6, dataObj.getClient_logo(5));

        Profile reqToMakeProfile;
        reqToMakeProfile = MainActivity.getProfileObject();

        //if(reqToMakeProfile.checkIfPageExist(mPageId)) {
        if( lastPositionInList == -1){
            reqToMakeProfile.addPage(mClientsPageObj);
        }else {
            reqToMakeProfile.addPageAtPosition(mClientsPageObj, lastPositionInList);
        }

    }


    void init_viewCampaign(){
        try {
            deleteHeading.setVisibility(View.GONE);
            deleteSubHeading.setVisibility(View.GONE);
            deleteParaGraph.setVisibility(View.GONE);

        }catch (NullPointerException e){
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if (editTitle !=null){
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

                if (dataObj.getHeaderClient() == null || !dataObj.getHeaderClient().equals(CROSS_BUTTON_HIDE)) {
                    deleteHeading.setVisibility(View.VISIBLE);
                }

                if (dataObj.getSubHeaderClient() == null || !dataObj.getSubHeaderClient().equals(CROSS_BUTTON_HIDE)) {
                    deleteSubHeading.setVisibility(View.VISIBLE);
                }

                if (dataObj.getParaClient() == null || !dataObj.getParaClient().equals(CROSS_BUTTON_HIDE)) {
                    deleteParaGraph.setVisibility(View.VISIBLE);
                }

        }catch (NullPointerException e){
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if (editTitle !=null){
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
}
