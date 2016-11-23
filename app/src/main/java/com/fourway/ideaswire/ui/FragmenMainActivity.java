package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.SaveProfileData;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.util.ArrayList;
import java.util.List;
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
        layout.removeAllViews();
        final Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                final int size = MainActivity.listOfTemplatePagesObj.size();
                final Button[] btn = new Button[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(FragmenMainActivity.this);
                row.setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT));
                //if (size>1)
                    for(pages obj: MainActivity.listOfTemplatePagesObj) {
                        String[] nameStrings = obj.nameis().split(" ", 2);
                        String name=null;
                        if (nameStrings.length>1){
                            name = nameStrings[1];
                        }else {
                            name = nameStrings[0];
                        }


                         float displayWidth=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics())/2;
                        float x ;//=  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                        if (size>3){
                            x =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                        }else {
                            x=displayWidth/size;
                        }

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

                                    for(int j=0;j<size;j++){
                                        if (v.getId()!=j){
                                            btn[j].setBackgroundColor(getResources().getColor(R.color.card));
                                        }else {
                                            btn[j].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));
                                        }
                                    }


                                }
                            });
                            row.addView(btn[i]);
                        }
                        // Add the LinearLayout element to the ScrollView
                        i++;
                    }
                 btn[getIndexOfPresentview()].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));
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
    public void pageTemplate(View view) {
        //startActivity(new Intent(getApplicationContext(),about_us_page_template.class));
        addPage();
    }

    void addPage(){
        final AlertDialog pageDialog=new AlertDialog.Builder(this).create();
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_about_us_page_tamplate,null);

        final ListView listView;
        final List<String> list=new ArrayList<>();
        final PageTemplateArrayAdapter adapter=new PageTemplateArrayAdapter(this,android.R.layout.simple_list_item_1,list);

        listView=(ListView)dialogView.findViewById(R.id.listView);

        final RelativeLayout layout = (RelativeLayout) dialogView.findViewById(R.id.dynamicPages2);

        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                final int size = MainActivity.listOfTemplatePagesObj.size();
                ImageButton[] btn = new ImageButton[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(FragmenMainActivity.this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT));
                for(pages obj: MainActivity.listOfTemplatePagesObj) {
                    int icon = obj.iconis();

                    float x =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                    float y =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

                    LinearLayout.LayoutParams buttonLayoutParams =
                            new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                                    (int)x,
                                    (int)y));
                    //buttonLayoutParams.setMargins(2,0, 0, 0);
                    btn[i] = new ImageButton(FragmenMainActivity.this);

                    btn[i].setLayoutParams(buttonLayoutParams);
                    btn[i].setImageResource(icon);
                    btn[i].setId(i);
                    btn[i].setBackgroundColor(getResources().getColor(android.R.color.black));
                    btn[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            final String pageName = MainActivity.listOfTemplatePagesObj.get(v.getId()).nameis();
                            final pages mNewPage = MainActivity.listOfTemplatePagesObj.get(v.getId()).getNewPage();

                            final AlertDialog mAlertDialog = new AlertDialog.Builder(FragmenMainActivity.this).create();
                            mAlertDialog.setTitle("Page Name");

                            View mAlertDialogView = LayoutInflater.from(FragmenMainActivity.this).inflate(R.layout.alert_dialog_page_name,null);
                            final EditText editPageName = (EditText) mAlertDialogView.findViewById(R.id.dialogPageName);
                            editPageName.setText(pageName +"(Copy)");

                            mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String newPageName = editPageName.getText().toString();
                                    if (!newPageName.equals("")) {
                                        mNewPage.set_nameis(pageName+ " " +newPageName);

                                        MainActivity.listOfTemplatePagesObj.add(size,mNewPage);
                                        list.add(newPageName);
                                        listView.setAdapter(adapter);
                                        Toast.makeText(FragmenMainActivity.this, newPageName + " Added... " , Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(FragmenMainActivity.this, "Please enter page name :(", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            mAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            mAlertDialog.setView(mAlertDialogView);
                            mAlertDialog.show();

                        }
                    });
                        row.addView(btn[i]);
                    // Add the LinearLayout element to the ScrollView
                    i++;
                }
                // When adding another view, make sure you do it on the UI
                // thread.
                layout.post(new Runnable() {

                    public void run() {

                        layout.addView(row);
                    }
                });
            }
        }, 500);

        for(pages obj: MainActivity.listOfTemplatePagesObj) {
            String name = obj.nameis();
            list.add(name);
        }



        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pages mPages= MainActivity.listOfTemplatePagesObj.get(position);
                if(view.getId()==R.id.imageButton7)
                {
                    String pageName = mPages.nameis();
                    MainActivity.listOfTemplatePagesObj.remove(position);
                    list.remove(position);
                    listView.setAdapter(adapter);
                    Toast.makeText(FragmenMainActivity.this, pageName + " delete", Toast.LENGTH_SHORT).show();
                }else if (view.getId()==R.id.switchbtn){
                    SwitchCompat switchCompat=(SwitchCompat)view;
                    boolean pageStatus = switchCompat.isChecked();

                    mPages.setPageStatus(pageStatus);

                }
            }
        });

        //setSupportActionBar(toolbar);


        pageDialog.setView(dialogView);
        pageDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pageDialog.dismiss();
                showBaseMenu();
            }
        });

        pageDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                showBaseMenu();
            }
        });

        pageDialog.show();
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

    abstract public interface viewCampaign{
        abstract void init_ViewCampaign();
        abstract void addLastPage();
    }

    public boolean checkPreview(){
        return showPreview;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showBaseMenu();
    }
}
