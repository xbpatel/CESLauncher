package com.xbpsolutions.ceslauncher.ui.splash;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

/**
 * Created by dhruvil on 06-03-2017.
 */

public class SplashFragmentPagerAdapter extends FragmentStatePagerAdapter {

  private ArrayList<IntroItem> items;

  public SplashFragmentPagerAdapter(FragmentManager fm, ArrayList<IntroItem> items) {
    super(fm);
    this.items = items;
  }

  @Override
  public Fragment getItem(int position) {
    return SplashItemFragment.newInstance(position, "");
  }

  @Override
  public int getCount() {
    return items.size();
  }
}
