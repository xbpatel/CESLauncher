package com.xbpsolutions.ceslauncher.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.ui.splash.IntroItem;
import com.xbpsolutions.ceslauncher.ui.splash.SplashFragmentPagerAdapter;
import com.xbpsolutions.ceslauncher.ui.widgets.EnchantedViewPager;

import java.util.ArrayList;


public class LauncherActivity extends AppCompatActivity {

    private EnchantedViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getSupportActionBar().hide();

        setup();


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


    }
}
