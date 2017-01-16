package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fourway.ideaswire.R;

/**
 * Created by 4way on 13-01-2017.
 */

public class FragmentLayout3 extends Fragment {
    TextView titleTextView;
    ListView listView;
    ListViewAdapter adapter;

    int[] listIconResource = {R.drawable.about_icon,R.drawable.service_icon,R.drawable.clients_icon,
            R.drawable.blog_icon,R.drawable.team_icon,R.drawable.contact_icon};

    String[] listName = {"About", "Services", "Clients", "Blog", "Team", "Contact"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_3, container, false);

        titleTextView = (TextView) view.findViewById(R.id.layout_title);
        listView = (ListView) view.findViewById(R.id.layout3_listView);

        adapter = new ListViewAdapter(getActivity(), listName, listIconResource);

        listView.setAdapter(adapter);

        return view;
    }

    public class ListViewAdapter extends BaseAdapter {

        private Context context;
        private String[] listName;
        private int[] listIconResource;

        public ListViewAdapter(Context context, String[] listName, int[] listIconResource) {
            this.context = context;
            this.listName = listName;
            this.listIconResource = listIconResource;
        }


        @Override
        public int getCount() {
            return listName.length;
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

            if (convertView == null) {
                gridView = new View(context);

                gridView = inflater.inflate(R.layout.fragment_layout3_list, null);

                TextView pageName = (TextView) gridView.findViewById(R.id.textView_layout3);
                ImageView pageImage = (ImageView) gridView.findViewById(R.id.icon_imageView);

                pageImage.setImageResource(listIconResource[position]);
                pageName.setText(listName[position]);
            } else {
                gridView = (View) convertView;
            }
            return gridView;
        }
    }
}
