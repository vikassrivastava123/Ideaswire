package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fourway.ideaswire.R;

public class activity_about_us_btn_cros extends Activity {
    Button b1,b2,b3,b4,b5,edit,delete;
    TextView t1,t2,t3,t4,mTitle;
    EditText putlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_about_us_btn_cros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");

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
        b1 = (Button) findViewById(R.id.button3);
        putlink = (EditText) findViewById(R.id.input_link);
        putlink.setTypeface(mycustomFont);
        b1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                my_f();
            }
        });
        b2 = (Button) findViewById(R.id.button4);
        b2.setTypeface(mycustomFont);
        b2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                change_link();
            }
        });

        b5 = (Button) findViewById(R.id.buttonback);
        b5.setTypeface(mycustomFont);
        edit = (Button) findViewById(R.id.edit_text2);
        delete = (Button) findViewById(R.id.delete2);
//        b5.setTypeface(mycustomFont);
//        b1.setTypeface(mycustomFont);
//        b2.setTypeface(mycustomFont);
        t1 = (TextView) findViewById(R.id.textView4);
        t2 = (TextView) findViewById(R.id.textView5);
        t3 = (TextView) findViewById(R.id.textView6);
        //t4 = (TextView) findViewById(R.id.editname);

        //t4.setTypeface(mycustomFont);
        t3.setTypeface(mycustomFont);
        t2.setTypeface(mycustomFont);
        t1.setTypeface(mycustomFont);
    }
    public void my_f()
    {
        edit.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
    }
    public void change_link()
    {
        //putlink.setVisibility(View.VISIBLE);
        Intent intent = new Intent(getApplicationContext(), about_us_inside_menu.class);
        startActivity(intent);
        //delete.setVisibility(View.VISIBLE);
    }
    }

