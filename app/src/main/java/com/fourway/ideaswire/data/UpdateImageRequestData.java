package com.fourway.ideaswire.data;

import java.io.File;

/**
 * Created by Vikas on 8/27/2016.
 */

public class UpdateImageRequestData {
    private String mToken;
    private String mProfileId;
    private File mImageData;
    private String mProfileName;

    private String mErrorMessage;

    public UpdateImageRequestData (String token, String p_id, String p_name, File img){
        mImageData = img; mProfileId = p_id; mToken = token; mProfileName = p_name;
    }
    public void setErrorMessage (String msg) {mErrorMessage = msg;}
    public String getErrorMessage (){return mErrorMessage;}
    public File getImageData(){return mImageData;}
    public String getProfileId (){return mProfileId;}
    public String getProfileName(){return mProfileName;}
    public String getAccessToken(){return  mToken;}
}
