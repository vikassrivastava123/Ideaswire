package com.fourway.ideaswire.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

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
        TextView textView;
        ImageButton button;
    }
    LayoutInflater li;
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View v = convertView;
        ViewHolder vh = new ViewHolder();

        if (v == null) {
            li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.design_profile_list, null);
            vh.nIv = (NetworkImageView) v.findViewById(R.id.imgViewProfile);
            vh.textView =(TextView)v.findViewById(R.id.campaignName);
            vh.button =(ImageButton)v.findViewById(R.id.editCampaign);
            vh.button.setVisibility(View.GONE);
            v.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) v.getTag();
        }
        //int listSize=mProfileList.size();

       // if(position>listSize-2) {
            vh.nIv.setImageResource(R.drawable.image_loader);
            String pName = (mProfileList.get(position)).getProfileName();
            String url = (mProfileList.get(position)).getImageUrl();
            if (url != null && !url.equalsIgnoreCase("null")) {
                vh.nIv.setImageUrl(url, VolleySingleton.getInstance(mContext).getImageLoader());
           }

        if (pName != null) {
            vh.textView.setText(pName);
        }
        //}else {
          //  vh.nIv.setVisibility(View.GONE);

        //}

        vh.nIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GridView)parent).performItemClick(v, position,0);
            }
        });

        return v;
    }
}
