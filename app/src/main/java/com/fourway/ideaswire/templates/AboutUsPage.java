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
    public dataOfTemplate getDataForTemplate() {
        mTemplateType = dataOfTemplate.getTemplateSelectedByUser();
        dataObj = (AboutUsDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new AboutUsDataTemplate(mTemplateType, true);
        }
        return dataObj;
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
      //  mTemplateType = 1;
        dataObj = (AboutUsDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new AboutUsDataTemplate(mTemplateType, false);
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

    @Override
    public void set_iconBlack(int iconOfpage) {

    }

    @Override
    public int iconBlack() {
        return R.drawable.about_black;
    }

    int theme;
    @Override
    public void set_theme(int theme) {
        this.theme = theme;
    }

    @Override
    public int themes() {
        return theme;
    }

    boolean status = true;
    @Override
    public void setPageStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean pageStatus() {
        return status;
    }
}
