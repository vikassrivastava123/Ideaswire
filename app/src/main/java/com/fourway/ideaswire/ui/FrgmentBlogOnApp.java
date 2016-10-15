package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fourway.ideaswire.R;

/**
 * Created by 4way on 15-10-2016.
 */
public class FrgmentBlogOnApp extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_blog, container, false);
        return v;
    }
}
