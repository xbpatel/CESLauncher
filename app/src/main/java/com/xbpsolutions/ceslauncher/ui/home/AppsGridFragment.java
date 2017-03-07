package com.xbpsolutions.ceslauncher.ui.home;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.GridView;
import com.xbpsolutions.ceslauncher.ui.home.adapter.AppsAdapterByName;
import com.xbpsolutions.ceslauncher.ui.home.adapter.BaseAppsAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Arnab Chakraborty
 */
public class AppsGridFragment extends GridFragmentRevised implements
    LoaderManager.LoaderCallbacks<ArrayList<AppModel>> {

  //AppListAdapter mAdapter;
  BaseAppsAdapter baseAppsAdapter;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    //setEmptyText("No Applications");
    baseAppsAdapter = new AppsAdapterByName(new ArrayList<AppModel>());
    gridLayoutManager = new GridLayoutManager(getContext(), 3);
    baseAppsAdapter.setGridLayoutManager(gridLayoutManager);
    // mAdapter = new AppListAdapter(getActivity());
    setGridAdapter(baseAppsAdapter);
    // till the data is loaded display a spinner
    setGridShown(false);
    // create the loader to load the apps list in background
    getLoaderManager().initLoader(0, null, this);
  }

  @Override
  public Loader<ArrayList<AppModel>> onCreateLoader(int id, Bundle bundle) {
    return new AppsLoader(getActivity());
  }

  @Override
  public void onLoadFinished(Loader<ArrayList<AppModel>> loader, ArrayList<AppModel> apps) {

    Collections.sort(apps, new Comparator<AppModel>() {
      @Override
      public int compare(AppModel o1, AppModel o2) {
        return o1.getLabel().compareTo(o2.getLabel());
      }
    });

    baseAppsAdapter.setData(apps);

    if (isResumed()) {
      setGridShown(true);
    } else {
      setGridShownNoAnimation(true);
    }
  }

  @Override
  public void onLoaderReset(Loader<ArrayList<AppModel>> loader) {
    baseAppsAdapter.setData(null);
  }

  @Override
  public void onGridItemClick(GridView g, View v, int position, long id) {
//    AppModel app = (AppModel) getGridAdapter().getItem(position);
//    if (app != null) {
//      Intent intent = getActivity().getPackageManager()
//          .getLaunchIntentForPackage(app.getApplicationPackageName());
//
//      if (intent != null) {
//        startActivity(intent);
//      }
//    }
  }
}
