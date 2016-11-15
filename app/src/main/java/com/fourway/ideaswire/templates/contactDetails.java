package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;

/**
 * Created by Vaibhav Gusain on 9/9/2016.
 */

public class contactDetails extends pages {
    contactDetailsDataTemplate dataObj = null;
    public int mTemplateType = 1;

    public contactDetails(){
        if (nameis == null) {
            nameis = "Contact";
        }
    }


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

    @Override
    public pages getNewPage() {
        return new contactDetails();
    }

    public String nameis ()
    {
        return nameis;
    }

    @Override
    public void set_iconis(int iconOfpage) {

    }

    @Override
    public int iconis() {
        return R.drawable.contact;
    }

    boolean status = true;
    @Override
    public void setPageStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean pageStatus() {
        return status;
    }

    public dataOfTemplate getDataForTemplateAsReceivedFromServer(int position){
        mTemplateType = 1;
        dataObj = (contactDetailsDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new contactDetailsDataTemplate(1, false);

        }
        return dataObj;
    }

    String nameis = null;
    @Override
    public void set_nameis(String nameOfpage) {
        nameis = nameOfpage;
    }
}
