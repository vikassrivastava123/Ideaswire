package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.blogpageDataTemplate;

public class activity_blogpage extends Activity {
    TextView mTitle,heading,subheading,textview,heading_belo,subheading_below,text_below;


    public String name()
    {
        return "Blogs";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_on_app);
        blogpageDataTemplate dataobj = new blogpageDataTemplate(1,true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        heading = (TextView) findViewById(R.id.service_heading);
        subheading = (TextView) findViewById(R.id.service_subheading);
        textview = (TextView) findViewById(R.id.textView23);
        heading_belo = (TextView) findViewById(R.id.service_heading_belowimg);
        subheading_below = (TextView) findViewById(R.id.service_subheading_belowimg);
        text_below = (TextView) findViewById(R.id.service_text_belowimg);
        heading.setText(dataobj.get_heading());
        subheading.setText(dataobj.get_subheading());
        textview.setText(dataobj.get_text_para());
        heading_belo.setText(dataobj.get_heading_belowimg());
        subheading_below.setText(dataobj.get_subheading_below());
        text_below.setText(dataobj.get_text_below_image());
        // /setSupportActionBar(toolbar);


    }

}
