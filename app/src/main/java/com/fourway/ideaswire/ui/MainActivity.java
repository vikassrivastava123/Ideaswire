package com.fourway.ideaswire.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Attribute;
import com.fourway.ideaswire.data.Page;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.ProfileFieldsEnum;
import com.fourway.ideaswire.templates.AboutUsDataTemplate;
import com.fourway.ideaswire.templates.AboutUsPage;
import com.fourway.ideaswire.templates.ClientDataTemplate;
import com.fourway.ideaswire.templates.ClientPage;
import com.fourway.ideaswire.templates.HomePage;
import com.fourway.ideaswire.templates.HomePageDataTemplate;
import com.fourway.ideaswire.templates.ServicePage;
import com.fourway.ideaswire.templates.ServicesDataTemplate;
import com.fourway.ideaswire.templates.TeamDataTemplate;
import com.fourway.ideaswire.templates.TeamPage;
import com.fourway.ideaswire.templates.blogpage;
import com.fourway.ideaswire.templates.blogpageDataTemplate;
import com.fourway.ideaswire.templates.contactDetails;
import com.fourway.ideaswire.templates.contactDetailsDataTemplate;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.util.ArrayList;
import java.util.List;

import static com.fourway.ideaswire.ui.select_layout_of_template.listOfTemplatePagesObjForAddPage;

public class MainActivity extends AppCompatActivity{

    public static List<pages> listOfTemplatePagesObj;


    //Value of activity that is passed as putExtra to recognize
    // which activity to open once gallery work finished
    public static final String OPEN_GALLERY_FOR = "Open_gallery _for_which_act";

    //intent value that will be passed for activity recog
    public static final int OPEN_GALLERY_FOR_CREATE_CAMPAIGN = 0;
    public static final int OPEN_GALLERY_FOR_EDIT_CAMPAIGN = 9;
    public static final int OPEN_GALLERY_FOR_SEARCH  = 1;
    public static final int OPEN_GALLERY_FOR_ABOUTUSPAGE_ON_APP  = 2;
    public static final int OPEN_PREVIOUS_ACTIVITY  = 3;
    public static final int OPEN_GALLERY_FOR_BLOG_ON_APP  = 4;
    public static final int OPEN_GALLERY_FOR_SERVICE_ON_APP  = 5;
    public static final int OPEN_GALLERY_FOR_HOME_PAGE_ON_APP=6;
    public static final int OPEN_GALLERY_FOR_CLIENTS_PAGE_ON_APP=7;
    public static final int OPEN_GALLERY_FOR_TEAM_PAGE_ON_APP=8;

    public static final String CREATE_CAMPAIGN_IMAGE_FROM = "Create_campaign_for_which_image";

    public static final int IMAGE_CREATE_CAMPAIGN = 0;
    public static final int IMAGE_SEARCH  = 1;


    //These are files names that will be saved locally
    public static final String CREATE_CAMPAIGN_IMAGE_CROPED_NAME = "Imaged";
    public static final String SEARCH__IMAGE_CROPED_NAME  = "searchedImage";
    public static final String About_Us_TemplateImage_IMAGE_CROPED_NAME = "aboutUsTemplateImage";
    public static final String Home_TemplateImage_IMAGE_CROPED_NAME_1 = "homeTemplateImage_1";
    public static final String Home_TemplateImage_IMAGE_CROPED_NAME_2 = "homeTemplateImage_2";
    public static final String Blog_TemplateImage_IMAGE_CROPED_NAME = "blogTemplateImage";
    public static final String Service_TemplateImage_IMAGE_CROPED_NAME = "serviceTemplateImage";
    public static final String CLIENTS_LOGO_IMAGE_CROPED_NAME_ = "clientsLogoImage_";
    public static final String TEAM_MEMBER_IMAGE_CROPED_NAME_ = "teamMemberImage_";

    public static final String OPEN_CAMERA_FOR_SEARCH = "openCameraSearch";

    public static final int REQUEST_GALLERY_IMAGE_SELECTOR = 101;
    public static final int REQUEST_GALLERY_IMAGE_SELECTOR_KITKAT = 102;
    public static final int REQUEST_CAMERA_IMAGE_SELECTOR = 103;
    public static final String ExplicitEditModeKey = "ExplicitEditMode";
    public static final String CROSS_BUTTON_HIDE = "*#doNotShow#*";

    public static final int THEME_DEFAULT = 0;
    public static final int THEME_ORANGE = 1;
    public static final int THEME_GREEN = 2;

    public static Profile requestToMakeProfile = null;
    public static boolean isLoginData;

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

        //Intent intent = new Intent(getApplicationContext(), loginUi.class);
        //Intent intent = new Intent(getApplicationContext(), HomepageBeforeLogin.class);
        //startActivity(intent);


       startActivity(new Intent(this,SplashScreen.class));



//        startCreateCampaignWithDefaultData(1);
    }


    private void startCreateCampaignWithDefaultData(int typeOfTemplateSelected){

        MainActivity.listOfTemplatePagesObj = new ArrayList<pages>();
        listOfTemplatePagesObjForAddPage = new ArrayList<pages>();


        pages abtusObj = new AboutUsPage();
        pages homeObj = new HomePage();
        pages blogpage = new blogpage();
        pages contactdetails = new contactDetails();
        pages ServicePage = new ServicePage();
        pages clientobj = new ClientPage();
        pages teamPages =new TeamPage();




        MainActivity.listOfTemplatePagesObj.add(0, abtusObj);
        abtusObj.setPageIndex(0);
        MainActivity.listOfTemplatePagesObj.add(1, homeObj);
        abtusObj.setPageIndex(1);
        MainActivity.listOfTemplatePagesObj.add(2, blogpage);
        abtusObj.setPageIndex(2);
        MainActivity.listOfTemplatePagesObj.add(3, contactdetails);
        abtusObj.setPageIndex(3);
        MainActivity.listOfTemplatePagesObj.add(4, ServicePage);
        abtusObj.setPageIndex(4);
        MainActivity.listOfTemplatePagesObj.add(5, clientobj);
        abtusObj.setPageIndex(5);
        MainActivity.listOfTemplatePagesObj.add(6, teamPages);
        abtusObj.setPageIndex(6);


        listOfTemplatePagesObjForAddPage.add(0, abtusObj);
        listOfTemplatePagesObjForAddPage.add(1, homeObj);
        listOfTemplatePagesObjForAddPage.add(2, blogpage);
        listOfTemplatePagesObjForAddPage.add(3, contactdetails);
        listOfTemplatePagesObjForAddPage.add(4, ServicePage);
        listOfTemplatePagesObjForAddPage.add(5, clientobj);
        listOfTemplatePagesObjForAddPage.add(6, teamPages);



        dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(0).getTemplateData(typeOfTemplateSelected, true);
        data.setEditMode(false);
        data.setIsInUpdateProfileMode(true);
//        Class intenetToLaunch = data.getIntentToLaunchPage();
//        Log.v("Create homepage", "5" + intenetToLaunch);
//        Intent intent = new Intent(getApplicationContext(), intenetToLaunch);
//
//        //Intent intent = new Intent(getApplicationContext(), FragmenMainActivity.class);
//        intent.putExtra("data",data);
//        startActivity(intent);

        Intent intent = new Intent(getApplicationContext(), FragmenMainActivity.class);
        intent.putExtra("data",data);
        startActivity(intent);


    }


  public Context getAppContxt(){
      return getApplicationContext();
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
        AboutUsDataTemplate data = (AboutUsDataTemplate)abtusObj.getTemplateData(1,false);
        for(Attribute atr : attributesFromServer) {
            String attrName = atr.getContentNme();
            switch (attrName){
                case ProfileFieldsEnum.PROFILE_THEME:
                    String themeS = atr.getContentValue();
                    int theme = Integer.parseInt(themeS);
                    abtusObj.set_theme(theme);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US:
                    String nameis = atr.getContentValue();
                    abtusObj.set_nameis(nameis);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_STATUS:
                    boolean status = Boolean.valueOf(atr.getContentValue());
                    abtusObj.setPageStatus(status);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_TITLE:
                    String abtTitle = atr.getContentValue();
                    data.set_title(abtTitle);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_CARD_IMAGE:
                    String url = atr.getContentValue();
                    data.set_url(url);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_HEADING:
                    String heading2 = atr.getContentValue();
                    data.set_heading(heading2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_SUBHEADING:
                    String subHeading = atr.getContentValue();
                    data.set_sub_heading(subHeading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_PARAGRAPH:
                    String para = atr.getContentValue();
                    data.set_text_para(para);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_BUTTON_SUBNAME_LINKED_PAGE:
                    String linkPageNumber = atr.getContentValue();
                    int pageNumber = Integer.parseInt(linkPageNumber);
                    data.set_submit_button_link(pageNumber);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_BUTTON_TEXT:
                    String btnText = atr.getContentValue();
                    data.set_button_text(btnText);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US_BUTTON_URL_TEXT:
                    String btnUrl = atr.getContentValue();
                    data.set_buttonUrl(btnUrl);
                    break;
            }
        }

        if (abtusObj.pageStatus()) {
            listOfTemplatePagesObj.add(abtusObj);
        }else if (isLoginData){
            listOfTemplatePagesObj.add(abtusObj);
        }
        int index = listOfTemplatePagesObj.indexOf(abtusObj);
        listOfTemplatePagesObj.get(index).setDataObj(data);


    }


    static void setAttributesForHomePage(ArrayList<Attribute> attributesFromServer){
        pages homePageObj = new HomePage();
        HomePageDataTemplate data = (HomePageDataTemplate)homePageObj.getTemplateData(1,false);
        for(Attribute atr : attributesFromServer) {
            String attrName = atr.getContentNme();
            switch (attrName){
                case ProfileFieldsEnum.PROFILE_THEME:
                    String themeS = atr.getContentValue();
                    int theme = Integer.parseInt(themeS);
                    homePageObj.set_theme(theme);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE:
                    String nameis = atr.getContentValue();
                    homePageObj.set_nameis(nameis);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_STATUS:
                    boolean status = Boolean.valueOf(atr.getContentValue());
                    homePageObj.setPageStatus(status);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_TITLE:
                    String abtTitle = atr.getContentValue();
                    data.setTitle(abtTitle);

                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_CARD_IMAGE_1:
                    String url_1 = atr.getContentValue();
                    data.setUrlOfImage_1(url_1);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_CARD_IMAGE_2:
                    String url_2 = atr.getContentValue();
                    data.setUrlOfImage_2(url_2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_HEADING:
                    String heading= atr.getContentValue();
                    data.setHeading(heading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_SUBHEADING:
                    String subHeading = atr.getContentValue();
                    data.setSubHeading(subHeading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE_PARAGRAPH:
                    String para = atr.getContentValue();
                    data.setParaGraph(para);
                    break;
            }
        }
        if (homePageObj.pageStatus()) {
            listOfTemplatePagesObj.add(homePageObj);
        }else if (isLoginData){
            listOfTemplatePagesObj.add(homePageObj);
        }

    }



    static void setAttributesForBlogPage(ArrayList<Attribute> attributesFromServer){
        pages blogPageObj = new blogpage();
        blogpageDataTemplate data = (blogpageDataTemplate)blogPageObj.getTemplateData(1,false);
        for(Attribute atr : attributesFromServer) {
            String attrName = atr.getContentNme();
            switch (attrName){
                case ProfileFieldsEnum.PROFILE_THEME:
                    String themeS = atr.getContentValue();
                    int theme = Integer.parseInt(themeS);
                    blogPageObj.set_theme(theme);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_BLOG:
                    String nameis = atr.getContentValue();
                    blogPageObj.set_nameis(nameis);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_STATUS:
                    boolean status = Boolean.valueOf(atr.getContentValue());
                    blogPageObj.setPageStatus(status);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_TITLE:
                    String abtTitle = atr.getContentValue();
                    data.setTitle(abtTitle);

                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_CARD_IMAGE:
                    String url = atr.getContentValue();
                    data.setUrlOfImage(url);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_1:
                    String heading = atr.getContentValue();
                    data.setHeaderBlog(heading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_1:
                    String subHeading = atr.getContentValue();
                    data.setSubHeader(subHeading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_1:
                    String para = atr.getContentValue();
                    data.setText_Para(para);
                    break;
                /*case ProfileFieldsEnum.PROFILE_PAGE_BLOG_BUTTON:
                    String btnText = atr.getContentValue();
                    data.set_blogpage_button_text(btnText);
                    break;*/
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_HEADING_2:
                    String heading2 = atr.getContentValue();
                    data.setHeaderBlogBlowing(heading2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_SUBHEADING_2:
                    String subHeading2 = atr.getContentValue();
                    data.setSubHeaderBlowing(subHeading2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_BLOG_PARAGRAPH_2:
                    String para2 = atr.getContentValue();
                    data.setText_ParaBlowing(para2);
                    break;

            }
        }
        if (blogPageObj.pageStatus()) {
            listOfTemplatePagesObj.add(blogPageObj);
        }else if (isLoginData){
            listOfTemplatePagesObj.add(blogPageObj);
        }

    }


    static void setAttributesForContactDetailPage(ArrayList<Attribute> attributesFromServer){
        pages contactDetailsPageObj = new contactDetails();
        contactDetailsDataTemplate data = (contactDetailsDataTemplate)contactDetailsPageObj.getTemplateData(1,false);
        for(Attribute atr : attributesFromServer) {
            String attrName = atr.getContentNme();
            switch (attrName){
                case ProfileFieldsEnum.PROFILE_THEME:
                    String themeS = atr.getContentValue();
                    int theme = Integer.parseInt(themeS);
                    contactDetailsPageObj.set_theme(theme);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US:
                    String nameis = atr.getContentValue();
                    contactDetailsPageObj.set_nameis(nameis);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_STATUS:
                    boolean status = Boolean.valueOf(atr.getContentValue());
                    contactDetailsPageObj.setPageStatus(status);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_TITLE:
                    String abtTitle = atr.getContentValue();
                    data.setTitle(abtTitle);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_HEADING:
                    String heading = atr.getContentValue();
                    data.setHeaderContact(heading);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_SUBHEADING:
                    String subHeading = atr.getContentValue();
                    data.setSubHeading(subHeading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_PARAGRAPH:
                    String paraGraph = atr.getContentValue();
                    data.setParaGraph(paraGraph);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_ADDRESS:
                    String address = atr.getContentValue();
                    data.setAddress(address);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_EMAIL:
                    String email = atr.getContentValue();
                    data.setEmail(email);

                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_PHONE_NUMBER:
                    String phoneNumber = atr.getContentValue();
                    data.setPhoneNumber(phoneNumber);

                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US_WEBSITE:
                    String website = atr.getContentValue();
                    data.setWebsite(website);
                    break;

            }
        }
        if (contactDetailsPageObj.pageStatus()) {
            listOfTemplatePagesObj.add(contactDetailsPageObj);
        }else if (isLoginData){
            listOfTemplatePagesObj.add(contactDetailsPageObj);
        }

    }

    static void setAttributesForServicePage(ArrayList<Attribute> attributesFromServer){
        pages servicePageObj = new ServicePage();
        ServicesDataTemplate data = (ServicesDataTemplate) servicePageObj.getTemplateData(1,false);
        for(Attribute atr : attributesFromServer) {
            String attrName = atr.getContentNme();
            switch (attrName){
                case ProfileFieldsEnum.PROFILE_THEME:
                    String themeS = atr.getContentValue();
                    int theme = Integer.parseInt(themeS);
                    servicePageObj.set_theme(theme);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_SERVICES:
                    String nameis = atr.getContentValue();
                    servicePageObj.set_nameis(nameis);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_SERVICES_STATUS:
                    boolean status = Boolean.valueOf(atr.getContentValue());
                    servicePageObj.setPageStatus(status);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_SERVICES_TITLE:
                    String abtTitle = atr.getContentValue();
                    data.setTitle(abtTitle);

                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_SERVICES_CARD_IMAGE:
                    String url = atr.getContentValue();
                    data.setUrlOfImage(url);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_SERVICES_HEADING_1:
                    String heading = atr.getContentValue();
                    data.setHeading(heading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_SERVICES_SUBHEADING_1:
                    String subHeading = atr.getContentValue();
                    data.setSubHeading(subHeading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_SERVICES_PARAGRAPH_1:
                    String para = atr.getContentValue();
                    data.setParaGraph(para);
                    break;
                /*case ProfileFieldsEnum.PROFILE_PAGE_BLOG_BUTTON:
                    String btnText = atr.getContentValue();
                    data.set_blogpage_button_text(btnText);
                    break;*/
                case ProfileFieldsEnum.PROFILE_PAGE_SERVICES_HEADING_2:
                    String heading2 = atr.getContentValue();
                    data.setHeading_below(heading2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_SERVICES_SUBHEADING_2:
                    String subHeading2 = atr.getContentValue();
                    data.setSubHeading_below(subHeading2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_SERVICES_PARAGRAPH_2:
                    String para2 = atr.getContentValue();
                    data.setGetParaGraph_below(para2);
                    break;

            }
        }
        if (servicePageObj.pageStatus()) {
            listOfTemplatePagesObj.add(servicePageObj);
        }else if (isLoginData){
            listOfTemplatePagesObj.add(servicePageObj);
        }

    }

    static void setAttributesForClientPage(ArrayList<Attribute> attributesFromServer){
        pages clientPage = new ClientPage();
        ClientDataTemplate data = (ClientDataTemplate) clientPage.getTemplateData(1,false);
        for(Attribute atr : attributesFromServer) {
            String attrName = atr.getContentNme();
            switch (attrName){
                case ProfileFieldsEnum.PROFILE_THEME:
                    String themeS = atr.getContentValue();
                    int theme = Integer.parseInt(themeS);
                    clientPage.set_theme(theme);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT:
                    String nameis = atr.getContentValue();
                    clientPage.set_nameis(nameis);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT_STATUS:
                    boolean status = Boolean.valueOf(atr.getContentValue());
                    clientPage.setPageStatus(status);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT_TITLE:
                    String title = atr.getContentValue();
                    data.setTitle(title);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT_HEADING:
                    String heading = atr.getContentValue();
                    data.setHeaderClient(heading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT_SUBHEADING:
                    String subHeading = atr.getContentValue();
                    data.setSubHeaderClient(subHeading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT_PARAGRAPH:
                    String para = atr.getContentValue();
                    data.setParaClient(para);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_1:
                    String logo_1 = atr.getContentValue();
                    data.setClient_logo(logo_1,0);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_2:
                    String logo_2 = atr.getContentValue();
                    data.setClient_logo(logo_2,1);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_3:
                    String logo_3 = atr.getContentValue();
                    data.setClient_logo(logo_3,2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_4:
                    String logo_4 = atr.getContentValue();
                    data.setClient_logo(logo_4,3);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_5:
                    String logo_5 = atr.getContentValue();
                    data.setClient_logo(logo_5,4);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_CLIENT_LOGO_6:
                    String logo_6 = atr.getContentValue();
                    data.setClient_logo(logo_6,5);
                    break;

            }
        }
        if (clientPage.pageStatus()) {
            listOfTemplatePagesObj.add(clientPage);
        }else if (isLoginData){
            listOfTemplatePagesObj.add(clientPage);
        }

    }

    static void setAttributesForTeamPage(ArrayList<Attribute> attributesFromServer){
        pages teamPage = new TeamPage();
        TeamDataTemplate data = (TeamDataTemplate) teamPage.getTemplateData(1,false);
        for(Attribute atr : attributesFromServer) {
            String attrName = atr.getContentNme();
            switch (attrName){
                case ProfileFieldsEnum.PROFILE_THEME:
                    String themeS = atr.getContentValue();
                    int theme = Integer.parseInt(themeS);
                    teamPage.set_theme(theme);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM:
                    String nameis = atr.getContentValue();
                    teamPage.set_nameis(nameis);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_STATUS:
                    boolean status = Boolean.valueOf(atr.getContentValue());
                    teamPage.setPageStatus(status);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_TITLE:
                    String title = atr.getContentValue();
                    data.setTitle(title);
                    break;

                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_HEADING:
                    String heading = atr.getContentValue();
                    data.setHeaderTeam(heading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_SUBHEADING:
                    String subHeading = atr.getContentValue();
                    data.setSubHeadingTeam(subHeading);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_PARAGRAPH:
                    String para = atr.getContentValue();
                    data.setParaGraphTeam(para);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_1_IMAGE:
                    String img_1 = atr.getContentValue();
                    data.setTeamImage(img_1,0);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_2_IMAGE:
                    String img_2 = atr.getContentValue();
                    data.setTeamImage(img_2, 1);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_3_IMAGE:
                    String img_3 = atr.getContentValue();
                    data.setTeamImage(img_3, 2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_4_IMAGE:
                    String img_4 = atr.getContentValue();
                    data.setTeamImage(img_4, 3);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_5_IMAGE:
                    String img_5 = atr.getContentValue();
                    data.setTeamImage(img_5, 4);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_6_IMAGE:
                    String img_6 = atr.getContentValue();
                    data.setTeamImage(img_6,5);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_1_NAME:
                    String name_1 = atr.getContentValue();
                    data.setTeamName(name_1,0);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_2_NAME:
                    String name_2 = atr.getContentValue();
                    data.setTeamName(name_2,1);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_3_NAME:
                    String name_3 = atr.getContentValue();
                    data.setTeamName(name_3,2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_4_NAME:
                    String name_4 = atr.getContentValue();
                    data.setTeamName(name_4,3);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_5_NAME:
                    String name_5 = atr.getContentValue();
                    data.setTeamName(name_5,4);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_6_NAME:
                    String name_6 = atr.getContentValue();
                    data.setTeamName(name_6,5);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_1_TITLE:
                    String title_1 = atr.getContentValue();
                    data.setTeamTitle(title_1,0);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_2_TITLE:
                    String title_2 = atr.getContentValue();
                    data.setTeamTitle(title_2,1);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_3_TITLE:
                    String title_3 = atr.getContentValue();
                    data.setTeamTitle(title_3,2);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_4_TITLE:
                    String title_4 = atr.getContentValue();
                    data.setTeamTitle(title_4,3);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_5_TITLE:
                    String title_5 = atr.getContentValue();
                    data.setTeamTitle(title_5,4);
                    break;
                case ProfileFieldsEnum.PROFILE_PAGE_TEAM_6_TITLE:
                    String title_6 = atr.getContentValue();
                    data.setTeamTitle(title_6,5);
                    break;


            }
        }
        if (teamPage.pageStatus()) {
            listOfTemplatePagesObj.add(teamPage);
        }else if (isLoginData){
            listOfTemplatePagesObj.add(teamPage);
        }

    }



    public static boolean addPagesToList(ArrayList<Page> pageList, boolean dataForLogin){
        isLoginData = dataForLogin;
        boolean bCanShowProfile = false;

      try{
           int pageNumber = 0;
           for(Page p: pageList){
               String[] pName = p.getPageName().split(" ",2);
            String pageName = pName[0];

               if(pageName == null || pageName.equals("null") == true){
                   ArrayList<Attribute> attributesFromServer = p.getAttributes();
                   setAttributesForAboutUsPage(attributesFromServer);
                   bCanShowProfile = true;
                return bCanShowProfile;
               }
               ArrayList<Attribute>attributesFromServer = p.getAttributes();
                switch (pageName){
                    case ProfileFieldsEnum.PROFILE_PAGE_ABOUT_US:
                        setAttributesForAboutUsPage(attributesFromServer);
                        bCanShowProfile = true;
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_BLOG:
                        setAttributesForBlogPage(attributesFromServer);
                        bCanShowProfile = true;
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_CLIENT:
                        setAttributesForClientPage(attributesFromServer);
                        bCanShowProfile = true;
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_HOMEPAGE:
                        setAttributesForHomePage(attributesFromServer);
                        bCanShowProfile = true;
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_CONTACT_US:
                        setAttributesForContactDetailPage(attributesFromServer);
                        bCanShowProfile = true;
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_SERVICES:
                        setAttributesForServicePage(attributesFromServer);
                        bCanShowProfile = true;
                        break;
                    case ProfileFieldsEnum.PROFILE_PAGE_TEAM:
                        setAttributesForTeamPage(attributesFromServer);
                        bCanShowProfile = true;
                        break;

                }





           }
        }catch (NullPointerException e){
            Log.v(TAG,"listOfTemplatePagesObj is null");
        }
         return bCanShowProfile;
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
            Log.d(TAG, "listOfTemplatePagesObj is null");
        }
    }

    public static void makeRequestToaddTemplateData(){


    }


    public static void setProfileObject(Profile argRequestToMakeProfile){
         requestToMakeProfile =  argRequestToMakeProfile;
    }

    public static Profile getProfileObject(){
        if(requestToMakeProfile == null){
            requestToMakeProfile = new Profile(editCampaign.mCampaignIdFromServer, Profile.TemplateID.PROFILE_TEMPLATE_ID_T1);
        }

        return requestToMakeProfile;

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}
