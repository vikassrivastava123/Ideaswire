package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.ServicesOnApp;

/**
 * Created by Vaibhav Gusain on 9/8/2016.
 */
public class ServicesDataTemplate extends dataOfTemplate {
    int templateSelected = 1;
    String about_us_heading;
    String heading= null;
    public String subheading_header =null;
    public String heading_text =null;
    public String heading_below_image = null;
    public String sub_heading_below_image =null;
    public String text_below_image =null;
    //public String website = null;
    public ServicesDataTemplate(int templateSelected, boolean isDefaultData)
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
        subheading_header = "Below Image Heading";
        heading_text = "Subheading";
        heading_below_image = "paragraph text is soo long man.what to do .";
        sub_heading_below_image = "Button textc";
        text_below_image = "+91-8800664433";

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
    public String get_subheading(){
        return subheading_header;
    }
    public String get_text_para(){
        return heading_text;
    }
    public String get_heading_belowimg(){
        return heading_below_image;
    }
    public String get_subheading_below(){
        return sub_heading_below_image;
    }
    public String get_text_below_image(){
        return text_below_image;
    }
    //public String get_website(){
      //  return website;
    //}

    @Override
    public Class getIntentToLaunchPage() {
        return ServicesOnApp.class;
    }

    @Override
    public int getTemplateSelected() {
        return templateSelected;
    }

}
