package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class about_us_page_template extends Activity {
    ListView listView;
    List<String> list=new ArrayList<>();
    PageTemplateArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_page_tamplate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       // getSupportActionBar().hide();

        listView=(ListView)findViewById(R.id.listView);

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dynamicPages2);

        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                final int size = MainActivity.listOfTemplatePagesObj.size();
                ImageButton[] btn = new ImageButton[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(about_us_page_template.this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT));
                for(pages obj: MainActivity.listOfTemplatePagesObj) {
                    int icon = obj.iconis();

                    float x =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                    float y =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

                    LinearLayout.LayoutParams buttonLayoutParams =
                            new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                                    (int)x,
                                    (int)y));
                    //buttonLayoutParams.setMargins(2,0, 0, 0);
                    btn[i] = new ImageButton(about_us_page_template.this);

                    btn[i].setLayoutParams(buttonLayoutParams);
                    btn[i].setImageResource(icon);
                    btn[i].setId(i);
                    btn[i].setBackgroundColor(getResources().getColor(android.R.color.black));
                    btn[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String pageName = MainActivity.listOfTemplatePagesObj.get(v.getId()).nameis();
                            MainActivity.listOfTemplatePagesObj.add(size,MainActivity.listOfTemplatePagesObj.get(v.getId()));
                            list.add(pageName);
                            listView.setAdapter(adapter);
                            Toast.makeText(about_us_page_template.this, pageName + " Added... " , Toast.LENGTH_SHORT).show();
                        }
                    });
                    if(i<7)
                    row.addView(btn[i]);
                    // Add the LinearLayout element to the ScrollView
                    i++;
                }
                // When adding another view, make sure you do it on the UI
                // thread.
                layout.post(new Runnable() {

                    public void run() {

                        layout.addView(row);
                    }
                });
            }
        }, 500);

        for(pages obj: MainActivity.listOfTemplatePagesObj) {
            String name = obj.nameis();
            list.add(name);
        }


        adapter=new PageTemplateArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.getId()==R.id.imageButton7)
                {
                    String pageName = MainActivity.listOfTemplatePagesObj.get(position).nameis();
                    MainActivity.listOfTemplatePagesObj.remove(position);
                    list.remove(position);
                    listView.setAdapter(adapter);
                    Toast.makeText(about_us_page_template.this, pageName + "delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //setSupportActionBar(toolbar);
    }

}
