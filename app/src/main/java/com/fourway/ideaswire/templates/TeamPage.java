package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;

/**
 * Created by Vijay on 03-10-2016.
 */
public class TeamPage extends pages {

    TeamDataTemplate dataObj=null;
    public int mTemplateType = 1;

    public TeamPage(){
        if (nameis == null) {
            nameis = "Team";
        }
    }

    @Override
    public dataOfTemplate getDataForTemplate(int templateType) {
        mTemplateType = templateType;
        dataObj=new TeamDataTemplate(templateType,true);
        return dataObj;
    }

    @Override
    public dataOfTemplate getDataForTemplateAsReceivedFromServer() {
        mTemplateType = 1;

     //  dataObj= (TeamDataTemplate) MainActivity.listOfTemplateDataObj.get(position);
        return dataObj;
    }

    @Override
    public pages getNewPage() {
        return new TeamPage();
    }

    String nameis = null;
    @Override
    public void set_nameis(String nameOfpage) {
        nameis = nameOfpage;
    }

    @Override
    public String nameis() {
        return nameis;
    }

    @Override
    public void set_iconis(int iconOfpage) {

    }

    @Override
    public int iconis() {
        return R.drawable.team;
    }
}
