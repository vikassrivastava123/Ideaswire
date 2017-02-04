package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.MainActivity;

/**
 * Created by Ritika on 8/24/2016.
 */

public class HomePageLayout_1 extends pages {

    HomePageLayout_1_DataTemplate dataObj = null;
    public int mTemplateType = 1;

    public HomePageLayout_1(){
        if (nameis == null) {
            nameis = "Home";
        }
    }

    public void setDataObj(HomePageLayout_1_DataTemplate dataObj) {
        this.dataObj = dataObj;
    }

    @Override
    public dataOfTemplate getDataForTemplate() {
        mTemplateType = dataOfTemplate.getTemplateSelectedByUser();
        dataObj = (HomePageLayout_1_DataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new HomePageLayout_1_DataTemplate(mTemplateType, true);
        }
        return dataObj;
    }


   @Override
   public dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = (HomePageLayout_1_DataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new HomePageLayout_1_DataTemplate(templateType, true);
        }
        return dataObj;
    }

    public dataOfTemplate getDataForTemplateAsReceivedFromServer(){
      //  mTemplateType = 1;
        dataObj = (HomePageLayout_1_DataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new HomePageLayout_1_DataTemplate(mTemplateType, false);
        }
        return dataObj;
    }

    @Override
    public pages getNewPage() {
        return new HomePageLayout_1();
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


        return R.drawable.home;
    }

    @Override
    public void set_iconBlack(int iconOfpage) {

    }

    @Override
    public int iconBlack() {
        return R.drawable.home_black;
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
}
