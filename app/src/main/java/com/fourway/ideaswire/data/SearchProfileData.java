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
    private String mSearchProfileId;
    private String mErrorMessage;
    private boolean mIsSearchByImage;
    private ArrayList<Profile> mProfileList;
    private int mTotalNumberOfProfiles = 0;

    public SearchProfileData(File img, String filename, String token){
        mImageFile = img; mToken = token; mFileName = filename; mIsSearchByImage = true;
    }

    public SearchProfileData(String search_profile_id, String filename, String token){
        mSearchProfileId = search_profile_id; mToken = token; mFileName = filename;
        mIsSearchByImage = false;
    }

    public String getAppKey (){ return  mToken;}
    public String getFileName (){return mFileName;}
    public File getImageFile (){return mImageFile;}
    public boolean isImageSearch(){return mIsSearchByImage;}

    public void setErrorMessage (String msg){mErrorMessage = msg;}
    public String getErrorMessage(){return mErrorMessage;}

    public void addProfile(Profile p) {mProfileList.add(mTotalNumberOfProfiles++, p);}
    public ArrayList<Profile> getProfileList (){return mProfileList;}
    public Profile getProfileAtIndex (int i) {return (i < mTotalNumberOfProfiles) ? mProfileList.get(i): null;}
    public int getTotalNumOfProfiles (){return mTotalNumberOfProfiles;}
}
