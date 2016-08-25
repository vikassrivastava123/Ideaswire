package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.AboutUsOnApp;

/**
 * Created by Ritika on 8/24/2016.
 */
public class HomePageDataTemplate extends dataOfTemplate{

    int templateSelected = 1;

    public HomePageDataTemplate(int templateSelected){
        setHeader("About All of Us");
        this.templateSelected = templateSelected;
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
