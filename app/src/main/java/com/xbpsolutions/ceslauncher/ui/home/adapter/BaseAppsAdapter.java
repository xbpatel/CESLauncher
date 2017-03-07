package com.xbpsolutions.ceslauncher.ui.home.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.ui.home.AppModel;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by RUS on 02.11.2016.
 */

public abstract class BaseAppsAdapter extends
    SectionedRecyclerAdapter<BaseAppsAdapter.SubheaderViewHolder, BaseAppsAdapter.MovieViewHolder> {

  public void setData(ArrayList<AppModel> data) {
    this.appsList = data;
  }

  public interface OnItemClickListener {

    void onItemClicked(int adapterPosition, int positionInCollection);
  }

  protected List<AppModel> appsList;

  protected OnItemClickListener onItemClickListener;

  public static class SubheaderViewHolder extends RecyclerView.ViewHolder {

    private static Typeface meduiumTypeface = null;

    public TextView subheaderText;

    public SubheaderViewHolder(View itemView) {
      super(itemView);
      this.subheaderText = (TextView) itemView.findViewById(R.id.subheaderText);

//      if (meduiumTypeface == null) {
//        meduiumTypeface = Typeface
//            .createFromAsset(itemView.getContext().getAssets(), "Roboto-Medium.ttf");
//      }
//      this.subheaderText.setTypeface(meduiumTypeface);
    }

  }

  public static class MovieViewHolder extends RecyclerView.ViewHolder {

    protected TextView textName;
    protected ImageView imgIcon;


    public MovieViewHolder(View itemView) {
      super(itemView);
      this.textName = (TextView) itemView.findViewById(R.id.text);
      this.imgIcon = (ImageView) itemView.findViewById(R.id.icon);

    }
  }

  public BaseAppsAdapter(List<AppModel> itemList) {
    super();
    this.appsList = itemList;
  }

  @Override
  public MovieViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
    return new MovieViewHolder(
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_item_icon_text, parent, false));
  }

  @Override
  public SubheaderViewHolder onCreateSubheaderViewHolder(ViewGroup parent, int viewType) {
    return new SubheaderViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false));
  }

  @Override
  public int getCount() {
    return appsList.size();
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
