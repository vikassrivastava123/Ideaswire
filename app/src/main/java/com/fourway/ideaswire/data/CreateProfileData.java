package com.fourway.ideaswire.data;

import java.io.File;

/**
 * Created by Vikas on 8/22/2016.
 */

public class CreateProfileData {


    public enum TemplateID  {
        PROFILE_TEMPLATE_ID_T1,
        PROFILE_TEMPLATE_ID_T2,
        PROFILE_TEMPLATE_ID_T3,
        PROFILE_TEMPLATE_ID_T4,

        PROFILE_TEMPLATE_ID_END // WARNING: Add all request types above this line only
    }
    private static final String TEMPLATE_STRING_T1 = "t1";
    private static final String TEMPLATE_STRING_T2 = "t2";
    private static final String TEMPLATE_STRING_T3 = "t3";
    private static final String TEMPLATE_STRING_T4 = "t4";

    private String mTemplateString;
    private File mImageData;
    private String mImageName;
    private String mImageCategory;
    private String mImageDepartment;
    private String mAccessToken;
    private String mErrorMessage;
    private TemplateID mTemplateId;

    public CreateProfileData (TemplateID t_id, String img_name, String img_cat, String img_dept, String token,
                              File img)
    {mTemplateId = t_id; mImageName = img_name; mImageCategory = img_cat; mImageDepartment = img_dept;
    mAccessToken = token; mImageData = img;
    setTemplateString ();}

    public String getTemplateId(){return mTemplateString;}
    public String getImageName(){return mImageName;}
    public String getImageCategory(){return mImageCategory;}
    public String getImageDepartment(){return mImageDepartment;}
    public String getAccessToken () {return mAccessToken;}
    public File getImageData () {return mImageData;}
    public String getErrorMessage (){return mErrorMessage;}

    public void  setErrorMessage (String msg){mErrorMessage = msg;}

    private void setTemplateString (){
        switch (mTemplateId){
            case PROFILE_TEMPLATE_ID_T1:
                mTemplateString = TEMPLATE_STRING_T1;
                break;
            case PROFILE_TEMPLATE_ID_T2:
                mTemplateString = TEMPLATE_STRING_T2;
                break;
            case PROFILE_TEMPLATE_ID_T3:
                mTemplateString = TEMPLATE_STRING_T3;
                break;
            case PROFILE_TEMPLATE_ID_T4:
                mTemplateString = TEMPLATE_STRING_T4;
                break;
            default:
                mTemplateString = TEMPLATE_STRING_T1;
                break;
        }
    }

    public static TemplateID getTemplateIdFromString(String template_id) {
        if (template_id.equals("t1")) {return TemplateID.PROFILE_TEMPLATE_ID_T1;}
        if (template_id.equals("t2")) {return TemplateID.PROFILE_TEMPLATE_ID_T2;}
        if (template_id.equals("t3")) {return TemplateID.PROFILE_TEMPLATE_ID_T3;}
        if (template_id.equals("t4")) {return TemplateID.PROFILE_TEMPLATE_ID_T4;}
        return TemplateID.PROFILE_TEMPLATE_ID_T1;
    }
}
