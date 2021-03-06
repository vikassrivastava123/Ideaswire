package com.fourway.ideaswire.data;

import java.util.ArrayList;

/**
 * Created by Vikas on 8/27/2016.
 */

public class GetUserProfileRequestData {
    private ArrayList<Profile> mProfileList;
    private String mToken;
    private String mErrorString;
    private int mTotalNumberOfProfiles = 0;

    public GetUserProfileRequestData (String token){
        mToken = token;
        mProfileList = new ArrayList<>();
    }

    public String getAccessToken(){return mToken;}
    public void setErrorMessage (String s) {mErrorString = s;}
    public String getErrorMessage (){return mErrorString;}
    public ArrayList<Profile> getProfileList (){return mProfileList;}
    public void addProfile(Profile p) {mProfileList.add(mTotalNumberOfProfiles++, p);}
    public Profile getProfileAtIndex (int i) {return (i < mTotalNumberOfProfiles) ? mProfileList.get(i): null;}
}
