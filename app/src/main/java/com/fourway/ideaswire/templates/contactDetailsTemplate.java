package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.AboutUsOnApp;

/**
 * Created by Vaibhav Gusain on 9/8/2016.
 */
public class contactDetailsTemplate  extends dataOfTemplate{
    int templateSelected = 1;
    String about_us_heading;
    String heading= null;
    public String subheading_header =null;
    public String heading_text =null;
    public String Address = null;
    public String Email =null;
    public String phonenumber =null;
    public String website = null;
    public contactDetailsTemplate(int templateSelected, boolean isDefaultData)
    {
        if(isDefaultData){

            initDeafultdata();

        }else{

            // when datasetis not installed
        }



        setHeader("About All of Us");
        set_profile_page_about_us_heading("Profile_Page_About_US_Heading ");
        this.templateSelected = templateSelected;

    }

    void initDeafultdata(){

        header = new String("Heading one");
        subheading_header = "Below Image Heading";
        heading_text = "Subheading";
        Address = "paragraph text is soo long man.what to do .";
        Email = "Button textc";
        phonenumber = "+91-8800664433";
        website = "www.4wayTechnologies.com";
    }


    public void set_profile_page_about_us_heading(String header){

        about_us_heading = header;
    }

    public String get_profile_page_about_us_heading(){
        return about_us_heading;
    }
    public String get_heading(){
        return header;
    }
    public String get_subheading(){
        return subheading_header;
    }
    public String get_text_para(){
        return heading_text;
    }
    public String get_Address(){
        return Address;
    }
    public String get_email(){
        return Email;
    }
    public String get_phonenumber(){
        return phonenumber;
    }
    public String get_website(){
        return website;
    }

    @Override
    public Class getIntentToLaunchPage() {
        return AboutUsOnApp.class;
    }

    @Override
    public int getTemplateSelected() {
        return templateSelected;
    }
}
