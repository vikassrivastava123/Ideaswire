package com.fourway.ideaswire.ui;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.AboutUsPage;

public class AddPages extends ListActivity  {

    TextView mTitle;
    Button theme_changer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pages);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        new select_layout_of_template().startModifyTemplate(1);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });

        String[] values = new String[] { "About us","Services","Clients","Blogs","Team","Contact"};

        // use your custom layout
        ArrayAdapter<String> adapter = new fristScreenAdapter<String>(this,
                values);

        setListAdapter(adapter);




    }
    private class fristScreenAdapter<S> extends ArrayAdapter<String> {

        private final Context context;
        private final String[] values;
        private Typeface tf;
        public fristScreenAdapter(Context context, String[] values) {
            super(context, R.layout.hs_first_login_item, values);
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
                convertView = inflater.inflate(R.layout.choose_page, null);

                holderObj.tvHeader = (TextView) convertView.findViewById(R.id.firstLine);
                holderObj.tvHeader.setTypeface(tf);
                holderObj.tvHeader.setTextColor(Color.BLACK);
                holderObj.imgView = (ImageView) convertView.findViewById(R.id.icon);

                convertView.setTag(holderObj);

            }else{
                holderObj = (viewHolder)convertView.getTag();
            }

            Log.v("fristScreenAdapter", "position" + position + " " + values[position]);
            holderObj.tvHeader.setText(values[position]);


            switch (position){
                case 0:
                    //holderObj.imgView.setImageResource(R.drawable.create_campaign);
                    holderObj.tvHeader.setText(select_layout_of_template.listOfTemplatePagesObj.get(0).nameis());
                    break;
                case 1:

                    ///holderObj.imgView.setImageResource(R.drawable.choose_template);
                    holderObj.tvHeader.setText(select_layout_of_template.listOfTemplatePagesObj.get(1).nameis());
                    break;
                case 2:

                    //holderObj.imgView.setImageResource(R.drawable.edit_template);
                    holderObj.tvHeader.setText(select_layout_of_template.listOfTemplatePagesObj.get(2).nameis());
                    break;
                case 3:
                    holderObj.tvHeader.setText(select_layout_of_template.listOfTemplatePagesObj.get(3).nameis());

                    //holderObj.imgView.setImageResource(R.drawable.preview);
                    break;
                case 4:
                    holderObj.tvHeader.setText(select_layout_of_template.listOfTemplatePagesObj.get(4).nameis());
                    //holderObj.imgView.setImageResource(R.drawable.makeitlive);
                    break;
            }
            return convertView;
        }
    }
    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
        Toast.makeText(this, "Clicked row " + position, Toast.LENGTH_SHORT).show();
        //add here
        select_layout_of_template.listOfTemplatePagesObj.add(position,new AboutUsPage());       }

}