package com.fourway.ideaswire.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static com.fourway.ideaswire.data.Attribute.ContentType.CONTENT_TYPE_AUDIO;
import static com.fourway.ideaswire.data.Attribute.ContentType.CONTENT_TYPE_IMAGE;
import static com.fourway.ideaswire.data.Attribute.ContentType.CONTENT_TYPE_TEXT;
import static com.fourway.ideaswire.data.Attribute.ContentType.CONTENT_TYPE_VIDEO;

/**
 * Created by Vikas on 8/23/2016.
 */

public class Attribute {
    private static final String CONTENT_TYPE_AUDIO_STR = "AUDIO";
    private static final String CONTENT_TYPE_VIDEO_STR = "VIDEO";
    private static final String CONTENT_TYPE_IMAGE_STR = "IMAGE";
    private static final String CONTENT_TYPE_TEXT_STR = "TEXT";

    public enum ContentType  {
        CONTENT_TYPE_TEXT,
        CONTENT_TYPE_IMAGE,
        CONTENT_TYPE_VIDEO,
        CONTENT_TYPE_AUDIO,

        CONTENT_TYPE_END // WARNING: Add all request types above this line only
    }

    public static final String ATTRIBUTE_JSON_TAG_ID = "attr_id";
    public static final String ATTRIBUTE_JSON_TAG_NAME = "attr_name";
    public static final String ATTRIBUTE_JSON_TAG_VALUE = "attr_value";
    public static final String ATTRIBUTE_JSON_TAG_CONTENT_TYPE = "content_type";
    public static final String ATTRIBUTE_JSON_TAG_CONTENT_URL = "content_url";
    public static final String ATTRIBUTE_JSON_TAG_PARENT_ID = "parent_id";
    public static final String ATTRIBUTE_JSON_TAG_PROFILE_ID = "profile_id";

    private static long mContentId = 0;
    private String mId;
    private String mProfileId;
    private String mParentId;
    private String mName;
    private String mValue;
    private ContentType mType;
    private String mUrl;

    //Hint: When creating an image attribute you first need to upload image and get its url
    public Attribute(String profile_id, String parent_id, String name, String value, ContentType type, String img_url){
        mId = mProfileId + String.valueOf(mContentId); mProfileId = profile_id; mParentId = parent_id;
        mName = name; mValue = value; mType = type; mUrl = img_url;
        mContentId++;
    }

    public Attribute(String profile_id, String parent_id, String name, String value){
        mId = mProfileId + String.valueOf(mContentId); mProfileId = profile_id; mParentId = parent_id;
        mName = name; mValue = value;  mType = CONTENT_TYPE_TEXT;
        mContentId++;
    }
    public Attribute(){}

    public String getContentId (){return mId;}
    public String getProfileId (){return mProfileId;}
    public String getParentId (){return mParentId;}
    public String getContentNme(){return mName;}
    public String getContentValue (){return mValue;}
    public ContentType getContentType () {return mType;}
    public String getImageUrl (){return mUrl;}

    public static String getContentTypeString (ContentType type){
        String str;
        switch (type){
            case CONTENT_TYPE_AUDIO:
                str = CONTENT_TYPE_AUDIO_STR;
                break;
            case CONTENT_TYPE_VIDEO:
                str = CONTENT_TYPE_VIDEO_STR;
                break;
            case CONTENT_TYPE_IMAGE:
                str = CONTENT_TYPE_IMAGE_STR;
                break;
            case CONTENT_TYPE_TEXT:
                str = CONTENT_TYPE_TEXT_STR;
                break;
            default:
                str = CONTENT_TYPE_TEXT_STR;
        }
        return str;
    }

    public static ContentType getContentTypeFromStr (String str){
        ContentType type = CONTENT_TYPE_TEXT;
        if (str.equals(CONTENT_TYPE_AUDIO_STR)) type = CONTENT_TYPE_AUDIO;
        if (str.equals(CONTENT_TYPE_VIDEO_STR)) type = CONTENT_TYPE_VIDEO;
        if (str.equals(CONTENT_TYPE_IMAGE_STR)) type = CONTENT_TYPE_IMAGE;
        return type;
    }

    public JSONObject getAttributeJsonObject () throws JSONException {
        JSONObject attr = new JSONObject();
        attr.put(ATTRIBUTE_JSON_TAG_ID, mId);
        attr.put(ATTRIBUTE_JSON_TAG_NAME, mName);
        attr.put(ATTRIBUTE_JSON_TAG_VALUE, mValue);
        attr.put(ATTRIBUTE_JSON_TAG_CONTENT_TYPE, mType);
        attr.put(ATTRIBUTE_JSON_TAG_CONTENT_URL, mUrl);
        attr.put(ATTRIBUTE_JSON_TAG_PARENT_ID, mParentId);
        attr.put(ATTRIBUTE_JSON_TAG_PROFILE_ID, mProfileId);
        return attr;
    }

    public static Attribute getAttributeFromJSONObject(JSONObject json) throws JSONException {
        Attribute attr = null;
        String id = json.getString(ATTRIBUTE_JSON_TAG_ID);
        String name = json.getString(ATTRIBUTE_JSON_TAG_NAME);
        String value = json.getString(ATTRIBUTE_JSON_TAG_VALUE);
        String type = json.getString(ATTRIBUTE_JSON_TAG_CONTENT_TYPE);
        String p_id = json.getString(ATTRIBUTE_JSON_TAG_PARENT_ID);
        String pro_id = json.getString(ATTRIBUTE_JSON_TAG_PROFILE_ID);

        ContentType c_type = getContentTypeFromStr (type);
        attr = new Attribute();
        attr.mId = id; attr.mType = c_type; attr.mParentId = p_id; attr.mName = name;
        attr.mProfileId = pro_id; attr.mValue = value;

        if (c_type == CONTENT_TYPE_IMAGE){
            String url = json.getString(ATTRIBUTE_JSON_TAG_CONTENT_URL);
            attr.mUrl = url;
        }
        return attr;
    }
}
