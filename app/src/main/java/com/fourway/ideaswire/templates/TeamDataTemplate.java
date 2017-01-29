package com.fourway.ideaswire.templates;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.fourway.ideaswire.ui.FragmentTeamOnApp;
import com.fourway.ideaswire.ui.MainActivity;
import com.fourway.ideaswire.ui.teamonapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vijay on 03-10-2016.
 */
public class TeamDataTemplate extends dataOfTemplate {
    int templateSelected = 1;

    public String title=null;
    public String headerTeam=null;
    public String subHeadingTeam =null;
    public String paraGraphTeam =null;

    public String team_1_name=null;
    public String team_2_name=null;
    public String team_3_name=null;
    public String team_4_name=null;
    public String team_5_name=null;
    public String team_6_name=null;

    public String team_1_title=null;
    public String team_2_title=null;
    public String team_3_title=null;
    public String team_4_title=null;
    public String team_5_title=null;
    public String team_6_title=null;

    public String team_1_image=null;
    public String team_2_image=null;
    public String team_3_image=null;
    public String team_4_image=null;
    public String team_5_image=null;
    public String team_6_image=null;

    public String team_image[];
    public String team_name[];
    public String team_title[];

    final static String TAG = "TeamDataTemplate";

    boolean   ismDefaultData = false;

    public transient List<Drawable> dataOfImageDrawables =new ArrayList<Drawable>();

    public transient  List<Integer> posOfSavedImages = new ArrayList<Integer>();

    public boolean isValInImageList(int val){
        boolean retVal = false;
        int size = posOfSavedImages.size();
        for(int i = 0;i<size;i++ ){
            if(posOfSavedImages.get(i).intValue() == val){
                retVal = true;
            }
        }
        return retVal;

    }

    public TeamDataTemplate(int templateSelected, boolean isDefaultData) {
        if (isDefaultData) {

            initDeafultdata();

        } else {

            // when datasetis not installed
        }

        this.ismDefaultData = isDefaultData;
        this.templateSelected = templateSelected;

        team_image = new String[6];
        team_title = new String[6];
        team_name = new String[6];
    }


    void initDeafultdata(){

        title="Team";
        /*headerTeam = "Totam Aperiam Consect";
        subHeadingTeam = "There are many variation of passages";
        paraGraphTeam = "There are many variation of passage of Lorem" +
                "Ipsum available, but the majority have suffered " +
                "alternation in some form by injected humour";*/
    }



        @Override
    public Class getIntentToLaunchPage() {
        return teamonapp.class;
    }

    @Override
    public Fragment getFragmentToLaunchPage() {
        return new FragmentTeamOnApp();
    }

    @Override
    public int getTemplateSelected() {
        return templateSelected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getHeaderTeam() {
        return headerTeam;
    }

    public void setHeaderTeam(String header) {
        this.headerTeam = header;
    }

    public String getSubHeadingTeam() {
        return subHeadingTeam;
    }

    public void setSubHeadingTeam(String subHeadingTeam) {
        this.subHeadingTeam = subHeadingTeam;
    }

    public String getParaGraphTeam() {
        return paraGraphTeam;
    }

    public void setParaGraphTeam(String paraGraphTeam) {
        this.paraGraphTeam = paraGraphTeam;
    }


    public String getTeamImage(int pos) {
        String url = team_image[pos];
        return url;
    }

    public String getTeamName(int pos) {
        String url = team_name[pos];
        return url;
    }

    public String getTeamTitle(int pos) {
        String url = team_title[pos];
        return url;
    }


    public int getAbsoluteValueOfImagePosition(int position){
        int retVal = position;
        int y = 0,j=0;
        try {
            for (int i = 0; i <= position;) {
                if (team_image[j] != null &&team_image[j].equals(MainActivity.CROSS_BUTTON_HIDE)) {
                    retVal++;
                }else{
                    i++;
                }
                j++;
            }
        }catch(NullPointerException e){
            Log.d(TAG,"getAbsoluteValueOfLogoPosition");
        }

        return retVal;
    }




    public void setTeamImage(String team_url, int pos) {
        team_image[pos] = team_url;
    }

    public void setTeamName(String teamName, int pos) {
        team_name[pos] = teamName;
    }

    public void setTeamTitle(String teamTitle, int pos) {
        team_title[pos] = teamTitle;
    }

    @Override
    public boolean isDefaultDataToCreateCampaign() {
        return ismDefaultData;
    }
}
