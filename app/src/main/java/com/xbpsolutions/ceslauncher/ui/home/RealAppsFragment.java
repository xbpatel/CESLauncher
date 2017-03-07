package com.xbpsolutions.ceslauncher.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.ui.home.adapter.AppsAdapterByName;
import com.xbpsolutions.ceslauncher.ui.home.adapter.BaseAppsAdapter;
import java.util.ArrayList;


public class RealAppsFragment extends Fragment implements
    LoaderManager.LoaderCallbacks<ArrayList<AppModel>> {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;


  AppsAdapterByName baseAppsAdapter;
  private RecyclerView recyclerView;
  GridLayoutManager gridLayoutManager ;
  public RealAppsFragment() {

  }

  // TODO: Rename and change types and number of parameters
  public static RealAppsFragment newInstance(String param1, String param2) {
    RealAppsFragment fragment = new RealAppsFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    gridLayoutManager = new GridLayoutManager(getContext(), 2);
    baseAppsAdapter = new AppsAdapterByName(new ArrayList<AppModel>());
    baseAppsAdapter.setGridLayoutManager(gridLayoutManager);
    getLoaderManager().initLoader(0, null, this);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_real_apps, container, false);

  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setAdapter(baseAppsAdapter);
  }

  @Override
  public Loader<ArrayList<AppModel>> onCreateLoader(int id, Bundle args) {
    return new AppsLoader(getActivity());
  }

  @Override
  public void onLoadFinished(Loader<ArrayList<AppModel>> loader, ArrayList<AppModel> data) {
    baseAppsAdapter.setData(data);
  }

  @Override
  public void onLoaderReset(Loader<ArrayList<AppModel>> loader) {
    baseAppsAdapter.setData(null);
  }
}
