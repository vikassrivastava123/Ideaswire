package com.fourway.ideaswire.data;

/**
 * Created by Vikas on 8/23/2016.
 */

public class ProfileStatusRequestData {
    private String mAccessToken;
    private String mProfileId;
    private Profile.ProfileStatus mStatus;
    private String mErrorMessage;

    public ProfileStatusRequestData (String token, String p_id){mAccessToken = token; mProfileId = p_id;}
    public void setStatus (Profile.ProfileStatus s) {mStatus = s;}
    public Profile.ProfileStatus getStatus (){return mStatus;}
    public void setErrorMessage (String m) {mErrorMessage = m;}
    public String getErrorMessage (){return mErrorMessage;}
    public String getToken(){return mAccessToken;}
    public String getProfileId (){return mProfileId;}
}
