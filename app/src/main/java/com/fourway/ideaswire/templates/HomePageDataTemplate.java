package com.fourway.ideaswire.templates;

import android.app.Fragment;

import com.fourway.ideaswire.ui.FragmentHomeOnApp;
import com.fourway.ideaswire.ui.home_page_onapp;

/**
 * Created by Ritika on 8/24/2016.
 */
public class HomePageDataTemplate extends dataOfTemplate{

    int templateSelected = 1;



    private String title=null;
    private String heading=null;
    private String subHeading=null;
    private String paraGraph=null;
    private String urlOfImage_1 =null;
    private String urlOfImage_2 =null;

    boolean   ismDefaultData = false;
    public HomePageDataTemplate(int templateSelected,boolean isDefaultData){

        if(isDefaultData){
            initDeafultdata();
        }else{
            // when datasetis not installed
        }
        ismDefaultData = isDefaultData;
        this.templateSelected = templateSelected;


    }

    public boolean isDefaultDataToCreateCampaign(){

        return ismDefaultData;
    }

    void initDeafultdata(){
        title="Home";
        /*heading = "Toatam Aperiam Consect";
        subHeading = "There are many variation of passages";
        paraGraph= "This is custom random para to generate space dont read ti its useless";*/
    }

    @Override
    public Class getIntentToLaunchPage() {
        return home_page_onapp.class;
    }

    @Override
    public Fragment getFragmentToLaunchPage() {
        return new FragmentHomeOnApp();
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

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getParaGraph() {
        return paraGraph;
    }

    public void setParaGraph(String paraGraph) {
        this.paraGraph = paraGraph;
    }

    public String getUrlOfImage_1() {
        return urlOfImage_1;
    }
    public String getUrlOfImage_2() {
        return urlOfImage_2;
    }

    public void setUrlOfImage_1(String urlOfImage_1) {
        this.urlOfImage_1 = urlOfImage_1;
    }
    public void setUrlOfImage_2(String urlOfImage_2) {
        this.urlOfImage_2 = urlOfImage_2;
    }


}
