package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.HomePageDataTemplate;

public class home_page_onapp extends Activity implements NavigationView.OnNavigationItemSelectedListener {
    TextView mTitle,heading,subheading,text_para;
    NavigationView navigationView;

    public String name()
    {
        return "Home Page";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_onapp);
        HomePageDataTemplate dataobj = new HomePageDataTemplate(1,true);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        heading = (TextView) findViewById(R.id.textView7 );
        subheading = (TextView) findViewById(R.id.textView8 );
        text_para = (TextView) findViewById(R.id.textView9 );
        heading.setText(dataobj.getheadingSelected());
        subheading.setText(dataobj.getsubheadingSelected());
        text_para.setText(dataobj.getparaSelected());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
