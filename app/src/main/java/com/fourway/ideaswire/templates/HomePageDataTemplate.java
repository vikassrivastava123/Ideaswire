package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.AboutUsOnApp;
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
    private String urlOfImage =null;

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
        heading = "Toatam Aperiam Consect";
        subHeading = "There are many variation of passages";
        paraGraph= "This is custom random para to generate space dont read ti its useless";
    }

    @Override
    public Class getIntentToLaunchPage() {
        return home_page_onapp.class;
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

    public String getUrlOfImage() {
        return urlOfImage;
    }

    public void setUrlOfImage(String urlOfImage) {
        this.urlOfImage = urlOfImage;
    }


}
