package com.xbpsolutions.ceslauncher.ui.splash;


import static android.R.attr.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xbpsolutions.ceslauncher.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashItemFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private int mParam1;
  private String mParam2;


  public SplashItemFragment() {
    // Required empty public constructor
  }

  public static SplashItemFragment newInstance(int param1, String param2) {
    SplashItemFragment fragment = new SplashItemFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getInt(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_splash_item, container, false);
  }

}