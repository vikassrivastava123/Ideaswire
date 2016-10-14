package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.ServicesOnApp;
import com.fourway.ideaswire.ui.activity_blogpage;

/**
 * Created by Vaibhav Gusain on 9/8/2016.
 */
public class blogpageDataTemplate extends dataOfTemplate {
    int templateSelected = 1;

    public String title=null;
    public String headerBlog=null;
    public String urlOfImage=null;
    public String subHeader=null;
    public String text_Para=null;
    public String headerBlogBlowing=null;
    public String subHeaderBlowing=null;

    boolean   ismDefaultData = false;

    public String text_ParaBlowing=null;


    //public String website = null;
    public blogpageDataTemplate(int templateSelected, boolean isDefaultData)
    {
        if(isDefaultData){

            initDeafultdata();

        }else{

            // when datasetis not installed
        }
        ismDefaultData= isDefaultData;
        this.templateSelected = templateSelected;

    }

    public boolean isDefaultDataToCreateCampaign(){

        return ismDefaultData;
    }

    void initDeafultdata(){
        title="Blog";
        headerBlog = "Totam Aperiam Consect";
        subHeader = "There are many variation of passages";
        text_Para = "There are many variation of passage of Lorem" +
                "Ipsum available, but the majority have suffered " +
                "alternation in some form by injected humour";

        headerBlogBlowing = "Totam Aperiam Consect";
        subHeaderBlowing = "There are many variation of passages";
        text_Para = "There are many variation of passage of Lorem" +
                "Ipsum available, but the majority have suffered " +
                "alternation in some form by injected humour";

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlOfImage() {
        return urlOfImage;
    }

    public void setUrlOfImage(String urlOfImage) {
        this.urlOfImage = urlOfImage;
    }

    public String getHeaderBlog() {
        return headerBlog;
    }

    public void setHeaderBlog(String headerBlog) {
        this.headerBlog = headerBlog;
    }

    public String getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(String subHeader) {
        this.subHeader = subHeader;
    }

    public String getText_Para() {
        return text_Para;
    }

    public void setText_Para(String text_Para) {
        this.text_Para = text_Para;
    }

    public String getText_ParaBlowing() {
        return text_ParaBlowing;
    }

    public void setText_ParaBlowing(String text_ParaBlowing) {
        this.text_ParaBlowing = text_ParaBlowing;
    }

    public String getSubHeaderBlowing() {
        return subHeaderBlowing;
    }

    public void setSubHeaderBlowing(String subHeaderBlowing) {
        this.subHeaderBlowing = subHeaderBlowing;
    }

    public String getHeaderBlogBlowing() {
        return headerBlogBlowing;
    }

    public void setHeaderBlogBlowing(String headerBlogBlowing) {
        this.headerBlogBlowing = headerBlogBlowing;
    }



    @Override
    public Class getIntentToLaunchPage() {
        return activity_blogpage.class;
    }

    @Override
    public int getTemplateSelected() {
        return templateSelected;
    }



}
