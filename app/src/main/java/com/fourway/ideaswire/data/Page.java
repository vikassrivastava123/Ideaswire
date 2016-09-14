package com.fourway.ideaswire.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Vikas on 8/25/2016.
 */

public class Page {
    private static final String PAGE_JSON_TAG_NAME = "Name";
    private static final String PAGE_JSON_TAG_PAGE_ATTRIBUTES = "pageAttributes";
    private static int mPageIdGenerator = 0;
    private String mId;
    private String mProfileId;
    private String mName;
    private ArrayList <Attribute> mAttributes;
    private int mIndex = 0;

    public  Page (String profile_id, String name){
        mProfileId = profile_id; mName = name;
        mId = profile_id + String.valueOf(mPageIdGenerator);
        mAttributes = new ArrayList<>();
        mPageIdGenerator++;

    }

    public String getPageId () {return mId;}

    public void addAttribute (Attribute attr){
        mAttributes.add(mIndex, attr);
    }

    public ArrayList<Attribute> getAttributes (){return mAttributes;}

    public JSONObject getPageJSONObject () throws JSONException {
        JSONObject page = new JSONObject();
        page.put(PAGE_JSON_TAG_NAME, mName);

        JSONArray pageAttributes = new JSONArray();

        int size = mAttributes.size();
        for (int i =0; i < size ; i++){
            Attribute attr = mAttributes.get(i);
            pageAttributes.put(i, attr.getAttributeJsonObject());
        }
        page.put(PAGE_JSON_TAG_PAGE_ATTRIBUTES, pageAttributes);

        return page;
    }

    public static Page getPageDataFromJSONObject (JSONObject js, Page p) throws JSONException {
        Page page = null;
        String name = js.getString(PAGE_JSON_TAG_NAME);
        JSONArray pageAttributes = js.getJSONArray(PAGE_JSON_TAG_PAGE_ATTRIBUTES);
        int size = pageAttributes.length();
        for (int i=0; i<size; i++)
        {
            Attribute attr = Attribute.getAttributeFromJSONObject(pageAttributes.getJSONObject(i));
            p.mAttributes.add(i, attr);
        }
        return page;
    }
    public String getPageName(){
        return mName;
    }


}
