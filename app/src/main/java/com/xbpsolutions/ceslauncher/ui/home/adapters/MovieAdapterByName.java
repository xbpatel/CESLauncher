package com.xbpsolutions.ceslauncher.ui.home.adapters;

import android.view.View;

import android.view.View.OnLongClickListener;
import com.xbpsolutions.ceslauncher.ui.home.AppModel;

import java.util.List;


/**
 * Created by RUS on 04.09.2016.
 */

public class MovieAdapterByName extends BaseMovieAdapter {

    public MovieAdapterByName(List<AppModel> itemList) {
        super(itemList);
    }

    @Override
    public boolean onPlaceSubheaderBetweenItems(int itemPosition, int nextItemPosition) {
        final AppModel movie1 = movieList.get(itemPosition);
        final AppModel movie2 = movieList.get(nextItemPosition);

        return !movie1.getLabel().substring(0, 1).equals(movie2.getLabel().substring(0, 1));
    }

    @Override
    public void onBindItemViewHolder(final MovieViewHolder holder, final int position) {
        final AppModel movie = movieList.get(position);

        holder.textMovieName.setText(movie.getLabel());
        holder.imageView.setImageDrawable(movie.getIcon());
        // holder.textMovieGenre.setText(movie.getGenre());
        // holder.textMovieYear.setText(String.valueOf(movie.getYear()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(holder.getAdapterPosition(), position);
            }
        });


        holder.itemView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.onItemLongPressed(holder.itemView,holder.getAdapterPosition(), position);
                return true;
            }
        });
    }

    @Override
    public void onBindSubheaderViewHolder(SubheaderViewHolder subheaderViewHolder, int nextItemPosition) {
        final AppModel nextMovie = movieList.get(nextItemPosition);
        subheaderViewHolder.subheaderText.setText(nextMovie.getLabel().substring(0, 1));
    }
}
