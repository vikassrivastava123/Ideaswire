package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;

/**
 * Created by Vaibhav Gusain on 9/9/2016.
 */
public class blogpage extends pages {
    blogpageDataTemplate dataObj = null;
    public int mTemplateType = 1;
    public blogpage(){
        if (nameis == null) {
            nameis = "Blog";
        }
    }

    @Override
    public dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = (blogpageDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new blogpageDataTemplate(templateType, true);

        }
        return dataObj;
    }

    String nameis = null;
    @Override
    public String nameis ()
    {
        return nameis;
    }

    @Override
    public void set_iconis(int iconOfpage) {

    }

    @Override
    public int iconis() {
        return R.drawable.blog;
    }

    public dataOfTemplate getDataForTemplateAsReceivedFromServer(){
        mTemplateType = 1;
        dataObj = (blogpageDataTemplate) getAlreadyCreatedDataObj();
        if(dataObj == null) {
            dataObj = new blogpageDataTemplate(1, false);

        }
        return dataObj;
    }

    @Override
    public pages getNewPage() {
        return new blogpage();
    }

    @Override
    public void set_nameis(String nameOfpage) {
        nameis = nameOfpage;
    }
}
