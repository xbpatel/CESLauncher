package com.xbpsolutions.ceslauncher.ui.messages;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.helper.PrefUtils;
import com.xbpsolutions.ceslauncher.ui.calls.CallModel;
import com.xbpsolutions.ceslauncher.ui.calls.widget.DSAvatarImageView;
import com.xbpsolutions.ceslauncher.ui.widgets.TfTextView;

import java.util.List;

/**
 * Created by excellent-3 on 13/04/17.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private final List<MessageModel> mValues;
    private Context context;

    public MessageListAdapter(Context ctx, List<MessageModel> items) {
        mValues = items;
        this.context = ctx;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.txtMessageSender.setText(getContactName(context, mValues.get(position).getNumber()));
        holder.txtMessageBody.setText(mValues.get(position).getBody());
        int color = manipulateColor(Color.parseColor(PrefUtils.getSelectedColor(context)), 0.8f);
        holder.imgMessageCopy.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TfTextView txtMessageSender;
        public TfTextView txtMessageBody;
        public ImageView imgMessageCopy;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtMessageBody = (TfTextView) view.findViewById(R.id.txtMessageBody);
            txtMessageSender = (TfTextView) view.findViewById(R.id.txtMessageSender);
            imgMessageCopy = (ImageView) view.findViewById(R.id.imgMessageCopy);

            txtMessageSender.setTextColor(Color.parseColor("#ddffffff"));
            txtMessageBody.setTextColor(Color.parseColor("#99ffffff"));



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


    public String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = phoneNumber;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

}
