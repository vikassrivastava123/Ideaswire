package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.TeamDataTemplate;
import com.fourway.ideaswire.templates.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class teamonapp extends Activity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    TextView mTitle,choose_cat;

    public String TAG="teamonapp";

    EditText title=null,
    heading=null,
    subHeading=null,
    paraGraph=null;

    ImageView deleteTeamImg;
    RelativeLayout teamImgLayout;

    ImageView deleteTitle=null,
    deleteHeading=null,
    deleteSubHeading=null,
    deletePara=null;

    private boolean showPreview = false;
    TeamDataTemplate dataObj;

    void init_viewCampaign(){
        try {

            deleteTitle.setVisibility(View.GONE);
            deleteHeading.setVisibility(View.GONE);
            deleteSubHeading.setVisibility(View.GONE);
            deletePara.setVisibility(View.GONE);
        }catch (NullPointerException e)
        {
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if (title!=null){
            title.setEnabled(false);
            title.setKeyListener(null);
        }

        if (heading!=null){
            heading.setEnabled(false);
            heading.setKeyListener(null);
        }


        if (subHeading!=null){
            subHeading.setEnabled(false);
            subHeading.setKeyListener(null);
        }
        if (paraGraph!=null){
            paraGraph.setEnabled(false);
            paraGraph.setKeyListener(null);
        }
    }

    void init_editCampaign(){
        try {

            deleteTitle.setVisibility(View.VISIBLE);
            deleteHeading.setVisibility(View.VISIBLE);
            deleteSubHeading.setVisibility(View.VISIBLE);
            deletePara.setVisibility(View.VISIBLE);
        }catch (NullPointerException e)
        {
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if (title!=null){
            title.setEnabled(true);
            title.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }

        if (heading!=null){
            heading.setEnabled(true);
            heading.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }


        if (subHeading!=null){
            subHeading.setEnabled(true);
            subHeading.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
        if (paraGraph!=null){
            paraGraph.setEnabled(false);
            paraGraph.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamonapp);

        dataObj=(TeamDataTemplate)getIntent().getSerializableExtra("data");

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dynamicPages);
        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                int size = MainActivity.listOfTemplatePagesObj.size();
                Button[] btn = new Button[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(teamonapp.this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT));
                for(pages obj: MainActivity.listOfTemplatePagesObj) {
                    String name = obj.nameis();



                    float x =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                    float y =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());

                    LinearLayout.LayoutParams buttonLayoutParams =
                            new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                                    (int)x,
                                    (int)y));
                    buttonLayoutParams.setMargins(2,2, 0, 0);
                    btn[i] = new Button(teamonapp.this);

                    btn[i].setLayoutParams(buttonLayoutParams);
                    btn[i].setText(name);
                    btn[i].setId(i);
                    btn[i].setBackgroundColor(getResources().getColor(R.color.card));
                    btn[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //Toast.makeText(getApplicationContext(),
                            //       "button is clicked" + v.getId(), Toast.LENGTH_LONG).show();
                     /*       dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(v.getId()).getTemplateData(1);

                            Class intenetToLaunch = data.getIntentToLaunchPage();
                            Log.v(TAG, "5" + intenetToLaunch);
                            Intent intent = new Intent(getApplicationContext(), intenetToLaunch);
                            intent.putExtra("data",data);
                            startActivity(intent);*/
                        }
                    });
                    row.addView(btn[i]);
                    // Add the LinearLayout element to the ScrollView
                    i++;
                }
                //btn[6].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));
                //btn[6].setFocusable(true);
                // When adding another view, make sure you do it on the UI
                // thread.
                layout.post(new Runnable() {

                    public void run() {

                        layout.addView(row);
                    }
                });
            }
        }, 500);


        title=(EditText)findViewById(R.id.Team_TITLE);
        heading=(EditText)findViewById(R.id.HeadingTeam);
        subHeading=(EditText)findViewById(R.id.SubHeadingTeam);
        paraGraph=(EditText)findViewById(R.id.paraGraphTeam);

        title.setText(dataObj.getTitle());
        heading.setText(dataObj.getHeader());
        subHeading.setText(dataObj.getSubheading_header());
        paraGraph.setText(dataObj.getHeading_text());

        deleteTitle=(ImageView)findViewById(R.id.deleteTitleTeam);
        deleteHeading =(ImageView)findViewById(R.id.deleteHeadingTeam);
        deleteSubHeading =(ImageView)findViewById(R.id.deleteSubHeadingTeam);
        deletePara =(ImageView)findViewById(R.id.deleteParaGraphTeam);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });
        gridView = (GridView) findViewById(R.id.gridView);

        final List<String> memberImageUrl=new ArrayList<>();
        final List<String> memberName=new ArrayList<>();
        final List<String> memberTitle =new ArrayList<>();



        memberName.add(dataObj.getTeam_1_name());
        memberName.add(dataObj.getTeam_2_name());
        memberName.add(dataObj.getTeam_3_name());
        memberName.add(dataObj.getTeam_4_name());
        memberName.add(dataObj.getTeam_5_name());
        memberName.add(dataObj.getTeam_6_name());

        memberTitle.add(dataObj.getTeam_1_title());
        memberTitle.add(dataObj.getTeam_2_title());
        memberTitle.add(dataObj.getTeam_3_title());
        memberTitle.add(dataObj.getTeam_4_title());
        memberTitle.add(dataObj.getTeam_5_title());
        memberTitle.add(dataObj.getTeam_6_title());

        memberImageUrl.add(dataObj.getTeam_1_image());
        memberImageUrl.add(dataObj.getTeam_2_image());
        memberImageUrl.add(dataObj.getTeam_3_image());
        memberImageUrl.add(dataObj.getTeam_4_image());
        memberImageUrl.add(dataObj.getTeam_5_image());
        memberImageUrl.add(dataObj.getTeam_6_image());


        final GridViewAdapter gridViewAdapter=new GridViewAdapter(this,android.R.layout.simple_list_item_1,memberImageUrl,memberName, memberTitle);

        gridView.setAdapter(gridViewAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if(v.getId()==R.id.deleteTeamImage){
                    memberImageUrl.remove(position);
                    memberName.remove(position);
                    memberTitle.remove(position);

                    gridView.setAdapter(gridViewAdapter);
                }


            }});



    }

    private class GridViewAdapter extends ArrayAdapter {

        Context context;
        List<String> memberImageUrl=new ArrayList<>();
        List<String> memberNameList=new ArrayList<>();
        List<String> memberTitleList =new ArrayList<>();

        public GridViewAdapter(Context context, int resource,List<String> memberImageUrl,List<String> memberNameList,List<String> memberPositionList) {
            super(context, resource,memberImageUrl);

            this.context=context;
            this.memberImageUrl=memberImageUrl;
            this.memberNameList=memberNameList;
            this.memberTitleList =memberPositionList;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            View v=LayoutInflater.from(context).inflate(R.layout.team_member_grid,null);
            NetworkImageView memberImg=(NetworkImageView) v.findViewById(R.id.memberImage);
            ImageView teamStaticImg = (ImageView)v.findViewById(R.id.Team_STATIC_img);

            teamImgLayout =(RelativeLayout)v.findViewById(R.id.team_img_layout);
            deleteTeamImg =(ImageView)v.findViewById(R.id.deleteTeamImage);

            TextView memberName =(TextView)v.findViewById(R.id.memberName);
            TextView memberPosition =(TextView)v.findViewById(R.id.memberPostion);


            deleteTeamImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView)parent).performItemClick(v, position,0);
                }
            });

            if(memberNameList.get(position)!=null)
            memberName.setText(memberNameList.get(position));

            if(memberTitleList.get(position)!=null)
            memberPosition.setText(memberTitleList.get(position));

            if( memberImageUrl.get(position)!=null)
            {
                Uri cardImageUri = Uri.parse(memberImageUrl.get(position));
                memberImg.setImageURI(cardImageUri);
                teamStaticImg.setVisibility(View.GONE);
            }
            else {
                memberImg.setVisibility(View.GONE);
                teamStaticImg.setImageResource(R.drawable.member_1);
            }


            return v;
        }
    }


    public void previewTemplate(View view) {
        TextView textViewShowPreview = (TextView)findViewById(R.id.textShow_preview);

        FloatingActionButton btn  = (FloatingActionButton)findViewById(R.id.floatingForPreview);
        Button showPreviewBtn  = (Button)findViewById(R.id.showPreview);


        if(showPreview == false) {
            textViewShowPreview.setText("Edit");
            btn.show();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_edit);
            init_viewCampaign();
            showPreview = true;
        }else {
            textViewShowPreview.setText("Preview");
            btn.hide();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_about);
            init_editCampaign();
            showPreview = false;
        }

    }

    public void pageTemplate(View view) {
        startActivity(new Intent(getApplicationContext(),about_us_page_template.class));
    }

    public void deleteTitleTeam(View view){
        title.setVisibility(View.GONE);
        deleteTitle.setVisibility(View.GONE);
    }

    public void deleteHeadingTeam(View view){
        heading.setVisibility(View.GONE);
        deleteHeading.setVisibility(View.GONE);
    }

    public void deleteSubHeadingTeam(View view){
        subHeading.setVisibility(View.GONE);
        deleteSubHeading.setVisibility(View.GONE);
    }

    public void deleteParaGraphTeam(View view){
        paraGraph.setVisibility(View.GONE);
        deletePara.setVisibility(View.GONE);
    }



}
