package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.HomePageLayout_1_DataTemplate;
import com.fourway.ideaswire.templates.dataOfTemplate;

/**
 * Created by 4way on 13-01-2017.
 */

public class FragmentLayout3 extends Fragment implements FragmenMainActivity.viewCampaign{
    TextView titleTextView;
    ListView listView;
    ListViewAdapter adapter;
    dataOfTemplate dataObj;
    Typeface myCustomFont;
    private String[] pageName;
    private int[] pageIconResourceId;
    int[] selectedPagePosition;

    private boolean showPreview = false;        /**Is in editable mode or privew/it is server data .
     in case it false : editable, True means : Priview or server data*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_3, container, false);

        dataObj = (dataOfTemplate)((FragmenMainActivity)getActivity()).getDatObject();

        if (!dataObj.isEditDefaultOrUpdateData()) {
            showPreview = true;
        }

        int numberOfPage = MainActivity.listOfTemplatePagesObj.size();
        String[] gridName = new String[numberOfPage];
        int[] rId = new int[numberOfPage];

        for (int i=0; i<numberOfPage;i++) {
            String[] nameStrings = MainActivity.listOfTemplatePagesObj.get(i).nameis().split(" ", 2);
            String name;


            if (nameStrings.length > 1) {
                name = nameStrings[1];
            } else {
                name = nameStrings[0];
            }
            gridName[i] = name;
            rId[i] = MainActivity.listOfTemplatePagesObj.get(i).
                    getImageForMainPage_BasedOnTemplate_Layout(dataObj.getTemplateSelected(),2);
        }

        pageName = gridName;
        pageIconResourceId = rId;


        myCustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");
        titleTextView = (TextView) view.findViewById(R.id.layout_title);
        titleTextView.setTypeface(myCustomFont);

        //Temporary solution for title set
        HomePageLayout_1_DataTemplate homeData = new HomePageLayout_1_DataTemplate(dataObj.getTemplateByServer(),true);
        titleTextView.setText(homeData.get_title());
        
        listView = (ListView) view.findViewById(R.id.layout3_listView);

        if (dataObj.isEditOrUpdateMode() && showPreview) {
            showListEditUpdatePreviewMode();
        }else {
            showListView(pageName, pageIconResourceId);
        }

        return view;
    }

    void showListView(String[] pName, int[] iconResource){
        adapter = new FragmentLayout3.ListViewAdapter(getActivity(),android.R.layout.simple_gallery_item, pName, iconResource);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(clickListener);
    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (dataObj.isEditOrUpdateMode() && showPreview) {
                position = selectedPagePosition[position];
            }

            FragmenMainActivity.pageButtonViewId = position;

            if (dataObj.isEditDefaultOrUpdateData() && !showPreview) {
                boolean add = true;
                for (int x = 0; x < FragmenMainActivity.selectedPageList.size(); x++) {
                    if (FragmenMainActivity.selectedPageList.get(x) == position) {
                        add = false;
                        break;
                    }
                }
                if (add) {
                    FragmenMainActivity.selectedPageList.add(position);
                }
            }

            dataObj = MainActivity.listOfTemplatePagesObj.get(position).getTemplateData(true);

            ((FragmenMainActivity)getActivity()).setDataObj(dataObj);
            ((FragmenMainActivity)getActivity()).setIndexOfPresentview(position); //set dataObj in FragmentMainActivity


            FragmentManager fragmentManager = getFragmentManager();


            FragmentTransaction transaction = fragmentManager.beginTransaction();

            Fragment fragmentToLaunch = dataObj.getFragmentToLaunchPage();
            ((FragmenMainActivity)getActivity()).setFragmentToLaunch(fragmentToLaunch);
            Bundle args = new Bundle();
            args.putSerializable("dataKey", dataObj);
            args.putInt("IndexKey", position);

            fragmentToLaunch.setArguments(args);

            transaction.replace(R.id.mainRLayout, fragmentToLaunch);
            transaction.commit();

            FragmenMainActivity.isFragmentMainPage = false;
        }
    };

    @Override
    public void init_ViewCampaign() {

        if (showPreview==false){
            showPreview = true;
            showListEditUpdatePreviewMode();
        }else {

            showPreview = false;
            showListView(pageName, pageIconResourceId);
        }

    }

    @Override
    public void addLastPage() {

    }

    @Override
    public void changeText() {

    }

    void showListEditUpdatePreviewMode() {
        if (dataObj.isEditOrUpdateMode() && showPreview) {
            int listSize = FragmenMainActivity.selectedPageList.size();
            String[] selectedPageName = new String[listSize];
            int[] selectedPageRid = new int[listSize];
            selectedPagePosition = new int[listSize];
            for (int i=0;i<listSize;i++) {
                String[] nameStrings = MainActivity.listOfTemplatePagesObj.get(FragmenMainActivity.selectedPageList.get(i)).nameis().split(" ", 2);
                String name;

                if (nameStrings.length > 1) {
                    name = nameStrings[1];
                } else {
                    name = nameStrings[0];
                }
                selectedPageName[i] = name;
                selectedPagePosition[i] = FragmenMainActivity.selectedPageList.get(i);
                selectedPageRid[i] = MainActivity.listOfTemplatePagesObj.get(FragmenMainActivity.selectedPageList.get(i)).
                        getImageForMainPage_BasedOnTemplate_Layout(dataObj.getTemplateSelected(),2);
            }
            showListView(selectedPageName, selectedPageRid);
        }

    }

    public class ListViewAdapter extends ArrayAdapter {

        private Context context;
        private String[] listName;
        private int[] listIconResource;

        public ListViewAdapter(Context context,  int resource, String[] listName, int[] listIconResource) {
            super(context,resource,listName);
            this.context = context;
            this.listName = listName;
            this.listIconResource = listIconResource;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView = inflater.inflate(R.layout.fragment_layout3_list, null);

            TextView pageName = (TextView) gridView.findViewById(R.id.textView_layout3);
            ImageView pageImage = (ImageView) gridView.findViewById(R.id.icon_imageView);
            pageName.setTypeface(myCustomFont);

            pageImage.setImageResource(listIconResource[position]);
            pageName.setText(listName[position]);

            return gridView;
        }
    }
}
