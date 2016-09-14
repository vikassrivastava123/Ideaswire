package com.fourway.ideaswire.templates;

/**
 * Created by Vaibhav Gusain on 9/9/2016.
 */
public class blogpage extends pages {
    blogpageDataTemplate dataObj = null;
    public int mTemplateType = 1;
    @Override
    dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = new blogpageDataTemplate(templateType,true);
        return dataObj;
    }
    @Override
    public String nameis ()
    {
        return "Blog Page";
    }
    dataOfTemplate getDataForTemplateAsReceivedFromServer(){
        mTemplateType = 1;
        dataObj = new blogpageDataTemplate(1,false);
        return dataObj;
    }

    @Override
    public void set_nameis(String nameOfpage) {

    }
}
