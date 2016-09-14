package com.fourway.ideaswire.templates;

/**
 * Created by Vaibhav Gusain on 9/9/2016.
 */
public class ServicePage extends pages {
    ServicesTemplate dataObj = null;
    public int mTemplateType = 1;
    @Override
    dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = new ServicesTemplate(templateType,true);
        return dataObj;
    }
    public String nameis ()
    {
        return "Service Page";
    }
    dataOfTemplate getDataForTemplateAsReceivedFromServer(){
        mTemplateType = 1;
        dataObj = new ServicesTemplate(1,false);
        return dataObj;
    }

    @Override
    public void set_nameis(String nameOfpage) {

    }
}
