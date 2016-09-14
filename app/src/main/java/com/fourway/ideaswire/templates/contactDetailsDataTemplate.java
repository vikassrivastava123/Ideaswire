package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.AboutUsOnApp;

/**
 * Created by Vaibhav Gusain on 9/8/2016.
 */
public class contactDetailsDataTemplate extends dataOfTemplate{
    int templateSelected = 1;
    String about_us_heading;
    public String subheading_header =null;
    public String heading_text =null;
    public String Address = null;
    public String Email =null;
    public String phonenumber =null;
    public String website = null;
    public contactDetailsDataTemplate(int templateSelected, boolean isDefaultData)
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

    String title,url,heading,subheading,paragraph,buttontext,heading2,subheading2,paragraph2,paragraph4,paragraph5,heading3,subheading3,paragraph3,heading4,subheading4,heading5,subheading5;

    public String get_contactDeatils_title(){
        return title;
    }
    public void set_contactDeatils_title(String arg){
        title = arg;
    }

    public String get_contactDeatils_image_url(){
        return url;
    }
    public void set_contactDeatils_image_url(String arg){
        url = arg;
    }

    public String get_contactDeatils_heading(){
        return heading;
    }
    public void set_contactDeatils_heading(String arg){
        heading = arg;
    }

    public String get_contactDeatils_heading2(){
        return heading2;
    }
    public void set_contactDeatils_heading2(String arg){
        heading2 = arg;
    }

    public String get_contactDeatils_heading3(){
        return heading3;
    }
    public void set_contactDeatils_heading3(String arg){
        heading3 = arg;
    }

    public String get_contactDeatils_heading4(){
        return heading4;
    }
    public void set_contactDeatils_heading4(String arg){
        heading4 = arg;
    }

    public String get_contactDeatils_heading5(){
        return heading5;
    }
    public void set_contactDeatils_heading5(String arg){
        heading5 = arg;
    }

    public String get_contactDeatils_subheading(){
        return subheading;
    }
    public void set_contactDeatils_subheading(String arg){
        subheading = arg;
    }

    public String get_contactDeatils_subheading2(){
        return subheading2;
    }
    public void set_contactDeatils_subheading2(String arg){
        subheading2 = arg;
    }

    public String get_contactDeatils_subheading3(){
        return subheading3;
    }
    public void set_contactDeatils_subheading3(String arg){
        subheading3 = arg;
    }

    public String get_contactDeatils_subheading4(){
        return subheading4;
    }
    public void set_contactDeatils_subheading4(String arg){
        subheading4 = arg;
    }

    public String get_contactDeatils_subheading5(){
        return subheading5;
    }
    public void set_contactDeatils_subheading5(String arg){
        subheading5 = arg;
    }


    public String get_contactDeatils_paragraph(){
        return paragraph;
    }
    public void set_contactDeatils_paragraph(String arg)
    {
        paragraph = arg;
    }

    public String get_contactDeatils_paragraph2(){
        return paragraph2;
    }
    public void set_contactDeatils_paragraph2(String arg)
    {
        paragraph2 = arg;
    }

    public String get_contactDeatils_paragraph3(){
        return paragraph3;
    }
    public void set_contactDeatils_paragraph3(String arg)
    {
        paragraph3 = arg;
    }

    public String get_contactDeatils_paragraph4(){
        return paragraph4;
    }
    public void set_contactDeatils_paragraph4(String arg)
    {
        paragraph4 = arg;
    }

    public String get_contactDeatils_paragraph5(){
        return paragraph5;
    }
    public void set_contactDeatils_paragraph5(String arg)
    {
        paragraph5 = arg;
    }


    public String get_contactDeatils_button_text(){
        return buttontext;
    }
    public void set_contactDeatils_button_text(String arg){
        buttontext = arg;
    }
}
