package com.xbpsolutions.ceslauncher.ui.home.adapter;

import android.view.View;
import com.xbpsolutions.ceslauncher.ui.home.AppModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RUS on 04.09.2016.
 */

public class AppsAdapterByName extends BaseAppsAdapter {

  public AppsAdapterByName(List<AppModel> itemList) {
    super(itemList);
  }

  public void setData(ArrayList<AppModel> data) {
    appsList = data;
    notifyDataSetChanged();
  }

  @Override
  public boolean onPlaceSubheaderBetweenItems(int itemPosition, int nextItemPosition) {
    final AppModel movie1 = appsList.get(itemPosition);
    final AppModel movie2 = appsList.get(nextItemPosition);

    return !movie1.getLabel().substring(0, 1).equals(movie2.getLabel().substring(0, 1));
  }

  @Override
  public void onBindItemViewHolder(final MovieViewHolder holder, final int position) {
    final AppModel movie = appsList.get(position);

    holder.textName.setText(movie.getLabel());
    holder.imgIcon.setImageDrawable(movie.getIcon());
//    holder.textMovieGenre.setText(movie.getGenre());
//    holder.textMovieYear.setText(String.valueOf(movie.getYear()));

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onItemClickListener.onItemClicked(holder.getAdapterPosition(), position);
      }
    });
  }

  @Override
  public void onBindSubheaderViewHolder(SubheaderViewHolder subheaderViewHolder,
      int nextItemPosition) {
    final AppModel nextMovie = appsList.get(nextItemPosition);
    subheaderViewHolder.subheaderText.setText(nextMovie.getLabel().substring(0, 1));
  }
}
