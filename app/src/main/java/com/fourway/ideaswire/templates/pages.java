package com.fourway.ideaswire.templates;

/**
 * Created by Ritika on 8/24/2016.
 */
public abstract class pages {

   dataOfTemplate dataObj;

    public dataOfTemplate getTemplateData(int type){

        dataObj = getDataForTemplate(type);
        return dataObj;

    }
    abstract dataOfTemplate getDataForTemplate(int templateType);
}
