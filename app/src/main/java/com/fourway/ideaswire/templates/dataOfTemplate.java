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
    static boolean mEditMode = false , mUpdateProfile = false , mIsPriviewMode = false;

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

    public boolean isEditMode(){
        return mEditMode;
    }

    /**
    * mUpdateProfile : Do not change this value from pages as need to know it is for update or new page edit request
    * mEditMode : When user creates new profile it should be in edit mode .
    */

    public boolean isEditDefaultOrUpdateData() {
        boolean bIneditMode = false;
        if((mUpdateProfile || mEditMode) && mIsPriviewMode == false ) {
            bIneditMode = true;
        }
            return bIneditMode;

    }

    public void setEditMode(boolean tf){
        mEditMode = tf;
    }

    public void setPriviewMode(boolean tf){
        mIsPriviewMode = tf;
    }

    public boolean isInUpdateProfileMode(){
        return mUpdateProfile;
    }

    public void  setIsInUpdateProfileMode(boolean argUpdateProfile){
        mUpdateProfile  = argUpdateProfile;
    }

 }
