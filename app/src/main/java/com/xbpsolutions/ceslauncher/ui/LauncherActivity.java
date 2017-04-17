package com.xbpsolutions.ceslauncher.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.helper.Functions;
import com.xbpsolutions.ceslauncher.helper.HomeManager;
import com.xbpsolutions.ceslauncher.helper.PermissionHelper;
import com.xbpsolutions.ceslauncher.helper.PrefUtils;
import com.xbpsolutions.ceslauncher.ui.splash.IntroItem;
import com.xbpsolutions.ceslauncher.ui.splash.SplashFragmentPagerAdapter;
import com.xbpsolutions.ceslauncher.ui.widgets.EnchantedViewPager;
import com.xbpsolutions.ceslauncher.ui.widgets.TfTextView;

import java.util.ArrayList;


public class LauncherActivity extends AppCompatActivity {

    private EnchantedViewPager pager;
    private TfTextView btnTryCes;
    private HomeManager mHManager;
    private Context thisCont = this;
    PermissionHelper permissionHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getSupportActionBar().hide();
        permissionHelper = new PermissionHelper(this, new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE}, 100);

        btnTryCes = (TfTextView) findViewById(R.id.btnTryCES);
        btnTryCes.setText("NEXT");
        btnTryCes.setOnClickListener(btnTryClickListner);
        // set first time color
        PrefUtils.setSelectedColor(this, getResources().getStringArray(R.array.mdcolor_900)[7]);
        PackageManager pm = getPackageManager();
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        mHManager = new HomeManager(pm, am);
        setup();




    }

    private View.OnClickListener btnTryClickListner = new OnClickListener() {
        @Override
        public void onClick(View view) {

            if (pager.getCurrentItem() == 2) {

                permissionHelper.request(new PermissionHelper.PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        Functions.resetPreferredLauncherAndOpenChooser(thisCont);
                    }

                    @Override
                    public void onPermissionDenied() {
                        Functions.resetPreferredLauncherAndOpenChooser(thisCont);
                    }

                    @Override
                    public void onPermissionDeniedBySystem() {
                        Functions.resetPreferredLauncherAndOpenChooser(thisCont);
                    }
                });


            } else {
                pager.setCurrentItem(pager.getCurrentItem() + 1);
            }


        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setup() {

        pager = (EnchantedViewPager) findViewById(R.id.pager);
        pager.useScale();

        IntroItem item = new IntroItem();
        item.color = getResources().getColor(R.color.neon_green);
        item.title = "Keys";

        IntroItem item1 = new IntroItem();
        item1.color = getResources().getColor(R.color.neon_orange);
        item1.title = "Keys";

        IntroItem item2 = new IntroItem();
        item2.color = getResources().getColor(R.color.neon_pink);
        item2.title = "Keys";

        ArrayList<IntroItem> items = new ArrayList<>();
        items.add(item);
        items.add(item1);
        items.add(item2);

        SplashFragmentPagerAdapter adapter = new SplashFragmentPagerAdapter(this, items);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 2) {
                    btnTryCes.setText("GET STARTED");
                } else {
                    btnTryCes.setText("NEXT");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }
}
