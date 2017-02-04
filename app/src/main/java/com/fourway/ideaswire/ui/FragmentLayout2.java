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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourway.ideaswire.R;
import com.fourway.ideaswire.templates.HomePageLayout_1_DataTemplate;
import com.fourway.ideaswire.templates.dataOfTemplate;

/**
 * Created by 4way on 13-01-2017.
 */

public class FragmentLayout2 extends Fragment implements FragmenMainActivity.viewCampaign{
    TextView titleTextView;
    GridView gridView;
    GridViewAdapter adapter;
    Typeface myCustomFont;
    dataOfTemplate dataObj;
    private String[] pageName;
    private int[] pageImageResourceId;
    int[] selectedPagePosition;

    private boolean showPreview = false;        /**Is in editable mode or privew/it is server data .
     in case it false : editable, True means : Priview or server data*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_2,container,false);

        dataObj = (dataOfTemplate)((FragmenMainActivity)getActivity()).getDatObject();

        if (!dataObj.isEditDefaultOrUpdateData()) {
            showPreview = true;
        }


        /**
         * Getting Page Name and ImageResourceId
         */
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
                    getImageForMainPage_BasedOnTemplate_Layout(dataObj.getTemplateSelected(),1);
        }

        pageName = gridName;
        pageImageResourceId = rId;




        myCustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");
        titleTextView  = (TextView) view.findViewById(R.id.layout_title);
        titleTextView.setTypeface(myCustomFont);

        //Temporary solution for title set
        HomePageLayout_1_DataTemplate homeData = new HomePageLayout_1_DataTemplate(dataObj.getTemplateByServer(),true);
        titleTextView.setText(homeData.get_title());

        gridView = (GridView) view.findViewById(R.id.layout2_gridView);


        if (dataObj.isEditOrUpdateMode() && showPreview) {
            showGridEditUpdatePreviewMode();
        }else {
            showGridView(pageName, pageImageResourceId);
        }




        return view;
    }

    /**
     * set gridView adapter
     * @param pName
     * @param imageResource
     */
    void showGridView(String[] pName, int[] imageResource){
        adapter = new GridViewAdapter(getActivity(),android.R.layout.simple_gallery_item, pName, imageResource);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(clickListener);
    }

    /**
     * Grid View onClick listener
     */
    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (dataObj.isEditOrUpdateMode() && showPreview) {   //set position when edit or update mode and preview mode
                position = selectedPagePosition[position];
            }

            FragmenMainActivity.pageButtonViewId = position; //update pageButtonViewId which is help to knowing which fragment is active

            /**
             * selectedPageList : it's save selected page listOfTemplateObj position id, which page add in profile data
             */
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

            ((FragmenMainActivity)getActivity()).setDataObj(dataObj); //set dataObj in FragmentMainActivity
            ((FragmenMainActivity)getActivity()).setIndexOfPresentview(position); //set dataObj in FragmentMainActivity



            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            Fragment fragmentToLaunch = dataObj.getFragmentToLaunchPage();

            ((FragmenMainActivity)getActivity()).setFragmentToLaunch(fragmentToLaunch); //set fragmentToLaunch in FragmentMainActivity

            Bundle args = new Bundle();
            args.putInt("IndexKey", position);
            fragmentToLaunch.setArguments(args);

            transaction.replace(R.id.mainRLayout, fragmentToLaunch);
            transaction.commit();

            FragmenMainActivity.isFragmentMainPage = false;
        }
    };

    /**
     * call when when Preview Button Click
     */
    @Override
    public void init_ViewCampaign() {
        if (showPreview==false){
            showPreview = true;
            showGridEditUpdatePreviewMode();
        }else {

            showPreview = false;
            showGridView(pageName, pageImageResourceId);
        }


    }


    /**
     * Getting selected Page Name and imageResourceId for Preview when edit or Update mode
     */
    void showGridEditUpdatePreviewMode() {
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
                        getImageForMainPage_BasedOnTemplate_Layout(dataObj.getTemplateSelected(),1);
            }
            showGridView(selectedPageName, selectedPageRid);
        }

    }

    @Override
    public void addLastPage() {

    }

    @Override
    public void changeText() {

    }

    public class GridViewAdapter extends ArrayAdapter {

        private Context context;
        private  String[] gridName;
        private  int[] gridImageResource;


        GridViewAdapter(Context context, int resource, String[] gridName, int[] gridImageResource) {
            super(context,resource,gridName);
            this.context = context;
            this.gridName = gridName;
            this.gridImageResource = gridImageResource;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView = inflater.inflate( R.layout.fragment_layout2_grid , null);

            TextView pageName = (TextView) gridView.findViewById(R.id.textView_layout2);
            pageName.setTypeface(myCustomFont);
            ImageView pageImage = (ImageView) gridView.findViewById(R.id.imageView_layout2);

            pageImage.setImageResource(gridImageResource[position]);
            pageName.setText(gridName[position]);

            return gridView;
        }


    }
}
