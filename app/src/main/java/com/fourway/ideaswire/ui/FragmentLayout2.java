package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourway.ideaswire.R;

/**
 * Created by 4way on 13-01-2017.
 */

public class FragmentLayout2 extends Fragment {
    TextView titleTextView;
    GridView gridView;
    GridViewAdapter adapter;

    String[] gridName = {"About","Services","Clients","Blog","Team","Contact"};
    int[] gridImageResource = {R.drawable.business_about_category,R.drawable.business_services_category,R.drawable.business_clients_category,
            R.drawable.business_blog_category,R.drawable.business_team_category,R.drawable.business_contact_category};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_2,container,false);



        titleTextView  = (TextView) view.findViewById(R.id.layout_title);
        gridView = (GridView) view.findViewById(R.id.layout2_gridView);

        adapter = new GridViewAdapter(getActivity(), gridName, gridImageResource);

        gridView.setAdapter(adapter);



        return view;
    }

    public class GridViewAdapter extends BaseAdapter {

        private Context context;
        private  String[] gridName;
        private  int[] gridImageResource;

        GridViewAdapter(Context context, String[] gridName, int[] gridImageResource) {
            this.context = context;
            this.gridName = gridName;
            this.gridImageResource = gridImageResource;
        }


        @Override
        public int getCount() {
            return gridName.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null){
                gridView = new View(context);

                gridView = inflater.inflate( R.layout.fragment_layout2_grid , null);

                TextView pageName = (TextView) gridView.findViewById(R.id.textView_layout2);
                ImageView pageImage = (ImageView) gridView.findViewById(R.id.imageView_layout2);

                pageImage.setImageResource(gridImageResource[position]);
                pageName.setText(gridName[position]);
            }else {
                gridView = (View) convertView;
            }
            return gridView;
        }


    }
}
