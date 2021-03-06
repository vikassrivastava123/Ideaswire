package com.fourway.ideaswire.templates;

import android.app.Fragment;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.FrgmentBlogOnApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vaibhav Gusain on 9/8/2016.
 */

public class blogpageDataTemplate extends dataOfTemplate {
    int templateSelected = 0;
    int layoutSelected = 0;

    public String title=null;
    public String headerBlog=null;
    public String urlOfImage=null;
    public String subHeader=null;
    public String text_Para=null;
    public String headerBlogBlowing=null;
    public String subHeaderBlowing=null;
    private List<Integer> resourceDrawableId;

    boolean   ismDefaultData = false;

    public String text_ParaBlowing=null;



    public blogpageDataTemplate(int templateSelected, boolean isDefaultData) {

        initDefaultResources();
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

    @Override
    public List<Integer> getDefaultDrawableResourceId() {
        initDefaultResources();
        return resourceDrawableId;
    }

    @Override
    public void setTemplateByServer(int temp) {
        this.templateSelected = temp;
    }

    @Override
    public int getTemplateByServer() {
        return templateSelected;
    }

    @Override
    public int getLayoutByServer() {
        return layoutSelected;
    }

    @Override
    public void setLayoutByServer(int layout) {
        this.layoutSelected = layout;
    }

    void initDeafultdata(){
        title="Blog";


        /*headerBlog = "Totam Aperiam Consect";
        subHeader = "There are many variation of passages";
        text_Para = "There are many variation of passage of Lorem" +
                "Ipsum available, but the majority have suffered " +
                "alternation in some form by injected humour";

        headerBlogBlowing = "Totam Aperiam Consect";
        subHeaderBlowing = "There are many variation of passages";
        text_ParaBlowing = "There are many variation of passage of Lorem" +
                "Ipsum available, but the majority have suffered " +
                "alternation in some form by injected humour";*/

    }

    void initDefaultResources() {
        resourceDrawableId = new ArrayList<>();
//        int temSel =  getTemplateSelected();

        switch (templateSelected){
            case 0:
                resourceDrawableId.add(R.drawable.business_blog_banner);
                break;
            case 1:
                resourceDrawableId.add(R.drawable.individual_blog_banner);
                break;
            case 2:
                resourceDrawableId.add(R.drawable.finance_blog_banner);
                break;
            case 3:
                resourceDrawableId.add(R.drawable.health_blog_banner);
                break;
            case 4:
                resourceDrawableId.add(R.drawable.entertainment_blog_banner);
                break;
            case 5:
                resourceDrawableId.add(R.drawable.information_blog_banner);
                break;
            case 6:
                resourceDrawableId.add(R.drawable.wedding_blog_banner);
                break;
            case 7:
                resourceDrawableId.add(R.drawable.restaurant_blog_banner);
                break;
            case 8:
                resourceDrawableId.add(R.drawable.others_blog_banner);
                break;
            default:
                resourceDrawableId.add(R.drawable.business_blog_banner);
                break;

        }
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
    public Fragment getFragmentToLaunchPage() {
        return  new FrgmentBlogOnApp();
    }

}
