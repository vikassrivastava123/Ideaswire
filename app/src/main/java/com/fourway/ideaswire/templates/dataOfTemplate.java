package com.fourway.ideaswire.templates;

import android.app.Fragment;

import com.fourway.ideaswire.ui.MainActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ritika on 8/24/2016.
 */
public abstract class dataOfTemplate implements Serializable {

    transient List<pages> listOfFooter = MainActivity.listOfTemplatePagesObj;
    String header;

    void setHeader(String header){
        this.header = header;
     }

    public String getHeader(){
         return header;
    }

    abstract public Class getIntentToLaunchPage();
    abstract public Fragment getFragmentToLaunchPage();
    abstract public boolean isDefaultDataToCreateCampaign();
    abstract public int getTemplateSelected();

    public int getPositionInList(){
        int test = 0;
        int size = MainActivity.listOfTemplatePagesObj.size();
        for(test = 0;test<size;test++){
            if(this.equals(MainActivity.listOfTemplatePagesObj.get(test).getDataForTemplate(1)) == true){
                return test;
            }
       }
        return -1;
    }

}
