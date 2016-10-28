package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.UploadImageForUrlData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.UploadImageForUrlRequest;
import com.fourway.ideaswire.templates.contactDetailsDataTemplate;

/**
 * Created by 4way on 24-10-2016.
 */
public class FragmentContactOnApp extends Fragment implements View.OnClickListener, FragmenMainActivity.viewCampaign, UploadImageForUrlRequest.UploadImageForUrlCallback{
    EditText title,heading,subheading,text_heading,address,email_add,number,website;
    TextView mTitle;

    ImageView deleteTitle=null;
    ImageView deleteHeading=null;
    ImageView deleteSubHeading=null;
    ImageView deleteParaGraph=null;
    ImageView deleteAddress=null;
    ImageView deleteEmail=null;
    ImageView deletePhone=null;
    ImageView deleteWeb=null;

    private boolean showPreview = false;
    contactDetailsDataTemplate dataobj;

    public String TAG="contact_details";
    public String name()
    {
        return "Contact Details";
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

        if(title!=null){
            title.setEnabled(false);
            title.setKeyListener(null);
        }

        if (heading!=null) {
            heading.setEnabled(false);
            heading.setKeyListener(null);
        }

        if(subheading!=null) {
            subheading.setEnabled(false);
            subheading.setKeyListener(null);
        }

        if(text_heading!=null) {
            text_heading.setEnabled(false);
            text_heading.setKeyListener(null);
        }

        if(address!=null){
            address.setEnabled(false);
            address.setKeyListener(null);
        }

        if(email_add!=null) {
            email_add.setEnabled(false);
            email_add.setKeyListener(null);
        }

        if(number!=null) {
            number.setEnabled(false);
            number.setKeyListener(null);
        }

        if(website!=null) {
            website.setEnabled(false);
            website.setKeyListener(null);
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

        if(title!=null){
            title.setEnabled(true);
            title.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if (heading!=null) {
            heading.setEnabled(true);
            heading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(subheading!=null) {
            subheading.setEnabled(true);
            subheading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(text_heading!=null) {
            text_heading.setEnabled(true);
            text_heading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(address!=null){
            address.setEnabled(true);
            address.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(email_add!=null) {
            email_add.setEnabled(true);
            email_add.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(number!=null) {
            number.setEnabled(true);
            number.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if(website!=null) {
            website.setEnabled(true);
            website.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
    }

    void showPreview(){

        init_viewCampaign();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact,container,false);

        dataobj = (contactDetailsDataTemplate)((FragmenMainActivity)getActivity()).getDatObject();

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

        title = (EditText)view.findViewById(R.id.Contact_TITLE);
        title.setText(dataobj.get_contactDeatils_title());
        heading = (EditText) view.findViewById(R.id.contactHeading);
        heading.setText(dataobj.get_heading());
        subheading = (EditText) view.findViewById(R.id.contactSubHeading);
        subheading.setText(dataobj.get_subheading());
        text_heading = (EditText) view.findViewById(R.id.contactParaGraph);
        text_heading.setText(dataobj.get_text_para());
        address = (EditText) view.findViewById(R.id.textView14);
        address.setText(dataobj.get_Address());
        email_add = (EditText) view.findViewById(R.id.textView16);
        email_add.setText(dataobj.get_email());
        number = (EditText) view.findViewById(R.id.textView18);
        number.setText(dataobj.get_phonenumber());
        website = (EditText) view.findViewById(R.id.textView20);
        website.setText(dataobj.get_website());


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteTitleContact:
                title.setVisibility(View.GONE);
                deleteTitle.setVisibility(View.GONE);
                break;
            case R.id.deleteHeadingContact:
                heading.setVisibility(View.GONE);
                deleteHeading.setVisibility(View.GONE);
                break;
            case R.id.deleteSubHeadingContact:
                subheading.setVisibility(View.GONE);
                deleteSubHeading.setVisibility(View.GONE);
                break;
            case R.id.deleteParaGraphContact:
                text_heading.setVisibility(View.GONE);
                deleteParaGraph.setVisibility(View.GONE);
                break;
            case R.id.deleteAddressContact:
                address.setVisibility(View.GONE);
                deleteAddress.setVisibility(View.GONE);
                break;
            case R.id.deleteEmailContact:
                email_add.setVisibility(View.GONE);
                deleteEmail.setVisibility(View.GONE);
                break;
            case R.id.deletePhoneContact:
                number.setVisibility(View.GONE);
                deletePhone.setVisibility(View.GONE);
                break;
            case R.id.deleteWebContact:
                website.setVisibility(View.GONE);
                deleteWeb.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {

    }

    @Override
    public void init_ViewCampaign() {

    }

    @Override
    public void addLastPage() {

    }
}
