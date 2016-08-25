package com.fourway.ideaswire.templates;

import com.fourway.ideaswire.ui.select_layout_of_template;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ritika on 8/24/2016.
 */
public abstract class dataOfTemplate implements Serializable {

    transient List<pages> listOfFooter = select_layout_of_template.listOfTemplatePagesObj;
    String header;

    void setHeader(String header){
        this.header = header;
     }

    public String getHeader(){
         return header;
    }

    abstract public Class getIntentToLaunchPage();
    abstract public int getTemplateSelected();

}
