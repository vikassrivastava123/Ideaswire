package com.fourway.ideaswire.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Attribute;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.ProfileFieldsEnum;
import com.fourway.ideaswire.templates.AboutUsDataTemplate;
import com.fourway.ideaswire.templates.AboutUsPage;
import com.fourway.ideaswire.templates.HomePage;
import com.fourway.ideaswire.templates.HomePageDataTemplate;
import com.fourway.ideaswire.templates.ServicePage;
import com.fourway.ideaswire.templates.blogpage;
import com.fourway.ideaswire.templates.contactDetails;
import com.fourway.ideaswire.templates.pages;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public static List<pages> listOfTemplatePagesObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ui);

        /*listOfTemplatePagesObj = new ArrayList<pages>();

        pages abtusObj = new AboutUsPage();
        listOfTemplatePagesObj.add(0, abtusObj);

        dataOfTemplate data = listOfTemplatePagesObj.get(0).getTemplateData(1);

        Class intenetToLaunch = data.getIntentToLaunchPage();

        Intent intent = new Intent(getApplicationContext(), intenetToLaunch);
        intent.putExtra("data",data);*/
        Intent intent = new Intent(getApplicationContext(), loginUi.class);
        startActivity(intent);
    }

    public static void initListOfPages(){

        if(listOfTemplatePagesObj == null){
            listOfTemplatePagesObj = new ArrayList<pages>();
        }else{
            listOfTemplatePagesObj.clear();
        }
    }
    private static String TAG = "MainActivity";

    static void setAttributesForAboutUsPage(ArrayList<Attribute> attributesFromServer){
        pages abtusObj = new AboutUsPage();
        AboutUsDataTemplate data = (AboutUsDataTemplate)abtusObj.getTemplateData(1);
        for(Attribute atr : attributesFromServer) {
            String attrName = atr.getContentNme();
            switch (attrName){
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_TITLE:
                    String abtTitle = atr.getContentValue();
                    data.set_heading(abtTitle);

                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_CARD_IMAGE:
                    String url = atr.getContentValue();
                    data.set_heading(url);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_HEADING:
                    String heading2 = atr.getContentValue();
                    data.set_heading2(heading2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_SUBHEADING:
                    String subHeading = atr.getContentValue();
                    data.set_sub_heading(subHeading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_PARAGRAPH:
                    String para = atr.getContentValue();
                    data.set_text_para(para);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_BUTTON_TEXT:
                    String btnText = atr.getContentValue();
                    data.set_button_text(btnText);
                    break;

            }
        }

        listOfTemplatePagesObj.add(abtusObj);
    }


    static void setAttributesForHomePage(ArrayList<Attribute> attributesFromServer){
        pages homePageObj = new HomePage();
        HomePageDataTemplate data = (HomePageDataTemplate)homePageObj.getTemplateData(1);
        for(Attribute atr : attributesFromServer) {
            String attrName = atr.getContentNme();
            switch (attrName){
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_TITLE:
                    String abtTitle = atr.getContentValue();
                    data.set_homepage_title(abtTitle);

                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_FULL_IMAGE:
                    String url = atr.getContentValue();
                    data.set_homepage_image_url(url);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_HEADING:
                    String heading2 = atr.getContentValue();
                    data.set_homepage_heading(heading2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_SUBHEADING:
                    String subHeading = atr.getContentValue();
                    data.set_homepage_subeading(subHeading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_PARAGRAPH:
                    String para = atr.getContentValue();
                    data.set_homepage_paragraph(para);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_BUTTON:
                    String btnText = atr.getContentValue();
                    data.set_homepage_button_text(btnText);
                    break;

            }
        }
        listOfTemplatePagesObj.add(homePageObj);
    }

   /*
    static void setAttributesForBlogPage(ArrayList<Attribute> attributesFromServer){
        pages blogPageObj = new blogpage();
        blogpageTemplate data = (blogpageTemplate)blogPageObj.getTemplateData(1);
        for(Attribute atr : attributesFromServer) {
            String attrName = atr.getContentNme();
            switch (attrName){
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_TITLE:
                    String abtTitle = atr.getContentValue();
                    data.set_blogpage_title(abtTitle);

                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_CARD_IMAGE:
                    String url = atr.getContentValue();
                    data.set_blogpage_image_url(url);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_1:
                    String heading = atr.getContentValue();
                    data.set_blogpage_heading(heading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_1:
                    String subHeading = atr.getContentValue();
                    data.set_blogpage_subheading(subHeading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_1:
                    String para = atr.getContentValue();
                    data.set_blogpage_paragraph(para);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_BUTTON:
                    String btnText = atr.getContentValue();
                    data.set_blogpage_button_text(btnText);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_2:
                    String heading2 = atr.getContentValue();
                    data.set_blogpage_heading2(heading2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_2:
                    String subHeading2 = atr.getContentValue();
                    data.set_blogpage_subheading2(subHeading2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_2:
                    String para2 = atr.getContentValue();
                    data.set_blogpage_paragraph2(para2);
                    break;

            }
        }
        listOfTemplatePagesObj.add(blogPageObj);
    }
*/
   /* static void setAttributesForContactDetailPage(ArrayList<Attribute> attributesFromServer){
        pages contactDetailsPageObj = new contactDetails();
        contactDetailsDataTemplate data = (contactDetailsDataTemplate)contactDetailsPageObj.getTemplateData(1);
        for(Attribute atr : attributesFromServer) {
            String attrName = atr.getContentNme();
            switch (attrName){
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_TITLE:
                    String abtTitle = atr.getContentValue();
                    data.set_contactDeatils_title(abtTitle);

                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_HEADING:
                    String heading = atr.getContentValue();
                    data.set_contactDeatils_heading(heading);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_PARAGRAPH_1:
                    String para = atr.getContentValue();
                    data.set_contactDeatils_paragraph(para);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_PARAGRAPH_2:
                    String para2 = atr.getContentValue();
                    data.set_contactDeatils_paragraph2(para2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_PARAGRAPH_3:
                    String para3 = atr.getContentValue();
                    data.set_contactDeatils_paragraph3(para3);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_PARAGRAPH_4:
                    String para4 = atr.getContentValue();
                    data.set_contactDeatils_paragraph4(para4);

                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_PARAGRAPH_5:
                    String para5 = atr.getContentValue();
                    data.set_contactDeatils_paragraph5(para5);

                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_SUBHEADING_1:
                    String subHeading1 = atr.getContentValue();
                    data.set_contactDeatils_subheading(subHeading1);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_SUBHEADING_2:
                    String subHeading2 = atr.getContentValue();
                    data.set_contactDeatils_subheading2(subHeading2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_SUBHEADING_3:
                    String subHeading3 = atr.getContentValue();
                    data.set_contactDeatils_subheading3(subHeading3);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_SUBHEADING_4:
                    String subHeading4 = atr.getContentValue();
                    data.set_contactDeatils_subheading4(subHeading4);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_SUBHEADING_5:
                    String subHeading5 = atr.getContentValue();
                    data.set_contactDeatils_subheading5(subHeading5);
                    break;
            }
        }
        listOfTemplatePagesObj.add(contactDetailsPageObj);
    }
*/
    public static void addPagesToList(ArrayList<Page> pageList){
      try{
           int pageNumber = 0;
           for(Page p: pageList){
            String pageName = p.getPageName();

               if(pageName == null || pageName.equals("null") == true){
                   ArrayList<Attribute> attributesFromServer = p.getAttributes();
                   setAttributesForAboutUsPage(attributesFromServer);
                return;
               }
               ArrayList<Attribute>attributesFromServer = p.getAttributes();
                switch (pageName){
                    case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US:
                        setAttributesForAboutUsPage(attributesFromServer);
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_BLOG:
                 //       setAttributesForBlogPage(attributesFromServer);
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_CLIENT:
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE:
                        setAttributesForHomePage(attributesFromServer);
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US:
               //         setAttributesForContactDetailPage(attributesFromServer);
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_SERVICES:
                        pages ServicePage = new ServicePage();
                        listOfTemplatePagesObj.add(ServicePage);
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_TEAM:
                        break;

                }

           }
        }catch (NullPointerException e){
            Log.v(TAG,"listOfTemplatePagesObj is null");
        }

    }







    public static void addPagesToCreateTemplate(){
        pages abtusObj = new AboutUsPage();
        pages homeObj = new HomePage();
        pages blogpage = new blogpage();
        pages contactdetails = new contactDetails();
        pages ServicePage = new ServicePage();

        try {
            listOfTemplatePagesObj.add(0, abtusObj);
            listOfTemplatePagesObj.add(1, homeObj);
            listOfTemplatePagesObj.add(2, blogpage);
            listOfTemplatePagesObj.add(3, contactdetails);
            listOfTemplatePagesObj.add(4, ServicePage);
        }catch(NullPointerException e){
            Log.d(TAG,"listOfTemplatePagesObj is null");
        }
    }



}
