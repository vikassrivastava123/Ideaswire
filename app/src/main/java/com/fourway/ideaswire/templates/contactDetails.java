package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.MainActivity;

/**
 * Created by Vaibhav Gusain on 9/9/2016.
 */

public class contactDetails extends pages {
    contactDetailsDataTemplate dataObj = null;
    public int mTemplateType = 1;


    @Override
    public dataOfTemplate getDataForTemplate(int templateType) {

        mTemplateType = templateType;
        dataObj = (contactDetailsDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new contactDetailsDataTemplate(templateType, true);

        }
        return dataObj;

    }

    @Override
    public dataOfTemplate getDataForTemplateAsReceivedFromServer() {
        mTemplateType = 1;
        dataObj = (contactDetailsDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new contactDetailsDataTemplate(1, false);

        }
        return dataObj;
    }

     public String nameis ()
    {
        return "Contact";
    }

    @Override
    public void set_iconis(int iconOfpage) {

    }

    @Override
    public int iconis() {
        return R.drawable.contact;
    }

    public dataOfTemplate getDataForTemplateAsReceivedFromServer(int position){
        mTemplateType = 1;
        dataObj = (contactDetailsDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new contactDetailsDataTemplate(1, false);

        }
        return dataObj;
    }

    @Override
    public void set_nameis(String nameOfpage) {

    }
}
