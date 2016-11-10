package com.fourway.ideaswire.ui;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.UploadImageForUrlData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.UploadImageForUrlRequest;
import com.fourway.ideaswire.templates.ClientDataTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 4way on 25-10-2016.
 */
public class FragmentClientsOnApp extends Fragment implements View.OnClickListener, FragmenMainActivity.viewCampaign, UploadImageForUrlRequest.UploadImageForUrlCallback {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    TextView mTitle,choose_cat;
    public String TAG="ClientOnApp";

    EditText editTitle =null,
            editHeading =null,
            editSubHeading =null,
            editParaGraph =null;

    ImageView deleteTitle=null,
            deleteHeading=null,
            deleteSubHeading=null,
            deleteParaGraph=null;

    ImageView deleteClientLogo;

    RelativeLayout clientLogoLayout;

    int cropRestart=0;

    ClientDataTemplate dataObj;
    private boolean showPreview = false;

    void init_viewCampaign(){
        try {
            deleteTitle.setVisibility(View.GONE);
            deleteHeading.setVisibility(View.GONE);
            deleteSubHeading.setVisibility(View.GONE);
            deleteParaGraph.setVisibility(View.GONE);

        }catch (NullPointerException e){
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if (editTitle !=null){
            editTitle.setEnabled(false);
            editTitle.setKeyListener(null);
        }
        if (editHeading !=null){
            editHeading.setEnabled(false);
            editHeading.setKeyListener(null);
        }
        if (editSubHeading !=null){
            editSubHeading.setEnabled(false);
            editSubHeading.setKeyListener(null);
        }
        if (editParaGraph !=null){
            editParaGraph.setEnabled(false);
            editParaGraph.setKeyListener(null);
        }
    }

    void init_editCampaign(){
        try {
            deleteTitle.setVisibility(View.VISIBLE);
            deleteHeading.setVisibility(View.VISIBLE);
            deleteSubHeading.setVisibility(View.VISIBLE);
            deleteParaGraph.setVisibility(View.VISIBLE);

        }catch (NullPointerException e){
            Log.v(TAG,"Null in init_viewCampaign");
        }

        if (editTitle !=null){
            editTitle.setEnabled(true);
            editTitle.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (editHeading !=null){
            editHeading.setEnabled(true);
            editHeading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (editSubHeading !=null){
            editSubHeading.setEnabled(true);
            editSubHeading.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
        if (editParaGraph !=null){
            editParaGraph.setEnabled(true);
            editParaGraph.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());
        }
    }


    void showPreview(){

        init_viewCampaign();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_clients, container, false);

        editTitle =(EditText)view.findViewById(R.id.Client_TITLE);
        editHeading = (EditText)view.findViewById(R.id.ClientHeading);
        editSubHeading =(EditText)view.findViewById(R.id.ClientSubHeading);
        editParaGraph =(EditText)view.findViewById(R.id.ClientParaGraph);

        deleteTitle =(ImageView)view.findViewById(R.id.deleteTitleClient);
        deleteHeading =(ImageView)view.findViewById(R.id.deleteHeadingClient);
        deleteSubHeading =(ImageView)view.findViewById(R.id.deleteSubHeadingClient);
        deleteParaGraph =(ImageView)view.findViewById(R.id.deleteParaClient);

        deleteTitle.setOnClickListener(this);
        deleteHeading.setOnClickListener(this);
        deleteSubHeading.setOnClickListener(this);
        deleteParaGraph.setOnClickListener(this);

        dataObj=(ClientDataTemplate)((FragmenMainActivity)getActivity()).getDatObject();

        Typeface mycustomFont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Regular.otf");

        String title=dataObj.getTitle();
        if (title!=null){
            editTitle.setText(title);
            editTitle.setTypeface(mycustomFont);
        }else {
            editTitle.setVisibility(View.GONE);
        }

        String heading=dataObj.getHeaderClient();
        if (heading!=null){
            editHeading.setText(heading);
            editHeading.setTypeface(mycustomFont);
        }else
        {
            editHeading.setVisibility(View.GONE);
        }

        String subHeading=dataObj.getSubHeaderClient();
        if (subHeading!=null){
            editSubHeading.setText(subHeading);
            editSubHeading.setTypeface(mycustomFont);
        }else {
            editSubHeading.setVisibility(View.GONE);
        }

        String para=dataObj.getParaClient();
        if(para!=null){
            editParaGraph.setText(para);
            editParaGraph.setTypeface(mycustomFont);
        }else {
            editParaGraph.setVisibility(View.GONE);
        }


        gridView = (GridView) view.findViewById(R.id.ClientGridView);

        final List<String> clientsLogoUrl =new ArrayList<>();

        clientsLogoUrl.add(dataObj.client_logo_1);
        clientsLogoUrl.add(dataObj.client_logo_2);
        clientsLogoUrl.add(dataObj.client_logo_3);
        clientsLogoUrl.add(dataObj.client_logo_4);
        clientsLogoUrl.add(dataObj.client_logo_5);
        clientsLogoUrl.add(dataObj.client_logo_6);




        // use your custom layout
        final GridViewAdapter gridViewAdapter=new GridViewAdapter(getActivity(),android.R.layout.simple_list_item_1, clientsLogoUrl);
        gridView.setAdapter(gridViewAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if(v.getId()==R.id.deleteClientLogo){
                    clientsLogoUrl.remove(position);
                    gridView.setAdapter(gridViewAdapter);
                }else if (v.getId() == R.id.imgvTemplateCatrgory){

                }
                /*Log.v("tets","test test");

                Intent sel = new Intent(ClientsOnapp.this,select_layout_of_template.class);
                startActivity(sel);
                //Toast.makeText(this,"Press Start Now", Toast.LENGTH_LONG).show();*/
            }});

        return view;
    }

    public void uploadToClientsOnApp() {

        if(showPreview == false) {
            String campnName = null;
         /*
        * Need to open gallery directly from here
        * From Cropped image OK clicked editCampaign.java will be opened
        * In editCampaign.java this campaign name(campnName) will be used to set defualt text
        * ScreenName will be used by CropedImage as it will be used to open gallery by multiple classes
        * */

           /* Intent inf = new Intent(getActivity(), CropedImage.class);
            inf.putExtra("ScreenName", MainActivity.CLIENTS_LOGO_IMAGE_CROPED_NAME_1);
            inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.OPEN_GALLERY_FOR_CLIENTS_PAGE_ON_APP);
            cropRestart=1;
            inf.putExtra("CampaignName", "Choose Image");
            startActivity(inf);*/


        }

    }

    private File getFileObjectFromBitmap (Bitmap b) throws IOException {
        File f = new File(getActivity().getApplicationContext().getCacheDir(), "Abc");

//Convert bitmap to byte array

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return f;
    }


    private class GridViewAdapter extends ArrayAdapter {
        Context context;
        List<String> clientLogoUrl=new ArrayList<>();


        public GridViewAdapter(Context context, int resource,List<String> clientLogoUrl) {
            super(context, resource,clientLogoUrl);

            this.context=context;
            this.clientLogoUrl=clientLogoUrl;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            View v=LayoutInflater.from(context).inflate(R.layout.cl,null);

            NetworkImageView clientLogo = (NetworkImageView) v.findViewById(R.id.imgvTemplateCatrgory);
            final ImageView clientStaticLogo = (ImageView)v.findViewById(R.id.Client_STATIC_LOGO);
            clientLogoLayout =(RelativeLayout)v.findViewById(R.id.client_logo_layout);
            deleteClientLogo =(ImageView)v.findViewById(R.id.deleteClientLogo);


            deleteClientLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView) parent).performItemClick(v, position, 0);

                }
            });

            if( clientLogoUrl.get(position)!=null)
            {
                Uri cardImageUri = Uri.parse(clientLogoUrl.get(position));
                clientLogo.setImageURI(cardImageUri);
                clientStaticLogo.setVisibility(View.GONE);
            }
            else {
                clientLogo.setVisibility(View.GONE);
                clientStaticLogo.setImageResource(R.drawable.clients_logo);
            }



            return v;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.deleteTitleClient:
                editTitle.setVisibility(View.GONE);
                deleteTitle.setVisibility(View.GONE);
                break;
            case R.id.deleteHeadingClient:
                editHeading.setVisibility(View.GONE);
                deleteHeading.setVisibility(View.GONE);
                break;
            case R.id.deleteSubHeadingClient:
                editSubHeading.setVisibility(View.GONE);
                deleteSubHeading.setVisibility(View.GONE);
                break;
            case R.id.deleteParaClient:
                editParaGraph.setVisibility(View.GONE);
                deleteParaGraph.setVisibility(View.GONE);
                break;

        }
    }

    void changeText(){
        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextService" + title);
        if (title != null) {
            dataObj.setTitle(title);
        }

        String header = String.valueOf(editHeading.getText());
        Log.d(TAG, "changeHeadingTxtService" + header);
        if (header != null) {
            dataObj.setHeaderClient(header);
        }

        String subheader = String.valueOf(editSubHeading.getText());
        Log.d(TAG, "changeSubHeadingService" + subheader);
        if (subheader != null) {
            dataObj.setSubHeaderClient(subheader);
        }

        String para = String.valueOf(editParaGraph.getText());
        Log.d(TAG, "changeParaService" + para);
        if (para != null) {
            dataObj.setParaClient(para);
        }
    }

    @Override
    public void init_ViewCampaign() {

    }

    @Override
    public void addLastPage() {
        changeText();
    }

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {

    }
}
