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
}
