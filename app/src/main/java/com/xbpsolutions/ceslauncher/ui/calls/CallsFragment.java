package com.xbpsolutions.ceslauncher.ui.calls;


import android.Manifest;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Activity;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.helper.PermissionHelper;
import com.xbpsolutions.ceslauncher.ui.BaseFragment;
import com.xbpsolutions.ceslauncher.ui.widgets.TfTextView;

public class CallsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    PermissionHelper permissionHelper;
    private TfTextView txtProcessPermission;

    private LinearLayout layoutNoPermissionLog;
    private RecyclerView listCallLog;
    boolean isPermissionAccepted;
    private static final int URL_LOADER = 1;

    public CallsFragment() {
        // Required empty public constructor
    }

    public static CallsFragment newInstance(String param1, String param2) {
        CallsFragment fragment = new CallsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calls, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        permissionHelper = new PermissionHelper(this, new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS}, 100);

        Log.e("Permission accepted?", "  " + isPermissionAccepted);

        txtProcessPermission = (TfTextView) view.findViewById(R.id.txtProcessPermission);
        layoutNoPermissionLog = (LinearLayout) view.findViewById(R.id.layoutNoPermissionLog);
        listCallLog = (RecyclerView) view.findViewById(R.id.listCallHistory);
        txtProcessPermission.setOnClickListener(permissionClick);
        setDisplayAccordingPermission();

    }

    @Override
    public void onResume() {
        super.onResume();

        if(!getLoaderManager().hasRunningLoaders()){
            getLoaderManager().restartLoader(URL_LOADER, null, this);
        }

    }

    private void setDisplayAccordingPermission() {

        isPermissionAccepted = permissionHelper.checkSelfPermission(new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS});

        if (isPermissionAccepted) {

            layoutNoPermissionLog.setVisibility(View.GONE);
            listCallLog.setVisibility(View.VISIBLE);
            getAllHistory();

        } else {

            layoutNoPermissionLog.setVisibility(View.VISIBLE);
            listCallLog.setVisibility(View.GONE);

        }
    }


    private View.OnClickListener permissionClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            permissionHelper.request(new PermissionHelper.PermissionCallback() {
                @Override
                public void onPermissionGranted() {
                    setDisplayAccordingPermission();
                    getAllHistory();
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(getActivity(), "Permission is not accepted", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onPermissionDeniedBySystem() {

                }
            });


        }
    };


    public void getAllHistory() {

        getLoaderManager().initLoader(URL_LOADER, null, this);
//        Log.e("Calls", "Called History Method");
//        Uri allCalls = Uri.parse("content://call_log/calls");
//        Cursor c = getActivity().managedQuery(allCalls, null, null, null, null);
//        if (c != null) {
//            c.moveToFirst();
//        }
//
//
//        int name = c.getColumnIndex(CallLog.Calls.CACHED_NAME);
//        int number = c.getColumnIndex(CallLog.Calls.NUMBER);
//        int type = c.getColumnIndex(CallLog.Calls.TYPE);
//        int date = c.getColumnIndex(CallLog.Calls.DATE);
//        int duration = c.getColumnIndex(CallLog.Calls.DURATION);
//
//        while (c.moveToNext()) {
//
//            String phNumber = c.getString(number);
//            String n = c.getString(name);
//            Log.e("History ", String.format("Name :%s, Number : %s", n, phNumber));
//
//        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case URL_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        getActivity(),   // Parent activity context
                        CallLog.Calls.CONTENT_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        android.provider.CallLog.Calls.DEFAULT_SORT_ORDER             // Default sort order
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor managedCursor) {


        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        while (managedCursor.moveToNext()) {

            String pname = managedCursor.getString(name);
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;

            int callTypeCode = Integer.parseInt(callType);
            switch (callTypeCode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Outgoing";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Incoming";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "Missed";
                    break;
            }

            if (pname == null) {

                String receivedNumber = contactExists(getActivity(), phNumber);
                Log.e("History: ", String.format(" Received Name : %s Phone : %s", pname, phNumber));

            } else {
                Log.e("History: ", String.format("Name : %s Phone : %s", pname, phNumber));
            }


        }
        managedCursor.close();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public String contactExists(Context context, String number) {
        /// number is the phone number
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return cur.getString(cur.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME));
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return "";
    }
}
