package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.SaveProfileData;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.util.Timer;
import java.util.TimerTask;

public class FragmenMainActivity extends Activity implements SaveProfileData.SaveProfileResponseCallback{
    Button abtBtn,blogBtn;
    Fragment fragment;
    dataOfTemplate dataObj;
    private static String TAG = "FragmenMainActivity";
    private boolean showPreview = false;
    int IndexKey = 0;
    Fragment fragmentToLaunch;
    TextView mTitle;
    Toolbar toolbar;
    FloatingActionButton fab;

    private viewCampaign previewCampaign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmen_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        dataObj = (dataOfTemplate) getIntent().getSerializableExtra("data");
        if(false == dataObj.isDefaultDataToCreateCampaign()){
            toolbar.setVisibility(View.GONE);
            showPreview = true;

        }

        showBaseMenu();

         fragmentToLaunch = dataObj.getFragmentToLaunchPage();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        Bundle args = new Bundle();
        args.putSerializable("dataKey", dataObj);
        IndexKey =0;
        args.putInt("IndexKey", 0);
        args.putBoolean("showPreviewKey", showPreview);
        fragmentToLaunch.setArguments(args);

        transaction.add(R.id.mainRLayout,fragmentToLaunch);
        transaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        //setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                aboutUsButtonAction();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();

/*                if(test == 0)
                    test = 2;
                else {
                    test = 0;
                }

                dataObj = MainActivity.listOfTemplatePagesObj.get(test).getTemplateData(1, dataObj.isDefaultDataToCreateCampaign());

                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.hide(fragmentToLaunch);
                fragmentToLaunch = dataObj.getFragmentToLaunchPage();

                Bundle args = new Bundle();
                args.putSerializable("dataKey", dataObj);
                args.putInt("IndexKey", 0);
                IndexKey = test;
                args.putBoolean("showPreviewKey", showPreview);
                fragmentToLaunch.setArguments(args);

                transaction.replace(R.id.mainRLayout,fragmentToLaunch);
                transaction.commit();
         */   }

        });
    }




    private void aboutUsButtonAction() {

        previewCampaign = (viewCampaign)fragmentToLaunch ;
        previewCampaign.addLastPage();

        Profile reqToMakeProfile =  MainActivity.getProfileObject();
        int numOfPages = reqToMakeProfile.getTotalNumberOfPagesAdded();

        if(numOfPages > 0) {

            //addPageToRequest(); //This function has check that ensures page is not added duplicate
            //Todo Need to show user popup to get his confirmation that this page will be added
            SaveProfileData req = new SaveProfileData(FragmenMainActivity.this, reqToMakeProfile, loginUi.mLogintoken,FragmenMainActivity.this);
            req.executeRequest();
        }else{
            Toast.makeText(this, "No page was added to your campaign", Toast.LENGTH_LONG).show();

            //addPageToRequest();//Todo All this code will be removed once it is done with help of dialogbox
            SaveProfileData req = new SaveProfileData(this, reqToMakeProfile, loginUi.mLogintoken, this);
            req.executeRequest();
        }

    }



    public dataOfTemplate getDatObject(){

        return dataObj;
    }

    public int getIndexOfPresentview(){

        return IndexKey;
    }




    void showBaseMenu(){

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dynamicPages);
        final Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                int size = MainActivity.listOfTemplatePagesObj.size();
                Button[] btn = new Button[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(FragmenMainActivity.this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT));
                //if (size>1)
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
                                    dataObj = MainActivity.listOfTemplatePagesObj.get(v.getId()).getTemplateData(1, dataObj.isDefaultDataToCreateCampaign());

                                    FragmentManager fragmentManager=getFragmentManager();


                                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                                    transaction.hide(fragmentToLaunch);
                                    fragmentToLaunch = dataObj.getFragmentToLaunchPage();
                                    Bundle args = new Bundle();
                                    args.putSerializable("dataKey", dataObj);
                                    args.putInt("IndexKey", v.getId());
                                    IndexKey = v.getId();
                                    args.putBoolean("showPreviewKey", showPreview);
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

    public void previewTemplate(View view) {
        previewCampaign = (viewCampaign)fragmentToLaunch ;//dataObj.getFragmentToLaunchPage();
        TextView textViewShowPreview = (TextView)findViewById(R.id.textShow_preview);

        Button showPreviewBtn  = (Button)findViewById(R.id.showPreview);


        if(showPreview == false) {
            textViewShowPreview.setText("Edit");
            fab.show();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_edit);
            previewCampaign.init_ViewCampaign();
            //init_viewCampaign();
            showPreview = true;
        }else {
            textViewShowPreview.setText("Preview");
            fab.hide();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_about);
            previewCampaign.init_ViewCampaign();
            //init_editCampaign();
            showPreview = false;
        }

        //   RelativeLayout previewLayout = (RelativeLayout)findViewById(R.id.previewLayout);
        // previewLayout.setVisibility(View.VISIBLE);

    }

static int test = 0;
    public void testApp(View v) {

        if(test == 0)
            test = 2;

        dataObj = MainActivity.listOfTemplatePagesObj.get(test).getTemplateData(1, dataObj.isDefaultDataToCreateCampaign());

        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.hide(fragmentToLaunch);

        fragmentToLaunch = dataObj.getFragmentToLaunchPage();
        Bundle args = new Bundle();
        args.putSerializable("dataKey", dataObj);
        args.putInt("IndexKey", 0);
        IndexKey = test;
        args.putBoolean("showPreviewKey", showPreview);
        fragmentToLaunch.setArguments(args);


        transaction.replace(R.id.mainRLayout,fragmentToLaunch);
        transaction.commit();

    }

    @Override
    public void onProfileSaveResponse(CommonRequest.ResponseCode res, Profile data) {
        Toast.makeText(this, String.valueOf(res), Toast.LENGTH_SHORT).show();
    }

    public abstract interface viewCampaign{
        abstract void init_ViewCampaign();
        abstract void addLastPage();
    }

    public boolean checkPreview(){
        return showPreview;
    }
}
