package com.fourway.ideaswire.templates;

/**
 * Created by Ritika on 8/24/2016.
 */
public abstract class pages {

   dataOfTemplate dataObj = null;
   int index = -1;
    public dataOfTemplate getTemplateData(int type,boolean defaultData){

        if(defaultData == true) {
            dataObj = getDataForTemplate(type);

        }else{
            dataObj = getDataForTemplateAsReceivedFromServer();
        }

        return dataObj;

    }
    public abstract dataOfTemplate getDataForTemplate(int templateType);
    public abstract dataOfTemplate getDataForTemplateAsReceivedFromServer();


    public void setDataObj(dataOfTemplate argDataObj){
        dataObj = argDataObj;
    }

    public void setPageIndex(int index){
        this.index = index;
    }

    public int getPageIndex(){
        return this.index;
    }

    public dataOfTemplate getAlreadyCreatedDataObj(){

        return dataObj;
    }

    public abstract pages getNewPage();

    public abstract void set_nameis(String nameOfpage);
    public abstract String nameis();

    public abstract void set_iconis(int iconOfpage);
    public abstract int iconis();
}
