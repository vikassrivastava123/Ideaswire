package com.fourway.ideaswire.templates;

import android.app.Fragment;

import com.fourway.ideaswire.ui.ClientsOnapp;
import com.fourway.ideaswire.ui.FragmentClientsOnApp;

/**
 * Created by Vijay on 03-10-2016.
 */
public class ClientDataTemplate extends dataOfTemplate {
    int templateSelected = 1;



    public String headerClient=null;
    public String subHeaderClient=null;
    public String paraClient=null;
    public String title=null;



    public String client_logo_1=null;
    public String client_logo_2=null;
    public String client_logo_3=null;
    public String client_logo_4=null;
    public String client_logo_5=null;
    public String client_logo_6=null;


    boolean   ismDefaultData = false;

    public ClientDataTemplate(int templateSelected, boolean isDefaultData){

        if(isDefaultData){
            initDeafultdata();
        }else{
            // when datasetis not installed
        }
        ismDefaultData = isDefaultData;
        this.templateSelected = templateSelected;

    }

    public boolean isDefaultDataToCreateCampaign(){

        return ismDefaultData;
    }

    void initDeafultdata(){

        title = "Client";
        headerClient = "Toatam Aperiam Consect";
        subHeaderClient = "There are many variation of passages";
        paraClient = "There are many variation of passage " +
                "Lorem Ipsum available but the majority have" +
                " in some form bye inject humour";

    }

    @Override
    public Class getIntentToLaunchPage() {
        return ClientsOnapp.class;
    }

    @Override
    public Fragment getFragmentToLaunchPage() {
        return new FragmentClientsOnApp();
    }

    @Override
    public int getTemplateSelected() {
        return templateSelected;
    }

    public String getSubHeaderClient() {
        return subHeaderClient;
    }

    public void setSubHeaderClient(String subHeaderClient) {
        this.subHeaderClient = subHeaderClient;
    }

    public String getHeaderClient() {
        return headerClient;
    }

    public void setHeaderClient(String headerClient) {
        this.headerClient = headerClient;
    }

    public String getParaClient() {
        return paraClient;
    }

    public void setParaClient(String paraClient) {
        this.paraClient = paraClient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClient_logo_1() {
        return client_logo_1;
    }

    public void setClient_logo_1(String client_logo_1) {
        this.client_logo_1 = client_logo_1;
    }

    public String getClient_logo_2() {
        return client_logo_2;
    }

    public void setClient_logo_2(String client_logo_2) {
        this.client_logo_2 = client_logo_2;
    }

    public String getClient_logo_3() {
        return client_logo_3;
    }

    public void setClient_logo_3(String client_logo_3) {
        this.client_logo_3 = client_logo_3;
    }

    public String getClient_logo_4() {
        return client_logo_4;
    }

    public void setClient_logo_4(String client_logo_4) {
        this.client_logo_4 = client_logo_4;
    }

    public String getClient_logo_5() {
        return client_logo_5;
    }

    public void setClient_logo_5(String client_logo_5) {
        this.client_logo_5 = client_logo_5;
    }

    public String getClient_logo_6() {
        return client_logo_6;
    }

    public void setClient_logo_6(String client_logo_6) {
        this.client_logo_6 = client_logo_6;
    }
}
