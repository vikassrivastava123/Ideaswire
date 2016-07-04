package com.fourway.ideaswire.data;

/**
 * Created by Vikas on 7/4/2016.
 */

public class SignUpData {
    private String mUsername;
    private String mPassword;
    private String mFirstName;
    private String mLastName;
    private String mEmailId;
    private String mMobileNumber;

    private String mResponseMessage;

    public SignUpData (String u_name, String u_password, String f_name,
                       String l_name, String email_id, String m_number){
        mUsername = u_name; mPassword = u_password; mFirstName = f_name;
        mLastName = l_name; mEmailId = email_id; mMobileNumber = m_number;
    }

    public String getUserName (){
        return mUsername;
    }

    public String getPassword (){
        return mPassword;
    }

    public String getEmailId (){  return mEmailId; }

    public String getFirstName(){
        return mFirstName;
    }

    public String getLastName(){
        return mLastName;
    }

    public String geMobileNumber(){
        return mMobileNumber;
    }

    public String geResponseMessage(){
        return mResponseMessage;
    }

    public void seResponseMessage(String msg){
        mResponseMessage = msg;
    }

}
