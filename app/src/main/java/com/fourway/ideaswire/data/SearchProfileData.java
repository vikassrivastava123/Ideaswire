package com.fourway.ideaswire.data;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Vikas on 7/23/2016.
 */

public class SearchProfileData {
    private File mImageFile;
    private String mFileName;
    private String mToken;
    private String mErrorMessage;
    private ArrayList<Profile> mProfileList;
    private int mTotalNumberOfProfiles = 0;

    public SearchProfileData(File img, String filename, String token){
        mImageFile = img; mToken = token; mFileName = filename;
    }

    public String getAppKey (){ return  mToken;}
    public String getFileName (){return mFileName;}
    public File getImageFile (){return mImageFile;}
    public void setErrorMessage (String msg){mErrorMessage = msg;}
    public String getErrorMessage(){return mErrorMessage;}

    public void addProfile(Profile p) {mProfileList.add(mTotalNumberOfProfiles++, p);}
    public ArrayList<Profile> getProfileList (){return mProfileList;}
    public Profile getProfileAtIndex (int i) {return (i < mTotalNumberOfProfiles) ? mProfileList.get(i): null;}
    public int getTotalNumOfProfiles (){return mTotalNumberOfProfiles;}
}
