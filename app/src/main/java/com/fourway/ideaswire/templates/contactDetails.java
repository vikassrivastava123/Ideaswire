package com.fourway.ideaswire.templates;

/**
 * Created by Vaibhav Gusain on 9/9/2016.
 */
public class contactDetails extends pages {
    contactDetailsTemplate dataObj = null;
    public int mTemplateType = 1;
    @Override
    dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = new contactDetailsTemplate(templateType,true);
        return dataObj;
    }
    @Override
     public String nameis ()
    {
        return "Contact DETAILS";
    }

    dataOfTemplate getDataForTemplateAsReceivedFromServer(){
        mTemplateType = 1;
        dataObj = new contactDetailsTemplate(1,false);
        return dataObj;
    }

    @Override
    public void set_nameis(String nameOfpage) {

    }
}
