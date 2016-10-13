package com.fourway.ideaswire.templates;

/**
 * Created by Ritika on 8/24/2016.
 */
public abstract class pages {

   dataOfTemplate dataObj;

    public dataOfTemplate getTemplateData(int type,boolean defaultData){

        if(defaultData == true) {
            dataObj = getDataForTemplate(type);
        }else{
            dataObj = getDataForTemplateAsReceivedFromServer();
        }
        return dataObj;

    }
    abstract dataOfTemplate getDataForTemplate(int templateType);
    abstract dataOfTemplate getDataForTemplateAsReceivedFromServer();

    public void setDataObj(dataOfTemplate argDataObj){
        dataObj = argDataObj;
    }
    public abstract void set_nameis(String nameOfpage);
    public abstract String nameis();
}
