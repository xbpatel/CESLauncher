package com.xbpsolutions.ceslauncher.ui.home;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
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
import com.xbpsolutions.ceslauncher.ui.widgets.SlidingDrawer;
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
    private LinearLayout linearBottomUninstall, linearBottomAppInfo, linearBottomOptions;
    private TfTextView txtUninstall;
    private SlidingDrawer slidingDrawer;

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
        linearBottomAppInfo = (LinearLayout) view.findViewById(R.id.linearBottomAppInfo);
        linearBottomOptions = (LinearLayout) view.findViewById(R.id.linearBottomOptions);

        txtUninstall = (TfTextView) view.findViewById(R.id.txtUninstall);
        linearBottomUninstall.setOnDragListener(new MyUnInstallDragListener());
        linearBottomAppInfo.setOnDragListener(new MyAppinfoDragListener());


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
        openApp(app.getApplicationPackageName());

    }


    private void openApp(String mPackage) {
        Intent intent = getActivity().getPackageManager()
                .getLaunchIntentForPackage(mPackage);

        if (intent != null) {
            startActivity(intent);
        }
    }

    private void openAppInfo(String mPackage) {

        try {
            //Open the specific App Info page:
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + mPackage));
            startActivity(intent);

        } catch (ActivityNotFoundException e) {
            //e.printStackTrace();

            //Open the generic Apps page:
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            startActivity(intent);

        }
    }

    @Override
    public void onItemLongPressed(View view, int adapterPosition, int positionInCollection) {

        AppModel app = movieList.get(positionInCollection);
        ClipData data = ClipData.newPlainText("Drag", app.getApplicationPackageName());
        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                view);
        view.startDrag(data, shadowBuilder, view, 0);
        linearBottomOptions.setVisibility(View.VISIBLE);

        if (app.isSystemApp()) {
            linearBottomUninstall.setVisibility(View.GONE);
        } else {
            linearBottomUninstall.setVisibility(View.VISIBLE);
        }


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

    class MyUnInstallDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    linearBottomUninstall
                            .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.darkRed));
                    // v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    linearBottomUninstall
                            .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.lightRed));
                    //  v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:

                    ClipData.Item item = event.getClipData().getItemAt(0);
                    Uri packageURI = Uri.parse("package:" + item.coerceToText(getActivity()));
                    Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                    startActivity(uninstallIntent);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    linearBottomOptions.setVisibility(View.GONE);
                    // v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }

    class MyAppinfoDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    linearBottomAppInfo
                            .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.darkRed));
                    // v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    linearBottomAppInfo
                            .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.lightRed));
                    //  v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:

                    ClipData.Item item = event.getClipData().getItemAt(0);

                    String mpackage = item.coerceToText(getActivity()).toString();
                    openAppInfo(mpackage);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    linearBottomAppInfo
                            .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.lightRed));
                    linearBottomOptions.setVisibility(View.GONE);
                    // v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
}
