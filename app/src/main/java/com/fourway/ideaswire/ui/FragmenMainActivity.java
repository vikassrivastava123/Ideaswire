package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.AboutUsDataTemplate;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.util.Timer;
import java.util.TimerTask;

public class FragmenMainActivity extends AppCompatActivity {
    Button abtBtn,blogBtn;
    Fragment fragment;
    dataOfTemplate dataObj;
    private static String TAG = "FragmenMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmen_main);

        dataObj = (dataOfTemplate) getIntent().getSerializableExtra("data");
        showBaseMenu();

        Fragment fragmentToLaunch = dataObj.getFragmentToLaunchPage();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.mainRLayout,fragmentToLaunch);
        transaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    void showBaseMenu(){

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dynamicPages);
        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                int size = MainActivity.listOfTemplatePagesObj.size();
                Button[] btn = new Button[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(FragmenMainActivity.this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT));
                if (size>1)
                    for(pages obj: MainActivity.listOfTemplatePagesObj) {
                        String name = obj.nameis();

                        // float displayWidth=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics());
                        float x =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                        float y =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());



                        LinearLayout.LayoutParams buttonLayoutParams =
                                new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                                        (int)x,
                                        (int)y));
                        buttonLayoutParams.setMargins(2,2, 0, 0);

                        //if (i!=0)
                        {
                            btn[i] = new Button(FragmenMainActivity.this);
                            btn[i].setLayoutParams(buttonLayoutParams);
                            btn[i].setText(name);
                            btn[i].setId(i);
                            btn[i].setBackgroundColor(getResources().getColor(R.color.card));
                            btn[i].setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    //Toast.makeText(getApplicationContext(),
                                    //       "button is clicked" + v.getId(), Toast.LENGTH_LONG).show();
                                    dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(v.getId()).getTemplateData(1, dataObj.isDefaultDataToCreateCampaign());
                                    Fragment fragmentToLaunch = data.getFragmentToLaunchPage();
                                    FragmentManager fragmentManager=getFragmentManager();
                                    FragmentTransaction transaction=fragmentManager.beginTransaction();

                                    Bundle args = new Bundle();
                                    args.putSerializable("dataKey", data);
                                    fragmentToLaunch.setArguments(args);

                                    transaction.replace(R.id.mainRLayout,fragmentToLaunch);
                                    transaction.commit();
                                }
                            });
                            row.addView(btn[i]);
                        }
                        // Add the LinearLayout element to the ScrollView
                        i++;
                    }
                // btn[0].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));
                //btn[0].setFocusable(true);
                // When adding another view, make sure you do it on the UI
                // thread.
                layout.post(new Runnable() {

                    public void run() {

                        layout.addView(row);
                    }
                });
            }
        }, 500);


    }


}
