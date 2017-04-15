package com.xbpsolutions.ceslauncher.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.ui.home.AppModel;

import java.util.List;


public class RevisedAppListAdapter extends RecyclerView.Adapter<RevisedAppListAdapter.ViewHolder> {

    private final List<AppModel> mValues;
    private Context context;
    private OnSetClicks onSetClicksListner;

    public void setOnSetClicksListner(OnSetClicks onSetClicksListner) {
        this.onSetClicksListner = onSetClicksListner;
    }

    public RevisedAppListAdapter(Context ctx, List<AppModel> items) {
        mValues = items;
        this.context = ctx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_icon_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

//
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);

        final AppModel movie = mValues.get(position);

        holder.txtAppName.setText(movie.getLabel());
        holder.imgIcon.setImageDrawable(movie.getIcon());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gotoScreen(context, HistoryDetailsActivity.class);
                onSetClicksListner.onClick(position);

            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onSetClicksListner.onLongClick(v,position);
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public AppModel mItem;
        public TextView txtAppName;
        public ImageView imgIcon;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            txtAppName = (TextView) view.findViewById(R.id.text);
            imgIcon = (ImageView) view.findViewById(R.id.icon);


        }

    }

    public interface OnSetClicks {

        void onClick(int position);

        void onLongClick(View view, int position);

    }
}
