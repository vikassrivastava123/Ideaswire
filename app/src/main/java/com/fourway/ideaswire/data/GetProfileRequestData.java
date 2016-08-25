package com.fourway.ideaswire.data;

/**
 * Created by Vikas on 8/26/2016.
 */

public class GetProfileRequestData {
    private String mAppKey;
    private String mProfileId;
    private Profile mProfile;
    private String mErrorMessage;

    public GetProfileRequestData (String key, String profile_id){
        mAppKey = key; mProfileId = profile_id;
    }

    public void setErrorMessage (String s) {mErrorMessage = s;}
    public String getErrorMessage () {return mErrorMessage;}
    public void setProfile (Profile p) {mProfile = p;}
    public Profile getProfile (){return mProfile;}
    public String getProfileId (){return mProfileId;}
    public String getAppKey(){return mAppKey;}
}
