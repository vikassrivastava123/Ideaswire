package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.fourway.ideaswire.R;
import com.fourway.ideaswire.data.Profile;
import com.fourway.ideaswire.data.UploadImageForUrlData;
import com.fourway.ideaswire.request.CommonRequest;
import com.fourway.ideaswire.request.SaveProfileData;
import com.fourway.ideaswire.request.UploadImageForUrlRequest;
import com.fourway.ideaswire.templates.ClientDataTemplate;
import com.fourway.ideaswire.templates.dataOfTemplate;
import com.fourway.ideaswire.templates.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClientsOnapp extends Activity implements SaveProfileData.SaveProfileResponseCallback, UploadImageForUrlRequest.UploadImageForUrlCallback{
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
            editTitle.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
        if (editHeading !=null){
            editHeading.setEnabled(true);
            editHeading.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
        if (editSubHeading !=null){
            editSubHeading.setEnabled(true);
            editSubHeading.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
        if (editParaGraph !=null){
            editParaGraph.setEnabled(true);
            editParaGraph.setKeyListener(new EditText(getApplicationContext()).getKeyListener());
        }
    }


    void showPreview(){

        init_viewCampaign();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        /*showImageForBackround();
        if(cropRestart==1) {
            PhotoAsyncTask obj = new PhotoAsyncTask();
            obj.execute();
            cropRestart=0;
        }*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_onapp);

        editTitle =(EditText)findViewById(R.id.Client_TITLE);
        editHeading = (EditText)findViewById(R.id.ClientHeading);
        editSubHeading =(EditText)findViewById(R.id.ClientSubHeading);
        editParaGraph =(EditText)findViewById(R.id.ClientParaGraph);

        deleteTitle =(ImageView)findViewById(R.id.deleteTitleClient);
        deleteHeading =(ImageView)findViewById(R.id.deleteHeadingClient);
        deleteSubHeading =(ImageView)findViewById(R.id.deleteSubHeadingClient);
        deleteParaGraph =(ImageView)findViewById(R.id.deleteParaClient);

        dataObj=(ClientDataTemplate)getIntent().getSerializableExtra("data");


        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dynamicPages);
        Timer timing = new Timer();
        timing.schedule(new TimerTask() {
            @Override
            public void run() {

                int size = MainActivity.listOfTemplatePagesObj.size();
                Button[] btn = new Button[size];
                int i = 0;
                final LinearLayout row = new LinearLayout(ClientsOnapp.this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT));
                for(pages obj: MainActivity.listOfTemplatePagesObj) {
                    String name = obj.nameis();



                    float x =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
                    float y =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());

                    LinearLayout.LayoutParams buttonLayoutParams =
                            new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                                    (int)x,
                                    (int)y));
                    buttonLayoutParams.setMargins(2,2, 0, 0);
                    btn[i] = new Button(ClientsOnapp.this);

                    btn[i].setLayoutParams(buttonLayoutParams);
                    btn[i].setText(name);
                    btn[i].setId(i);
                    btn[i].setBackgroundColor(getResources().getColor(R.color.card));
                    btn[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //Toast.makeText(getApplicationContext(),
                            //       "button is clicked" + v.getId(), Toast.LENGTH_LONG).show();
                            dataOfTemplate data = MainActivity.listOfTemplatePagesObj.get(v.getId()).getTemplateData(1,dataObj.isDefaultDataToCreateCampaign());

                            Class intenetToLaunch = data.getIntentToLaunchPage();
                            Log.v(TAG, "5" + intenetToLaunch);
                            Intent intent = new Intent(getApplicationContext(), intenetToLaunch);
                            intent.putExtra("data",data);
                            startActivity(intent);
                        }
                    });
                    row.addView(btn[i]);
                    // Add the LinearLayout element to the ScrollView
                    i++;
                }
                //btn[5].setBackgroundColor(getResources().getColor(R.color.skyBlueBckgrnd));
                //btn[5].setFocusable(true);
                // When adding another view, make sure you do it on the UI
                // thread.
                layout.post(new Runnable() {

                    public void run() {

                        layout.addView(row);
                    }
                });
            }
        }, 500);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");

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


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });
        gridView = (GridView) findViewById(R.id.ClientGridView);

        final List<String> clientsLogoUrl =new ArrayList<>();

        clientsLogoUrl.add(dataObj.client_logo_1);
        clientsLogoUrl.add(dataObj.client_logo_2);
        clientsLogoUrl.add(dataObj.client_logo_3);
        clientsLogoUrl.add(dataObj.client_logo_4);
        clientsLogoUrl.add(dataObj.client_logo_5);
        clientsLogoUrl.add(dataObj.client_logo_6);




        // use your custom layout
        final GridViewAdapter gridViewAdapter=new GridViewAdapter(this,android.R.layout.simple_list_item_1, clientsLogoUrl);
        gridView.setAdapter(gridViewAdapter);

        
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if(v.getId()==R.id.deleteClientLogo){
                    clientsLogoUrl.remove(position);
                    gridView.setAdapter(gridViewAdapter);
                }
                /*Log.v("tets","test test");

                Intent sel = new Intent(ClientsOnapp.this,select_layout_of_template.class);
                startActivity(sel);
                //Toast.makeText(this,"Press Start Now", Toast.LENGTH_LONG).show();*/
            }});


    }

    @Override
    public void onProfileSaveResponse(CommonRequest.ResponseCode res, Profile data) {

    }

    @Override
    public void onUploadImageForUrlResponse(CommonRequest.ResponseCode res, UploadImageForUrlData data) {

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

    public void previewTemplate(View view) {
        TextView textViewShowPreview = (TextView)findViewById(R.id.textShow_preview);

        FloatingActionButton btn  = (FloatingActionButton)findViewById(R.id.floatingForPreview);
        Button showPreviewBtn  = (Button)findViewById(R.id.showPreview);


        if(showPreview == false) {
            textViewShowPreview.setText("Edit");
            btn.show();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_edit);
            init_viewCampaign();
            showPreview = true;
        }else {
            textViewShowPreview.setText("Preview");
            btn.hide();
            showPreviewBtn.setBackgroundResource(R.drawable.preview_about);
            init_editCampaign();
            showPreview = false;
        }

        //   RelativeLayout previewLayout = (RelativeLayout)findViewById(R.id.previewLayout);
        // previewLayout.setVisibility(View.VISIBLE);

    }

    public void pageTemplate(View view) {
        startActivity(new Intent(getApplicationContext(),about_us_page_template.class));
    }

    public void deleteTitleClient(View view){
        editTitle.setVisibility(View.GONE);
        deleteTitle.setVisibility(View.GONE);
    }
    public void deleteHeadingClient(View view){
        editHeading.setVisibility(View.GONE);
        deleteHeading.setVisibility(View.GONE);
    }
    public void deleteSubHeadingClient(View view){
        editSubHeading.setVisibility(View.GONE);
        deleteSubHeading.setVisibility(View.GONE);
    }
    public void deleteParaClient(View view){
        editParaGraph.setVisibility(View.GONE);
        deleteParaGraph.setVisibility(View.GONE);
    }

    public void changeTitleTextClient(View view){
        String title = String.valueOf(editTitle.getText());
        Log.d(TAG, "changeTitleTextService" + title);
        if (title != null) {
            dataObj.setTitle(title);
        }
    }
    public void changeHeadingTxtClient(View view){
        String header = String.valueOf(editHeading.getText());
        Log.d(TAG, "changeHeadingTxtService" + header);
        if (header != null) {
            dataObj.setHeaderClient(header);
        }
    }

    public void changeSubHeadingClient(View view){
        String subheader = String.valueOf(editSubHeading.getText());
        Log.d(TAG, "changeSubHeadingService" + subheader);
        if (subheader != null) {
            dataObj.setSubHeaderClient(subheader);
        }
    }

    public void changeParaClient(View view){
        String para = String.valueOf(editParaGraph.getText());
        Log.d(TAG, "changeParaService" + para);
        if (para != null) {
            dataObj.setParaClient(para);
        }
    }

}
