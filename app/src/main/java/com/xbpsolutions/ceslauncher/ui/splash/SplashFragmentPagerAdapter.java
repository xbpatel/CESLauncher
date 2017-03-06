package com.xbpsolutions.ceslauncher.ui.splash;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by dhruvil on 06-03-2017.
 */

public class SplashFragmentPagerAdapter extends FragmentStatePagerAdapter {

  public SplashFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    return null;
  }

  @Override
  public int getCount() {
    return 0;
  }
}
