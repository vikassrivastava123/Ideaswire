package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.MainActivity;

/**
 * Created by Vaibhav Gusain on 9/9/2016.
 */
public class blogpage extends pages {
    blogpageDataTemplate dataObj = null;
    public int mTemplateType = 1;
    public blogpage(){
        if (nameis == null) {
            nameis = "Blog";
        }
    }

    @Override
    public dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = (blogpageDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new blogpageDataTemplate(templateType, true);

        }
        return dataObj;
    }

    @Override
    public dataOfTemplate getDataForTemplate() {
        mTemplateType = dataOfTemplate.getTemplateSelectedByUser();
        dataObj = (blogpageDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new blogpageDataTemplate(mTemplateType, true);

        }
        return dataObj;
    }

    String nameis = null;
    @Override
    public String nameis ()
    {
        return nameis;
    }

    @Override
    public void set_iconis(int iconOfpage) {

    }

    @Override
    public int iconis() {
        return R.drawable.blog;
    }

    @Override
    public void set_iconBlack(int iconOfpage) {

    }

    @Override
    public int iconBlack() {
        return R.drawable.blog_black;
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
    public int getImageForMainPage_BasedOnTemplate_Layout(int temp,  int layout) {
        int  imageResourceId = 0;
        if (layout == 1) {
            switch (temp) {
                case MainActivity.TEM_BUSINESS:
                    imageResourceId = R.drawable.business_blog_category;
                    break;
                case MainActivity.TEM_INDIVIDUAL:
                    imageResourceId = R.drawable.individual_blog_category;
                    break;
                case MainActivity.TEM_FINANCE:
                    imageResourceId = R.drawable.finance_blog_category;
                    break;
                case MainActivity.TEM_HEALTH:
                    imageResourceId = R.drawable.health_blog_category;
                    break;
                case MainActivity.TEM_ENTERTAINMENT:
                    imageResourceId = R.drawable.entertainment_blog_category;
                    break;
                case MainActivity.TEM_INFORMATION:
                    imageResourceId = R.drawable.information_blog_category;
                    break;
                case MainActivity.TEM_WEDDING:
                    imageResourceId = R.drawable.wedding_blog_category;
                    break;
                case MainActivity.TEM_RESTAURANT:
                    imageResourceId = R.drawable.restaurant_blog_category;
                    break;
                case MainActivity.TEM_OTHERS:
                    imageResourceId = R.drawable.others_blog_category;
                    break;
                default:
                    imageResourceId = R.drawable.business_blog_category;
                    break;

            }
        }else if (layout == 2) {
            imageResourceId = R.drawable.blog_icon;
        }
        return imageResourceId;
    }

    public dataOfTemplate getDataForTemplateAsReceivedFromServer(){
        mTemplateType = 1;
        dataObj = (blogpageDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new blogpageDataTemplate(1, false);

        }
        return dataObj;
    }

    @Override
    public pages getNewPage() {
        return new blogpage();
    }

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
