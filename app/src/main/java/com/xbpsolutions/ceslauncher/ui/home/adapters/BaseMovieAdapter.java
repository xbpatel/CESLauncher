package com.xbpsolutions.ceslauncher.ui.home.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.ui.home.AppModel;
import com.xbpsolutions.ceslauncher.ui.home.adapter.SectionedRecyclerAdapter;
import com.xbpsolutions.ceslauncher.ui.widgets.TfTextView;

import java.util.List;


/**
 * Created by RUS on 02.11.2016.
 */

public abstract class BaseMovieAdapter extends SectionedRecyclerAdapter<BaseMovieAdapter.SubheaderViewHolder, BaseMovieAdapter.MovieViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(int adapterPosition, int positionInCollection);
        void onItemLongPressed(View view,int adapterPosition, int positionInCollection);
    }

    protected List<AppModel> movieList;

    protected OnItemClickListener onItemClickListener;

    public static class SubheaderViewHolder extends RecyclerView.ViewHolder {

        private static Typeface meduiumTypeface = null;

        public TfTextView subheaderText;

        public SubheaderViewHolder(View itemView) {
            super(itemView);
            this.subheaderText = (TfTextView) itemView.findViewById(R.id.subheaderText);

//            if(meduiumTypeface == null) {
//                meduiumTypeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Roboto-Medium.ttf");
//            }
//            this.subheaderText.setTypeface(meduiumTypeface);
        }

    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        protected TfTextView textMovieName;
        protected ImageView imageView;


        public MovieViewHolder(View itemView) {
            super(itemView);
            this.textMovieName = (TfTextView) itemView.findViewById(R.id.text);
            this.imageView = (ImageView)itemView.findViewById(R.id.icon);

        }
    }

    public BaseMovieAdapter(List<AppModel> itemList) {
        super();
        this.movieList = itemList;
    }

    @Override
    public MovieViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_icon_text, parent, false));
    }

    @Override
    public SubheaderViewHolder onCreateSubheaderViewHolder(ViewGroup parent, int viewType) {
        return new SubheaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false));
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
