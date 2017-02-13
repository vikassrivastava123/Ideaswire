package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.AboutUsPage;
import com.fourway.ideaswire.templates.ClientPage;
import com.fourway.ideaswire.templates.HomePageLayout_1;
import com.fourway.ideaswire.templates.ServicePage;
import com.fourway.ideaswire.templates.TeamPage;
import com.fourway.ideaswire.templates.blogpage;
import com.fourway.ideaswire.templates.contactDetails;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.util.ArrayList;
import java.util.List;

public class select_layout_of_template extends Activity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    public static List<pages> listOfTemplatePagesObjForAddPage;


    private final String TAG = "seleclayouttemplate";
    TextView mTitle,t;
    Typeface myCustomFont;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_layout_of_template);

        myCustomFont = Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
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
        t= (TextView) findViewById(R.id.HeaderOfSelectLAyout);
        mTitle.setTypeface(myCustomFont);
        t.setTypeface(myCustomFont);
        gridView = (GridView) findViewById(R.id.gridViewForSelectLayout);

        String[] values = new String[] { "Layout 1", "Layout 2", "Layout 3",
                "Layout 4"};

        // use your custom layout
        int selTemp = dataOfTemplate.getTemplateSelectedByUser();
        int layoutResource[] = getLayoutResourceBasedOnTemplate(selTemp);
        ArrayAdapter adapter = new GridViewAdapter(this, values, layoutResource);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

//                if (position != 3) {
                    dataOfTemplate.setLayoutSelected(position);

                    startCreateCampaignWithDefaultData();
//                }else {
//                    Toast.makeText(select_layout_of_template.this, "Coming soon", Toast.LENGTH_SHORT).show();
//                }
            }
        });



    }

    int[] getLayoutResourceBasedOnTemplate(int selTemp) {

        switch (selTemp) {
            case MainActivity.TEM_BUSINESS:
                int layoutResourceBusiness[] = {R.drawable.business_homepage_layout1, R.drawable.business_homepage_layout2,
                        R.drawable.business_homepage_layout3, R.drawable.business_homepage_layout4};
                return layoutResourceBusiness;

            case MainActivity.TEM_INFORMATION:
                int layoutResourceInfo[] = {R.drawable.information_homepage_layout_1, R.drawable.information_homepage_layout_2,
                        R.drawable.information_homepage_layout_3, R.drawable.information_homepage_layout_4};
                return layoutResourceInfo;

            case MainActivity.TEM_INDIVIDUAL:
                int layoutResourceIndividual[] = {R.drawable.individual_homepage_1, R.drawable.individual_homepage_2,
                        R.drawable.individual_homepage_3, R.drawable.individual_homepage_4};
                return layoutResourceIndividual;

            case MainActivity.TEM_FINANCE:
                int layoutResourceFinance[] = {R.drawable.finance_homepage_layout_1, R.drawable.finance_homepage_layout_2,
                        R.drawable.finance_homepage_layout_3, R.drawable.finance_homepage_layout_4};
                return layoutResourceFinance;

            case MainActivity.TEM_HEALTH:
                int layoutResourceHealth[] = {R.drawable.health_homepage_layout_1, R.drawable.health_homepage_layout_2,
                        R.drawable.health_homepage_layout_3, R.drawable.health_homepage_layout_4};
                return layoutResourceHealth;

            case MainActivity.TEM_WEDDING:
                int layoutResourceWedding[] = {R.drawable.wedding_homepage_layout_1, R.drawable.wedding_homepage_layout_2,
                        R.drawable.wedding_homepage_layout_3, R.drawable.wedding_homepage_layout_4};
                return layoutResourceWedding;

            case MainActivity.TEM_ENTERTAINMENT:
                int layoutResourceEnt[] = {R.drawable.entertaiment_homepage_layout_1, R.drawable.entertaiment_homepage_layout_2,
                        R.drawable.entertaiment_homepage_layout_3, R.drawable.entertaiment_homepage_layout_4};
                return layoutResourceEnt;

            case MainActivity.TEM_RESTAURANT:
                int layoutResourceResto[] = {R.drawable.restro_homepage_layout_1, R.drawable.restro_homepage_layout_2,
                        R.drawable.restro_homepage_layout_3, R.drawable.restro_homepage_layout_4};
                return layoutResourceResto;

            case MainActivity.TEM_OTHERS:
                int layoutResourceOther[] = {R.drawable.others_homepage_layout_1, R.drawable.others_homepage_layout_2,
                        R.drawable.others_homepage_layout_3, R.drawable.others_homepage_layout_4};
                return layoutResourceOther;

            default:
                int layoutResourceBusinessD[] = {R.drawable.business_homepage_layout1, R.drawable.business_homepage_layout2,
                        R.drawable.business_homepage_layout3, R.drawable.business_homepage_layout4};
                return layoutResourceBusinessD;

        }

    }

    private void startCreateCampaignWithDefaultData(){

        int typeOfTemplateSelected  = dataOfTemplate.getTemplateSelectedByUser();
        int layoutSelected  = dataOfTemplate.getLayoutSelected();
        MainActivity.listOfTemplatePagesObj = new ArrayList<pages>();
        listOfTemplatePagesObjForAddPage = new ArrayList<pages>();


        pages homePageLayout_1 = new HomePageLayout_1();
        pages aboutUsPage = new AboutUsPage();
        pages blogpage = new blogpage();
        pages contactdetails = new contactDetails();
        pages ServicePage = new ServicePage();
        pages clientobj = new ClientPage();
        pages teamPages =new TeamPage();

        homePageLayout_1.getDataForTemplate().setLayoutByServer(layoutSelected);
        homePageLayout_1.getDataForTemplate().setTemplateByServer(typeOfTemplateSelected);



//        abtusObj.setPageIndex(0);
        if (layoutSelected == 0 || layoutSelected == 3) {
            MainActivity.listOfTemplatePagesObj.add(homePageLayout_1);
        }
        MainActivity.listOfTemplatePagesObj.add(aboutUsPage);
        MainActivity.listOfTemplatePagesObj.add(ServicePage);
        MainActivity.listOfTemplatePagesObj.add(clientobj);
        MainActivity.listOfTemplatePagesObj.add(blogpage);
        MainActivity.listOfTemplatePagesObj.add(teamPages);
//        abtusObj.setPageIndex(1);

//        abtusObj.setPageIndex(2);
        MainActivity.listOfTemplatePagesObj.add(contactdetails);
//        abtusObj.setPageIndex(3);

//        abtusObj.setPageIndex(4);

//        abtusObj.setPageIndex(5);

//        abtusObj.setPageIndex(6);


        listOfTemplatePagesObjForAddPage.add(aboutUsPage);
        listOfTemplatePagesObjForAddPage.add(ServicePage);
        listOfTemplatePagesObjForAddPage.add(clientobj);
        //listOfTemplatePagesObjForAddPage.add(homeObj);
        listOfTemplatePagesObjForAddPage.add(blogpage);
        listOfTemplatePagesObjForAddPage.add(teamPages);
        listOfTemplatePagesObjForAddPage.add(contactdetails);








        dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(0).getTemplateData(true);
        data.setEditMode(true);
        data.setIsInUpdateProfileMode(false);
        for (int i=0;i<MainActivity.listOfTemplatePagesObj.size();i++) {
            MainActivity.listOfTemplatePagesObj.get(i).getTemplateData(true).setTemplateByServer(typeOfTemplateSelected);
            MainActivity.listOfTemplatePagesObj.get(i).getTemplateData(true).setLayoutByServer(layoutSelected);

            if (i < 6) {
                listOfTemplatePagesObjForAddPage.get(i).getTemplateData(true).setTemplateByServer(typeOfTemplateSelected);
                listOfTemplatePagesObjForAddPage.get(i).getTemplateData(true).setLayoutByServer(layoutSelected);
            }

        }

        Intent intent = new Intent(getApplicationContext(), FragmenMainActivity.class);

        intent.putExtra("data",data);
        startActivity(intent);


    }

    private class GridViewAdapter extends ArrayAdapter {

        private final Context context;
        private final String[] values;
        int layoutResource[];

        public GridViewAdapter(Context context, String[] values,int layoutResource[]) {
            super(context, R.layout.choose_category_grid_layout, values);
            this.context = context;
            this.values = values;
            this.layoutResource = layoutResource;
        }


        class viewHolder {
            ImageView imgView;
            TextView txtView;

        }

        LayoutInflater inflater;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final viewHolder holderObj;

            if (convertView == null) {

                holderObj = new viewHolder();
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.design_choose_layout_of_template, null);

                holderObj.imgView = (ImageView) convertView.findViewById(R.id.imgvTemplateLayout);
                holderObj.txtView = (TextView) convertView.findViewById(R.id.layoutText);
                holderObj.txtView.setTypeface(myCustomFont);

                convertView.setTag(holderObj);

            } else {
                holderObj = (viewHolder) convertView.getTag();
            }

            holderObj.imgView.setImageResource(layoutResource[position]);
            holderObj.txtView.setText(values[position]);



            return convertView;
        }
    }
}
