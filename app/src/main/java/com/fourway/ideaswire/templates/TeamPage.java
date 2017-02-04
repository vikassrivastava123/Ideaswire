package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.ui.MainActivity;

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
    public dataOfTemplate getDataForTemplate() {
        mTemplateType = dataOfTemplate.getTemplateSelectedByUser();
        dataObj = (TeamDataTemplate) getAlreadyCreatedDataObj();
        if (dataObj == null){
            dataObj=new TeamDataTemplate(mTemplateType,true);
        }
        return dataObj;
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

    @Override
    public void set_iconBlack(int iconOfpage) {

    }

    @Override
    public int iconBlack() {
        return R.drawable.team_black;
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

    @Override
    public int getImageForMainPage_BasedOnTemplate_Layout(int temp, int layout) {
        int  imageResourceId = 0;
        if (layout == 1) {
            switch (temp) {
                case MainActivity.TEM_BUSINESS:
                    imageResourceId = R.drawable.business_team_category;
                    break;
                case MainActivity.TEM_INDIVIDUAL:
                    imageResourceId = R.drawable.individual_team_category;
                    break;
                case MainActivity.TEM_FINANCE:
                    imageResourceId = R.drawable.finance_team_category;
                    break;
                case MainActivity.TEM_HEALTH:
                    imageResourceId = R.drawable.health_team_category;
                    break;
                case MainActivity.TEM_ENTERTAINMENT:
                    imageResourceId = R.drawable.entertainment_team_category;
                    break;
                case MainActivity.TEM_INFORMATION:
                    imageResourceId = R.drawable.information_team_category;
                    break;
                case MainActivity.TEM_WEDDING:
                    imageResourceId = R.drawable.wedding_team_category;
                    break;
                case MainActivity.TEM_RESTAURANT:
                    imageResourceId = R.drawable.restaurant_team_category;
                    break;
                case MainActivity.TEM_OTHERS:
                    imageResourceId = R.drawable.others_team_category;
                    break;
                default:
                    imageResourceId = R.drawable.business_team_category;
                    break;

            }
        }else if (layout == 2) {
            imageResourceId = R.drawable.team_icon;
        }
        return imageResourceId;
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
