package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.fourway.ideaswire.templates.HomePage;
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
    void startModifyTemplate(int typeOfTemplateSelected){


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_layout_of_template);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
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
        mTitle.setTypeface(mycustomFont);
        t.setTypeface(mycustomFont);
        gridView = (GridView) findViewById(R.id.gridViewForSelectLayout);

        String[] values = new String[] { "business", "entertainment", "finance",
                "health"};

        // use your custom layout
        ArrayAdapter<String> adapter = new GridViewAdapter<String>(this,
                values);
        gridView.setAdapter(adapter);

        ////
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Log.v(TAG, "CL hi"+position);
                //TODO: uncomment code after test
                //startModifyTemplate(position);
                //Toast.makeText(this,"Press Start Now", Toast.LENGTH_LONG).show();
                 dataOfTemplate.setLayoutSelected(position);

               startCreateCampaignWithDefaultData();

            }});

        ////

    }

    private void startCreateCampaignWithDefaultData(){

        int typeOfTemplateSelected  = dataOfTemplate.getTemplateSelectedByUser();
        MainActivity.listOfTemplatePagesObj = new ArrayList<pages>();
        listOfTemplatePagesObjForAddPage = new ArrayList<pages>();


        pages abtusObj = new AboutUsPage();
        pages homeObj = new HomePage();
        pages blogpage = new blogpage();
        pages contactdetails = new contactDetails();
        pages ServicePage = new ServicePage();
        pages clientobj = new ClientPage();
        pages teamPages =new TeamPage();




        MainActivity.listOfTemplatePagesObj.add(0, abtusObj);
        abtusObj.setPageIndex(0);
        MainActivity.listOfTemplatePagesObj.add(1, homeObj);
        abtusObj.setPageIndex(1);
        MainActivity.listOfTemplatePagesObj.add(2, blogpage);
        abtusObj.setPageIndex(2);
        MainActivity.listOfTemplatePagesObj.add(3, contactdetails);
        abtusObj.setPageIndex(3);
        MainActivity.listOfTemplatePagesObj.add(4, ServicePage);
        abtusObj.setPageIndex(4);
        MainActivity.listOfTemplatePagesObj.add(5, clientobj);
        abtusObj.setPageIndex(5);
        MainActivity.listOfTemplatePagesObj.add(6, teamPages);
        abtusObj.setPageIndex(6);


        listOfTemplatePagesObjForAddPage.add(0, abtusObj);
        listOfTemplatePagesObjForAddPage.add(1, homeObj);
        listOfTemplatePagesObjForAddPage.add(2, blogpage);
        listOfTemplatePagesObjForAddPage.add(3, contactdetails);
        listOfTemplatePagesObjForAddPage.add(4, ServicePage);
        listOfTemplatePagesObjForAddPage.add(5, clientobj);
        listOfTemplatePagesObjForAddPage.add(6, teamPages);



        dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(0).getTemplateData(typeOfTemplateSelected, true);
        data.setEditMode(true);
        data.setIsInUpdateProfileMode(false);
//        Class intenetToLaunch = data.getIntentToLaunchPage();
//        Log.v("Create homepage", "5" + intenetToLaunch);
//        Intent intent = new Intent(getApplicationContext(), intenetToLaunch);
//
//        //Intent intent = new Intent(getApplicationContext(), FragmenMainActivity.class);
//        intent.putExtra("data",data);
//        startActivity(intent);

        Intent intent = new Intent(getApplicationContext(), FragmenMainActivity.class);

        intent.putExtra("data",data);
        startActivity(intent);


    }

    private class GridViewAdapter<String> extends ArrayAdapter<String> {

        private final Context context;
        private final String[] values;
        int layoutResource[] = {R.drawable.homepage_layout1, R.drawable.homepage_layout2, R.drawable.homepage_layout3, R.drawable.homepage_layout4 };

        public GridViewAdapter(Context context, String[] values) {
            super(context, R.layout.choose_category_grid_layout, values);
            this.context = context;
            this.values = values;
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

                convertView.setTag(holderObj);

            } else {
                holderObj = (viewHolder) convertView.getTag();
            }

            holderObj.imgView.setImageResource(layoutResource[position]);

            Log.v("chooseLayoutAdapter", "position" + position + " " + values[position]);
            switch (position) {
                case 0:
                    holderObj.txtView.setText("Layout 1");
                    break;
                case 1:
                    holderObj.txtView.setText("Layout 2");
                    break;
                case 2:
                    holderObj.txtView.setText("Layout 3");
                    break;
                case 3:
                    holderObj.txtView.setText("Layout 4");
                    break;
            }


            return convertView;
        }
    }
}
