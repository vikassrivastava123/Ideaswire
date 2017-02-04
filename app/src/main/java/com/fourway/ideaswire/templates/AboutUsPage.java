package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.MainActivity;

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
    public String nameis ()
    {
        return nameis;
    }

    @Override
    public dataOfTemplate getDataForTemplateAsReceivedFromServer() {
        mTemplateType = 1;
        dataObj = (AboutUsDataTemplate) getAlreadyCreatedDataObj();
        if (dataObj == null) {
            dataObj = new AboutUsDataTemplate(1, false);
        }
        return dataObj;
    }

    @Override
    public pages getNewPage() {
        return new AboutUsPage();
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
                    imageResourceId = R.drawable.business_about_category;
                    break;
                case MainActivity.TEM_INDIVIDUAL:
                    imageResourceId = R.drawable.individual_about_category;
                    break;
                case MainActivity.TEM_FINANCE:
                    imageResourceId = R.drawable.finance_about_category;
                    break;
                case MainActivity.TEM_HEALTH:
                    imageResourceId = R.drawable.health_about_category;
                    break;
                case MainActivity.TEM_ENTERTAINMENT:
                    imageResourceId = R.drawable.entertainment_about_category;
                    break;
                case MainActivity.TEM_INFORMATION:
                    imageResourceId = R.drawable.information_about_category;
                    break;
                case MainActivity.TEM_WEDDING:
                    imageResourceId = R.drawable.wedding_about_category;
                    break;
                case MainActivity.TEM_RESTAURANT:
                    imageResourceId = R.drawable.restaurant_about_category;
                    break;
                case MainActivity.TEM_OTHERS:
                    imageResourceId = R.drawable.others_about_category;
                    break;
                default:
                    imageResourceId = R.drawable.business_about_category;
                    break;

            }
        }else if (layout == 2) {
            imageResourceId = R.drawable.about_icon;
        }
        return imageResourceId;
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

