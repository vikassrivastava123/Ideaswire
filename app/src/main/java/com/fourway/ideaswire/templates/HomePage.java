package com.fourway.ideaswire.templates;

/**
 * Created by Ritika on 8/24/2016.
 */
public class HomePage extends pages {

    AboutUsDataTemplate dataObj = null;
    @Override
    dataOfTemplate getDataForTemplate(int templateType) {

        dataObj = new AboutUsDataTemplate(templateType);
        return dataObj;
    }
}

