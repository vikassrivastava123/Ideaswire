package com.fourway.ideaswire.templates;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.fourway.ideaswire.ui.ClientsOnapp;
import com.fourway.ideaswire.ui.FragmentClientsOnApp;
import com.fourway.ideaswire.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vijay on 03-10-2016.
 */
public class ClientDataTemplate extends dataOfTemplate {
    int templateSelected = 0;
    int layoutSelected = 0;

    public String headerClient=null;
    public String subHeaderClient=null;
    public String paraClient=null;
    public String title=null;

    public String client_logo[];
    final static String TAG = "ClientDataTemplate";

    boolean   ismDefaultData = false;

    public transient  List<Drawable> dataOfLogoDrawables =new ArrayList<Drawable>();

    public transient  List<Integer> posOfSavedImagesInLogo = new ArrayList<Integer>();

    public boolean isValInLogoList(int val){
        boolean retVal = false;
        int size = posOfSavedImagesInLogo.size();
        for(int i = 0;i<size;i++ ){
           if(posOfSavedImagesInLogo.get(i).intValue() == val){
               retVal = true;
           }
        }
        return retVal;

    }

    public int sizeOfLogoList(){
        int retSize = -1;
        if(dataOfLogoDrawables!=null){
            retSize = dataOfLogoDrawables.size();
        }
       return retSize;
    }

    public ClientDataTemplate(int templateSelected, boolean isDefaultData){

        if(isDefaultData){
            initDeafultdata();
        }else{
            // when datasetis not installed
        }
        ismDefaultData = isDefaultData;
        this.templateSelected = templateSelected;

        client_logo = new String[6];

    }

    public boolean isDefaultDataToCreateCampaign(){

        return ismDefaultData;
    }

    @Override
    public List<Integer> getDefaultDrawableResourceId() {
        return null;
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

        title = "Client";
        /*headerClient = "Totam Aperiam Consect";
        subHeaderClient = "There are many variation of passages";
        paraClient = "There are many variation of passage " +
                "Lorem Ipsum available but the majority have" +
                " in some form bye inject humour";*/

    }

    @Override
    public Class getIntentToLaunchPage() {
        return ClientsOnapp.class;
    }

    @Override
    public Fragment getFragmentToLaunchPage() {
        return new FragmentClientsOnApp();
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

    public String getClient_logo(int pos){
        String url = client_logo[pos];
        return url;
    }

    public int getAbsoluteValueOfLogoPosition(int position){
        int retVal = position;
        int y = 0,j=0;
        try {
            for (int i = 0; i <= position;) {
                if (client_logo[j] != null &&client_logo[j].equals(MainActivity.CROSS_BUTTON_HIDE)) {
                    retVal++;
                }else{
                    i++;
                }
                j++;
            }
        }catch(NullPointerException e){
            Log.d(TAG,"getAbsoluteValueOfLogoPosition");
        }

        return retVal;
    }

    public void setClient_logo(String client_url,int pos){
        client_logo[pos] = client_url;
    }




}
