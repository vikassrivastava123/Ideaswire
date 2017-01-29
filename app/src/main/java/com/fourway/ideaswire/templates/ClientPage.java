package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;

/**
 * Created by Vijay on 03-10-2016.
 */
public class ClientPage extends pages {

    ClientDataTemplate dataObj = null;
    public int mTemplateType = 1;

    public ClientPage(){
        if (nameis == null) {
            nameis = "Client";
        }
    }


    @Override
    public dataOfTemplate getDataForTemplate() {
        mTemplateType = dataOfTemplate.getTemplateSelectedByUser();
        dataObj = (ClientDataTemplate) getAlreadyCreatedDataObj();
        if (dataObj == null){
            dataObj = new ClientDataTemplate(mTemplateType,true);
        }
        return dataObj;
    }


    @Override
    public dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = (ClientDataTemplate) getAlreadyCreatedDataObj();
        if (dataObj == null){
            dataObj = new ClientDataTemplate(templateType,true);
        }
        return dataObj;
    }

    @Override
    public dataOfTemplate getDataForTemplateAsReceivedFromServer() {
        mTemplateType = 1;
        dataObj = (ClientDataTemplate) getAlreadyCreatedDataObj();
     //   dataObj = (ClientDataTemplate) MainActivity.listOfTemplateDataObj.get(position);
        if (dataObj == null){
            dataObj = new ClientDataTemplate(1,false);
        }
        return dataObj;
    }

    @Override
    public pages getNewPage() {
        return new ClientPage();
    }

    @Override
    public void set_nameis(String nameOfpage) {
        nameis = nameOfpage;
    }

    String nameis;
    @Override
    public String nameis() {
        return nameis;
    }

    @Override
    public void set_iconis(int iconOfpage) {

    }

    @Override
    public int iconis() {
        return R.drawable.client;
    }

    @Override
    public void set_iconBlack(int iconOfpage) {

    }

    @Override
    public int iconBlack() {
        return R.drawable.client_black;
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
