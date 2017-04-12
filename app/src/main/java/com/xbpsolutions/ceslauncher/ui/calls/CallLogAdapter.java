package com.xbpsolutions.ceslauncher.ui.calls;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Created by excellent-3 on 12/04/17.
 */

public class CallLogAdapter extends SimpleCursorAdapter {

    public CallLogAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }
}
