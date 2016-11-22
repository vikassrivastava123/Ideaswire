package com.fourway.ideaswire.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fourway.ideaswire.R;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

 final class FontsOverride {

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);

            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}

public class HomepageBeforeLogin extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    int currentPage = 0, NUM_PAGES = 6;
    ViewPager mViewPager;
    Handler mHandler;
    Runnable mUpdate;
    PagerAdapter mAdapter;
    ImageButton mGalleryBtn;
    ImageButton mCameraBtn;
    ImageButton naveButton;

    private File mCurrentPhoto;



    NavigationView navigationView=null;
    Toolbar toolbar=null;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homepage_before_login);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Montserrat-Regular.otf");
        naveButton = (ImageButton)findViewById(R.id.nave_button);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new HomeScreenPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mViewPager.setOnPageChangeListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        startSlideShow();
        setUiPageViewController();
//
      // initHomeResources();

        naveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonHandler();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void backButtonHandler(){
        drawer.openDrawer(GravityCompat.END);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
//            main_fragment fragment = new main_fragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container,fragment);
//            fragmentTransaction.commit();
            Intent intent = new Intent(this,CreateCampaign_homePage.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this,CreateCampaign_homePage.class);
            startActivity(intent);
//            GalleryFragment fragment = new GalleryFragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container,fragment);
//            fragmentTransaction.commit();

        }else if (id==R.id.nav_login) {
            startActivity(new Intent(this,loginUi.class));

        }else if (id == R.id.nav_slideshow) {
//            SlidshowFrag fragment = new SlidshowFrag();
//            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container,fragment);
//            fragmentTransaction.commit();

        } //else if (id == R.id.Recent_searches) {}
//            SlidshowFrag fragment = new SlidshowFrag();
//            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container,fragment);
//            fragmentTransaction.commit();

         //else if (id == R.id.Demo) {
//            SlidshowFrag fragment = new SlidshowFrag();
//            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container,fragment);
//            fragmentTransaction.commit();

        //} else if (id == R.id.share_feedback) {
//            SlidshowFrag fragment = new SlidshowFrag();
//            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container,fragment);
//            fragmentTransaction.commit();
        //}
        //else if (id == R.id.rate_us) {
//            SlidshowFrag fragment = new SlidshowFrag();
//            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container,fragment);
//            fragmentTransaction.commit();
        //}
        //else if (id == R.id.terms_service) {
//            SlidshowFrag fragment = new SlidshowFrag();
//            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container,fragment);
//            fragmentTransaction.commit();
        //}



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    void initHomeResources() {

       // mGalleryBtn = (ImageButton) findViewById(R.id.galleryButton);
       // mCameraBtn = (ImageButton) findViewById(R.id.cameraButton);

    }


    void startSlideShow() {
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
            params.setMargins(4, 30, 4, 0);
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

        Intent inf = new Intent (this,CropedImage.class);
        inf.putExtra(MainActivity.OPEN_GALLERY_FOR ,MainActivity.OPEN_GALLERY_FOR_SEARCH );

        inf.putExtra("ScreenName",MainActivity.OPEN_CAMERA_FOR_SEARCH );
        startActivity(inf);



     /*   Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            try {
//               // createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
            // Continue only if the File was successfully created
            //if (mCurrentPhoto != null)
            {
             //   takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhoto));
                startActivityForResult(takePictureIntent, REQUEST_CAMERA_IMAGE_SELECTOR);
            }
        }
        */
    }

    void startGallery(){

        Intent inf = new Intent (this,CropedImage.class);
        inf.putExtra(MainActivity.OPEN_GALLERY_FOR ,MainActivity.OPEN_GALLERY_FOR_SEARCH );
        startActivity(inf);

      /*  Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_IMAGE_SELECTOR);
*/

    }

    public void galleryButtonOnClick(View view) {
       /* if (Build.VERSION.SDK_INT >= 23) {
            Log.d("galleryButtonOnClick", "Build.VERSION.SDK_INT >= 23");
            verifyStoragePermissions(this);
        }else
        */
        {
            startGallery();
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSINOS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    final static int GALLERY_OPEN_REQUEST = 1;

    public void verifyStoragePermissions(Activity activity) {

        if (Build.VERSION.SDK_INT >= 23) {
            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                Log.d("verifySPermissions", "permission != PackageManager.PERMISSION_GRANTED");
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    Log.d("verifySPermissions", "shouldShowRequestPermissionRationale");
                } else {

                    Log.d("verifySPermissions", "requestPermissions 1");
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            GALLERY_OPEN_REQUEST);
                    Log.d("verifySPermissions", "requestPermissions 2");
                }
             /*   ActivityCompat.requestPermissions(
                        activity,
                        PERMISSINOS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );*/
            }else{
                Log.d("verifySPermissions", "permission ==== PackageManager.PERMISSION_GRANTED");
                startGallery();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GALLERY_OPEN_REQUEST: {
                Log.d("PermissionsResult", "permission =GALLERY_OPEN_REQUEST");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PermissionsResult", "permission was granted, yay!");
                    //
                    startGallery();


                } else {
                    Log.d("PermissionsResult", "permission denied, boo! Disable functionality that depends on this permission.");

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("onActivityResult", "start :");

        if (requestCode == MainActivity.REQUEST_CAMERA_IMAGE_SELECTOR && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            Log.d("onActivityResult", "URI :" + imageUri);
            String uriString = null;
            uriString = FileUtils.getPath(this, imageUri);
            Log.d("onActivityResult", "URI :" + uriString);

          //  Intent inf = new Intent (this,CropedImage.class);
          //  inf.putExtra(MainActivity.OPEN_GALLERY_FOR ,MainActivity.OPEN_GALLERY_FOR_SEARCH );
          //  startActivity(inf);



            Intent EditPhotoIntent = new Intent(this, EditPhotoSelectedUi.class);
            EditPhotoIntent.putExtra("imageUri", uriString);
            startActivity(EditPhotoIntent);
        }else{
            Log.d("onActivityResult", "No data received");
        }
    }//
}