package com.xbpsolutions.ceslauncher.ui.calls;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
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

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.helper.PermissionHelper;
import com.xbpsolutions.ceslauncher.helper.PrefUtils;
import com.xbpsolutions.ceslauncher.ui.calls.widget.DSAvatarImageView;
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
    private boolean isPermissionGranted = false;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String pName = (mValues.get(position).getPname() == null || TextUtils.isEmpty(mValues.get(position).getPname())) ? mValues.get(position).getPhNumber() : mValues.get(position).getPname();
        holder.txtCallLogName.setText(pName);

        String avtarName = (mValues.get(position).getPname() == null || TextUtils.isEmpty(mValues.get(position).getPname())) ? "Unknown" : mValues.get(position).getPname();
        holder.profileImage.setName(avtarName);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        int callTypeCode = Integer.parseInt(mValues.get(position).getCallType());
        int color = manipulateColor(Color.parseColor(PrefUtils.getSelectedColor(context)), 0.8f);
        holder.imgCallType.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        holder.imgCall.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        holder.timestamp.setTextColor(Color.parseColor("#99ffffff"));
        holder.profileImage.setBackgroundColor(Color.parseColor(PrefUtils.getSelectedColor(context)));
        holder.profileImage.setTextColor(color);

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
            //  holder.profileImage.setImageResource(R.mipmap.ic_launcher_round);
        }


        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callNumber(mValues.get(position).getPhNumber());

            }
        });

        holder.timestamp.setReferenceTime(mValues.get(position).getCallDayTime().getTime());


    }

    private void callNumber(String number) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        context.startActivity(callIntent);
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
        public DSAvatarImageView profileImage;
        public RelativeTimeTextView timestamp;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtCallLogName = (TfTextView) view.findViewById(R.id.txtCallLogName);
            imgCallType = (ImageView) view.findViewById(R.id.imgCallType);
            imgCall = (ImageView) view.findViewById(R.id.imgCallLog);
            profileImage = (DSAvatarImageView) view.findViewById(R.id.profile_image);
            timestamp = (RelativeTimeTextView) view.findViewById(R.id.timestamp);


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
