package com.xbpsolutions.ceslauncher.ui.calls;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xbpsolutions.ceslauncher.R;

/**
 * Demonstrates the use of a RecyclerViewCursorAdapter to display a list of movies.
 * <p>
 * Created by adammcneilly on 12/8/15.
 */
public class CallListAdapter extends RecyclerViewCursorAdapter<CallListAdapter.MovieViewHolder> {

    /**
     * Column projection for the query to pull Movies from the database.
     */
    public static final String[] MOVIE_COLUMNS = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME
    };

    /**
     * Index of the name column.
     */
    private static final int NAME_INDEX = 1;

    /**
     * Constructor.
     *
     * @param context The Context the Adapter is displayed in.
     */
    public CallListAdapter(Context context) {
        super(context);

        setupCursorAdapter(null, 0, R.layout.list_item_calllog, false);
    }

    /**
     * Returns the ViewHolder to use for this adapter.
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent));
    }

    /**
     * Moves the Cursor of the CursorAdapter to the appropriate position and binds the view for
     * that item.
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        // Move cursor to this position
        mCursorAdapter.getCursor().moveToPosition(position);

        // Set the ViewHolder
        setViewHolder(holder);

        // Bind this view
        mCursorAdapter.bindView(null, mContext, mCursorAdapter.getCursor());
    }

    /**
     * ViewHolder used to display a movie name.
     */
    public class MovieViewHolder extends RecyclerViewCursorViewHolder {
        public final TextView mMovieName;

        public MovieViewHolder(View view) {
            super(view);

            mMovieName = (TextView) view.findViewById(R.id.movie_name);
        }

        @Override
        public void bindCursor(Cursor cursor) {
            mMovieName.setText(cursor.getString(NAME_INDEX));
        }
    }
}
