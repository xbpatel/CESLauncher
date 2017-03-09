package com.xbpsolutions.ceslauncher.ui;

import static com.xbpsolutions.ceslauncher.helper.PrefUtils.getSelectedColor;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.tabwidget.TabBarView;
import com.xbpsolutions.ceslauncher.tabwidget.TabBarView.IconTabProvider;
import com.xbpsolutions.ceslauncher.ui.home.MovieFragment;
import com.xbpsolutions.ceslauncher.ui.settings.SettingsFragment;
import java.util.ArrayList;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

  private TabBarView tabBarView;

  SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  ViewPager mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    LayoutInflater inflator =
        (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View v = inflator.inflate(R.layout.custom_ab, null);

    tabBarView = (TabBarView) v.findViewById(R.id.tab_bar);

    ArrayList<String> colors = new ArrayList<>();
    colors.add("#ffffff");
    colors.add("#ffffff");
    colors.add("#ffffff");
    colors.add("#ffffff");
    tabBarView.setStripColors(colors);
    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    getSupportActionBar().setCustomView(v);
    getSupportActionBar().setElevation(0f);
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    tabBarView.setViewPager(mViewPager);
  }

  @Override
  protected void onResume() {
    super.onResume();
    setThemeColor();
  }

  public void setThemeColor() {
    String color = getSelectedColor(HomeActivity.this);
    mViewPager.setBackgroundColor(Color.parseColor(color));
    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));

    if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(Color.parseColor(color));
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.settings_menu, menu);
    return false;
  }

  public class SectionsPagerAdapter extends FragmentPagerAdapter implements IconTabProvider {

    private int[] tab_icons = {R.drawable.ic_action_apps,
        R.drawable.ic_action_call,
        R.drawable.ic_action_message, R.drawable.ic_action_settings
    };


    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      // getItem is called to instantiate the fragment for the given page.
      // Return a PlaceholderFragment (defined as a static inner class
      // below).

      switch (position) {
        case 0:
          return new MovieFragment();
        case 3:
          return new SettingsFragment();
        default:
          return PlaceholderFragment.newInstance(position + 1);
      }

      // return AppsGridFragment.instantiate(Homthis,"");
    }

    @Override
    public int getCount() {
      // Show 3 total pages.
      return tab_icons.length;
    }


    @Override
    public int getPageIconResId(int position) {
      return tab_icons[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
      Locale l = Locale.getDefault();
      switch (position) {
        case 0:
          return getString(R.string.title_section1).toUpperCase(l);
        case 1:
          return getString(R.string.title_section2).toUpperCase(l);
        case 2:
          return getString(R.string.title_section3).toUpperCase(l);
        case 3:
          return getString(R.string.title_section4).toUpperCase(l);
      }
      return null;
    }
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_main, container,
          false);
      TextView textView = (TextView) rootView
          .findViewById(R.id.section_label);
      textView.setText(Integer.toString(getArguments().getInt(
          ARG_SECTION_NUMBER)));
      return rootView;
    }
  }

  @Override
  public void onBackPressed() {
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {

    switch (keyCode) {
      case KeyEvent.KEYCODE_HOME:
        Toast.makeText(this, "Home Pressed", Toast.LENGTH_SHORT).show();
        break;
    }

    return true;
  }
}
