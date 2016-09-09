package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourway.ideaswire.R;

public class Gridlayout extends Activity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    TextView mTitle,choose_cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridlayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });
        gridView = (GridView) findViewById(R.id.gridView);
        //     gridAdapter = new GridViewAdapter(this, R.layout.activity_choose_template__category, getData());
        //    gridView.setAdapter(gridAdapter);

        String[] values = new String[] { "business", "entertainment", "finance",
                "health", "individual","information"};

        // use your custom layout
        ArrayAdapter<String> adapter = new GridViewAdapter<String>(this,
                values);
        gridView.setAdapter(adapter);

        ////
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Log.v("tets","test test");

                Intent sel = new Intent(Gridlayout.this,select_layout_of_template.class);
                startActivity(sel);
                //Toast.makeText(this,"Press Start Now", Toast.LENGTH_LONG).show();
            }});
    }

    private class GridViewAdapter<S> extends ArrayAdapter<String> {

        private final Context context;
        private final String[] values;
        private Typeface tf;
        public GridViewAdapter(Context context, String[] values) {
            super(context, R.layout.choose_category_grid_layout, values);
            this.context = context;
            this.values = values;
            Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
            this.tf = mycustomFont;
        }


        class viewHolder{
            TextView tvHeader;
            ImageView imgView;

        }
        LayoutInflater inflater;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final viewHolder holderObj ;

            if(convertView == null){

                holderObj = new viewHolder();
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.choose_template, null);

                holderObj.tvHeader = (TextView) convertView.findViewById(R.id.tvTemplateCatrgory);
                holderObj.tvHeader.setTypeface(tf);
                holderObj.imgView = (ImageView) convertView.findViewById(R.id.imgvTemplateCatrgory);

                convertView.setTag(holderObj);

            }else{
                holderObj = (viewHolder)convertView.getTag();
            }

            Log.v("fristScreenAdapter", "position" + position + " " + values[position]);
            holderObj.tvHeader.setText(values[position]);



            switch (position){
                case 0:
                    holderObj.imgView.setImageResource(R.drawable.about_category_1);
                    break;
                case 1:
                    holderObj.imgView.setImageResource(R.drawable.blog_category);
                    break;
                case 2:

                    holderObj.imgView.setImageResource(R.drawable.contact_category_1);
                    break;
                case 3:

                    holderObj.imgView.setImageResource(R.drawable.clients_category_1);
                    break;
                case 4:
                    holderObj.imgView.setImageResource(R.drawable.services_category_1x);
                    break;
                case 5:
                    holderObj.imgView.setImageResource(R.drawable.team_category_1);
                    break;
                case 6:
                    holderObj.imgView.setImageResource(R.drawable.services_category_1x);
                    break;

            }
            return convertView;
        }
    }


}
