package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;

/**
 * Created by Ritika on 8/24/2016.
 */
public class HomePage extends pages {

    HomePageDataTemplate dataObj = null;
    public int mTemplateType = 1;

    public HomePage(){
        if (nameis == null) {
            nameis = "Home";
        }
    }

    @Override
    public dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = (HomePageDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new HomePageDataTemplate(templateType, true);
        }
        return dataObj;
    }
    public String nameis ()
    {
        return nameis;
    }

    @Override
    public dataOfTemplate getDataForTemplateAsReceivedFromServer() {
        mTemplateType = 1;
        dataObj = (HomePageDataTemplate) getAlreadyCreatedDataObj();
        if (dataObj == null) {
            dataObj = new HomePageDataTemplate(1, false);
        }
        return dataObj;
    }

    @Override
    public pages getNewPage() {
        return new HomePage();
    }

    @Override
    public void set_iconis(int iconOfpage) {

    }

    @Override
    public int iconis() {
        return R.drawable.home;
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

    String nameis = null;
    @Override
    public void set_nameis(String nameOfpage) {
        nameis = nameOfpage;
    }
}

