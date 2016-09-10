package com.fourway.ideaswire.data;

import java.io.File;

/**
 * Created by Vikas on 8/22/2016.
 */

public class CreateProfileData {

    private File mImageData;
    private String mProfileName;
    private String mProfileCategory;
    private String mProfileDepartment;
    private String mAccessToken;
    private String mErrorMessage;
    private String mProfileId;
    private File mVideoFile;


    public CreateProfileData (String profile_name, String profile_category, String profile_department,
                              String token, File img)
    {
        mProfileName = profile_name; mProfileCategory = profile_category;
        mProfileDepartment = profile_department; mVideoFile = null;
        mAccessToken = token; mImageData = img; mProfileId = null;
    }

    public CreateProfileData (String profile_name, String profile_category, String profile_department,
                              String token, File img, File video)
    {
        mProfileName = profile_name; mProfileCategory = profile_category;
        mProfileDepartment = profile_department; mVideoFile = video;
        mAccessToken = token; mImageData = img; mProfileId = null;
    }

    public String getProfileName(){return mProfileName;}
    public String getProfileCategory(){return mProfileCategory;}
    public String getProfileDepartment(){return mProfileDepartment;}
    public String getAccessToken () {return mAccessToken;}
    public File getImageData () {return mImageData;}
    public String getProfileId (){return mProfileId;}
    public String getErrorMessage (){return mErrorMessage;}
    public File getVideoFile (){return mVideoFile;}

    public void  setErrorMessage (String msg){mErrorMessage = msg;}
    public void setProfileId (String p_id) {mProfileId = p_id;}


}
