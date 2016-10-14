package com.fourway.ideaswire.ui;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fourway.ideaswire.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vijay on 27-09-2016.
 */
public class PageTemplateArrayAdapter extends ArrayAdapter{
    Context context;
    List<String> items=new ArrayList<>();

    public PageTemplateArrayAdapter(Context context, int resource, List<String> items) {
        super(context, resource,items);

        this.context=context;
        this.items=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v= LayoutInflater.from(context).inflate(R.layout.about_us_page_template_list,null);
        TextView tv=(TextView)v.findViewById(R.id.textView2);
        SwitchCompat switchCompat =(SwitchCompat) v.findViewById(R.id.switchbtn);
        ImageButton imageButton=(ImageButton)v.findViewById(R.id.imageButton7);

        tv.setText(items.get(position));

        return v;
    }
}
