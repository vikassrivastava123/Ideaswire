package com.fourway.ideaswire.templates;

import android.app.Fragment;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.FragmentAboutUsOnApp;
import com.fourway.ideaswire.ui.MainActivity;
import com.fourway.ideaswire.ui.home_page_onapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ritika on 8/24/2016.
 */
public class AboutUsDataTemplate extends dataOfTemplate{

    int templateSelected = 0;
    int layoutSelected = 0;



    private String title=null;
    private String heading=null;
    private String subHeading=null;
    private String paraGraph=null;
    private String urlOfImage_1 =null;
    private String urlOfImage_2 =null;
    private List<Integer> resourceDrawableId;

    boolean   ismDefaultData = false;
    public AboutUsDataTemplate(int templateSelected, boolean isDefaultData){

        resourceDrawableId = new ArrayList<>();
        initDefaultResources();

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

    @Override
    public List<Integer> getDefaultDrawableResourceId() {
        return resourceDrawableId;
    }

    @Override
    public void setTemplateByServer(int temp) {
        this.templateSelected = temp;
    }

    @Override
    public int getTemplateByServer() {
        return templateSelected;
    }

    @Override
    public int getLayoutByServer() {
        return layoutSelected;
    }

    @Override
    public void setLayoutByServer(int layout) {
        this.layoutSelected = layout;
    }

    void initDeafultdata(){

        title="About Us";
        /*heading = "Toatam Aperiam Consect";
        subHeading = "There are many variation of passages";
        paraGraph= "This is custom random para to generate space dont read ti its useless";*/
    }

    void initDefaultResources() {
        int temSel =  getTemplateSelected();

        switch (temSel){
            case MainActivity.TEM_BUSINESS:
                resourceDrawableId.add(R.drawable.business_about_banner);
                break;
            case MainActivity.TEM_INDIVIDUAL:
                resourceDrawableId.add(R.drawable.individual_about_banner);
                break;
            case MainActivity.TEM_FINANCE:
                resourceDrawableId.add(R.drawable.finance_about_banner);
                break;
            case MainActivity.TEM_HEALTH:
                resourceDrawableId.add(R.drawable.health_about_banner);
                break;
            case MainActivity.TEM_ENTERTAINMENT:
                resourceDrawableId.add(R.drawable.entertainment_about_banner);
                break;
            case MainActivity.TEM_INFORMATION:
                resourceDrawableId.add(R.drawable.information_about_banner);
                break;
            case MainActivity.TEM_WEDDING:
                resourceDrawableId.add(R.drawable.wedding_about_banner);
                break;
            case MainActivity.TEM_RESTAURANT:
                resourceDrawableId.add(R.drawable.restaurant_about_banner);
                break;
            case MainActivity.TEM_OTHERS:
                resourceDrawableId.add(R.drawable.others_about_banner);
                break;
            default:
                resourceDrawableId.add(R.drawable.business_about_banner);
                break;

        }
    }

    @Override
    public Class getIntentToLaunchPage() {
        return home_page_onapp.class;
    }

    @Override
    public Fragment getFragmentToLaunchPage() {
        return new FragmentAboutUsOnApp();
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
