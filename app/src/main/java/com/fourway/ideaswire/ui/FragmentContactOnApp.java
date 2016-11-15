package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Attribute;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.ProfileFieldsEnum;
import com.fourway.ideaswire.templates.contactDetailsDataTemplate;
import com.fourway.ideaswire.templates.pages;

/**
 * Created by 4way on 24-10-2016.
 */
public class FragmentContactOnApp extends Fragment implements View.OnClickListener, FragmenMainActivity.viewCampaign{
    EditText editTitle, editHeading, editSubheading, editPara, editAddress, editEmail_add,editNumber,editWebsite;
    TextView mTitle;

    ImageView deleteTitle=null;
    ImageView deleteHeading=null;
    ImageView deleteSubHeading=null;
    ImageView deleteParaGraph=null;
    ImageView deleteAddress=null;
    ImageView deleteEmail=null;
    ImageView deletePhone=null;
    ImageView deleteWeb=null;

    TextView addressTextView,emailTextView,numberTextView,websiteTextView;

    private boolean showPreview = false;
    contactDetailsDataTemplate dataobj;

    //Variables to make request to server
    Page mContactPageObj;
    String mProfileId = null;
    String mPageName = null;
    String mPageId = null;

    int indexInList = -1;
    pages mthispage = null;

    public String TAG="contact_details";
    public String name()
    {
        return "Contact Details";
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact,container,false);

        dataobj = (contactDetailsDataTemplate)((FragmenMainActivity)getActivity()).getDatObject();

        if(dataobj.isDefaultDataToCreateCampaign() == false){
            showPreview = true;
        }else{
            indexInList = (int)((FragmenMainActivity)getActivity()).getIndexOfPresentview();
            mthispage = MainActivity.listOfTemplatePagesObj.get(indexInList);
            mPageName = mthispage.nameis();
        }

        addressTextView=(TextView)view.findViewById(R.id.textView13);
        emailTextView = (TextView)view.findViewById(R.id.textView15);
        numberTextView = (TextView)view.findViewById(R.id.textView17);
        websiteTextView = (TextView)view.findViewById(R.id.textView19);

        deleteTitle=(ImageView)view.findViewById(R.id.deleteTitleContact);
        deleteHeading=(ImageView)view.findViewById(R.id.deleteHeadingContact);
        deleteSubHeading=(ImageView)view.findViewById(R.id.deleteSubHeadingContact);
        deleteParaGraph=(ImageView)view.findViewById(R.id.deleteParaGraphContact);
        deleteAddress=(ImageView)view.findViewById(R.id.deleteAddressContact);
        deleteEmail=(ImageView)view.findViewById(R.id.deleteEmailContact);
        deletePhone=(ImageView)view.findViewById(R.id.deletePhoneContact);
        deleteWeb=(ImageView)view.findViewById(R.id.deleteWebContact);

        deleteTitle.setOnClickListener(this);
        deleteHeading.setOnClickListener(this);
        deleteSubHeading.setOnClickListener(this);
        deleteParaGraph.setOnClickListener(this);
        deleteAddress.setOnClickListener(this);
        deleteEmail.setOnClickListener(this);
        deletePhone.setOnClickListener(this);
        deleteWeb.setOnClickListener(this);

        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        String title=dataobj.getTitle();
        editTitle = (EditText)view.findViewById(R.id.Contact_TITLE);
        if(title != null && !title.equals("")) {
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }

        String header=dataobj.getHeaderContact();
        editHeading = (EditText) view.findViewById(R.id.contactHeading);
        if(header !=null && !header.equals("")){
            editHeading.setText(header);
            editHeading.setTypeface(mycustomFont);
        }

        String subHeading = dataobj.getSubHeading();
        editSubheading = (EditText) view.findViewById(R.id.contactSubHeading);
        if (subHeading != null && !subHeading.equals("")) {
            editSubheading.setText(subHeading);
            editSubheading.setTypeface(mycustomFont);
        }

        String paraGraph = dataobj.getParaGraph();
        editPara = (EditText) view.findViewById(R.id.contactParaGraph);
        if (paraGraph != null && !paraGraph.equals("")){
            editPara.setText(paraGraph);
            editPara.setTypeface(mycustomFont);
        }

        String address = dataobj.getAddress();
        editAddress = (EditText) view.findViewById(R.id.textView14);
        if (address != null && !address.equals("")) {
            editAddress.setText(address);
            editAddress.setTypeface(mycustomFont);
        }

        String email = dataobj.getEmail();
        editEmail_add = (EditText) view.findViewById(R.id.textView16);
        if (email != null && !email.equals("")) {
            editEmail_add.setText(email);
            editEmail_add.setTypeface(mycustomFont);
        }
        String number = dataobj.getPhoneNumber();
        editNumber = (EditText) view.findViewById(R.id.textView18);
        if (number != null && !number.equals("")) {
            editNumber.setText(number);
            editNumber.setTypeface(mycustomFont);
        }

        String website = dataobj.getWebsite();
        editWebsite = (EditText) view.findViewById(R.id.textView20);
        if (website != null && !website.equals("")) {
            editWebsite.setText(website);
            editWebsite.setTypeface(mycustomFont);
        }

        if(showPreview == true) {
            init_viewCampaign();
        }else{
            init_editCampaign();
        }

        if (dataobj.isDefaultDataToCreateCampaign()){
            showPreview();
        }

        return view;
    }

    public void setAttribute(String name, String value){

        if(name != null && value != null) {
            Attribute atrbtObj = new Attribute(mProfileId, mPageId, name, value);
            mContactPageObj.addAttribute(atrbtObj);
        }
    }

    int lastPositionInList = -1;
    void init_contactPage_request(){
        mProfileId = editCampaign.mCampaignIdFromServer;
        //mPageName = ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US;
        mContactPageObj = MainActivity.getProfileObject().getPageByName(mPageName);

        if(mContactPageObj != null)
        {
            lastPositionInList = MainActivity.getProfileObject().getIndexOfPageFromName(mPageName);
            MainActivity.getProfileObject().deletePageByName(mPageName);
        }

        mContactPageObj = new Page(mProfileId, mPageName);
        mPageId = mContactPageObj.getPageId();
    }

    private void addPageToRequest(){

        init_contactPage_request();



        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US, mthispage.nameis() );
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_STATUS, String.valueOf(mthispage.pageStatus()) );

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_TITLE, dataobj.getTitle());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_HEADING, dataobj.getHeaderContact());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_SUBHEADING, dataobj.getSubHeading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_PARAGRAPH, dataobj.getParaGraph());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_ADDRESS, dataobj.getAddress());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_EMAIL, dataobj.getEmail());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_PHONE_NUMBER, dataobj.getPhoneNumber());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_WEBSITE, dataobj.getWebsite());


        Profile reqToMakeProfile =  MainActivity.getProfileObject();

        //if(reqToMakeProfile.checkIfPageExist(mPageId)) {
        /*if( MainActivity.getProfileObject().getIndexOfPageFromName(mPageName) != -1){
            int index = reqToMakeProfile.getIndexOfPage(mPageId);
            reqToMakeProfile.replacePage(index, mAbtUsPageObj);
        }else*/
        {
            if(lastPositionInList == -1){
                reqToMakeProfile.addPage(mContactPageObj);
            }else {
                reqToMakeProfile.addPageAtPosition(mContactPageObj, lastPositionInList);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteTitleContact:
                editTitle.setVisibility(View.GONE);
                deleteTitle.setVisibility(View.GONE);
                editTitle.setText(null);
                break;
            case R.id.deleteHeadingContact:
                editHeading.setVisibility(View.GONE);
                deleteHeading.setVisibility(View.GONE);
                editHeading.setText(null);
                break;
            case R.id.deleteSubHeadingContact:
                editSubheading.setVisibility(View.GONE);
                deleteSubHeading.setVisibility(View.GONE);
                editSubheading.setText(null);
                break;
            case R.id.deleteParaGraphContact:
                editPara.setVisibility(View.GONE);
                editPara.setText(null);
                deleteParaGraph.setVisibility(View.GONE);
                break;
            case R.id.deleteAddressContact:
                editAddress.setVisibility(View.GONE);
                editAddress.setText(null);
                deleteAddress.setVisibility(View.GONE);
                addressTextView.setVisibility(View.GONE);
                break;
            case R.id.deleteEmailContact:
                editEmail_add.setVisibility(View.GONE);
                editEmail_add.setText(null);
                deleteEmail.setVisibility(View.GONE);
                emailTextView.setVisibility(View.GONE);
                break;
            case R.id.deletePhoneContact:
                editNumber.setVisibility(View.GONE);
                editNumber.setText(null);
                deletePhone.setVisibility(View.GONE);
                numberTextView.setVisibility(View.GONE);
                break;
            case R.id.deleteWebContact:
                editWebsite.setVisibility(View.GONE);
                editWebsite.setText(null);
                deleteWeb.setVisibility(View.GONE);
                websiteTextView.setVisibility(View.GONE);
                break;
        }
    }

    void init_viewCampaign() {
        try {
            deleteTitle.setVisibility(View.GONE);
            deleteHeading.setVisibility(View.GONE);
            deleteSubHeading.setVisibility(View.GONE);
            deleteParaGraph.setVisibility(View.GONE);
            deleteAddress.setVisibility(View.GONE);
            deleteEmail.setVisibility(View.GONE);
            deletePhone.setVisibility(View.GONE);
            deleteWeb.setVisibility(View.GONE);
        }catch (NullPointerException e)
        {
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if(editTitle !=null){
            editTitle.setEnabled(false);
            editTitle.setKeyListener(null);
        }

        if (editHeading !=null) {
            editHeading.setEnabled(false);
            editHeading.setKeyListener(null);
        }

        if(editSubheading !=null) {
            editSubheading.setEnabled(false);
            editSubheading.setKeyListener(null);
        }

        if(editPara !=null) {
            editPara.setEnabled(false);
            editPara.setKeyListener(null);
        }

        if(editAddress !=null){
            editAddress.setEnabled(false);
            editAddress.setKeyListener(null);
        }

        if(editEmail_add !=null) {
            editEmail_add.setEnabled(false);
            editEmail_add.setKeyListener(null);
        }

        if(editNumber!=null) {
            editNumber.setEnabled(false);
            editNumber.setKeyListener(null);
        }

        if(editWebsite!=null) {
            editWebsite.setEnabled(false);
            editWebsite.setKeyListener(null);
        }
    }

    void init_editCampaign(){

        try {
            deleteTitle.setVisibility(View.VISIBLE);
            deleteHeading.setVisibility(View.VISIBLE);
            deleteSubHeading.setVisibility(View.VISIBLE);
            deleteParaGraph.setVisibility(View.VISIBLE);
            deleteAddress.setVisibility(View.VISIBLE);
            deleteEmail.setVisibility(View.VISIBLE);
            deletePhone.setVisibility(View.VISIBLE);
            deleteWeb.setVisibility(View.VISIBLE);
        }catch (NullPointerException e)
        {
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if(editTitle !=null){
            editTitle.setEnabled(true);
            editTitle.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if (editHeading !=null) {
            editHeading.setEnabled(true);
            editHeading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(editSubheading !=null) {
            editSubheading.setEnabled(true);
            editSubheading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(editPara !=null) {
            editPara.setEnabled(true);
            editPara.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(editAddress !=null){
            editAddress.setEnabled(true);
            editAddress.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(editEmail_add !=null) {
            editEmail_add.setEnabled(true);
            editEmail_add.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(editNumber!=null) {
            editNumber.setEnabled(true);
            editNumber.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(editWebsite!=null) {
            editWebsite.setEnabled(true);
            editWebsite.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
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
        Log.d(TAG, "changeTitleTextAbtUs" + title);
        if (title != null) {
            dataobj.setTitle(title);
        }

        String header = String.valueOf(editHeading.getText());
        Log.d(TAG, "changeHeadingTxtAbtUs" + header);
        if (header != null) {
            dataobj.setHeaderContact(header);
        }

        String subheader = String.valueOf(editSubheading.getText());
        Log.d(TAG, "changeSubHeadingAbtUs" + subheader);
        if (subheader != null) {
            dataobj.setSubHeading(subheader);
        }

        String para = String.valueOf(editPara.getText());
        Log.d(TAG, "changeParaAbtUs" + para);
        if (para != null) {
            dataobj.setParaGraph(para);
        }

        String address = String.valueOf(editAddress.getText());
        Log.d(TAG, "changeParaAbtUs" + para);
        if (address != null) {
            dataobj.setAddress(address);
        }

        String email = String.valueOf(editEmail_add.getText());
        Log.d(TAG, "changeParaAbtUs" + para);
        if (email != null) {
            dataobj.setEmail(email);
        }
        String phoneNumber = String.valueOf(editNumber.getText());
        Log.d(TAG, "changeParaAbtUs" + para);
        if (phoneNumber != null) {
            dataobj.setPhoneNumber(phoneNumber);
        }
        String website = String.valueOf(editWebsite.getText());
        Log.d(TAG, "changeParaAbtUs" + para);
        if (website != null) {
            dataobj.setWebsite(website);
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
            MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataobj);
        }
    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (indexInList >=0) {
            if (hidden) {
                changeText();
                addPageToRequest();
                MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataobj);
            } else {
                changeText();
                MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataobj);
            }
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            changeText();
            MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataobj);
        } else {
            changeText();
            MainActivity.listOfTemplatePagesObj.get(indexInList).setDataObj(dataobj);
        }
    }
}
