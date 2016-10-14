package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.MainActivity;

/**
 * Created by Ritika on 8/24/2016.
 */
public class HomePage extends pages {

    HomePageDataTemplate dataObj = null;
    public int mTemplateType = 1;
    @Override
    public dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = new HomePageDataTemplate(templateType, true);
        return dataObj;
    }
    public String nameis ()
    {
        return "Home";
    }
    @Override
    public dataOfTemplate getDataForTemplateAsReceivedFromServer() {

        mTemplateType = 1;
        dataObj = (HomePageDataTemplate) getAlreadyCreatedDataObj();
        if (dataObj == null) {
            dataObj = new HomePageDataTemplate(1, false);
        }
        return dataObj;
    }
    @Override
    public void set_iconis(int iconOfpage) {

    }

    @Override
    public int iconis() {
        return R.drawable.home;
    }

    @Override
    public void set_nameis(String nameOfpage) {

    }
}

