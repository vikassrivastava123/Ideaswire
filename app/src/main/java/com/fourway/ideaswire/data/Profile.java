package com.fourway.ideaswire.data;

import java.util.ArrayList;

import static com.fourway.ideaswire.data.Profile.ProfileStatus.PROFILE_STATUS_ACTIVE;
import static com.fourway.ideaswire.data.Profile.ProfileStatus.PROFILE_STATUS_ARCHIVE;
import static com.fourway.ideaswire.data.Profile.ProfileStatus.PROFILE_STATUS_CREATED;
import static com.fourway.ideaswire.data.Profile.ProfileStatus.PROFILE_STATUS_DELETED;
import static com.fourway.ideaswire.data.Profile.ProfileStatus.PROFILE_STATUS_NOT_CREATED;
import static com.fourway.ideaswire.data.Profile.ProfileStatus.PROFILE_STATUS_NOT_SET;

/**
 * Created by Vikas on 8/23/2016.
 */

public class Profile {

    public enum ProfileStatus  {
        PROFILE_STATUS_NOT_SET,
        PROFILE_STATUS_ACTIVE,
        PROFILE_STATUS_ARCHIVE,
        PROFILE_STATUS_CREATED,
        PROFILE_STATUS_NOT_CREATED,
        PROFILE_STATUS_DELETED,

        PROFILE_STATUS_END // WARNING: Add all request types above this line only
    }
    private String mId;
    private CreateProfileData.TemplateID mTemplateId;
    private String mImageName;
    private String mImageCategory;
    private String mImageDepartment;
    private String mImageUrl;
    private String mImageKey;
    private int mTotalNumberOfContent = 0;
    private ProfileStatus mStatus = PROFILE_STATUS_NOT_SET;
    private ArrayList <ProfileContent> mContentList;


    public Profile (String id, String template_Id, String img_name, String img_cat, String img_dept,
                    String img_url, String img_key){
        mId = id; mTemplateId = CreateProfileData.getTemplateIdFromString(template_Id);
        mImageName = img_name; mImageCategory = img_cat; mImageDepartment = img_dept;
        mImageUrl = img_url; mImageKey = img_key;
    }

    public void setProfileStatus (ProfileStatus s) {mStatus = s;}
    public ProfileStatus getProfileStatus (){return mStatus;}

    public void addContent (ProfileContent c){
        mContentList.add(mTotalNumberOfContent, c);
        mTotalNumberOfContent++;
    }

    public ProfileContent getContentAtIndex (int i){
        if (i >= mTotalNumberOfContent) {
            return null;
        }
        return mContentList.get(i);
    }

    public ArrayList<ProfileContent> getContentList(){
        return mContentList;
    }

    public String getImageName (){return mImageName;}
    public String getImageCategory (){return mImageCategory;}
    public String getImageDepartment (){return mImageDepartment;}
    public String getImageUrl (){return mImageUrl;}
    public String getImageKey (){return mImageKey;}
    public String getProfileId (){return mId;}

    public static ProfileStatus getStatusFromString (String s){
        if ("ACTIVE".equals(s)) {return PROFILE_STATUS_ACTIVE;}
        if ("ARCHIVE".equals(s)) {return PROFILE_STATUS_ARCHIVE;}
        if ("CREATED".equals(s)) {return PROFILE_STATUS_CREATED;}
        if ("NOT CREATED".equals(s)) {return PROFILE_STATUS_NOT_CREATED;}
        if ("DELETED".equals(s)) {return PROFILE_STATUS_DELETED;}
        return PROFILE_STATUS_NOT_SET;
    }
}
