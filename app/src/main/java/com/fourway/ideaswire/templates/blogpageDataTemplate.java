package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.ServicesOnApp;

/**
 * Created by Vaibhav Gusain on 9/8/2016.
 */
public class blogpageDataTemplate extends dataOfTemplate {
    int templateSelected = 1;
    String about_us_heading;

    public String subheading_header =null;
    public String heading_text =null;
    public String heading_below_image = null;
    public String sub_heading_below_image =null;
    public String text_below_image =null;
    //public String website = null;
    public blogpageDataTemplate(int templateSelected, boolean isDefaultData)
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

    String title,url,heading,subheading,paragraph,buttontext,heading2,subheading2,paragraph2;

    public String get_blogpage_title(){
        return title;
    }
    public void set_blogpage_title(String arg){
        title = arg;
    }

    public String get_blogpage_image_url(){
        return url;
    }
    public void set_blogpage_image_url(String arg){
        url = arg;
    }

    public String get_blogpage_heading(){
        return heading;
    }
    public void set_blogpage_heading(String arg){
        heading = arg;
    }

    public String get_blogpage_heading2(){
        return heading2;
    }
    public void set_blogpage_heading2(String arg){
        heading2 = arg;
    }

    public String get_blogpage_subheading(){
        return subheading;
    }
    public void set_blogpage_subheading(String arg){
        subheading = arg;
    }

    public String get_blogpage_subheading2(){
        return subheading2;
    }
    public void set_blogpage_subheading2(String arg){
        subheading2 = arg;
    }


    public String get_blogpage_paragraph(){
        return paragraph;
    }
    public void set_blogpage_paragraph(String arg)
    {
        paragraph = arg;
    }

    public String get_blogpage_paragraph2(){
        return paragraph2;
    }
    public void set_blogpage_paragraph2(String arg)
    {
        paragraph2 = arg;
    }


    public String get_blogpage_button_text(){
        return buttontext;
    }
    public void set_blogpage_button_text(String arg){
        buttontext = arg;
    }

}
