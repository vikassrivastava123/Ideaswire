package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourway.ideaswire.R;

public class ChooseTemplate_Category extends Activity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    TextView mTitle,choose_cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_template__category);
        Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(mycustomFont);
        choose_cat = (TextView) findViewById(R.id.HeaderCategory);
        choose_cat.setTypeface(mycustomFont);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
                //Intent intent = new Intent(getBaseContext(),CreateCampaign_homePage.class);
                //startActivity(intent);
            }
        });
        gridView = (GridView) findViewById(R.id.gridView);
   //     gridAdapter = new GridViewAdapter(this, R.layout.activity_choose_template__category, getData());
    //    gridView.setAdapter(gridAdapter);

        String[] values = new String[] { "business", "entertainment", "finance",
                "health", "individual","information","other","restaurent","see_all_template","wedding"};

        // use your custom layout
        ArrayAdapter<String> adapter = new GridViewAdapter<String>(this,
                values);
        gridView.setAdapter(adapter);

        ////
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Log.v("tets","test test");

                Intent sel = new Intent(ChooseTemplate_Category.this,select_layout_of_template.class);
                startActivity(sel);
                //Toast.makeText(this,"Press Start Now", Toast.LENGTH_LONG).show();
            }});
        ////

  }





    private class GridViewAdapter<S> extends ArrayAdapter<String>{

        private final Context context;
        private final String[] values;
        private Typeface tf;
        public GridViewAdapter(Context context, String[] values) {
            super(context, R.layout.choose_category_grid_layout, values);
            this.context = context;
            this.values = values;
            Typeface mycustomFont=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
            this.tf = mycustomFont;
        }


        class viewHolder{
            TextView tvHeader;
            ImageView imgView;

        }
        LayoutInflater inflater;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final viewHolder holderObj ;

            if(convertView == null){

                holderObj = new viewHolder();
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.choose_category_grid_layout, null);

                holderObj.tvHeader = (TextView) convertView.findViewById(R.id.tvTemplateCatrgory);
                holderObj.tvHeader.setTypeface(tf);
                holderObj.imgView = (ImageView) convertView.findViewById(R.id.imgvTemplateCatrgory);

                convertView.setTag(holderObj);

            }else{
                holderObj = (viewHolder)convertView.getTag();
            }

            Log.v("fristScreenAdapter", "position" + position + " " + values[position]);
            holderObj.tvHeader.setText(values[position]);



            switch (position){
                case 0:
                    holderObj.imgView.setImageResource(R.drawable.business);
                    break;
                case 1:
                    holderObj.imgView.setImageResource(R.drawable.entertainment);
                    break;
                case 2:

                    holderObj.imgView.setImageResource(R.drawable.finance);
                    break;
                case 3:

                    holderObj.imgView.setImageResource(R.drawable.health);
                    break;
                case 4:
                    holderObj.imgView.setImageResource(R.drawable.individual);
                    break;
                case 5:
                    holderObj.imgView.setImageResource(R.drawable.information);
                    break;
                case 6:
                    holderObj.imgView.setImageResource(R.drawable.other);
                    break;
                case 7:
                    holderObj.imgView.setImageResource(R.drawable.restaurent);
                    break;
                case 8:
                    holderObj.imgView.setImageResource(R.drawable.see_all_template);
                    break;
                case 9:
                    holderObj.imgView.setImageResource(R.drawable.wedding);
                    break;
            }
            return convertView;
        }
    }


/*
    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids_choose_category);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));

            if(bitmap !=null) {
                imageItems.add(new ImageItem(bitmap, "Image#" + i));
                Log.v("ArrayList", "item + i == " + i);
            }else{
                Log.v("ArrayList", "error + i == ");
            }
        }
        return imageItems;
    }
*/


}

/*
 class ImageItem {
    private Bitmap image;
    private String editTitle;

    public ImageItem(Bitmap image, String editTitle) {
        super();
        this.image = image;
        this.editTitle = editTitle;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return editTitle;
    }

    public void setTitle(String editTitle) {
        this.editTitle = editTitle;
    }
}

 class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

try {
    Log.v("GridViewAdapter", "getView position == " + position);
    ImageItem item = (ImageItem) data.get(position);
    holder.imageTitle.setText(item.getTitle());
    holder.image.setImageBitmap(item.getImage());

}catch (NullPointerException e){

    Log.v("GridViewAdapter", "catch getView position == " + position);

}finally {
    return row;
}
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}*/
