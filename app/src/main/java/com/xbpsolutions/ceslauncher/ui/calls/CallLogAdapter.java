package com.xbpsolutions.ceslauncher.ui.calls;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.helper.PrefUtils;
import com.xbpsolutions.ceslauncher.ui.widgets.TfTextView;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by excellent-3 on 13/04/17.
 */

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.ViewHolder> {

    private final List<CallModel> mValues;
    private Context context;


    public CallLogAdapter(Context ctx, List<CallModel> items) {
        mValues = items;
        this.context = ctx;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_calllog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String pName = (mValues.get(position).getPname() == null || TextUtils.isEmpty(mValues.get(position).getPname())) ? "Unknown" : mValues.get(position).getPname();
        holder.txtCallLogName.setText(pName);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        int callTypeCode = Integer.parseInt(mValues.get(position).getCallType());
        int color = manipulateColor(Color.parseColor(PrefUtils.getSelectedColor(context)), 0.8f);
        holder.imgCallType.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        holder.imgCall.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        switch (callTypeCode) {
            case CallLog.Calls.OUTGOING_TYPE:
                holder.imgCallType.setImageResource(R.drawable.ic_call_made_black_24dp);
                break;

            case CallLog.Calls.INCOMING_TYPE:
                holder.imgCallType.setImageResource(R.drawable.ic_call_received_black_24dp);
                break;

            case CallLog.Calls.MISSED_TYPE:
                holder.imgCallType.setImageResource(R.drawable.ic_call_missed_black_24dp);
                break;

            case CallLog.Calls.REJECTED_TYPE:
                holder.imgCallType.setImageResource(R.drawable.ic_call_received_black_24dp);
                holder.imgCallType.setColorFilter(ContextCompat.getColor(context, R.color.darkRed), PorterDuff.Mode.SRC_ATOP);
                break;
        }


        if (mValues.get(position).getPhotoUri() != null) {
            holder.profileImage.setImageURI(mValues.get(position).getPhotoUri());
        } else {
            holder.profileImage.setImageResource(R.mipmap.ic_launcher_round);
        }


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public CallModel mItem;
        public TfTextView txtCallLogName;
        public ImageView imgCallType;
        public ImageView imgCall;
        public CircleImageView profileImage;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtCallLogName = (TfTextView) view.findViewById(R.id.txtCallLogName);
            imgCallType = (ImageView) view.findViewById(R.id.imgCallType);
            imgCall = (ImageView) view.findViewById(R.id.imgCallLog);
            profileImage = (CircleImageView) view.findViewById(R.id.profile_image);


        }

    }

    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }


}
