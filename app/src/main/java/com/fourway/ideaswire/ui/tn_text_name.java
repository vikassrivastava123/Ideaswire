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

public class tn_text_name extends ListActivity {
    Button b1,b2,b3,b4,b5,edit,delete;
    TextView t1,t2,t3,t4,mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tn_text_name);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });
        b1 = (Button) findViewById(R.id.button3);
        b1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                my_f();
            }
        });
       b2 = (Button) findViewById(R.id.button4);
//        b3 = (Button) findViewById(R.id.button5);
//        b4 = (Button) findViewById(R.id.button6);
        b5 = (Button) findViewById(R.id.buttonback);
        edit = (Button) findViewById(R.id.edit_text2);
        delete = (Button) findViewById(R.id.delete2);
        b5.setTypeface(mycustomFont);
        b1.setTypeface(mycustomFont);
        b4.setTypeface(mycustomFont);
        b3.setTypeface(mycustomFont);
        b2.setTypeface(mycustomFont);
        t1 = (TextView) findViewById(R.id.textView4);
        t2 = (TextView) findViewById(R.id.textView5);
        t3 = (TextView) findViewById(R.id.textView6);
        //t4 = (TextView) findViewById(R.id.editname);
        //b4 = (Button) findViewById(R.id.button6);
        //t4.setTypeface(mycustomFont);
        t3.setTypeface(mycustomFont);
        t2.setTypeface(mycustomFont);
        t1.setTypeface(mycustomFont);
        String[] values = new String[] { "Link", "Button Text"};

        // use your custom layout
        ArrayAdapter<String> adapter = new fristScreenAdapter<String>(this,
                values);
        setListAdapter(adapter);

    }
    public void my_f()
    {
        edit.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
    }
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this,"Vaibhav dont knnow the functionality", Toast.LENGTH_LONG).show();
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
                convertView = inflater.inflate(R.layout.hs_first_login_item, null);

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
                    break;
                case 1:

                    ///holderObj.imgView.setImageResource(R.drawable.choose_template);
                    break;

            }
            return convertView;
        }
    }
}
