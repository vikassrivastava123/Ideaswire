package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.MainActivity;

/**
 * Created by Vaibhav Gusain on 9/9/2016.
 */
public class blogpage extends pages {
    blogpageDataTemplate dataObj = null;
    public int mTemplateType = 1;
    @Override
    public dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj = new blogpageDataTemplate(templateType,true);
        return dataObj;
    }
    @Override
    public String nameis ()
    {
        return "Blog";
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
    public void set_nameis(String nameOfpage) {

    }
}
