package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.MainActivity;

/**
 * Created by Vijay on 03-10-2016.
 */
public class ClientPage extends pages {

    ClientDataTemplate dataObj = null;
    public int mTemplateType = 1;

    public ClientPage(){
        nameis = "Clients";
    }


    @Override
    public dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = new ClientDataTemplate(templateType,true);
        return dataObj;
    }

    @Override
    public dataOfTemplate getDataForTemplateAsReceivedFromServer() {
        mTemplateType = 1;
     //   dataObj = (ClientDataTemplate) MainActivity.listOfTemplateDataObj.get(position);
        return dataObj;
    }

    @Override
    public void set_nameis(String nameOfpage) {

    }

    String nameis;
    @Override
    public String nameis() {
        return nameis;
    }

    @Override
    public void set_iconis(int iconOfpage) {

    }

    @Override
    public int iconis() {
        return R.drawable.client;
    }
}