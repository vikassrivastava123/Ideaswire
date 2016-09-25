package com.fourway.ideaswire.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.fourway.ideaswire.R;

public class searchResults extends AppCompatActivity {

    SearchProfileListAdapter msearchProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_results);

        GridView gv = (GridView) findViewById(R.id.searchResultsGridView);

        msearchProfileAdapter = new SearchProfileListAdapter(this, loginUi.mProfileList);

        gv.setAdapter(msearchProfileAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

}
