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

import static com.fourway.ideaswire.ui.MainActivity.CROSS_BUTTON_HIDE;

/**
 * Created by 4way on 24-10-2016.
 */
public class FragmentContactOnApp extends Fragment implements View.OnClickListener, FragmenMainActivity.viewCampaign{
    EditText editTitle, editHeading, editSubheading, editPara, editAddress, editEmail_add,editNumber,editWebsite;
    TextView mTitle;

    ImageView deleteHeading=null;
    ImageView deleteSubHeading=null;
    ImageView deleteParaGraph=null;
    ImageView deleteAddress=null;
    ImageView deleteEmail=null;
    ImageView deletePhone=null;
    ImageView deleteWeb=null;

    TextView addressTextView,emailTextView,numberTextView,websiteTextView;

    private boolean showPreview = false;    /**Is in editable mode or privew/it is server data .
                                                in case it false : editable, True means : Priview or server data*/



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

      //  mEditMode = getActivity().getIntent().getBooleanExtra(MainActivity.ExplicitEditModeKey, false);

        dataobj = (contactDetailsDataTemplate)((FragmenMainActivity)getActivity()).getDatObject();

        if (dataobj.isEditDefaultOrUpdateData() == true){

            indexInList = (int)((FragmenMainActivity)getActivity()).getIndexOfPresentview();
            mthispage = MainActivity.listOfTemplatePagesObj.get(indexInList);
            mPageName = mthispage.nameis();
            showPreview = false;    //since editable
        }else {
            showPreview = true;     //in preview or server data
        }

        addressTextView=(TextView)view.findViewById(R.id.textView13);
        emailTextView = (TextView)view.findViewById(R.id.textView15);
        numberTextView = (TextView)view.findViewById(R.id.textView17);
        websiteTextView = (TextView)view.findViewById(R.id.textView19);


        deleteHeading=(ImageView)view.findViewById(R.id.deleteHeadingContact);
        deleteSubHeading=(ImageView)view.findViewById(R.id.deleteSubHeadingContact);
        deleteParaGraph=(ImageView)view.findViewById(R.id.deleteParaGraphContact);
        deleteAddress=(ImageView)view.findViewById(R.id.deleteAddressContact);
        deleteEmail=(ImageView)view.findViewById(R.id.deleteEmailContact);
        deletePhone=(ImageView)view.findViewById(R.id.deletePhoneContact);
        deleteWeb=(ImageView)view.findViewById(R.id.deleteWebContact);

        deleteHeading.setVisibility(View.GONE);
        deleteSubHeading.setVisibility(View.GONE);
        deleteParaGraph.setVisibility(View.GONE);
        deleteAddress.setVisibility(View.GONE);
        deleteEmail.setVisibility(View.GONE);
        deletePhone.setVisibility(View.GONE);
        deleteWeb.setVisibility(View.GONE);

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
        if(title == null || !title.equals(CROSS_BUTTON_HIDE)) {
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else {
            editTitle.setVisibility(View.GONE);
        }

        String header=dataobj.getHeaderContact();
        editHeading = (EditText) view.findViewById(R.id.contactHeading);
        if(header == null || !header.equals(CROSS_BUTTON_HIDE)){
            editHeading.setText(header);
            editHeading.setTypeface(mycustomFont);
        }else {
            editHeading.setVisibility(View.GONE);
        }

        String subHeading = dataobj.getSubHeading();
        editSubheading = (EditText) view.findViewById(R.id.contactSubHeading);
        if (subHeading == null || !subHeading.equals(CROSS_BUTTON_HIDE)) {
            editSubheading.setText(subHeading);
            editSubheading.setTypeface(mycustomFont);
        }else {
            editSubheading.setVisibility(View.GONE);
        }

        String paraGraph = dataobj.getParaGraph();
        editPara = (EditText) view.findViewById(R.id.contactParaGraph);
        if (paraGraph == null || !paraGraph.equals(CROSS_BUTTON_HIDE)){
            editPara.setText(paraGraph);
            editPara.setTypeface(mycustomFont);
        }else {
            editPara.setVisibility(View.GONE);
        }

        String address = dataobj.getAddress();
        editAddress = (EditText) view.findViewById(R.id.textView14);
        if (address == null || !address.equals(CROSS_BUTTON_HIDE)) {
            editAddress.setText(address);
            editAddress.setTypeface(mycustomFont);
        }else {
            editAddress.setVisibility(View.GONE);
            addressTextView.setVisibility(View.GONE);
        }

        String email = dataobj.getEmail();
        editEmail_add = (EditText) view.findViewById(R.id.textView16);
        if (email == null || !email.equals(CROSS_BUTTON_HIDE)) {
            editEmail_add.setText(email);
            editEmail_add.setTypeface(mycustomFont);
        }else {
            editEmail_add.setVisibility(View.GONE);
            emailTextView.setVisibility(View.GONE);
        }

        String number = dataobj.getPhoneNumber();
        editNumber = (EditText) view.findViewById(R.id.textView18);
        if (number == null || !number.equals(CROSS_BUTTON_HIDE)) {
            editNumber.setText(number);
            editNumber.setTypeface(mycustomFont);
        }else {
            editNumber.setVisibility(View.GONE);
            numberTextView.setVisibility(View.GONE);
        }

        String website = dataobj.getWebsite();
        editWebsite = (EditText) view.findViewById(R.id.textView20);
        if (website == null || !website.equals(CROSS_BUTTON_HIDE)) {
            editWebsite.setText(website);
            editWebsite.setTypeface(mycustomFont);
        }else {
            editWebsite.setVisibility(View.GONE);
            websiteTextView.setVisibility(View.GONE);
        }

        //showPreview();

        if(showPreview == false) {
            init_editCampaign();
        }else {
            init_viewCampaign();
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

        if(mContactPageObj != null){
          lastPositionInList = MainActivity.getProfileObject().getIndexOfPageFromName(mPageName);
          MainActivity.getProfileObject().deletePageByName(mPageName);
        }

        mContactPageObj = new Page(mProfileId, mPageName);
        mPageId = mContactPageObj.getPageId();
    }

    private void addPageToRequest(){

        init_contactPage_request();


        try {
            setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US, mthispage.nameis() );
            setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_STATUS, String.valueOf(mthispage.pageStatus()) );
        }catch (NullPointerException e) {
            Log.v(TAG,e.getMessage());
        }


        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_TITLE, dataobj.getTitle());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_HEADING, dataobj.getHeaderContact());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_SUBHEADING, dataobj.getSubHeading());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_PARAGRAPH, dataobj.getParaGraph());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_ADDRESS, dataobj.getAddress());
        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_EMAIL, dataobj.getEmail());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_PHONE_NUMBER, dataobj.getPhoneNumber());

        setAttribute(ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_WEBSITE, dataobj.getWebsite());


        Profile reqToMakeProfile;
        reqToMakeProfile = MainActivity.getProfileObject();


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

            case R.id.deleteHeadingContact:
                editHeading.setVisibility(View.GONE);
                deleteHeading.setVisibility(View.GONE);
                editHeading.setText(CROSS_BUTTON_HIDE);
                dataobj.setHeaderContact(CROSS_BUTTON_HIDE);
                break;
            case R.id.deleteSubHeadingContact:
                editSubheading.setVisibility(View.GONE);
                deleteSubHeading.setVisibility(View.GONE);
                editSubheading.setText(CROSS_BUTTON_HIDE);
                dataobj.setSubHeading(CROSS_BUTTON_HIDE);
                break;
            case R.id.deleteParaGraphContact:
                editPara.setVisibility(View.GONE);
                editPara.setText(CROSS_BUTTON_HIDE);
                dataobj.setParaGraph(CROSS_BUTTON_HIDE);
                deleteParaGraph.setVisibility(View.GONE);
                break;
            case R.id.deleteAddressContact:
                editAddress.setVisibility(View.GONE);
                editAddress.setText(CROSS_BUTTON_HIDE);
                dataobj.setAddress(CROSS_BUTTON_HIDE);
                deleteAddress.setVisibility(View.GONE);
                addressTextView.setVisibility(View.GONE);
                break;
            case R.id.deleteEmailContact:
                editEmail_add.setVisibility(View.GONE);
                editEmail_add.setText(CROSS_BUTTON_HIDE);
                dataobj.setEmail(CROSS_BUTTON_HIDE);
                deleteEmail.setVisibility(View.GONE);
                emailTextView.setVisibility(View.GONE);
                break;
            case R.id.deletePhoneContact:
                editNumber.setVisibility(View.GONE);
                editNumber.setText(CROSS_BUTTON_HIDE);
                dataobj.setPhoneNumber(CROSS_BUTTON_HIDE);
                deletePhone.setVisibility(View.GONE);
                numberTextView.setVisibility(View.GONE);
                break;
            case R.id.deleteWebContact:
                editWebsite.setVisibility(View.GONE);
                editWebsite.setText(CROSS_BUTTON_HIDE);
                dataobj.setWebsite(CROSS_BUTTON_HIDE);
                deleteWeb.setVisibility(View.GONE);
                websiteTextView.setVisibility(View.GONE);
                break;
        }
    }

    void init_viewCampaign() {
        try {

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

            if (dataobj.getHeaderContact() == null || !dataobj.getHeaderContact().equals(CROSS_BUTTON_HIDE)) {
                deleteHeading.setVisibility(View.VISIBLE);
            }

            if (dataobj.getSubHeading() == null || !dataobj.getSubHeading().equals(CROSS_BUTTON_HIDE)) {
                deleteSubHeading.setVisibility(View.VISIBLE);
            }

            if (dataobj.getParaGraph() == null || !dataobj.getParaGraph().equals(CROSS_BUTTON_HIDE)) {
                deleteParaGraph.setVisibility(View.VISIBLE);
            }

            if (dataobj.getAddress() == null || !dataobj.getAddress().equals(CROSS_BUTTON_HIDE)) {
                deleteAddress.setVisibility(View.VISIBLE);
            }

            if (dataobj.getEmail() == null || !dataobj.getEmail().equals(CROSS_BUTTON_HIDE)) {
                deleteEmail.setVisibility(View.VISIBLE);
            }

            if (dataobj.getPhoneNumber() == null || !dataobj.getPhoneNumber().equals(CROSS_BUTTON_HIDE)) {
                deletePhone.setVisibility(View.VISIBLE);
            }

            if (dataobj.getWebsite() == null || !dataobj.getWebsite().equals(CROSS_BUTTON_HIDE)) {
                deleteWeb.setVisibility(View.VISIBLE);
            }








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

//    void showPreview(){
//
//        if(((FragmenMainActivity)getActivity()).checkPreview()){
//            init_ViewCampaign();
//            showPreview=true;
//        }
//    }

    public  void changeText(){

        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextAbtUs" + title);
        if (!title.equals("")) {
            dataobj.setTitle(title);
        }

        String header = String.valueOf(editHeading.getText());
        Log.d(TAG, "changeHeadingTxtAbtUs" + header);
        if (!header.equals("")) {
            dataobj.setHeaderContact(header);
        }

        String subheader = String.valueOf(editSubheading.getText());
        Log.d(TAG, "changeSubHeadingAbtUs" + subheader);
        if (!subheader.equals("")) {
            dataobj.setSubHeading(subheader);
        }

        String para = String.valueOf(editPara.getText());
        Log.d(TAG, "changeParaAbtUs" + para);
        if (!para.equals("")) {
            dataobj.setParaGraph(para);
        }

        String address = String.valueOf(editAddress.getText());
        Log.d(TAG, "changeParaAbtUs" + para);
        if (!address.equals("")) {
            dataobj.setAddress(address);
        }

        String email = String.valueOf(editEmail_add.getText());
        Log.d(TAG, "changeParaAbtUs" + para);
        if (!email.equals("")) {
            dataobj.setEmail(email);
        }
        String phoneNumber = String.valueOf(editNumber.getText());
        Log.d(TAG, "changeParaAbtUs" + para);
        if (!phoneNumber.equals("")) {
            dataobj.setPhoneNumber(phoneNumber);
        }
        String website = String.valueOf(editWebsite.getText());
        Log.d(TAG, "changeParaAbtUs" + para);
        if (!website.equals("")) {
            dataobj.setWebsite(website);
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
