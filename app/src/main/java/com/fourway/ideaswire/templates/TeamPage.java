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
        dataObj = (TeamDataTemplate) getAlreadyCreatedDataObj();
        if (dataObj == null){
            dataObj=new TeamDataTemplate(templateType,true);
        }
        return dataObj;
    }

    @Override
    public dataOfTemplate getDataForTemplateAsReceivedFromServer() {
        mTemplateType = 1;
        dataObj = (TeamDataTemplate) getAlreadyCreatedDataObj();
     //  dataObj= (TeamDataTemplate) MainActivity.listOfTemplateDataObj.get(position);
        if (dataObj == null){
            dataObj=new TeamDataTemplate(1,false);
        }
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

    boolean status = true;
    @Override
    public void setPageStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean pageStatus() {
        return status;
    }

    int theme;
    @Override
    public void set_theme(int theme) {
        this.theme = theme;
    }

    @Override
    public int themes() {
        return theme;
    }
}
