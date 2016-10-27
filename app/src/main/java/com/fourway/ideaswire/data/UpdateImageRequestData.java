package com.fourway.ideaswire.data;

import java.io.File;

/**
 * Created by Vikas on 8/27/2016.
 */

public class UpdateImageRequestData {
    private String mToken;
    private String mProfileId;
    private File mImageData;
    private String mProfileName;
    private CreateProfileData.ProfileType mProfileType;
    private CreateProfileData.ProfileStatus mProfileStatus;

    private String mErrorMessage;

    public UpdateImageRequestData (String token, String p_id, String p_name, File img,
                                   CreateProfileData.ProfileType type,
                                   CreateProfileData.ProfileStatus p_status){
        mImageData = img; mProfileId = p_id; mToken = token; mProfileName = p_name; mProfileType = type;
        mProfileStatus = p_status;
    }
    public void setErrorMessage (String msg) {mErrorMessage = msg;}
    public String getErrorMessage (){return mErrorMessage;}
    public File getImageData(){return mImageData;}
    public String getProfileId (){return mProfileId;}
    public String getProfileName(){return mProfileName;}
    public String getAccessToken(){return  mToken;}
    public CreateProfileData.ProfileType getProfileType(){return mProfileType;}
    public CreateProfileData.ProfileStatus getProfileStatus (){return mProfileStatus;}
}
