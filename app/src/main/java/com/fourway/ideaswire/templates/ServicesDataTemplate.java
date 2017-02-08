package com.fourway.ideaswire.templates;

import android.app.Fragment;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.FragmentServiceOnApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vaibhav Gusain on 9/8/2016.
 */
public class ServicesDataTemplate extends dataOfTemplate {
    int templateSelected = 0;
    int layoutSelected = 0;
    public String title=null;
    public String heading =null;
    public String subHeading =null;
    public String paraGraph = null;
    public String heading_below =null;
    public String subHeading_below =null;
    public String paraGraph_below=null;
    private String urlOfImage =null;
    private List<Integer> resourceDrawableId;

    boolean   ismDefaultData = false;




    //public String website = null;
    public ServicesDataTemplate(int templateSelected, boolean isDefaultData)
    {
        initDefaultResources();
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

    @Override
    public List<Integer> getDefaultDrawableResourceId() {
        initDefaultResources();
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

        title="Service";


        /*heading = "Totam Aperiam Consect";
        subHeading = "There are many variation of passages";
        paraGraph = "There are many variation of passage of Lorem" +
                "Ipsum available, but the majority have suffered " +
                "alternation in some form by injected humour";

        heading_below = "Totam Aperiam Consect";
        subHeading_below = "There are many variation of passages";
        getParaGraph_below = "There are many variation of passage of Lorem" +
                "Ipsum available, but the majority have suffered " +
                "alternation in some form by injected humour";*/

    }

    void initDefaultResources() {
//        int temSel =  getTemplateSelected();
        resourceDrawableId = new ArrayList<>();

        switch (templateSelected){
            case 0:
                resourceDrawableId.add(R.drawable.business_services_banner);
                break;
            case 1:
                resourceDrawableId.add(R.drawable.individual_services_banner);
                break;
            case 2:
                resourceDrawableId.add(R.drawable.finance_services_banner);
                break;
            case 3:
                resourceDrawableId.add(R.drawable.health_services_banner);
                break;
            case 4:
                resourceDrawableId.add(R.drawable.entertainment_services_banner);
                break;
            case 5:
                resourceDrawableId.add(R.drawable.information_services_banner);
                break;
            case 6:
                resourceDrawableId.add(R.drawable.wedding_services_banner);
                break;
            case 7:
                resourceDrawableId.add(R.drawable.restaurant_services_banner);
                break;
            case 8:
                resourceDrawableId.add(R.drawable.others_services_banner);
                break;
            default:
                resourceDrawableId.add(R.drawable.business_services_banner);
                break;

        }
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
        return paraGraph_below;
    }

    public void setGetParaGraph_below(String getParaGraph_below) {
        this.paraGraph_below = getParaGraph_below;
    }

    public String getUrlOfImage() {
        return urlOfImage;
    }

    public void setUrlOfImage(String urlOfImage) {
        this.urlOfImage = urlOfImage;
    }



    @Override
    public Fragment getFragmentToLaunchPage() {
        return new FragmentServiceOnApp();
    }



}
