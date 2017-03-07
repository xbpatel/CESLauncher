package com.xbpsolutions.ceslauncher.ui.home;

import android.content.res.Resources;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.ui.home.adapters.BaseMovieAdapter;
import com.xbpsolutions.ceslauncher.ui.home.adapters.MovieAdapterByName;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return recyclerView;
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
        recyclerView.setLayoutManager(gridLayoutManager);
        sectionedRecyclerAdapter.setGridLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(sectionedRecyclerAdapter);
        //  sectionedRecyclerAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClicked(int adapterPosition, int positionInCollection) {
        final String text = "Item clicked: adapter position = " + adapterPosition +
                ", position in collection = " + positionInCollection;

        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Loader<ArrayList<AppModel>> onCreateLoader(int id, Bundle args) {
        return new AppsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<AppModel>> loader, ArrayList<AppModel> data) {

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
}
