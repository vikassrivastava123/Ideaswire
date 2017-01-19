package com.fourway.ideaswire.data;

import java.io.File;

/**
 * Created by 4way on 18-01-2017.
 */

public class UpdateProfileData {

    private File mImageData;
    private String mProfileName;
    private String mProfileCategory;
    private String mProfileDepartment;
    private String mAccessToken;
    private String mErrorMessage;
    private String mProfileId;
    private File mVideoFile;
    private ProfileType mProfileType;
    private ProfileStatus mProfileStatus;
    private String xImageProfileId;



    public enum ProfileType{
        PROFILE_TYPE_LOGO, //TEXT to server is "LOGO"
        PROFILE_TYPE_INDIVIDUAL, //TEXT to server is "INDIVIDUAL"

        PROFILE_TYPE_END
    }

    public enum ProfileStatus{
        PROFILE_STATUS_DRAFT, //TEXT to server is "DRAFT"
        PROFILE_STATUS_ACTIVE, //TEXT to server is "ACTIVE"

        PROFILE_STATUS_END
    }

    public UpdateProfileData(String xImage_ProfileId, String profile_name, String profile_category, String profile_department,
                             String token, File img)
    {
        mProfileName = profile_name; mProfileCategory = profile_category;
        mProfileDepartment = profile_department; mVideoFile = null;
        mAccessToken = token; mImageData = img; mProfileId = null;
        mProfileType = ProfileType.PROFILE_TYPE_LOGO; mProfileStatus = ProfileStatus.PROFILE_STATUS_ACTIVE;
        xImageProfileId = xImage_ProfileId;
    }

    public UpdateProfileData(String xImage_ProfileId, String profile_name, String profile_category, String profile_department,
                             String token, File img, ProfileType p_type, ProfileStatus p_status)
    {
        mProfileName = profile_name; mProfileCategory = profile_category;
        mProfileDepartment = profile_department; mVideoFile = null;
        mAccessToken = token; mImageData = img; mProfileId = null;
        mProfileType = p_type; mProfileStatus = p_status;
        xImageProfileId = xImage_ProfileId;
    }

    public UpdateProfileData(String xImage_ProfileId, String profile_name, String profile_category, String profile_department,
                             String token, File img, File video)
    {
        mProfileName = profile_name; mProfileCategory = profile_category;
        mProfileDepartment = profile_department; mVideoFile = video;
        mAccessToken = token; mImageData = img; mProfileId = null;
        mProfileType = ProfileType.PROFILE_TYPE_INDIVIDUAL;
        mProfileStatus = ProfileStatus.PROFILE_STATUS_ACTIVE;
        xImageProfileId = xImage_ProfileId;
    }

    public String getProfileName(){return mProfileName;}
    public String getProfileCategory(){return mProfileCategory;}
    public String getProfileDepartment(){return mProfileDepartment;}
    public String getAccessToken () {return mAccessToken;}
    public File getImageData () {return mImageData;}
    public String getProfileId (){return mProfileId;}
    public String getErrorMessage (){return mErrorMessage;}
    public File getVideoFile (){return mVideoFile;}
    public ProfileType getProfileType(){return mProfileType;}
    public ProfileStatus getProfileStatus(){return mProfileStatus;}
    public String getxImageProfileId() {return xImageProfileId;}

    public void  setErrorMessage (String msg){mErrorMessage = msg;}
    public void setProfileId (String p_id) {mProfileId = p_id;}
}
