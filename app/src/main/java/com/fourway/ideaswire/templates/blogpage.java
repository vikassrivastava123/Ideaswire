package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;

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
