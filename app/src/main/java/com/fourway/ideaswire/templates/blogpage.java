package com.fourway.ideaswire.templates;

/**
 * Created by Vaibhav Gusain on 9/9/2016.
 */
public class blogpage extends pages {
    blogpageTemplate dataObj = null;
    public int mTemplateType = 1;
    @Override
    dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = new blogpageTemplate(templateType,true);
        return dataObj;
    }
    @Override
    public String nameis ()
    {
        return "Blog Page";
    }
}
