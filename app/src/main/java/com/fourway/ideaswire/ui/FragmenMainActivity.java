package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Attribute;
import com.fourway.ideaswire.data.GetUserProfileRequestData;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.ProfileFieldsEnum;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.GetUserProfileRequest;
import com.fourway.ideaswire.request.SaveProfileData;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.fourway.ideaswire.ui.loginUi.mLogintoken;

public class FragmenMainActivity extends Activity implements SaveProfileData.SaveProfileResponseCallback,GetUserProfileRequest.GetUserProfilesResponseCallback {

    Fragment fragment;
    dataOfTemplate dataObj = null;
    private static String TAG = "FragmenMainActivity";
    private boolean showPreview = false;
    private Boolean isUpdateRequest;
    int IndexKey = 0;
    Fragment fragmentToLaunch;
    TextView mTitle;
    Toolbar toolbar;
    FloatingActionButton fab;
    static ArrayList<Integer> selectedPageList = null;
    static  int pageButtonViewId;
    static  boolean isChangeThemeManual = false;
    ProgressDialog mProgressDialog;
    HorizontalScrollView sv;
    int selectedLayout;
    int selectedTemplate;
    ImageView fragmentBackBtn; //back button for Layout 2 and Layout 3

    Fragment fragmentLayout2 = new FragmentLayout2();
    Fragment fragmentLayout3 = new FragmentLayout3();
    public static boolean isFragmentMainPage = true;


    public static boolean isImageUploading = false; //check image uploading status, if it is "true" means image uploading in progress


    private static ViewPager mPager;
    private viewCampaign previewCampaign;
    static int theme = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
//            if(dataObj != null) {
            dataObj = MainActivity.listOfTemplatePagesObj.get(pageButtonViewId).getTemplateData(true);
//            }else{
//                dataObj = (dataOfTemplate) getIntent().getSerializableExtra("data");  //not need
//            }
        }catch (NullPointerException e){
            Log.d(TAG,e.toString());

        }catch (IndexOutOfBoundsException e){
            Log.d(TAG,e.toString());
        }

        if (isChangeThemeManual) {
            isChangeThemeManual = false;
        }

        theme = MainActivity.listOfTemplatePagesObj.get(pageButtonViewId).themes(); //theme selected by user
        selectedTemplate = dataObj.getTemplateByServer();//dataObj.getTemplateSelected(); //template selected by user
        int themeForApply = getThemeForApply(selectedTemplate, theme); // getting theme for apply according template and user selected them

        applyTheme(themeForApply); //apply theme

        super.onCreate(savedInstanceState);





        setContentView(R.layout.activity_fragmen_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       // mEditMode = super.getIntent().getBooleanExtra(MainActivity.ExplicitEditModeKey, false);

        fragmentBackBtn = (ImageView)findViewById(R.id.backBtnViewFromFragment);
        if (isFragmentMainPage) {
            fragmentBackBtn.setVisibility(View.GONE);
        }

        selectedLayout = dataObj.getLayoutByServer();//dataObj.getLayoutSelected(); // selected layout by User



       /**
        * We can show in either Preview or Edit mode
        */

        if(false == dataObj.isEditDefaultOrUpdateData())
        {
            toolbar.setVisibility(View.GONE);
            showPreview = true;
        }

        /**
         * selectedPageList : it's save selected page base menu button id, which page add in profile data
         */
        if( selectedPageList == null && dataObj.isEditDefaultOrUpdateData() == true) {
            selectedPageList = new ArrayList<>();

            if(dataObj.isInUpdateProfileMode()== true){
                int sizeOfPages = MainActivity.listOfTemplatePagesObj.size();
                for(int i = 0 ;i< sizeOfPages;i++){
                   selectedPageList.add(i);             // add all button when update mode
               }
            }else {
                selectedPageList.add(0);    // by default add 1st button when edit edit mode
            }
        }

        sv = (HorizontalScrollView) findViewById(R.id.scrollViewPages);
        showBaseMenu();


        launchMainFragmentBasedOnSelectedLayout(selectedLayout); //Launch Main fragment based on layout

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        //setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutUsButtonAction();
            }

        });


    }


    private void setTemplateLayoutThemeAtZerothIndex() {
        Page zerothPage = MainActivity.getProfileObject().getPageAtIndex(0);
        String mParentId = zerothPage.getPageId();
        String mProfileId = editCampaign.mCampaignIdFromServer;
        Attribute attributeTemplate = new Attribute(mProfileId,mParentId, ProfileFieldsEnum.PROFILE_TEMPLATE,String.valueOf(selectedTemplate));
        Attribute attributeLayout = new Attribute(mProfileId,mParentId, ProfileFieldsEnum.PROFILE_LAYOUT,String.valueOf(selectedLayout));
        Attribute attributeTheme = new Attribute(mProfileId,mParentId, ProfileFieldsEnum.PROFILE_THEME,String.valueOf(theme));

        zerothPage.addAttribute(attributeTemplate);
        zerothPage.addAttribute(attributeLayout);
        zerothPage.addAttribute(attributeTheme);
    }


    private void aboutUsButtonAction() {

        mProgressDialog = new ProgressDialog(FragmenMainActivity.this,
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);

        previewCampaign = (viewCampaign)fragmentToLaunch ;
        previewCampaign.addLastPage();

        setTemplateLayoutThemeAtZerothIndex();
        Profile reqToMakeProfile =  null;
        reqToMakeProfile = MainActivity.getProfileObject(); //data for profile

        isUpdateRequest = dataObj.isInUpdateProfileMode();

        if (isUpdateRequest) {
            mProgressDialog.setMessage("Updating profile...  ");
        }else {
            mProgressDialog.setMessage("Please wait...  ");
        }



        int numOfPages = reqToMakeProfile.getTotalNumberOfPagesAdded();

        if(numOfPages > 0) {

            //addPageToRequest(); //This function has check that ensures page is not added duplicate
            SaveProfileData req = new SaveProfileData(FragmenMainActivity.this, reqToMakeProfile, mLogintoken,FragmenMainActivity.this,isUpdateRequest);
            req.executeRequest();
        }else{
            Toast.makeText(this, "No page was added to your campaign", Toast.LENGTH_LONG).show();

            //addPageToRequest();
            SaveProfileData req = new SaveProfileData(this, reqToMakeProfile, mLogintoken, this,isUpdateRequest);
            req.executeRequest();
        }
        mProgressDialog.show();

    }



    public dataOfTemplate getDatObject(){

        return dataObj;
    }

    public void setDataObj(dataOfTemplate dataObj) {
        this.dataObj = dataObj;
        fragmentBackBtn.setVisibility(View.VISIBLE);
    }

    public void setFragmentToLaunch(Fragment fragmentToLaunch) {
        this.fragmentToLaunch = fragmentToLaunch;
    }

    public int getIndexOfPresentview(){

        return IndexKey;
    }
public void setIndexOfPresentview(int index){

        IndexKey = index;
    }




    void showBaseMenu() {
        if (selectedLayout == 0) {

            final Typeface mycustomFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");

            final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dynamicPages);
            layout.removeAllViews();
            final Timer timing = new Timer();
            timing.schedule(new TimerTask() {
                @Override
                public void run() {

                    final int size = MainActivity.listOfTemplatePagesObj.size();
                    int numberOfBtn = 0;
                    final Button[] btn = new Button[size];
                    final int[] iconWhite = new int[size];
                    final int[] iconBlack = new int[size];
                    int i = 0;
                    final LinearLayout row = new LinearLayout(FragmenMainActivity.this);
                    row.setBackgroundColor(fetchThemeBackgroundColor());
                    row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    //if (size>1)
                    for (pages obj : MainActivity.listOfTemplatePagesObj) {
                        iconWhite[i] = obj.iconis();
                        iconBlack[i] = obj.iconBlack();
                        String[] nameStrings = obj.nameis().split(" ", 2);
                        String name = null;

                        if (nameStrings.length > 1) {
                            name = nameStrings[1];
                        } else {
                            name = nameStrings[0];
                        }

                        numberOfBtn = size;  // number of button when data come from server and preview mode


                        if (showPreview && dataObj.isEditOrUpdateMode()) {
                            try {
                                numberOfBtn = selectedPageList.size();  // buttons which page are add in profile data
                            } catch (NullPointerException e) {
                                Log.d(TAG, e.toString());
                            }

                        }

                        float displayWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics()) / 2;
                        float x;//=  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                        if (numberOfBtn > 3) {
                            x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                        } else {
                            x = displayWidth / numberOfBtn;
                        }

                        float y = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());


                        LinearLayout.LayoutParams buttonLayoutParams =
                                new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                                        (int) x,
                                        (int) y));
                        buttonLayoutParams.setMargins(2, 2, 0, 0);

                        //if (i!=0)
                        {
                            btn[i] = new Button(FragmenMainActivity.this);
                            btn[i].setLayoutParams(buttonLayoutParams);
                            btn[i].setText(name);
                            btn[i].setSingleLine();
                            btn[i].setAllCaps(true);
                            btn[i].setTypeface(mycustomFont);
                            btn[i].setId(i);
                            btn[i].setCompoundDrawablesWithIntrinsicBounds(0, iconBlack[i], 0, 0);
                            btn[i].setBackgroundColor(getResources().getColor(R.color.card));
                            btn[i].setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    if (pageButtonViewId != v.getId() && !isImageUploading) {
                                        pageButtonViewId = v.getId();
                                        //Toast.makeText(getApplicationContext(),
                                        //       "button is clicked" + v.getId(), Toast.LENGTH_LONG).show();
                                        if (dataObj.isEditDefaultOrUpdateData() && !showPreview) {
                                            boolean add = true;
                                            for (int x = 0; x < selectedPageList.size(); x++) {
                                                if (selectedPageList.get(x) == v.getId()) {
                                                    add = false;
                                                    break;
                                                }
                                            }
                                            if (add) {
                                                selectedPageList.add(v.getId());
                                            }
                                        }
                                        dataObj = MainActivity.listOfTemplatePagesObj.get(v.getId()).getTemplateData(1, dataObj.isDefaultDataToCreateCampaign());

                                        FragmentManager fragmentManager = getFragmentManager();


                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                                        transaction.hide(fragmentToLaunch);
                                        fragmentToLaunch = dataObj.getFragmentToLaunchPage();
                                        Bundle args = new Bundle();
                                        args.putSerializable("dataKey", dataObj);
                                        args.putInt("IndexKey", v.getId());
                                        IndexKey = v.getId();
                                        args.putBoolean("showPreviewKey", showPreview);
                                        fragmentToLaunch.setArguments(args);

                                        transaction.replace(R.id.mainRLayout, fragmentToLaunch);
                                        transaction.commit();

                                        for (int j = 0; j < size; j++) {
                                            if (v.getId() != j) {
                                                btn[j].setBackgroundColor(getResources().getColor(R.color.card));
                                                btn[j].setCompoundDrawablesWithIntrinsicBounds(0, iconBlack[j], 0, 0);
                                                btn[j].setTextColor(getResources().getColor(R.color.text));
                                            } else {
                                                btn[j].setBackgroundColor(fetchThemeBackgroundColor());
                                                btn[j].setCompoundDrawablesWithIntrinsicBounds(0, iconWhite[j], 0, 0);
                                                btn[j].setTextColor(Color.WHITE);
                                            }
                                        }
                                    }

                                }
                            });
                            if (dataObj.isDefaultDataToCreateCampaign() && showPreview) {
                                boolean addV = false;
                                for (int m = 0; m < selectedPageList.size(); m++) {
                                    if (selectedPageList.get(m) == i) {
                                        addV = true;
                                        break;
                                    }
                                }
                                if (addV) {
                                    row.addView(btn[i]);
                                }
                            } else {
                                row.addView(btn[i]);
                            }

                        }
                        // Add the LinearLayout element to the ScrollView
                        i++;
                    }
                    btn[pageButtonViewId].setBackgroundColor(fetchThemeBackgroundColor());
                    btn[pageButtonViewId].setCompoundDrawablesWithIntrinsicBounds(0, iconWhite[pageButtonViewId], 0, 0);
                    btn[pageButtonViewId].setTextColor(Color.WHITE);


                    //btn[0].setFocusable(true);
                    // When adding another view, make sure you do it on the UI
                    // thread.
                    final int finalNumberOfBtn = numberOfBtn;
                    layout.post(new Runnable() {

                        public void run() {

                            layout.addView(row);
//                        if (pageButtonViewId > 3) {//TODO: for auto scroll basemenu
//                           for (int b = 0; b<(((finalNumberOfBtn -4)/2)+1); b++) {
//                               sv.arrowScroll(View.FOCUS_RIGHT);
//                           }
//                        }
                        }
                    });

                }
            }, 500);


        }
    }



    private int fetchThemeBackgroundColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[] { R.attr.customBackgroundAttributeColor });
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    boolean toglePreviewButton = false;
    public void previewTemplate(View view) {
        if (!isImageUploading) {
            previewCampaign = (viewCampaign) fragmentToLaunch;//dataObj.getFragmentToLaunchPage();
            TextView textViewShowPreview = (TextView) findViewById(R.id.textShow_preview);

            Button showPreviewBtn = (Button) findViewById(R.id.showPreview);


            if (toglePreviewButton == false) {    //this means app is in edit mode and user pressed
                //priview button so now it should be priview mode
                textViewShowPreview.setText("Edit");
                fab.show();
                showPreviewBtn.setBackgroundResource(R.drawable.preview_edit);
                previewCampaign.init_ViewCampaign();

                dataObj.setPriviewMode(true);   //take app to priview mode

                //init_viewCampaign();
                showPreview = true;
                toglePreviewButton = true;
                showBaseMenu();
            } else {
                textViewShowPreview.setText("Preview");
                fab.hide();
                showPreviewBtn.setBackgroundResource(R.drawable.preview_about);
                previewCampaign.init_ViewCampaign();

                dataObj.setPriviewMode(false);  //take app to edit mode

                //init_editCampaign();
                showPreview = false;
                toglePreviewButton = false;
                showBaseMenu();
            }
        }

        //   RelativeLayout previewLayout = (RelativeLayout)findViewById(R.id.previewLayout);
        // previewLayout.setVisibility(View.VISIBLE);

    }
    public void pageTemplate(View view) {
        //startActivity(new Intent(getApplicationContext(),about_us_page_template.class));
        if (!showPreview && !isImageUploading)
        addPage();
    }

    public void changeThemes(View view){
        if (!showPreview && !isImageUploading){
            AlertDialog themeDialog = new AlertDialog.Builder(this, R.style.AppTheme).create();

            View themeDialogView = LayoutInflater.from(this).inflate(R.layout.themes_chooser,null);

            ImageButton slideLeft = (ImageButton)themeDialogView .findViewById(R.id.slideLeft);
            ImageButton slideRight = (ImageButton)themeDialogView .findViewById(R.id.slideRight);

            mPager = (ViewPager)themeDialogView. findViewById(R.id.theme_pager);
            mPager.setAdapter(new SlideThemes_Adapter(this));

            slideLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentItem = mPager.getCurrentItem();
                    if (currentItem != 0){
                        currentItem--;
                    }else {
                        currentItem = mPager.getAdapter().getCount();
                    }
                    mPager.setCurrentItem(currentItem);
                }
            });

            slideRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentItem = mPager.getCurrentItem();
                    if (currentItem != mPager.getAdapter().getCount()-1){
                        currentItem++;
                    }else {
                        currentItem = 0;
                    }
                    mPager.setCurrentItem(currentItem);
                }
            });


            themeDialog.setView(themeDialogView);
            themeDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                 //  previewCampaign.changeText();
              //      testForCahngeTheme();
                    isChangeThemeManual = true;
                    theme = mPager.getCurrentItem() + 1;
                    MainActivity.listOfTemplatePagesObj.get(pageButtonViewId).set_theme(theme);
                    recreate();
                }
            });

            themeDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            themeDialog.show();

        }
    }

    void addPage(){
        final AlertDialog pageDialog=new AlertDialog.Builder(this, R.style.AppTheme).create();
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_about_us_page_tamplate,null);
        final pages currentPages = MainActivity.listOfTemplatePagesObj.get(pageButtonViewId);

        final ListView listView;
        final List<String> list=new ArrayList<>();
        final PageTemplateArrayAdapter adapter=new PageTemplateArrayAdapter(this,android.R.layout.simple_list_item_1,list);

        listView=(ListView)dialogView.findViewById(R.id.listView);

        final RelativeLayout layout = (RelativeLayout) dialogView.findViewById(R.id.dynamicPages2);

        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                final int size = select_layout_of_template.listOfTemplatePagesObjForAddPage.size();
                ImageButton[] btn = new ImageButton[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(FragmenMainActivity.this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT));
                for(pages obj: select_layout_of_template.listOfTemplatePagesObjForAddPage) {
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

                            final String pageName = select_layout_of_template.listOfTemplatePagesObjForAddPage.get(v.getId()).nameis();
                            final pages mNewPage = select_layout_of_template.listOfTemplatePagesObjForAddPage.get(v.getId()).getNewPage();
                            dataOfTemplate dot = mNewPage.getTemplateData(1,true);
                            mNewPage.setDataObj(dot);

                            final AlertDialog mAlertDialog = new AlertDialog.Builder(FragmenMainActivity.this).create();
                            mAlertDialog.setTitle("Page Name");

                            final View mAlertDialogView = LayoutInflater.from(FragmenMainActivity.this).inflate(R.layout.alert_dialog_page_name,null);
                            final EditText editPageName = (EditText) mAlertDialogView.findViewById(R.id.dialogPageName);
                            editPageName.setText(pageName +"(Copy)");

                            mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String newPageName = editPageName.getText().toString();
                                    if (!newPageName.equals("")) {
                                        boolean nameAlreadyExist = false;
                                        for(pages obj: MainActivity.listOfTemplatePagesObj) {
                                            String[] nameStrings = obj.nameis().split(" ", 2);
                                            String name=null;
                                            if (nameStrings.length>1){
                                                name = nameStrings[1];
                                            }else {
                                                name = nameStrings[0];
                                            }

                                            if (newPageName.equals(name)){
                                                nameAlreadyExist = true;
                                                break;
                                            }
                                        }
                                        if (!nameAlreadyExist) {
                                            mNewPage.set_nameis(pageName + " " + newPageName);

                                            MainActivity.listOfTemplatePagesObj.add(MainActivity.listOfTemplatePagesObj.size(), mNewPage);
                                            list.add(newPageName);
                                            adapter.notifyDataSetChanged();
                                            Toast.makeText(FragmenMainActivity.this, newPageName + " Added... ", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(FragmenMainActivity.this, newPageName + " is already added", Toast.LENGTH_SHORT).show();
                                            mAlertDialog.setView(mAlertDialogView);
                                            mAlertDialog.show();

                                        }
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
        }, 250);

        for(pages obj: MainActivity.listOfTemplatePagesObj) {
            String[] nameStrings = obj.nameis().split(" ", 2);
            String name=null;

            if (nameStrings.length>1){
                name = nameStrings[1];
            }else {
                name = nameStrings[0];
            }
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
                    if (currentPages != mPages) {
                        MainActivity.listOfTemplatePagesObj.remove(position); //delete from main list
                        MainActivity.getProfileObject().deletePageByName(pageName);
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(FragmenMainActivity.this, pageName + " delete", Toast.LENGTH_SHORT).show();
                        for (int c=0;c<MainActivity.listOfTemplatePagesObj.size();c++){
                            if (currentPages == MainActivity.listOfTemplatePagesObj.get(c)){
                                IndexKey = c;
                            }
                        }
                    }else {
                        Toast.makeText(FragmenMainActivity.this, pageName + " can't delete because its open", Toast.LENGTH_SHORT).show();

                    }
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
                if (selectedLayout > 0 && isFragmentMainPage) {
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(fragmentToLaunch);
                    ft.attach(fragmentToLaunch);
                    ft.commit();
                }
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


    @Override
    public void onProfileSaveResponse(CommonRequest.ResponseCode res, Profile data) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        AlertDialog.Builder  errorDialog = new AlertDialog.Builder(this);
        errorDialog.setIcon(android.R.drawable.ic_dialog_alert);
        errorDialog.setPositiveButton("OK", null);
        errorDialog.setTitle("Error");
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            errorDialog.setIcon(android.R.drawable.ic_dialog_info);
            errorDialog.setCancelable(false);
            errorDialog.setTitle("Congratulation!");

            if (!dataObj.isInUpdateProfileMode())
            errorDialog.setMessage("Your app successfully created. ");
            else
            errorDialog.setMessage("Your app successfully updated. ");

            errorDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    GetUserProfileRequestData data = new GetUserProfileRequestData(mLogintoken);
                    GetUserProfileRequest request = new GetUserProfileRequest(FragmenMainActivity.this, data, FragmenMainActivity.this);
                    request.executeRequest();
                    mProgressDialog.setMessage("Please wait...  ");
                    mProgressDialog.show();


                }
            });
            errorDialog.show();
        }else {
            switch (res) {
                case COMMON_RES_CONNECTION_TIMEOUT:
                    errorDialog.setMessage("Connection time out");
                    errorDialog.show();
                    break;
                case COMMON_RES_INTERNAL_ERROR:
                    errorDialog.setMessage("Internal error");
                    errorDialog.show();
                    break;
                case COMMON_RES_FAILED_TO_CONNECT:
                    errorDialog.setMessage("failed to connect");
                    errorDialog.show();
                    break;
                case COMMON_RES_SERVER_ERROR_WITH_MESSAGE:
                    errorDialog.setMessage(data.getErrorMessage());
                    errorDialog.show();
                    break;
                case COMMON_RES_PROFILE_DATA_NO_CONTENT:
                    errorDialog.setMessage("Profile data not content");
                    errorDialog.show();
                    break;
            }
        }
    }

    @Override
    public void onResponse(CommonRequest.ResponseCode res, GetUserProfileRequestData data) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        switch (res) {
            case COMMON_RES_SUCCESS:
                loginUi.mProfileList = data.getProfileList();
                Intent intent = new Intent(getApplicationContext(), CreateCampaign_homePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
        }

    }


    abstract public interface viewCampaign{
        abstract void init_ViewCampaign();
        abstract void addLastPage();
        abstract void changeText();
    }

    /*
    * Preview button is pressed if  showPreview = true;
    * */
    public boolean checkPreview(){
        return showPreview;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        /*if (isChangeThemeManual) {
            theme = MainActivity.listOfTemplatePagesObj.get(0).themes();
            switch (theme) {
                case MainActivity.THEME_ORANGE:
                    setTheme(R.style.AppTheme_Orange);
                    break;
                case MainActivity.THEME_GREEN:
                    setTheme(R.style.AppTheme_Green);
                    break;
                default:
                    setTheme(R.style.AppTheme);
            }
            setContentView(R.layout.activity_fragmen_main);
            getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
            showBaseMenu();
            isChangeThemeManual = false;
        }
        Toast.makeText(this, "onrestart", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isChangeThemeManual) {     //init static variables
            pageButtonViewId = 0;
            selectedPageList = null;
            dataObj.setEditMode(false);
            dataObj.setIsInUpdateProfileMode(false);
            dataObj.setPriviewMode(false);
            if (select_layout_of_template.listOfTemplatePagesObjForAddPage != null)
            select_layout_of_template.listOfTemplatePagesObjForAddPage.clear();
            MainActivity.requestToMakeProfile = null;
            isImageUploading = false;
            isFragmentMainPage = true;
        }
    }

    /**
     * launch main fragment based on selected layout
     * @param selectedLayout
     */

    private void launchMainFragmentBasedOnSelectedLayout(int selectedLayout) {


        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        IndexKey = pageButtonViewId;

        if (isFragmentMainPage) {
            switch (selectedLayout) {

                case 0:
                    fragmentToLaunch = dataObj.getFragmentToLaunchPage();
                    break;
                case 1:
                    fragmentToLaunch = fragmentLayout2;
                    break;
                case 2:
                    fragmentToLaunch = fragmentLayout3;
                    break;
                case 3:
                    break;

            }
        }else {
            fragmentToLaunch = dataObj.getFragmentToLaunchPage();
        }

        Bundle args = new Bundle();
        args.putSerializable("dataKey", dataObj);
        args.putInt("IndexKey", IndexKey);

        fragmentToLaunch.setArguments(args);

        try {
            if(selectedPageList.size()>1) {
                transaction.replace(R.id.mainRLayout, fragmentToLaunch);
            }
            else {
                transaction.add(R.id.mainRLayout, fragmentToLaunch);
            }
        }catch (NullPointerException e){
            Log.v(TAG, e.toString());
            transaction.add(R.id.mainRLayout, fragmentToLaunch);
        }

        transaction.commit();

    }

    /**
     * back arrow button for Layout 2 and Layout 3
     * @param view
     */

    public void backFromFragment(View view) {
        fragmentBackBtn.setVisibility(View.GONE);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragmentToLaunch);

        switch (selectedLayout) {

            case 0:
                break;
            case 1:
                this.fragmentToLaunch = fragmentLayout2;
                break;
            case 2:
                this.fragmentToLaunch = fragmentLayout3;
                break;
            case 3:
                break;

        }
        setFragmentToLaunch(fragmentToLaunch);
        transaction.replace(R.id.mainRLayout, fragmentToLaunch);
        transaction.commit();

        isFragmentMainPage = true;
    }

    /**
     * this function return absolute theme for apply
     * if "themeSelected == THEME_DEFAULT" means user not selected any theme, so return theme according selected template
     * else return user selected theme
     * @param temSel
     * @param themeSelected
     * @return
     */

    public int getThemeForApply(int temSel, int themeSelected) {
        int themeForApply = 0;

        if (themeSelected == MainActivity.THEME_DEFAULT) {
            switch (temSel) {

                case MainActivity.TEM_BUSINESS:
                    themeForApply = MainActivity.THEME_BUSINESS;
                    break;
                case MainActivity.TEM_INDIVIDUAL:
                    themeForApply = MainActivity.THEME_INDIVIDUAL;
                    break;
                case MainActivity.TEM_FINANCE:
                    themeForApply = MainActivity.THEME_FINANCE;
                    break;
                case MainActivity.TEM_HEALTH:
                    themeForApply = MainActivity.THEME_HEALTH;
                    break;
                case MainActivity.TEM_ENTERTAINMENT:
                    themeForApply = MainActivity.THEME_ENTERTAINMENT;
                    break;
                case MainActivity.TEM_INFORMATION:
                    themeForApply = MainActivity.THEME_INFORMATION;
                    break;
                case MainActivity.TEM_WEDDING:
                    themeForApply = MainActivity.THEME_WEDDING;
                    break;
                case MainActivity.TEM_RESTAURANT:
                    themeForApply = MainActivity.THEME_RESTAURANT;
                    break;
                case MainActivity.TEM_OTHERS:
                    themeForApply = MainActivity.THEME_OTHERS;
                    break;
            }
        }else {
           themeForApply = themeSelected;
        }
        return themeForApply;
    }

    /**
     *
     * @param themeForApply
     */
    public void applyTheme(int themeForApply) {
        switch (themeForApply){

            case MainActivity.THEME_APP:
                setTheme(R.style.AppTheme);
                break;
            case MainActivity.THEME_ORANGE:
                setTheme(R.style.AppTheme_Orange);
                break;
            case MainActivity.THEME_GREEN:
                setTheme(R.style.AppTheme_Green);
                break;
            case MainActivity.THEME_BUSINESS:
                setTheme(R.style.AppTheme_Business);
                break;
            case MainActivity.THEME_INDIVIDUAL:
                setTheme(R.style.AppTheme_Individual);
                break;
            case MainActivity.THEME_FINANCE:
                setTheme(R.style.AppTheme_Finance);
                break;
            case MainActivity.THEME_HEALTH:
                setTheme(R.style.AppTheme_Health);
                break;
            case MainActivity.THEME_ENTERTAINMENT:
                setTheme(R.style.AppTheme_Entertainment);
                break;
            case MainActivity.THEME_INFORMATION:
                setTheme(R.style.AppTheme_Information);
                break;
            case MainActivity.THEME_WEDDING:
                setTheme(R.style.AppTheme_Wedding);
                break;
            case MainActivity.THEME_RESTAURANT:
                setTheme(R.style.AppTheme_Restaurant);
                break;
            case MainActivity.THEME_OTHERS:
                setTheme(R.style.AppTheme_Others);
                break;

            default:
                setTheme(R.style.AppTheme);
        }
    }
}



class SlideThemes_Adapter extends PagerAdapter {

    private int[] mResources = {R.drawable.theme_blue,R.drawable.theme_orange,R.drawable.theme_green};
    private Context context;
    LayoutInflater mLayoutInflater;

    public SlideThemes_Adapter(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.slide_thems_layout, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.theme_image);
        imageView.setImageResource(mResources[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
