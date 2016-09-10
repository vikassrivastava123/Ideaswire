package com.fourway.ideaswire.ui;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.AboutUsPage;

public class AddPages extends ListActivity  {
    ArrayAdapter<String> adapter;
    TextView mTitle;
    Button theme_changer,add_pages;
Button aboutbtn,servicebtn,clientbtn,blogbtn,teambtn,contactbtn,backbtn;
    LinearLayout li;

    String[] values = new String[] { "About us","Services","Clients","Blogs","Team","Contact"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pages);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        aboutbtn=(Button)findViewById(R.id.imageButton3);
        servicebtn=(Button)findViewById(R.id.imageButton4);
       clientbtn=(Button)findViewById(R.id.imageButton5);
        blogbtn=(Button)findViewById(R.id.imageButton6);
        teambtn=(Button)findViewById(R.id.imageButton8);
        contactbtn=(Button)findViewById(R.id.imageButton9);
        backbtn=(Button)findViewById(R.id.imageButton2);
       add_pages=(Button) findViewById(R.id.add_pages2);
        li = (LinearLayout) findViewById(R.id.barlyt);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                li.setVisibility(View.GONE);
                add_pages.setVisibility(View.VISIBLE);
            }
        });
        add_pages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                li.setVisibility(View.VISIBLE);
                add_pages.setVisibility(View.GONE);
            }

        });
        aboutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           int lastposition= values.length;
               String []newarr = new String[lastposition+1];
            for (int i=0 ;i< lastposition;i++)
            {
                newarr[i] = values[i];

            }
            newarr[lastposition] = "About us";
            values = newarr;
                //adapter.notifyDataSetChanged();
           adapter =   new fristScreenAdapter<String>(AddPages.this,values);
              setListAdapter(adapter);
                Toast.makeText(AddPages.this,"about page added successfully",Toast.LENGTH_SHORT).show();
            }

        });
        servicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastposition= values.length;
                String []newarr = new String[lastposition+1];
                for (int i=0 ;i< lastposition;i++)
                {
                    newarr[i] = values[i];

                }
                newarr[lastposition] = "services";
                values = newarr;
                adapter =   new fristScreenAdapter<String>(AddPages.this,values);
                setListAdapter(adapter);
                Toast.makeText(AddPages.this,"service page added successfully",Toast.LENGTH_SHORT).show();
            }
        });
        clientbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastposition= values.length;
                String []newarr = new String[lastposition+1];
                for (int i=0 ;i< lastposition;i++)
                {
                    newarr[i] = values[i];

                }
                newarr[lastposition] = "client";
                values = newarr;
                adapter =   new fristScreenAdapter<String>(AddPages.this,values);
                setListAdapter(adapter);
                Toast.makeText(AddPages.this,"client page added successfully",Toast.LENGTH_SHORT).show();
            }
        });
        blogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastposition= values.length;
                String []newarr = new String[lastposition+1];
                for (int i=0 ;i< lastposition;i++)
                {
                    newarr[i] = values[i];

                }
                newarr[lastposition] = "blog";
                values = newarr;
                adapter =   new fristScreenAdapter<String>(AddPages.this,values);
                setListAdapter(adapter);
                Toast.makeText(AddPages.this,"blog page added successfully",Toast.LENGTH_SHORT).show();
            }
        });
        teambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastposition= values.length;
                String []newarr = new String[lastposition+1];
                for (int i=0 ;i< lastposition;i++)
                {
                    newarr[i] = values[i];

                }
                newarr[lastposition] = "team";
                values = newarr;
                adapter =   new fristScreenAdapter<String>(AddPages.this,values);
                setListAdapter(adapter);
                Toast.makeText(AddPages.this,"team page added successfully",Toast.LENGTH_SHORT).show();
            }
        });
        contactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastposition= values.length;
                String []newarr = new String[lastposition+1];
                for (int i=0 ;i< lastposition;i++)
                {
                    newarr[i] = values[i];

                }
                newarr[lastposition] = "contact";
                values = newarr;
                adapter =   new fristScreenAdapter<String>(AddPages.this,values);
                setListAdapter(adapter);
                Toast.makeText(AddPages.this,"contact page added successfully",Toast.LENGTH_SHORT).show();
            }
        });
//
//        backbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });



        // use your custom layout
        adapter = new fristScreenAdapter<String>(this,
                values);

        setListAdapter(adapter);




    }
    private class fristScreenAdapter<S> extends ArrayAdapter<String> {

        //viewHolder holderObj ;
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

            SwitchCompat switchCompat;
            ImageButton imgdelete;

        }
        LayoutInflater inflater;
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final viewHolder holderObj ;

            if(convertView == null){

                holderObj = new viewHolder();
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.choose_page, null);

                holderObj.tvHeader = (TextView) convertView.findViewById(R.id.firstLine);
                holderObj.tvHeader.setTypeface(tf);
                holderObj.tvHeader.setTextColor(Color.BLACK);

                holderObj.switchCompat=(SwitchCompat)convertView.findViewById(R.id.switchbtn);
                holderObj.imgdelete=(ImageButton)convertView.findViewById(R.id.imagedlt) ;

                convertView.setTag(holderObj);

            }else{
                holderObj = (viewHolder)convertView.getTag();
            }

            Log.v("fristScreenAdapter", "position" + position + " " + values[position]);
            holderObj.tvHeader.setText(values[position]);

            holderObj.imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });

holderObj.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
});

            switch (position){
                case 0:
                    //holderObj.imgView.setImageResource(R.drawable.create_campaign);

                    break;
                case 1:

                    ///holderObj.imgView.setImageResource(R.drawable.choose_template);
                    break;
                case 2:

                    //holderObj.imgView.setImageResource(R.drawable.edit_template);
                    break;
                case 3:

                    //holderObj.imgView.setImageResource(R.drawable.preview);
                    break;
                case 4:

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
        //select_layout_of_template.listOfTemplatePagesObj.add(position,new AboutUsPage());       }
    }

}
