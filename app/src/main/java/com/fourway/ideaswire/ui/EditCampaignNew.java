package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.GetProfileRequestData;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.UpdateProfileData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.GetProfileRequest;
import com.fourway.ideaswire.request.UpdateProfileRequest;
import com.fourway.ideaswire.request.helper.VolleySingleton;
import com.fourway.ideaswire.templates.AboutUsPage;
import com.fourway.ideaswire.templates.ClientPage;
import com.fourway.ideaswire.templates.HomePage;
import com.fourway.ideaswire.templates.ServicePage;
import com.fourway.ideaswire.templates.TeamPage;
import com.fourway.ideaswire.templates.blogpage;
import com.fourway.ideaswire.templates.contactDetails;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.fourway.ideaswire.ui.editCampaign.mCampaignIdFromServer;

public class EditCampaignNew extends Activity implements GetProfileRequest.GetProfileResponseCallback,UpdateProfileRequest.UpdateProfileResponseCallback{
    NetworkImageView editImageView;
    ImageView editCropImageView;
    EditText edCampaignName;
    int profilePosition;

    int cropRestart=0;
    FileInputStream in;


    private String mProfileId = null;
    public static String Tag = "EditCampaignNew";
    CheckBox statusBox=null;
    RadioButton statusOn = null;
    RadioButton statusDraft = null;
    private ArrayList<Profile> mProfileList;
    Button btn1;

    boolean blankProfileData = false;

    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_campaign_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Typeface mycustomFont= Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        mProfileList = loginUi.mProfileList;
        profilePosition = getIntent().getIntExtra("profilePosition",0);

        editImageView = (NetworkImageView)findViewById(R.id.imageToEditNew);
        editCropImageView = (ImageView)findViewById(R.id.imageCropToEditNew);
        edCampaignName = (EditText)findViewById(R.id.etCampaignName);

        statusDraft = (RadioButton)findViewById(R.id.StatusDraft);
        statusOn = (RadioButton)findViewById(R.id.StatusOn);
        statusDraft.setTypeface(mycustomFont);
        statusOn.setTypeface(mycustomFont);
        statusBox = (CheckBox)findViewById(R.id.checkBoxStatus);
        btn1 = (Button) findViewById(R.id.btn_createCampaignNew);

        statusDraft.setTypeface(mycustomFont);
        statusOn.setTypeface(mycustomFont);
        statusBox.setTypeface(mycustomFont);
        btn1.setTypeface(mycustomFont);

        statusBox.setOnCheckedChangeListener(statusChangeListener);


        Profile p = loginUi.mProfileList.get(profilePosition);
        //Clear page if Already have data in page
        if (p.getTotalNumberOfPages() != 0){
            p.clearPage();
        }

        GetProfileRequestData data = new GetProfileRequestData(loginUi.mLogintoken, p.getProfileId(), p);
        GetProfileRequest request =
                new GetProfileRequest(EditCampaignNew.this, data, EditCampaignNew.this);
        request.executeRequest();

        mProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Getting data...   ");
        mProgressDialog.show();



    }

    public void selectImage(View view) {


        Intent inf = new Intent(this, CropedImage.class);

        inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_EDIT_CAMPAIGN);
        startActivity(inf);
        cropRestart = 1;

    }

    public void deleteCropFile(){
        String path = getFilesDir().getAbsolutePath() + "/" + MainActivity.CREATE_CAMPAIGN_IMAGE_CROPED_NAME;

        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (cropRestart == 1 ) {
            try {
                in = openFileInput(MainActivity.CREATE_CAMPAIGN_IMAGE_CROPED_NAME);
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                editCropImageView.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                cropRestart = 0;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageCampaign(){

        Toast.makeText(EditCampaignNew.this, "Image Not set", Toast.LENGTH_SHORT).show();

    }

    UpdateProfileData.ProfileStatus  mSetStatus = UpdateProfileData.ProfileStatus.PROFILE_STATUS_ACTIVE;
    CompoundButton.OnCheckedChangeListener statusChangeListener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                mSetStatus = UpdateProfileData.ProfileStatus.PROFILE_STATUS_ACTIVE;
            }else {
                mSetStatus = UpdateProfileData.ProfileStatus.PROFILE_STATUS_DRAFT;
            }
        }
    };


    UpdateProfileData.ProfileType mProfileType = UpdateProfileData.ProfileType.PROFILE_TYPE_LOGO;
    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        int id = view.getId();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.StatusOn:
                Log.v(Tag,"StausOn"+checked);
                statusOn.toggle();
                if (checked) {
                    //mSetStatus = CreateProfileData.ProfileStatus.PROFILE_STATUS_DRAFT;
                    mProfileType = UpdateProfileData.ProfileType.PROFILE_TYPE_INDIVIDUAL;
                }
                Log.v(Tag,"StatusOn after toggle"+statusOn.isChecked());
                break;
            case R.id.StatusDraft:
                Log.v(Tag,"statusDraft"+checked);
                statusDraft.toggle();
                if (checked) {
                    //mSetStatus = CreateProfileData.ProfileStatus.PROFILE_STATUS_ACTIVE;
                    mProfileType = UpdateProfileData.ProfileType.PROFILE_TYPE_LOGO;
                }
                Log.v(Tag, "StatusOn after toggle" + statusDraft.isChecked());
                break;
        }

    }

    public static Profile reqToEditProfile = null;

    ProgressDialog pd;
    public void editProfile(View view)  {
        if (!edCampaignName.getText().toString().equals("")){
            pd = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
            pd.setMessage("Please wait...");

            String campaignName = edCampaignName.getText().toString();
            //BitmapDrawable bitmapDrawable = (BitmapDrawable)editImageView.getDrawable();
            FileInputStream in = null;
            File sendFile = null;
            try {
                in = openFileInput(MainActivity.CREATE_CAMPAIGN_IMAGE_CROPED_NAME);
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                sendFile = getFileObjectFromBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            UpdateProfileData data = new UpdateProfileData(mProfileId, campaignName, "bussiness", "profile_department",
                    loginUi.mLogintoken, sendFile, mProfileType,mSetStatus );

            UpdateProfileRequest request = new UpdateProfileRequest(EditCampaignNew.this, data, this);
            request.executeRequest();

            mProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Updating profile...   ");
            mProgressDialog.show();


        }else {
            Toast.makeText(EditCampaignNew.this, "Please Fill Campaign Name", Toast.LENGTH_SHORT).show();
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

    public void addDefaultDataForAddPage()
    {
        select_layout_of_template.listOfTemplatePagesObjForAddPage = new ArrayList<pages>();

        pages abtusObj = new AboutUsPage();
        pages homeObj = new HomePage();
        pages blogpage = new blogpage();
        pages contactdetails = new contactDetails();
        pages ServicePage = new ServicePage();
        pages clientobj = new ClientPage();
        pages teamPages =new TeamPage();

        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(0, abtusObj);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(1, homeObj);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(2, blogpage);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(3, contactdetails);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(4, ServicePage);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(5, clientobj);
        select_layout_of_template.listOfTemplatePagesObjForAddPage.add(6, teamPages);
    }

    @Override
    public void onGetProfileResponse(CommonRequest.ResponseCode res, GetProfileRequestData data) {
        mProgressDialog.dismiss();
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            Profile p = data.getProfile();
            mProfileId = data.getProfileId();
            ArrayList<Page> pageList = p.getAllPages();
            MainActivity.initListOfPages();


            boolean bcanShowProfile = MainActivity.addPagesToList(pageList, true);

            if(bcanShowProfile == true) {
                //shoEditProfile();
                String url =  (mProfileList.get(profilePosition)).getImageUrl();
                if (url != null && !url.equalsIgnoreCase("null")){
                    editImageView.setImageUrl(url, VolleySingleton.getInstance(this).getImageLoader());
                }

                String cName = (mProfileList.get(profilePosition)).getProfileName();
                if (cName !=null){
                    edCampaignName.setText(cName);
                }
            }else{
                Toast.makeText(getBaseContext(), "Error : Please Try Later", Toast.LENGTH_LONG).show();
            }
        }else if (res == CommonRequest.ResponseCode.COMMON_RES_PROFILE_DATA_NO_CONTENT) {
            Profile p = data.getProfile();
            mProfileId = data.getProfileId();
            blankProfileData = true;
            String url =  (mProfileList.get(profilePosition)).getImageUrl();
            if (url != null && !url.equalsIgnoreCase("null")){
                editImageView.setImageUrl(url, VolleySingleton.getInstance(this).getImageLoader());
            }

            String cName = (mProfileList.get(profilePosition)).getProfileName();
            if (cName !=null){
                edCampaignName.setText(cName);
            }
        }
    }

    @Override
    public void onUpdateProfileResponse(CommonRequest.ResponseCode res, UpdateProfileData data) {
        mProgressDialog.dismiss();
        switch (res) {
            case COMMON_RES_SUCCESS:
                deleteCropFile();
                Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();

                mCampaignIdFromServer = data.getProfileId(); // it is use for create new profile data if onGetProfileResponse COMMON_RES_PROFILE_DATA_NO_CONTENT
/**
 ---------------------------------Update Profile Data---------------------------------
*/
                if (!blankProfileData) {
                    Profile p = loginUi.mProfileList.get(profilePosition);
                    reqToEditProfile = p;

                    if (reqToEditProfile != null) {
                        MainActivity.setProfileObject(reqToEditProfile);
                    }

                    mCampaignIdFromServer = p.getProfileId();
                    dataOfTemplate dataOfTemplate = MainActivity.listOfTemplatePagesObj.get(0).getTemplateData(1, false);
                    dataOfTemplate.setEditMode(false); //when in edit mode while updating profile
                    dataOfTemplate.setIsInUpdateProfileMode(true);

                    addDefaultDataForAddPage();
                    reqToEditProfile.setTotalNumberOfPages();
                    Intent intent = new Intent(this, FragmenMainActivity.class);
                    intent.putExtra("data", dataOfTemplate);
                    intent.putExtra(MainActivity.ExplicitEditModeKey, true);
                    startActivity(intent);
                }else {
                    Intent iny = new Intent(this,ChooseTemplate_Category.class);
                    startActivity(iny);
                }
                break;
        }
    }
}
