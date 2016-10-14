package com.fourway.ideaswire.templates;

/**
 * Created by Ritika on 8/24/2016.
 */
public class AboutUsPage extends pages {

    AboutUsDataTemplate dataObj = null;
    public int mTemplateType = 1;

    public AboutUsPage(){
         nameis = "About Us 2";
    }


    public void setDataObj(AboutUsDataTemplate dataObj) {
        this.dataObj = dataObj;
    }

    @Override
    dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = new AboutUsDataTemplate(templateType,true);
        return dataObj;
    }
    dataOfTemplate getDataForTemplateAsReceivedFromServer(){
        mTemplateType = 1;
        dataObj = (AboutUsDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new AboutUsDataTemplate(1, false);

        }
        return dataObj;
    }

    String nameis;
    @Override
    public void set_nameis(String nameOfpage) {
        nameis = nameOfpage;
    }

    @Override
    public String nameis()
    {
        return nameis;
    }
}
