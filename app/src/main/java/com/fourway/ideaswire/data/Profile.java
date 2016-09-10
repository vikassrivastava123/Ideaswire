package com.fourway.ideaswire.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public enum TemplateID  {
        PROFILE_TEMPLATE_ID_NOT_SET,
        PROFILE_TEMPLATE_ID_T1,
        PROFILE_TEMPLATE_ID_T2,
        PROFILE_TEMPLATE_ID_T3,
        PROFILE_TEMPLATE_ID_T4,

        PROFILE_TEMPLATE_ID_END // WARNING: Add all request types above this line only
    }
    public static final String PROFILE_PAGE_ARRAY_NAME = "attributes";
    public static final String PROFILE_DATA_JSON_TAG = "Profile_data";


    private static final String TEMPLATE_STRING_T1 = "t1";
    private static final String TEMPLATE_STRING_T2 = "t2";
    private static final String TEMPLATE_STRING_T3 = "t3";
    private static final String TEMPLATE_STRING_T4 = "t4";
    private static final String TEMPLATE_STRING_NOT_SET = "not set";

    private String mId;
    private String mSaveResponseErrorMessage;
    private TemplateID mTemplateId;
    private String mImageUrl; //used in search result
    private String mTemplateString;
    private int mTotalNumberOfPages = 0;
    private ProfileStatus mStatus = PROFILE_STATUS_NOT_SET;
    private ArrayList <Page> mPages;
    private String mProfileName;
    private String mProfileCategory;
    private String mProfileType;
    private String mProfileDepartment;


    public Profile (String profile_id, TemplateID template_Id){
        mId = profile_id; mTemplateId = template_Id; setTemplateString();
        mPages = new ArrayList<>();
    }

    public void setProfileName (String s) {mProfileName =s;}
    public void setProfileCategory (String s) {mProfileCategory =s;}
    public void setProfileDepartment (String s) {mProfileDepartment =s;}
    public void setProfileType (String s) {mProfileType =s;}

    public String getProfileName (){return mProfileName;}
    public String getProfileCategory(){return mProfileCategory;}
    public String getProfileType(){return mProfileType;}
    public String getProfileDepartment(){return mProfileDepartment;}

    public void setProfileStatus (ProfileStatus s) {mStatus = s;}
    public ProfileStatus getProfileStatus (){return mStatus;}
    public void setErrorMessage (String s) {mSaveResponseErrorMessage =s;}
    public String getErrorMessage() {return mSaveResponseErrorMessage;}

    public void addPage (Page p){
        mPages.add(mTotalNumberOfPages, p);
        mTotalNumberOfPages++;
    }

    public void removePage(String page_id){
        for (int i=0; i< mTotalNumberOfPages; i++){
            Page p = mPages.get(i);
            if (p.getPageId().equals(page_id)){
                mPages.remove(i);
                mTotalNumberOfPages--;
                break;
            }
        }
    }

    public Page getPageAtIndex (int i){
        if (i >= mTotalNumberOfPages) {
            return null;
        }
        return mPages.get(i);
    }
    public ArrayList<Page> getAllPages(){
        return mPages;
    }

    public String getProfileId (){return mId;}
    public void setImageUrl (String u) {mImageUrl = u;}
    public String getImageUrl (){return mImageUrl;}
    public TemplateID getTemplateId (){return mTemplateId;}
    public TemplateID getTemplateId (String str) {return getTemplateIdFromString (str);}

    public static ProfileStatus getStatusFromString (String s){
        if ("ACTIVE".equals(s)) {return PROFILE_STATUS_ACTIVE;}
        if ("ARCHIVE".equals(s)) {return PROFILE_STATUS_ARCHIVE;}
        if ("CREATED".equals(s)) {return PROFILE_STATUS_CREATED;}
        if ("NOT CREATED".equals(s)) {return PROFILE_STATUS_NOT_CREATED;}
        if ("DELETED".equals(s)) {return PROFILE_STATUS_DELETED;}
        return PROFILE_STATUS_NOT_SET;
    }

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
            case PROFILE_TEMPLATE_ID_NOT_SET:
                mTemplateString = TEMPLATE_STRING_NOT_SET;
                break;
            default:
                mTemplateString = TEMPLATE_STRING_T1;
                break;
        }
    }

    public static TemplateID getTemplateIdFromString(String template_id) {
        if (template_id.equals(TEMPLATE_STRING_T1)) {return TemplateID.PROFILE_TEMPLATE_ID_T1;}
        if (template_id.equals(TEMPLATE_STRING_T2)) {return TemplateID.PROFILE_TEMPLATE_ID_T2;}
        if (template_id.equals(TEMPLATE_STRING_T3)) {return TemplateID.PROFILE_TEMPLATE_ID_T3;}
        if (template_id.equals(TEMPLATE_STRING_T4)) {return TemplateID.PROFILE_TEMPLATE_ID_T4;}
        if (template_id.equals(TEMPLATE_STRING_NOT_SET)) {return TemplateID.PROFILE_TEMPLATE_ID_NOT_SET;}
        return TemplateID.PROFILE_TEMPLATE_ID_T1;
    }

    public JSONArray createJSONArray () throws JSONException {
        JSONArray ja = new JSONArray();
        for (int i=0; i<mTotalNumberOfPages; i++){
            ja.put(i, mPages.get(i).getPageJSONObject());
        }
        return ja;
    }

    public static ArrayList<Page> createPageListFromJSONArray(JSONArray ja, Profile profile){
        ArrayList<Page> pages = new ArrayList<>();
        int size = ja.length();
        for (int i=0; i<size; i++){
            try {
                Page p = new Page(profile.getProfileId(), profile.getProfileName());
                Page.getPageDataFromJSONObject(ja.getJSONObject(i), p);
                pages.add(i, p);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return pages;
    }

    public void addAllPagesToList (JSONArray ja, Profile p){
        mPages = createPageListFromJSONArray(ja, p);
    }
}
