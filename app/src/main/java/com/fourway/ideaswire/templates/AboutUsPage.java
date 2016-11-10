package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;

/**
 * Created by Ritika on 8/24/2016.
 */

public class AboutUsPage extends pages {

    AboutUsDataTemplate dataObj = null;
    public int mTemplateType = 1;

    public AboutUsPage(){
        if (nameis == null) {
            nameis = "About";
        }
    }



    public void setDataObj(AboutUsDataTemplate dataObj) {
        this.dataObj = dataObj;
    }

    @Override
   public dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = (AboutUsDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new AboutUsDataTemplate(templateType, true);
        }
        return dataObj;
    }

    public dataOfTemplate getDataForTemplateAsReceivedFromServer(){
        mTemplateType = 1;
        dataObj = (AboutUsDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new AboutUsDataTemplate(1, false);
        }
        return dataObj;
    }

    @Override
    public pages getNewPage() {
        return new AboutUsPage();
    }

    String nameis = null;
    @Override
    public void set_nameis(String nameOfpage) {
        nameis = nameOfpage;
    }

    @Override
    public String nameis()
    {
        return nameis;
    }

    @Override
    public void set_iconis(int iconOfpage) {
    }

    @Override
    public int iconis() {


        return R.drawable.about;
    }
}
