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
}
