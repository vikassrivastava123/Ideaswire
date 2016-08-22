package com.fourway.ideaswire.data;

/**
 * Created by Vikas on 8/23/2016.
 */

public class ProfileContent {
    public enum ContentType  {
        CONTENT_TYPE_IMAGE,
        CONTENT_TYPE_VIDEO,
        CONTENT_TYPE_AUDIO,

        CONTENT_TYPE_END // WARNING: Add all request types above this line only
    }

    private String mId;
    private String mProfileId;
    private String mUrl;
    private ContentType mContentType;

    public ProfileContent (ContentType type, String id, String profile_id, String url){
        mContentType = type; mId= id; mProfileId = profile_id; mUrl = url;
    }

    public String getContentId (){return mId;}
    public String getProfileId (){return mProfileId;}
    public String getContentUrl(){return mUrl;}
    public ContentType getContentType (){return mContentType;}
}
