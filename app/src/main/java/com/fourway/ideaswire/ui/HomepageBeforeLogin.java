package com.fourway.ideaswire.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fourway.ideaswire.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class HomepageBeforeLogin extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener{

    int currentPage = 0 , NUM_PAGES = 5;
    ViewPager mViewPager;
    Handler mHandler;
    Runnable mUpdate;
    PagerAdapter mAdapter;
    ImageButton mGalleryBtn;
    ImageButton mCameraBtn;

    private File mCurrentPhoto;

    final int REQUEST_GALLERY_IMAGE_SELECTOR = 101;
    final int REQUEST_CAMERA_IMAGE_SELECTOR = 102;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_before_login);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new HomeScreenPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);


        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mViewPager.setOnPageChangeListener(this);

        startSlideShow();
        setUiPageViewController();

        initHomeResources();
    }


    void initHomeResources() {

        mGalleryBtn = (ImageButton) findViewById(R.id.galleryButton);
        mCameraBtn = (ImageButton) findViewById(R.id.cameraButton);

    }


   void startSlideShow()
    {
        mHandler = new Handler();
        mUpdate = new Runnable() {
        public void run() {
            if (currentPage == NUM_PAGES - 1) {
                currentPage = 0;
            }
            mViewPager.setCurrentItem(currentPage++, true);
        }
    };
    new Timer().schedule(new TimerTask() {

        @Override
        public void run() {
            mHandler.post(mUpdate);
        }
    }, 500, 3000);
}

    private int dotsCount;
    private ImageView[] dots;
    private LinearLayout pager_indicator;

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];


        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.nonselecteditem_dot, null));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selecteditem_dot, null));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.nonselecteditem_dot, null));
        }

        dots[position].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selecteditem_dot, null));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void cameraButtonOnClick(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            try {
//               // createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
            // Continue only if the File was successfully created
            if (mCurrentPhoto != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhoto));
                startActivityForResult(takePictureIntent, REQUEST_CAMERA_IMAGE_SELECTOR);
            }
        }
    }

    public void galleryButtonOnClick(View view) {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(galleryIntent, REQUEST_GALLERY_IMAGE_SELECTOR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GALLERY_IMAGE_SELECTOR:
                if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Uri selectedImage = data.getData();
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if (cursor == null || cursor.getCount() < 1) {
                        mCurrentPhoto = null;
                        break;
                    }
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    if(columnIndex < 0) { // no column index
                        mCurrentPhoto = null;
                        break;
                    }
                    mCurrentPhoto = new File(cursor.getString(columnIndex));
                    cursor.close();
                } else {
                    mCurrentPhoto = null;
                }
                break;
            case REQUEST_CAMERA_IMAGE_SELECTOR:
                if (resultCode != Activity.RESULT_OK) {
                    mCurrentPhoto = null;
                }
                break;
        }
        if (mCurrentPhoto != null) {
            ImageView imageView = (ImageView)findViewById(R.id.cameraImage);
            Picasso.with(this).load(mCurrentPhoto).into(imageView);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
