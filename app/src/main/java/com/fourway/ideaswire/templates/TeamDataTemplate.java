package com.fourway.ideaswire.templates;

import android.app.Fragment;

import com.fourway.ideaswire.ui.teamonapp;

/**
 * Created by Vijay on 03-10-2016.
 */
public class TeamDataTemplate extends dataOfTemplate {
    int templateSelected = 1;

    public String title=null;
    public String header=null;
    public String subheading_header =null;
    public String heading_text =null;

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
    boolean   ismDefaultData = false;

    public TeamDataTemplate(int templateSelected, boolean isDefaultData) {
        if (isDefaultData) {

            initDeafultdata();

        } else {

            // when datasetis not installed
        }

        this.ismDefaultData = isDefaultData;
        this.templateSelected = templateSelected;
    }

    void initDeafultdata(){

        title="Team";
        header = "Totam Aperiam Consect";
        subheading_header = "There are many variation of passages";
        heading_text = "There are many variation of passage of Lorem" +
                "Ipsum available, but the majority have suffered " +
                "alternation in some form by injected humour";
    }



        @Override
    public Class getIntentToLaunchPage() {
        return teamonapp.class;
    }

    @Override
    public Fragment getFragmentToLaunchPage() {
        return null;
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

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public void setHeader(String header) {
        this.header = header;
    }

    public String getSubheading_header() {
        return subheading_header;
    }

    public void setSubheading_header(String subheading_header) {
        this.subheading_header = subheading_header;
    }

    public String getHeading_text() {
        return heading_text;
    }

    public void setHeading_text(String heading_text) {
        this.heading_text = heading_text;
    }

    public String getTeam_1_name() {
        return team_1_name;
    }

    public void setTeam_1_name(String team_1_name) {
        this.team_1_name = team_1_name;
    }

    public String getTeam_2_name() {
        return team_2_name;
    }

    public void setTeam_2_name(String team_2_name) {
        this.team_2_name = team_2_name;
    }

    public String getTeam_3_name() {
        return team_3_name;
    }

    public void setTeam_3_name(String team_3_name) {
        this.team_3_name = team_3_name;
    }

    public String getTeam_4_name() {
        return team_4_name;
    }

    public void setTeam_4_name(String team_4_name) {
        this.team_4_name = team_4_name;
    }

    public String getTeam_5_name() {
        return team_5_name;
    }

    public void setTeam_5_name(String team_5_name) {
        this.team_5_name = team_5_name;
    }

    public String getTeam_6_name() {
        return team_6_name;
    }

    public void setTeam_6_name(String team_6_name) {
        this.team_6_name = team_6_name;
    }

    public String getTeam_1_title() {
        return team_1_title;
    }

    public void setTeam_1_title(String team_1_title) {
        this.team_1_title = team_1_title;
    }

    public String getTeam_2_title() {
        return team_2_title;
    }

    public void setTeam_2_title(String team_2_title) {
        this.team_2_title = team_2_title;
    }

    public String getTeam_3_title() {
        return team_3_title;
    }

    public void setTeam_3_title(String team_3_title) {
        this.team_3_title = team_3_title;
    }

    public String getTeam_4_title() {
        return team_4_title;
    }

    public void setTeam_4_title(String team_4_title) {
        this.team_4_title = team_4_title;
    }

    public String getTeam_5_title() {
        return team_5_title;
    }

    public void setTeam_5_title(String team_5_title) {
        this.team_5_title = team_5_title;
    }

    public String getTeam_6_title() {
        return team_6_title;
    }

    public void setTeam_6_title(String team_6_title) {
        this.team_6_title = team_6_title;
    }

    public String getTeam_1_image() {
        return team_1_image;
    }

    public void setTeam_1_image(String team_1_image) {
        this.team_1_image = team_1_image;
    }

    public String getTeam_2_image() {
        return team_2_image;
    }

    public void setTeam_2_image(String team_2_image) {
        this.team_2_image = team_2_image;
    }

    public String getTeam_3_image() {
        return team_3_image;
    }

    public void setTeam_3_image(String team_3_image) {
        this.team_3_image = team_3_image;
    }

    public String getTeam_4_image() {
        return team_4_image;
    }

    public void setTeam_4_image(String team_4_image) {
        this.team_4_image = team_4_image;
    }

    public String getTeam_5_image() {
        return team_5_image;
    }

    public void setTeam_5_image(String team_5_image) {
        this.team_5_image = team_5_image;
    }

    public String getTeam_6_image() {
        return team_6_image;
    }

    public void setTeam_6_image(String team_6_image) {
        this.team_6_image = team_6_image;
    }

    @Override
    public boolean isDefaultDataToCreateCampaign() {
        return ismDefaultData;
    }
}
