package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
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
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



/**
 * Created by 4way on 15-10-2016.
 */
public class FragmentAboutUsOnApp extends Fragment  implements UploadImageForUrlRequest.UploadImageForUrlCallback{

    ImageView deleteTitleAboutUsBtnView = null,deleteCARD_IMAGEBtnView = null;
    ImageView deleteHeadingAboutUsBtnView = null;
    ImageView deleteSubHeaderAboutUsBtnView = null;
    ImageView deleteParaAboutUsBtnView = null;

    EditText editTitle = null,editHeader = null,editSubHeading = null,editParaGraphAboutUs = null;

    NetworkImageView cardImage;
    ImageView cardImageCrop;
    Button submit_button;

    AboutUsDataTemplate dataObj = null;
    private boolean showPreview = false;

    //Variables to make request to server
    Page  mAbtUsPageObj;
    String mProfileId = null;
    String mPageName = null;
    String mParentId = null;

    int indexInList = -1;

    private static String TAG = "FragmentAboutUsOnApp";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_about, container, false);

        dataObj = (AboutUsDataTemplate)((FragmenMainActivity)getActivity()).getDatObject();//savedInstanceState.getSerializable("dataKey");

        if(dataObj.isDefaultDataToCreateCampaign() == false){
           showPreview = true;
       }else{
            indexInList = (int)((FragmenMainActivity)getActivity()).getIndexOfPresentview();
        }

        deleteTitleAboutUsBtnView = (ImageView)view.findViewById(R.id.deleteTitleAboutUs);
        deleteCARD_IMAGEBtnView = (ImageView)view.findViewById(R.id.deleteCARD_IMAGE);
        deleteHeadingAboutUsBtnView = (ImageView)view.findViewById(R.id.deleteHeadingAboutUs);
        deleteSubHeaderAboutUsBtnView = (ImageView)view.findViewById(R.id.deleteSubHeaderAboutUs);
        deleteParaAboutUsBtnView = (ImageView)view.findViewById(R.id.deleteParaAboutUs);


        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        String title = dataObj.get_title();
        editTitle = (EditText) view.findViewById(R.id.ABOUT_TITLE);
        if(title != null) {
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else{
            editTitle.setVisibility(View.GONE);
        }

        String header = dataObj.get_heading();
        editHeader = (EditText) view.findViewById(R.id.ABOUT_US_HEADING);
        if(header != null) {
            editHeader.setText(header);
            editHeader.setTypeface(mycustomFont);
        }else{
            editHeader.setVisibility(View.GONE  );
        }

        String subHeading = dataObj.get_sub_heading();
        editSubHeading = (EditText) view.findViewById(R.id.ABOUT_US_SUBHEADING);
        if(subHeading != null) {
            editSubHeading.setText(subHeading);
            editSubHeading.setTypeface(mycustomFont);
        }else{
            editSubHeading.setVisibility(View.GONE);
        }

        String paraGraphAboutUs =  dataObj.get_text_para();
        editParaGraphAboutUs = (EditText) view.findViewById(R.id.paraGraphAboutUs);
        if(paraGraphAboutUs != null){
            editParaGraphAboutUs.setText(paraGraphAboutUs);
            editParaGraphAboutUs.setTypeface(mycustomFont);
        }else{
            editParaGraphAboutUs.setVisibility(View.GONE);
        }

        submit_button = (Button)  view.findViewById(R.id.buttonMainAbtUs);
        cardImage = (NetworkImageView)  view.findViewById(R.id.ABOUT_US_CARD_IMAGE);
        cardImageCrop = (ImageView) view.findViewById(R.id.ABOUT_US_STATIC_IMAGE);

        String urlOfProfile = dataObj.get_url();
        if(urlOfProfile != null && !urlOfProfile.equalsIgnoreCase("null")){
            cardImage.setImageUrl(urlOfProfile, VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());
            cardImageCrop.setVisibility(View.GONE);

        }else{
            cardImage.setVisibility(View.GONE);
            cardImageCrop.setImageResource(R.drawable.about_banner_1);
        }

        //TODO Vijay
        //showImageForBackround();
        submit_button.setTypeface(mycustomFont);
        submit_button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if(showPreview == false) {
                    showMenuOptionToLinkPage();
                }else{

                    int posInListOfPage = dataObj.get_submit_button_link();

                    if(posInListOfPage >= 0) {
                        dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(posInListOfPage).getTemplateData(1,false);

                        Fragment fragmentToLaunch = data.getFragmentToLaunchPage();
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction transaction=fragmentManager.beginTransaction();

                        Bundle args = new Bundle();
                        args.putSerializable("dataKey", data);
                        args.putBoolean("showPreviewKey", showPreview);
                        fragmentToLaunch.setArguments(args);
                        transaction.replace(R.id.mainRLayout,fragmentToLaunch);
                        transaction.commit();
                        //  Class intenetToLaunch = data.getIntentToLaunchPage();
                      //  Intent intent = new Intent(getApplicationContext(), intenetToLaunch);
                      //  intent.putExtra("data", data);
                      //  startActivity(intent);
                    }
                }


            }
        });

        String submitBtnText = dataObj.get_button_text();

        if(submitBtnText != null) {
            submit_button.setText(submitBtnText);
        }else{
            submit_button.setVisibility(View.GONE);
        }
        mProfileId = editCampaign.mCampaignIdFromServer;
        mPageName = ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US;

        mAbtUsPageObj  = new Page(mProfileId,mPageName);
        mParentId = mAbtUsPageObj.getPageId();



       if(showPreview == true) {
           init_viewCampaign();
       }else{
           //init_editCampaign();
       }


        return view;
    }


    void init_viewCampaign(){

        try {
            deleteTitleAboutUsBtnView.setVisibility(View.GONE);
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
            deleteTitleAboutUsBtnView.setVisibility(View.VISIBLE);
            deleteCARD_IMAGEBtnView.setVisibility(View.VISIBLE);
            deleteHeadingAboutUsBtnView.setVisibility(View.VISIBLE);
            deleteSubHeaderAboutUsBtnView.setVisibility(View.VISIBLE);
            deleteParaAboutUsBtnView.setVisibility(View.VISIBLE);
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

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_HEADING, dataObj.get_heading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_SUBHEADING, dataObj.get_sub_heading());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_TITLE, dataObj.get_title());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_CARD_IMAGE, dataObj.get_url());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_PARAGRAPH, dataObj.get_text_para());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_BUTTON_TEXT, dataObj.get_button_text());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_BUTTON_SUBNAME_LINKED_PAGE, String.valueOf(dataObj.get_submit_button_link()));

        Profile reqToMakeProfile =  MainActivity.getProfileObject();

        if(reqToMakeProfile.checkIfPageExist(mParentId) == false) {
            reqToMakeProfile.addPage(mAbtUsPageObj);
        }
    }


    ProgressDialog pbImage = null;

     @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {

        pbImage.hide();
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            String imageUrl = data.getResponseUrl();
            dataObj.set_url(imageUrl);
            Log.v(TAG,"Url received" + imageUrl);
        }

    }

    public void setAttribute(String name, String value){

        if(name != null && value != null) {
            Attribute atrbtObj = new Attribute(mProfileId, mParentId, name, value);
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
        /*final EditText addUrl = new EditText(AboutUsOnApp.this);
        addUrl.setHint("Add Url");
        builderInner.setView(addUrl);

        final EditText addNameToButton = new EditText(AboutUsOnApp.this);
        addNameToButton.setHint("Edit Button Name");
        builderInner.setView(addNameToButton);*/

        builderInner.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {

                        String linkUrlToButton = addUrl.getText().toString();
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

        builderInner.show();

    }



    void showLinkButtonToActivity(){

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        //  builderSingle.setIcon(R.drawable.back_black);
        builderSingle.setTitle("Edit Link");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.select_dialog_singlechoice);

        for(pages obj: MainActivity.listOfTemplatePagesObj){
            arrayAdapter.add(obj.nameis());

        }
        builderSingle.setIcon(R.drawable.logo);
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
                } else {
                    showLinkButtonToActivity();
                }
                return true;
            }
        });

        popup.show();//showing popup menu
    }



    private class PhotoAsyncTask extends AsyncTask<Void, Void, Void>
    {


        public void AboutUsImageUpload () throws IOException {

            FileInputStream in = null;


            try {
                in = getActivity().openFileInput(MainActivity.About_Us_TemplateImage_IMAGE_CROPED_NAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            File sendFile = getFileObjectFromBitmap (bitmap);


            UploadImageForUrlData data =
                    new UploadImageForUrlData(loginUi.mLogintoken, editCampaign.mCampaignIdFromServer, sendFile, "About us banner", 1);
            UploadImageForUrlRequest req = new UploadImageForUrlRequest(getActivity().getApplicationContext(), data, FragmentAboutUsOnApp.this);
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
                AboutUsImageUpload();
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
            FileInputStream in = getActivity().openFileInput(MainActivity.About_Us_TemplateImage_IMAGE_CROPED_NAME);

            cardImage.setVisibility(View.GONE);
            cardImageCrop.setVisibility(View.VISIBLE);

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            cardImageCrop.setImageDrawable(new BitmapDrawable(getResources(), bitmap));


        }catch (FileNotFoundException e){
            Log.v("editCampaign","About_Us_TemplateImage_IMAGE_CROPED_NAME file not found");
        }

    }

    void changeText(){

        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextAbtUs" + title);
        if (title != null) {
            dataObj.set_title(title);
        }

        String header = String.valueOf(editHeader.getText());
        Log.d(TAG, "changeHeadingTxtAbtUs" + header);
        if (header != null) {
            dataObj.set_heading(header);
        }

        String subheader = String.valueOf(editSubHeading.getText());
        Log.d(TAG, "changeSubHeadingAbtUs" + subheader);
        if (subheader != null) {
            dataObj.set_sub_heading(subheader);
        }

        String para = String.valueOf(editParaGraphAboutUs.getText());

        Log.d(TAG, "changeParaAbtUs" + para);
        if (para != null) {
            dataObj.set_text_para(para);
        }
    }


    public void deleteCardImageAboutUs(View view) {

        cardImage.setVisibility(View.GONE);
        deleteCARD_IMAGEBtnView.setVisibility(View.GONE);

    }

    public void deleteTitleAboutUs(View view) {

        editTitle.setVisibility(View.GONE);
        deleteTitleAboutUsBtnView.setVisibility(View.GONE);

    }

    public void deleteHeadingAboutUs(View view) {

        editHeader.setVisibility(View.GONE);
        deleteHeadingAboutUsBtnView.setVisibility(View.GONE);
    }

    public void deleteSubHeadingAboutUs(View view) {
        editSubHeading.setVisibility(View.GONE);
        deleteSubHeaderAboutUsBtnView.setVisibility(View.GONE);
    }

    public void deleteParaAboutUs(View view) {
        editParaGraphAboutUs.setVisibility(View.GONE);
        deleteParaAboutUsBtnView.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        //if(indexInList >=0 )
        {
            changeText();
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
