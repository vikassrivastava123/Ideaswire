package com.fourway.ideaswire.templates;

/**
 * Created by Ritika on 8/24/2016.
 */
public class AboutUsPage extends pages {

    AboutUsDataTemplate dataObj = null;
    public int mTemplateType = 1;
    @Override
    dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = new AboutUsDataTemplate(templateType,true);
        return dataObj;
    }
    @Override
    public String nameis()
    {
        return "About Us 2";
    }
}
