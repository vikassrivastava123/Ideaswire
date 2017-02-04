package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.MainActivity;

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
    public dataOfTemplate getDataForTemplate() {
        mTemplateType = dataOfTemplate.getTemplateSelectedByUser();
        dataObj = (ServicesDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new ServicesDataTemplate(mTemplateType,true);
        }
        return dataObj;
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
    @Override
    public void set_iconBlack(int iconOfpage) {

    }

    @Override
    public int iconBlack() {
        return R.drawable.services_black;
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
    public int getImageForMainPage_BasedOnTemplate_Layout(int temp, int layout) {
        int  imageResourceId = 0;
        if (layout == 1) {
            switch (temp) {
                case MainActivity.TEM_BUSINESS:
                    imageResourceId = R.drawable.business_services_category;
                    break;
                case MainActivity.TEM_INDIVIDUAL:
                    imageResourceId = R.drawable.individual_services_category;
                    break;
                case MainActivity.TEM_FINANCE:
                    imageResourceId = R.drawable.finance_services_category;
                    break;
                case MainActivity.TEM_HEALTH:
                    imageResourceId = R.drawable.health_services_category;
                    break;
                case MainActivity.TEM_ENTERTAINMENT:
                    imageResourceId = R.drawable.entertainment_services_category;
                    break;
                case MainActivity.TEM_INFORMATION:
                    imageResourceId = R.drawable.information_services_category;
                    break;
                case MainActivity.TEM_WEDDING:
                    imageResourceId = R.drawable.wedding_services_category;
                    break;
                case MainActivity.TEM_RESTAURANT:
                    imageResourceId = R.drawable.restaurant_services_category;
                    break;
                case MainActivity.TEM_OTHERS:
                    imageResourceId = R.drawable.others_services_category;
                    break;
                default:
                    imageResourceId = R.drawable.business_services_category;
                    break;

            }
        }else if (layout == 2) {
            imageResourceId = R.drawable.service_icon;
        }
        return imageResourceId;
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
