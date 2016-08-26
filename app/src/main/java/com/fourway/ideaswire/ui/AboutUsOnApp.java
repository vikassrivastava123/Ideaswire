package com.fourway.ideaswire.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.dataOfTemplate;

public class AboutUsOnApp extends AppCompatActivity {

    ViewPager mViewPager;
    PagerAdapter mAdapter;
    private String TAG = "AboutUsOnApp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataOfTemplate dataObj = (dataOfTemplate)getIntent().getSerializableExtra("data");
        int seltemplate = dataObj.getTemplateSelected();

        if(seltemplate == 1) {
            setContentView(R.layout.about_us_template1_on_app);
        }else{
            setContentView(R.layout.about_us_template1_on_app);
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpagerForFooter);
        Log.v(TAG,"mViewPager" + mViewPager);
        mAdapter = new footerAdapter(this);
        mViewPager.setAdapter(mAdapter);
        Log.v(TAG, "setAdapter");

       // TextView test = (TextView)findViewById(R.id.tvHeaderAbtUstplte);
       // test.setText(dataObj.getHeader());


    }

}
