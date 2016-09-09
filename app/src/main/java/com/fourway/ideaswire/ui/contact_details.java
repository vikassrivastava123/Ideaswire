package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.contactDetailsTemplate;

public class contact_details extends Activity {
    TextView mTitle,heading,subheading,text_heading,address,email_add,number,website;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        contactDetailsTemplate dataobj = new contactDetailsTemplate(1,true);

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
        heading = (TextView) findViewById(R.id.textView10);
        heading.setText(dataobj.get_heading());
        subheading = (TextView) findViewById(R.id.textView11);
        subheading.setText(dataobj.get_subheading());
        text_heading = (TextView) findViewById(R.id.textView12);
        text_heading.setText(dataobj.get_text_para());
        address = (TextView) findViewById(R.id.textView14);
        address.setText(dataobj.get_Address());
        email_add = (TextView) findViewById(R.id.textView16);
        email_add.setText(dataobj.get_email());
        number = (TextView) findViewById(R.id.textView18);
        number.setText(dataobj.get_phonenumber());
        website = (TextView) findViewById(R.id.textView20);
        website.setText(dataobj.get_website());
    }

}
