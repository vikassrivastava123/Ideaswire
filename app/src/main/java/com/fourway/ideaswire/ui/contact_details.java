package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.contactDetailsDataTemplate;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.util.Timer;
import java.util.TimerTask;

public class contact_details extends Activity {
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

    void showPreview(){

        init_viewCampaign();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        deleteTitle=(ImageView)findViewById(R.id.deleteTitleContact);
        deleteHeading=(ImageView)findViewById(R.id.deleteHeadingContact);
        deleteSubHeading=(ImageView)findViewById(R.id.deleteSubHeadingContact);
        deleteParaGraph=(ImageView)findViewById(R.id.deleteParaGraphContact);
        deleteAddress=(ImageView)findViewById(R.id.deleteAddressContact);
        deleteEmail=(ImageView)findViewById(R.id.deleteEmailContact);
        deletePhone=(ImageView)findViewById(R.id.deletePhoneContact);
        deleteWeb=(ImageView)findViewById(R.id.deleteWebContact);


        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dynamicPages);
        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                int size = MainActivity.listOfTemplatePagesObj.size();
                Button[] btn = new Button[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(contact_details.this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT));
                for(pages obj: MainActivity.listOfTemplatePagesObj) {
                    String name = obj.nameis();



                    float x =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                    float y =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());

                    LinearLayout.LayoutParams buttonLayoutParams =
                            new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                                    (int)x,
                                    (int)y));
                    buttonLayoutParams.setMargins(2,2, 0, 0);
                    btn[i] = new Button(contact_details.this);

                    btn[i].setLayoutParams(buttonLayoutParams);
                    btn[i].setText(name);
                    btn[i].setId(i);
                    btn[i].setBackgroundColor(getResources().getColor(R.color.card));
                    btn[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //Toast.makeText(getApplicationContext(),
                            //       "button is clicked" + v.getId(), Toast.LENGTH_LONG).show();
                            dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(v.getId()).getTemplateData(1,dataobj.isDefaultDataToCreateCampaign());

                            Class intenetToLaunch = data.getIntentToLaunchPage();
                            Log.v(TAG, "5" + intenetToLaunch);
                            Intent intent = new Intent(getApplicationContext(), intenetToLaunch);
                            intent.putExtra("data",data);
                            startActivity(intent);
                        }
                    });
                    row.addView(btn[i]);
                    // Add the LinearLayout element to the ScrollView
                    i++;
                }
                btn[3].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));
                btn[3].setFocusable(true);
                // When adding another view, make sure you do it on the UI
                // thread.
                layout.post(new Runnable() {

                    public void run() {

                        layout.addView(row);
                    }
                });
            }
        }, 500);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        dataobj = (contactDetailsDataTemplate)getIntent().getSerializableExtra("data");

        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });

        title = (EditText)findViewById(R.id.Contact_TITLE);
        title.setText(dataobj.getTitle());
        heading = (EditText) findViewById(R.id.contactHeading);
        heading.setText(dataobj.getHeaderContact());
        subheading = (EditText) findViewById(R.id.contactSubHeading);
        subheading.setText(dataobj.getSubHeading());
        text_heading = (EditText) findViewById(R.id.contactParaGraph);
        text_heading.setText(dataobj.getParaGraph());
        address = (EditText) findViewById(R.id.textView14);
        address.setText(dataobj.getAddress());
        email_add = (EditText) findViewById(R.id.textView16);
        email_add.setText(dataobj.getEmail());
        number = (EditText) findViewById(R.id.textView18);
        number.setText(dataobj.getPhoneNumber());
        website = (EditText) findViewById(R.id.textView20);
        website.setText(dataobj.getWebsite());


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
            title.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if (heading!=null) {
            heading.setEnabled(true);
            heading.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if(subheading!=null) {
            subheading.setEnabled(true);
            subheading.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if(text_heading!=null) {
            text_heading.setEnabled(true);
            text_heading.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if(address!=null){
            address.setEnabled(true);
            address.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if(email_add!=null) {
            email_add.setEnabled(true);
            email_add.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if(number!=null) {
            number.setEnabled(true);
            number.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if(website!=null) {
            website.setEnabled(true);
            website.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
    }

    public void previewTemplate(View view) {
        TextView textViewShowPreview = (TextView) findViewById(R.id.textShow_preview);

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.floatingForPreview);
        Button showPreviewBtn = (Button) findViewById(R.id.showPreview);


        if (showPreview == false) {
            textViewShowPreview.setText("Edit");
            btn.show();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_edit);
            init_viewCampaign();
            showPreview = true;
        } else {
            textViewShowPreview.setText("Preview");
            btn.hide();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_about);
            init_editCampaign();
            showPreview = false;
        }
    }

    public void pageTemplate(View view) {
        startActivity(new Intent(getApplicationContext(),about_us_page_template.class));
    }

    public void deleteTitleContact(View view){
        title.setVisibility(View.GONE);
        deleteTitle.setVisibility(View.GONE);
    }

    public void deleteHeadingContact(View view){
        heading.setVisibility(View.GONE);
        deleteHeading.setVisibility(View.GONE);
    }

    public void deleteSubHeadingContact(View view){
        subheading.setVisibility(View.GONE);
        deleteSubHeading.setVisibility(View.GONE);
    }

    public void deleteParaGraphContact(View view){
        text_heading.setVisibility(View.GONE);
        deleteParaGraph.setVisibility(View.GONE);
    }

    public void deleteAddressContact(View view){
        address.setVisibility(View.GONE);
        deleteAddress.setVisibility(View.GONE);
    }

    public void deleteEmailContact(View view){
        email_add.setVisibility(View.GONE);
        deleteEmail.setVisibility(View.GONE);
    }

    public void deletePhoneContact(View view){
        number.setVisibility(View.GONE);
        deletePhone.setVisibility(View.GONE);
    }

    public void deleteWebContact(View view){
        website.setVisibility(View.GONE);
        deleteWeb.setVisibility(View.GONE);
    }

}
