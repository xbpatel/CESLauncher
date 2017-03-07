package com.xbpsolutions.ceslauncher.ui;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.helper.HomeManager;
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launcher);
    getSupportActionBar().hide();

    btnTryCes = (TfTextView) findViewById(R.id.btnTryCES);
    btnTryCes.setText("NEXT");
    btnTryCes.setOnClickListener(btnTryClickListner);

    PackageManager pm = getPackageManager();
    ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
    mHManager = new HomeManager(pm, am);
    setup();


  }

  private View.OnClickListener btnTryClickListner = new OnClickListener() {
    @Override
    public void onClick(View view) {

      if (pager.getCurrentItem() == 2) {
        resetPreferredLauncherAndOpenChooser(thisCont);
      } else {
        pager.setCurrentItem(pager.getCurrentItem() + 1);
      }


    }
  };

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

  public static void resetPreferredLauncherAndOpenChooser(Context context) {
    PackageManager packageManager = context.getPackageManager();
    ComponentName componentName = new ComponentName(context, MockHome.class);
    packageManager
        .setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP);

    Intent selector = new Intent(Intent.ACTION_MAIN);
    selector.addCategory(Intent.CATEGORY_HOME);
    selector.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(selector);

    packageManager
        .setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
            PackageManager.DONT_KILL_APP);
  }
}
