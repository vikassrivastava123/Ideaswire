package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.UploadImageForUrlData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.UploadImageForUrlRequest;
import com.fourway.ideaswire.templates.TeamDataTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 4way on 25-10-2016.
 */
public class FragmentTeamOnApp extends Fragment implements View.OnClickListener, FragmenMainActivity.viewCampaign, UploadImageForUrlRequest.UploadImageForUrlCallback {

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
            title.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }

        if (heading!=null){
            heading.setEnabled(true);
            heading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }


        if (subHeading!=null){
            subHeading.setEnabled(true);
            subHeading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (paraGraph!=null){
            paraGraph.setEnabled(false);
            paraGraph.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);

        dataObj=(TeamDataTemplate)((FragmenMainActivity)getActivity()).getDatObject();

        title=(EditText)view.findViewById(R.id.Team_TITLE);
        heading=(EditText)view.findViewById(R.id.HeadingTeam);
        subHeading=(EditText)view.findViewById(R.id.SubHeadingTeam);
        paraGraph=(EditText)view.findViewById(R.id.paraGraphTeam);

        title.setText(dataObj.getTitle());
        heading.setText(dataObj.getHeader());
        subHeading.setText(dataObj.getSubheading_header());
        paraGraph.setText(dataObj.getHeading_text());

        deleteTitle=(ImageView)view.findViewById(R.id.deleteTitleTeam);
        deleteHeading =(ImageView)view.findViewById(R.id.deleteHeadingTeam);
        deleteSubHeading =(ImageView)view.findViewById(R.id.deleteSubHeadingTeam);
        deletePara =(ImageView)view.findViewById(R.id.deleteParaGraphTeam);

        deleteTitle.setOnClickListener(this);
        deleteHeading.setOnClickListener(this);
        deleteSubHeading.setOnClickListener(this);
        deletePara.setOnClickListener(this);

        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        gridView = (GridView) view.findViewById(R.id.gridView);

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


        final GridViewAdapter gridViewAdapter=new GridViewAdapter(getActivity(),android.R.layout.simple_list_item_1,memberImageUrl,memberName, memberTitle);

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


        return view;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteTitleTeam:
                title.setVisibility(View.GONE);
                deleteTitle.setVisibility(View.GONE);
                break;
            case R.id.deleteHeadingTeam:
                heading.setVisibility(View.GONE);
                deleteHeading.setVisibility(View.GONE);
                break;
            case R.id.deleteSubHeadingTeam:
                subHeading.setVisibility(View.GONE);
                deleteSubHeading.setVisibility(View.GONE);
                break;
            case R.id.deleteParaGraphTeam:
                paraGraph.setVisibility(View.GONE);
                deletePara.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {

    }

    @Override
    public void init_ViewCampaign() {

    }

    @Override
    public void addLastPage() {

    }
}
