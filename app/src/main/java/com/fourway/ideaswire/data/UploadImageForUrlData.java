package com.fourway.ideaswire.data;

import java.io.File;

/**
 * Created by Vikas on 9/9/2016.
 */

public class UploadImageForUrlData {
    private String mAppKey;
    private String mCampaignId;
    private String mErrorMessage;
    private int mUniqueSequenceNumber;
    private File mImageData;
    private String mResponseUrl;
    private String mImageName;

    public UploadImageForUrlData (
            String token, String campaign_id, File img_data, String image_name, int unique_seq_number){
        mAppKey = token; mCampaignId = campaign_id; mUniqueSequenceNumber = unique_seq_number;
        mImageData = img_data; mImageName = image_name;
    }

    public void setAccessToken (String key) {mAppKey = key;}
    public void setCampaignId (String id) {mCampaignId = id;}
    public void setErrorMessage (String msg) {mErrorMessage = msg;}
    public void setResponseUrl (String url) {mResponseUrl = url;}
    public void setSequenceNumber (int num) {mUniqueSequenceNumber = num;}

    public String getAccessToken(){return mAppKey;}
    public String getCampaignId(){return mCampaignId;}
    public String getErrorMessage(){return mErrorMessage;}
    public int getUniqueSequenceNumber(){return mUniqueSequenceNumber;}
    public File getImageData(){return mImageData;}
    public String getResponseUrl(){return  mResponseUrl;}
    public String getImageName(){return  mImageName;}
}
