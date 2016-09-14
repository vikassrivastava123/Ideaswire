package com.fourway.ideaswire.templates;

/**
 * Created by Ritika on 8/24/2016.
 */
public class HomePage extends pages {

    HomePageDataTemplate dataObj = null;
    @Override
    dataOfTemplate getDataForTemplate(int templateType) {

        dataObj = new HomePageDataTemplate(templateType, true);
        return dataObj;
    }
    public String nameis ()
    {
        return "Home Page";
    }
    dataOfTemplate getDataForTemplateAsReceivedFromServer(){

        dataObj = new HomePageDataTemplate(1,false);
        return dataObj;
    }

    @Override
    public void set_nameis(String nameOfpage) {

    }
}

