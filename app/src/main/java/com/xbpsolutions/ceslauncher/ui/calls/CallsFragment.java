package com.xbpsolutions.ceslauncher.ui.calls;


import android.Manifest;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.helper.Functions;
import com.xbpsolutions.ceslauncher.helper.PermissionHelper;
import com.xbpsolutions.ceslauncher.ui.BaseFragment;
import com.xbpsolutions.ceslauncher.ui.widgets.TfTextView;

public class CallsFragment extends BaseFragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    PermissionHelper permissionHelper;
    private TfTextView txtProcessPermission;

    private LinearLayout layoutNoPermissionLog;
    private RecyclerView listCallLog;
    boolean isPermissionAccepted;

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


        permissionHelper = new PermissionHelper(this, new String[]{Manifest.permission.READ_CALL_LOG}, 100);

        Log.e("Permission accepted?", "  " + isPermissionAccepted);


        txtProcessPermission = (TfTextView) view.findViewById(R.id.txtProcessPermission);
        layoutNoPermissionLog = (LinearLayout) view.findViewById(R.id.layoutNoPermissionLog);
        listCallLog = (RecyclerView) view.findViewById(R.id.listCallHistory);
        txtProcessPermission.setOnClickListener(permissionClick);

        setDisplayAccordingPermission();


    }

    private void setDisplayAccordingPermission() {

        isPermissionAccepted = permissionHelper.checkSelfPermission(new String[]{Manifest.permission.READ_CALL_LOG});
        if (isPermissionAccepted) {
            layoutNoPermissionLog.setVisibility(View.GONE);
            listCallLog.setVisibility(View.VISIBLE);

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


    private void getAllHistory() {


        Uri allCalls = Uri.parse("content://call_log/calls");
        Cursor c = getActivity().managedQuery(allCalls, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }

        int name = c.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int number = c.getColumnIndex(CallLog.Calls.NUMBER);
        int type = c.getColumnIndex(CallLog.Calls.TYPE);
        int date = c.getColumnIndex(CallLog.Calls.DATE);
        int duration = c.getColumnIndex(CallLog.Calls.DURATION);

        while (c.moveToNext()) {

            String phNumber = c.getString(number);
            String n = c.getString(name);
            Log.e("History ", String.format("Name :%s, Number : %s", n, phNumber));

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
