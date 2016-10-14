package com.fourway.ideaswire.templates;

/**
 * Created by Vaibhav Gusain on 9/9/2016.
 */
public class contactDetails extends pages {
    contactDetailsDataTemplate dataObj = null;
    public int mTemplateType = 1;
    @Override
    dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = new contactDetailsDataTemplate(templateType,true);
        return dataObj;
    }
    @Override
     public String nameis ()
    {
        return "Contact DETAILS";
    }

    dataOfTemplate getDataForTemplateAsReceivedFromServer(){
        mTemplateType = 1;
        dataObj = (contactDetailsDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new contactDetailsDataTemplate(1, false);

        }
        return dataObj;
    }

    @Override
    public void set_nameis(String nameOfpage) {

    }
}
