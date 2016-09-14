package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.AboutUsOnApp;

/**
 * Created by Ritika on 8/24/2016.
 */
public class HomePageDataTemplate extends dataOfTemplate{

    int templateSelected = 1;
    String header1;
    String header2;
    String text_view;
    public HomePageDataTemplate(int templateSelected,boolean flag){
        setHeader("About All of Us");
        this.templateSelected = templateSelected;
        header1 = "vaibhav custom header";
        header2 = "This is some random heading custom";
        text_view= "This is custom random para to generate space dont read ti its useless";
    }

    @Override
    public Class getIntentToLaunchPage() {
        return AboutUsOnApp.class;
    }

    @Override
    public int getTemplateSelected() {
        return templateSelected;
    }

    public String getheadingSelected() {
        return header1;
    }
    public String getsubheadingSelected() {
        return header2;
    }
    public String getparaSelected() {
        return text_view;
    }

    String title,url,heading,subheading,paragraph,buttontext;

    public String get_homepage_title(){
        return title;
    }
    public void set_homepage_title(String arg){
        title = arg;
    }

    public String get_homepage_image_url(){
        return url;
    }
    public void set_homepage_image_url(String arg){
        url = arg;
    }

    public String get_homepage_heading(){
        return heading;
    }
    public void set_homepage_heading(String arg){
        heading = arg;
    }

    public String get_homepage_subheasing(){
        return subheading;
    }
    public void set_homepage_subeading(String arg){
        subheading = arg;
    }
    public String get_homepage_paragraph(){
        return paragraph;
    }
    public void set_homepage_paragraph(String arg)
    {
        paragraph = arg;
    }

    public String get_homepage_button_text(){
        return buttontext;
    }
    public void set_homepage_button_text(String arg){
        buttontext = arg;
    }






}
