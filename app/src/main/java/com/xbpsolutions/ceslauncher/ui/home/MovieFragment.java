package com.xbpsolutions.ceslauncher.ui.home;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.ui.home.adapters.BaseMovieAdapter;
import com.xbpsolutions.ceslauncher.ui.home.adapters.MovieAdapterByName;
import com.xbpsolutions.ceslauncher.ui.widgets.TfTextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by RUS on 04.09.2016.
 */
public class MovieFragment extends Fragment implements BaseMovieAdapter.OnItemClickListener,
    LoaderManager.LoaderCallbacks<ArrayList<AppModel>> {

  private ArrayList<AppModel> movieList;
  private RecyclerView recyclerView;
  private BaseMovieAdapter sectionedRecyclerAdapter;
  private boolean isDragStarted = false;
  private LinearLayout linearBottomUninstall;
  private TfTextView txtUninstall;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.recycler_view, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    linearBottomUninstall = (LinearLayout) view.findViewById(R.id.linearBottomUninstall);
    txtUninstall = (TfTextView) view.findViewById(R.id.txtUninstall);
    linearBottomUninstall.setVisibility(View.GONE);
    linearBottomUninstall.setOnDragListener(new MyDragListener());
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(0, null, this);
  }

  private void setAdapterByName(ArrayList<AppModel> apps) {
    Collections.sort(apps, new Comparator<AppModel>() {
      @Override
      public int compare(AppModel o1, AppModel o2) {
        return o1.getLabel().compareTo(o2.getLabel());
      }
    });
    sectionedRecyclerAdapter = new MovieAdapterByName(apps);
    sectionedRecyclerAdapter.setOnItemClickListener(this);

    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
    recyclerView.setLayoutManager(gridLayoutManager);
    sectionedRecyclerAdapter.setGridLayoutManager(gridLayoutManager);
    recyclerView.setAdapter(sectionedRecyclerAdapter);

    //  sectionedRecyclerAdapter.notifyDataSetChanged();
  }


  @Override
  public void onItemClicked(int adapterPosition, int positionInCollection) {

    AppModel app = movieList.get(positionInCollection);
    if (app != null) {
      Intent intent = getActivity().getPackageManager()
          .getLaunchIntentForPackage(app.getApplicationPackageName());

      if (intent != null) {
        startActivity(intent);
      }
    }
  }

  @Override
  public void onItemLongPressed(View view, int adapterPosition, int positionInCollection) {

    AppModel app = movieList.get(positionInCollection);
    ClipData data = ClipData.newPlainText("Drag", app.getApplicationPackageName());
    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
        view);
    view.startDrag(data, shadowBuilder, view, 0);
    linearBottomUninstall.setVisibility(View.VISIBLE);


  }

  @Override
  public Loader<ArrayList<AppModel>> onCreateLoader(int id, Bundle args) {
    return new AppsLoader(getActivity());
  }

  @Override
  public void onLoadFinished(Loader<ArrayList<AppModel>> loader, ArrayList<AppModel> data) {

    movieList = data;
    Collections.sort(data, new Comparator<AppModel>() {
      @Override
      public int compare(AppModel o1, AppModel o2) {
        return o1.getLabel().compareTo(o2.getLabel());
      }
    });

    setAdapterByName(data);

    // baseAppsAdapter.setData(apps);
  }

  @Override
  public void onLoaderReset(Loader<ArrayList<AppModel>> loader) {

  }

  class MyDragListener implements OnDragListener {


    @Override
    public boolean onDrag(View v, DragEvent event) {
      int action = event.getAction();
      switch (event.getAction()) {
        case DragEvent.ACTION_DRAG_STARTED:


          // do nothing
          break;
        case DragEvent.ACTION_DRAG_ENTERED:

          linearBottomUninstall.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.darkRed));
          // v.setBackgroundDrawable(enterShape);
          break;
        case DragEvent.ACTION_DRAG_EXITED:
          linearBottomUninstall.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.lightRed));
          //  v.setBackgroundDrawable(normalShape);
          break;
        case DragEvent.ACTION_DROP:

          ClipData.Item item = event.getClipData().getItemAt(0);
          Uri packageURI = Uri.parse("package:" + item.coerceToText(getActivity()));
          Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
          startActivity(uninstallIntent);

          break;
        case DragEvent.ACTION_DRAG_ENDED:
          linearBottomUninstall.setVisibility(View.GONE);
          // v.setBackgroundDrawable(normalShape);
        default:
          break;
      }
      return true;
    }
  }
}
