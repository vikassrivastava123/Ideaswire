package com.fourway.ideaswire.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.request.helper.VolleySingleton;

import java.util.ArrayList;
/**
 * Created by Vikas on 9/10/2016.
 */

public class SearchProfileListAdapter extends BaseAdapter{
    private ArrayList<Profile> mProfileList;
    private Context mContext;

    public SearchProfileListAdapter (Context c, ArrayList<Profile> p){
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

    private class ViewHolder{
        NetworkImageView nIv;
    }
    LayoutInflater li;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder vh = new ViewHolder();

        if (v == null) {
            li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.design_profile_list, null);
            vh.nIv = (NetworkImageView) v.findViewById(R.id.imgViewProfile);
            v.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) v.getTag();
        }

        vh.nIv.setImageResource(R.drawable.ic_menu_manage);
        String url =  (mProfileList.get(position)).getImageUrl();
        if (url != null && !url.equalsIgnoreCase("null")){
            vh.nIv.setImageUrl(url, VolleySingleton.getInstance(mContext).getImageLoader());
        }

        return v;
    }
}
