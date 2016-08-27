package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.AboutUsOnApp;

/**
 * Created by Ritika on 8/24/2016.
 */
public class AboutUsDataTemplate extends dataOfTemplate{

    int templateSelected = 1;
    String about_us_heading = null;
    public AboutUsDataTemplate(int templateSelected){
        setHeader("About All of Us");
        set_profile_page_about_us_heading("Profile_Page_About_US_Heading ");
        this.templateSelected = templateSelected;

    }

    public void set_profile_page_about_us_heading(String header){

        about_us_heading = header;
    }

    public String get_profile_page_about_us_heading(){
            return about_us_heading;
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
