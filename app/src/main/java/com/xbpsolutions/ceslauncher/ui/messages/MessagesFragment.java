package com.xbpsolutions.ceslauncher.ui.messages;


import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.helper.PermissionHelper;
import com.xbpsolutions.ceslauncher.helper.VerticalSpaceItemDecoration;
import com.xbpsolutions.ceslauncher.ui.widgets.TfTextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    PermissionHelper permissionHelper;
    private TfTextView txtProcessPermission;

    private LinearLayout layoutNoPermissionLog;
    private RecyclerView listMessages;
    boolean isPermissionAccepted;
    private static final int URL_LOADER_MESSAGES = 12;
    private boolean isDecorationAdded = false;
    private VerticalSpaceItemDecoration verticalSpaceItemDecoration;


    public MessagesFragment() {
        // Required empty public constructor
    }


    public static MessagesFragment newInstance(String param1, String param2) {
        MessagesFragment fragment = new MessagesFragment();
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
        verticalSpaceItemDecoration = new VerticalSpaceItemDecoration(5);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        permissionHelper = new PermissionHelper(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS}, 110);
        Log.e("SMS Permission?", "  " + isPermissionAccepted);

        txtProcessPermission = (TfTextView) view.findViewById(R.id.txtProcessPermissionSMS);
        layoutNoPermissionLog = (LinearLayout) view.findViewById(R.id.layoutNoPermissionLogSMS);
        listMessages = (RecyclerView) view.findViewById(R.id.listMessages);
        txtProcessPermission.setOnClickListener(permissionClick);
        setDisplayAccordingPermission();
        getAllHistory();
    }

    public void getAllHistory() {

        getActivity().getSupportLoaderManager().initLoader(URL_LOADER_MESSAGES, null, this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null && requestCode == 110) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
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


    @Override
    public void onResume() {
        super.onResume();
       // getActivity().getSupportLoaderManager().restartLoader(URL_LOADER_MESSAGES, null, this);
    }

    private void setDisplayAccordingPermission() {

        isPermissionAccepted = permissionHelper.checkSelfPermission(new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS});

        if (isPermissionAccepted) {

            layoutNoPermissionLog.setVisibility(View.GONE);
            listMessages.setVisibility(View.VISIBLE);
            getAllHistory();

        } else {

            layoutNoPermissionLog.setVisibility(View.VISIBLE);
            listMessages.setVisibility(View.GONE);
        }
    }


    private void setupCalls(ArrayList<MessageModel> calls) {

        Log.e("Total Messages Count", " " + calls.size());

        MessageListAdapter adapter = new MessageListAdapter(getActivity(), calls);
        listMessages.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMessages.removeItemDecoration(verticalSpaceItemDecoration);
        listMessages.addItemDecoration(verticalSpaceItemDecoration);
        listMessages.setAdapter(adapter);
        listMessages.getAdapter().notifyDataSetChanged();

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("----IsUserVisibleHint", " " + isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.e("Inside Loader", "On Create Loader");

        CursorLoader messageLoader = null;

        switch (id) {
            case URL_LOADER_MESSAGES:
                messageLoader = new CursorLoader(
                        getActivity(),
                        Telephony.Sms.Inbox.CONTENT_URI,
                        null,
                        null,
                        null,
                        Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
                );

                break;
        }

        return messageLoader;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor managedCursor) {
        Log.e("Inside Loader", "On Loader Finished");

        int count = managedCursor.getCount();
        Log.e("Inside Loader", "Count " + count);

        Log.e("SMS", "OnLoadFinished  " + managedCursor.getCount());

        int number = managedCursor.getColumnIndex(Telephony.Sms.Inbox.ADDRESS);
        int body = managedCursor.getColumnIndex(Telephony.Sms.Inbox.BODY);
        int date = managedCursor.getColumnIndex(Telephony.Sms.Inbox.DATE);
        int person = managedCursor.getColumnIndex(Telephony.Sms.Inbox.PERSON);

        ArrayList<MessageModel> calls = new ArrayList<>();

        if (managedCursor.getCount() > 0) {
            managedCursor.moveToFirst();
        }

        if (managedCursor.moveToFirst()) {
            do {

                Log.e("Inside While", "Prepanned");
                String pNumber = managedCursor.getString(number);
                String pbody = managedCursor.getString(body);
                String pDate = managedCursor.getString(date);
                Date receivedDate = new Date(Long.valueOf(pDate));
                String pperson = managedCursor.getString(person);

                MessageModel model = new MessageModel();
                model.setBody(pbody);
                model.setNumber(pNumber);
                model.setReceivedDate(receivedDate);
                model.setPerson(pperson);
                calls.add(model);

            } while (managedCursor.moveToNext());
        }

        //managedCursor.close();
        setupCalls(calls);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
