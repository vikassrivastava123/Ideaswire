package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;

/**
 * Created by Vaibhav Gusain on 9/9/2016.
 */
public class ServicePage extends pages {
    ServicesDataTemplate dataObj = null;
    public int mTemplateType = 1;

    public ServicePage(){
        if (nameis == null) {
            nameis = "Services";
        }
    }
    @Override
    public dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = (ServicesDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new ServicesDataTemplate(templateType,true);
        }
        return dataObj;
    }
    public String nameis ()
    {
        return nameis;
    }

    @Override
    public void set_iconis(int iconOfpage) {

    }

    @Override
    public int iconis() {
        return R.drawable.services;
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

    @Override
    public dataOfTemplate getDataForTemplateAsReceivedFromServer(){
        mTemplateType = 1;
        dataObj = (ServicesDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new ServicesDataTemplate(1, false);

        }
        return dataObj;
    }

    @Override
    public pages getNewPage() {
        return new ServicePage();
    }

    String nameis = null;
    @Override
    public void set_nameis(String nameOfpage) {
        nameis = nameOfpage;
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
}
