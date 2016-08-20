package com.fourway.ideaswire.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourway.ideaswire.R;

public class HomeScreenFirstLogin extends ListActivity {


    public void startNowBtn(View view) {
        Intent intent = new Intent(this,CreateCampaign_homePage.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_first_login);

        String[] values = new String[] { "CREATE CAMPAIGN", "CHOOSE TEMPLATE", "EDIT TEMPLATE",
                "PREVIEW", "MAKE IT LIVE"};

        // use your custom layout
        ArrayAdapter<String> adapter = new fristScreenAdapter<String>(this,
        values);
        setListAdapter(adapter);
     }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this,"Press Start Now", Toast.LENGTH_LONG).show();
    }




    private class fristScreenAdapter<S> extends ArrayAdapter<String>{

        private final Context context;
        private final String[] values;

        public fristScreenAdapter(Context context, String[] values) {
            super(context, R.layout.hs_first_login_item, values);
            this.context = context;
            this.values = values;
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
                holderObj.imgView = (ImageView) convertView.findViewById(R.id.icon);

                convertView.setTag(holderObj);

            }else{
                holderObj = (viewHolder)convertView.getTag();
            }

            Log.v("fristScreenAdapter", "position" + position + " " + values[position]);
            holderObj.tvHeader.setText(values[position]);


               switch (position){
                case 0:
                    holderObj.imgView.setImageResource(R.drawable.create_campaign);
                    break;
                case 1:

                    holderObj.imgView.setImageResource(R.drawable.choose_template);
                    break;
                case 2:

                    holderObj.imgView.setImageResource(R.drawable.edit_template);
                    break;
                case 3:

                    holderObj.imgView.setImageResource(R.drawable.preview);
                    break;
                case 4:

                    holderObj.imgView.setImageResource(R.drawable.makeitlive);
                    break;
            }
            return convertView;
        }
    }

}
