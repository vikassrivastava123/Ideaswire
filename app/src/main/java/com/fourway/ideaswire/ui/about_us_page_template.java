package com.fourway.ideaswire.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class about_us_page_template extends AppCompatActivity {
    ListView listView;
    List<String> list=new ArrayList<>();

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

        getSupportActionBar().hide();

        listView=(ListView)findViewById(R.id.listView);

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dynamicPages2);

        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                int size = MainActivity.listOfTemplatePagesObj.size();
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
                    //btn[i].setText(name);
                    btn[i].setBackgroundColor(getResources().getColor(android.R.color.black));
                    btn[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(),
                                    "button is clicked" + v.getId(), Toast.LENGTH_SHORT).show();
                        }
                    });
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


        PageTemplateArrayAdapter adapter=new PageTemplateArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        //setSupportActionBar(toolbar);
    }

}
