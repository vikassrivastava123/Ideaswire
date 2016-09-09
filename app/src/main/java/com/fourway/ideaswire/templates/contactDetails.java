package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.AboutUsOnApp;

/**
 * Created by Vaibhav Gusain on 9/8/2016.
 */
public class contactDetails  extends dataOfTemplate{

    int templateSelected = 1;
    String about_us_heading = null;
    public String header =null;
    public String header2 =null;
    public String sub_header = null;
    public String text_para =null;
    public String button_text =null;
    public contactDetails(int templateSelected, boolean isDefaultData)
    {
        if(isDefaultData){

            initDeafultdata();

        }else{

            // when datasetis not installed
        }



        setHeader("About All of Us");
        set_profile_page_about_us_heading("Profile_Page_About_US_Heading ");
        this.templateSelected = templateSelected;

    }

    void initDeafultdata(){

        header = new String("Heading one");
        header2 = "Below Image Heading";
        sub_header = "Subheading";
        text_para = "paragraph text is soo long man.what to do .";
        button_text = "Button textc";

    }


    public void set_profile_page_about_us_heading(String header){

        about_us_heading = header;
    }

    public String get_profile_page_about_us_heading(){
        return about_us_heading;
    }
    public String get_heading(){
        return header;
    }
    public String get_heading2(){
        return header2;
    }
    public String get_sub_heading(){
        return sub_header;
    }
    public String get_text_para(){
        return text_para;
    }
    public String get_button_text(){
        return button_text;
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
