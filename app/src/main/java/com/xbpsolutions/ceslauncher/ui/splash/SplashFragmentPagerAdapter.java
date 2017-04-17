package com.xbpsolutions.ceslauncher.ui.splash;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.ui.widgets.EnchantedViewPager;

import java.util.ArrayList;


public class SplashFragmentPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<IntroItem> items;

    public SplashFragmentPagerAdapter(Context mContext, ArrayList<IntroItem> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.introview, null, false);
        layout.setTag(EnchantedViewPager.ENCHANTED_VIEWPAGER_POSITION + position);
        setupLayout(layout, position);
        container.addView(layout);



        return layout;
    }

    private void setupLayout(ViewGroup layout, int position) {

        View verticalStrip = layout.findViewById(R.id.verticalStrip);
        int color = items.get(position).color;
        verticalStrip.setBackgroundColor(color);

    }
}
