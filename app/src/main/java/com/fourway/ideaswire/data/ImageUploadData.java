package com.fourway.ideaswire.data;

import java.io.File;

/**
 * Created by Vikas on 7/23/2016.
 */

public class ImageUploadData {
    private File mImageFile;
    private String mFileName;
    private String mToken;
    private String mErrorMessage;

    public ImageUploadData (File img, String filename, String token){
        mImageFile = img; mToken = token; mFileName = filename;
    }

    public String getAppKey (){ return  mToken;}
    public String getFileName (){return mFileName;}
    public File getImageFile (){return mImageFile;}

    public void setErrorMessage (String msg){mErrorMessage = msg;}
    public String getErrorMessage(){return mErrorMessage;}
}
