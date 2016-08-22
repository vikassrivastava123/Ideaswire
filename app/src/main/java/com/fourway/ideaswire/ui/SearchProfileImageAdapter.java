package com.fourway.ideaswire.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.request.helper.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Vikas on 8/23/2016.
 */

public class SearchProfileImageAdapter extends BaseAdapter {
    private ArrayList<Profile> mProfileList;
    private Context mContext;

    public SearchProfileImageAdapter (Context c, ArrayList<Profile> p){
        mContext = c; mProfileList = p;
    }
    @Override
    public int getCount() {
        return mProfileList.size();
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
        LayoutInflater li;
        View v = convertView;
        li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //TODO: Ashutosh: Please provide layout for single Image in search result
       /* v = li.inflate(R.layout.search_result_single_image_layout, null);
        NetworkImageView image = (NetworkImageView) v.findViewById(R.id.searchResultNetworkImage);
        image.setImageResource(R.drawable.gallery_default_thumb);
        String url =  (mProfileList.get(position)).getImageUrl();
        image.setImageUrl(url, VolleySingleton.getInstance(mContext).getImageLoader());*/

        return v;
    }
}
