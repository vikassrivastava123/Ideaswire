package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.AboutUsOnApp;

/**
 * Created by Ritika on 8/24/2016.
 */
public class AboutUsDataTemplate extends dataOfTemplate{

    int templateSelected = 1;

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

    boolean   ismDefaultData = false;

    public AboutUsDataTemplate(int templateSelected, boolean isDefaultData){

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

    void setDataReceivedServer(){

    }

    void initDeafultdata(){

        title = "Business";
        headerAboutUs = "Toatam Aperiam Consect";
        sub_header = "There are many variation of passages";
        text_para = "There are many variation of passage " +
                "Lorem Ipsum available but the majority have" +
                " in some form bye inject humour";
        button_text = "BUTTON TEXT";

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
    public int getTemplateSelected() {
        return templateSelected;
    }



}
