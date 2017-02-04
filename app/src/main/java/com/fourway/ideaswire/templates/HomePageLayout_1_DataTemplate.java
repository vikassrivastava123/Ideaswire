package com.fourway.ideaswire.templates;

import android.app.Fragment;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.AboutUsOnApp;
import com.fourway.ideaswire.ui.FragmentHomeLayout_1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ritika on 8/24/2016.
 */
public class HomePageLayout_1_DataTemplate extends dataOfTemplate{

    int templateSelected = 0;
    int layoutSelected = 0;

    public String headerAboutUs = null;
    public String title =null;
    private String urlOfImage =null;
    public String header2 =null;
    public String sub_header = null;
    public String text_para =null;
    public String button_text =null;
    public String link_button = null;
    private int pageNumberInList = 0;
    private String btnUrl = null;
    private List<Integer> resourceDrawableId;

    boolean   ismDefaultData = false;

    public HomePageLayout_1_DataTemplate(int templateSelected, boolean isDefaultData){

        if(isDefaultData){
            resourceDrawableId = new ArrayList<>();
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

    void setDataReceivedServer(){

    }

    void initDeafultdata(){


        int temSel =  getTemplateSelected();

        switch (temSel){
            case 0:
                title = "Business";
                resourceDrawableId.add(R.drawable.business_home_banner);
                break;
            case 1:
                title = "Individual";
                resourceDrawableId.add(R.drawable.individual_home_banner);
                break;
            case 2:
                title = "Finance";
                resourceDrawableId.add(R.drawable.finance_home_banner);
                break;
            case 3:
                title = "Health";
                resourceDrawableId.add(R.drawable.health_home_banner);
                break;
            case 4:
                title = "Entertainment";
                resourceDrawableId.add(R.drawable.entertainment_home_banner);
                break;
            case 5:
                title = "Information";
                resourceDrawableId.add(R.drawable.information_home_banner);
                break;
            case 6:
                title = "Wedding";
                resourceDrawableId.add(R.drawable.wedding_home_banner);
                break;
            case 7:
                title = "Restaurant";
                resourceDrawableId.add(R.drawable.restaurant_home_banner);
                break;
            case 8:
                title = "Others";
                resourceDrawableId.add(R.drawable.others_home_banner);
                break;
            default:
                title = "Business";
                resourceDrawableId.add(R.drawable.business_home_banner);
                break;

        }

      /*  headerAboutUs = "Totam Aperiam Consect";
        sub_header = "There are many variation of passages";
        text_para = "There are many variation of passage " +
                "Lorem Ipsum available but the majority have" +
                " in some form bye inject humour";
        button_text = "BUTTON TEXT";*/

    }


    public String get_title(){
        return title;
    }

    public void set_title(String atr){
        title = atr;
    }

    public void set_url(String atr){
        urlOfImage = atr;
    }

    public String get_url(){
        return  urlOfImage;
    }

    public String get_heading(){
        return headerAboutUs;
    }

    public void set_heading(String atr){
        headerAboutUs = atr;
    }


    public String get_sub_heading(){
        return sub_header;
    }

    public void set_sub_heading(String atr){
        sub_header = atr;
    }
    public String get_text_para(){
        return text_para;
    }

    public void set_text_para(String atr){
       text_para = atr;
    }

    public String get_button_text(){
        return button_text;
    }

    public void set_button_text(String atr){
        button_text = atr;
    }

    public void set_submit_button_link(int pageNumberInList){
        this.pageNumberInList = pageNumberInList;

    }

    public int get_submit_button_link(){
     //   dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(pageNumberInList).getTemplateData(1);
        return pageNumberInList;

    }

    public void set_buttonUrl(String url){
        btnUrl = url;
    }

    public String get_buttonUrl(){
        return btnUrl;
    }

    @Override
    public Class getIntentToLaunchPage() {
       return AboutUsOnApp.class;
    }

    @Override

    public Fragment getFragmentToLaunchPage() {
        return  new FragmentHomeLayout_1();
    }


}
