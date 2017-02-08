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
    static int mSelectedTemplate = 0;

    static int mSelectedLayout = 0;

    void setHeader(String header){
        this.header = header;
     }

    public String getHeader(){
         return header;
    }

    abstract public Fragment getFragmentToLaunchPage();
    abstract public boolean isDefaultDataToCreateCampaign();
    abstract public List<Integer> getDefaultDrawableResourceId();

    /**
     * temporary method
     * @param temp
     */
    abstract public void setTemplateByServer(int temp);
    abstract public int getTemplateByServer();
    abstract public int getLayoutByServer();
    abstract public void setLayoutByServer(int layout);
    public int getTemplateSelected(){
        return mSelectedTemplate;
    }

    public static void setTemplateSelected(int selectedTemplate){
        mSelectedTemplate = selectedTemplate;
    }

    public static int getTemplateSelectedByUser(){
        return mSelectedTemplate;
    }

    public static void setLayoutSelected(int selectedLayout){
        mSelectedLayout = selectedLayout;
    }

    public static int getLayoutSelected(){
        return mSelectedLayout;
    }


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

    /**
     * isEditUpdate return true if it's update mode or edit mode
     * @return
     */
    public boolean isEditOrUpdateMode() {
        boolean editUpdate = false;
        if (mUpdateProfile || mEditMode) {
            editUpdate = true;
        }
        return editUpdate;
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
