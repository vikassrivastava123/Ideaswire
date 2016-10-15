package com.fourway.ideaswire.templates;

import android.app.Fragment;

import com.fourway.ideaswire.ui.ServicesOnApp;

/**
 * Created by Vaibhav Gusain on 9/8/2016.
 */
public class ServicesDataTemplate extends dataOfTemplate {
    int templateSelected = 1;

    public String title=null;
    public String heading =null;
    public String subHeading =null;
    public String paraGraph = null;
    public String heading_below =null;
    public String subHeading_below =null;
    public String getParaGraph_below=null;
    private String urlOfImage =null;

    boolean   ismDefaultData = false;




    //public String website = null;
    public ServicesDataTemplate(int templateSelected, boolean isDefaultData)
    {
        if(isDefaultData){

            initDeafultdata();

        }else{

            // when datasetis not installed
        }

        ismDefaultData= isDefaultData;
        this.templateSelected = templateSelected;

    }

    public boolean isDefaultDataToCreateCampaign(){

        return ismDefaultData;
    }

    void initDeafultdata(){

        title="Service";
        heading = "Totam Aperiam Consect";
        subHeading = "There are many variation of passages";
        paraGraph = "There are many variation of passage of Lorem" +
                "Ipsum available, but the majority have suffered " +
                "alternation in some form by injected humour";

        heading_below = "Totam Aperiam Consect";
        subHeading_below = "There are many variation of passages";
        getParaGraph_below = "There are many variation of passage of Lorem" +
                "Ipsum available, but the majority have suffered " +
                "alternation in some form by injected humour";

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

    public String getHeading_below() {
        return heading_below;
    }

    public void setHeading_below(String heading_below) {
        this.heading_below = heading_below;
    }

    public String getSubHeading_below() {
        return subHeading_below;
    }

    public void setSubHeading_below(String subHeading_below) {
        this.subHeading_below = subHeading_below;
    }

    public String getGetParaGraph_below() {
        return getParaGraph_below;
    }

    public void setGetParaGraph_below(String getParaGraph_below) {
        this.getParaGraph_below = getParaGraph_below;
    }

    public String getUrlOfImage() {
        return urlOfImage;
    }

    public void setUrlOfImage(String urlOfImage) {
        this.urlOfImage = urlOfImage;
    }


    @Override
    public Class getIntentToLaunchPage() {
        return ServicesOnApp.class;
    }

    @Override
    public Fragment getFragmentToLaunchPage() {
        return null;
    }

    @Override
    public int getTemplateSelected() {
        return templateSelected;
    }

}
