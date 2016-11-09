package com.fourway.ideaswire.templates;

import android.app.Fragment;

import com.fourway.ideaswire.ui.FragmentContactOnApp;
import com.fourway.ideaswire.ui.contact_details;

/**
 * Created by Vaibhav Gusain on 9/8/2016.
 */
public class contactDetailsDataTemplate extends dataOfTemplate{
    int templateSelected = 1;

    public String headerContact =null;
    public String title =null;
    public String subHeading =null;
    public String paraGraph =null;
    public String address = null;
    public String email =null;
    public String phoneNumber =null;
    public String website = null;
    boolean   ismDefaultData = false;

    public contactDetailsDataTemplate(int templateSelected, boolean isDefaultData)
    {
        if(isDefaultData){

            initDeafultdata();

        }else{

            // when datasetis not installed
        }
        ismDefaultData = isDefaultData;
        this.templateSelected = templateSelected;
    }

    void initDeafultdata(){

        title="Contact";
        headerContact = "Totam Aperiam Consect";
        subHeading = "There are many variation of passages";
        paraGraph = "There are many variation of passage of Lorem" +
                "Ipsum available, but the majority have suffered " +
                "alternation in some form by injected humour";
        address = "Variation Passages Repetition,\n" +
                " 5462 Distracted" +
                "\nSimple Random - 1100083";
        email = "dummy254@gmail.com";
        phoneNumber = "+91-8800664433";
        website = "www.4wayTechnologies.com";
    }




    @Override
    public Class getIntentToLaunchPage() {
        return contact_details.class;
    }

    @Override
    public Fragment getFragmentToLaunchPage() {
        return new FragmentContactOnApp();
    }

    @Override
    public boolean isDefaultDataToCreateCampaign() {
         return ismDefaultData;
    }

    @Override
    public int getTemplateSelected() {
        return templateSelected;
    }


    public String getHeaderContact() {
        return headerContact;
    }

    public void setHeaderContact(String headerContact) {
        this.headerContact = headerContact;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getParaGraph() {
        return paraGraph;
    }

    public void setParaGraph(String paraGraph) {
        this.paraGraph = paraGraph;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
