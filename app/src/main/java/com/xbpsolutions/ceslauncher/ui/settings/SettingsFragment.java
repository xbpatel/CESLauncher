package com.xbpsolutions.ceslauncher.ui.settings;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.helper.Functions;
import com.xbpsolutions.ceslauncher.helper.PrefUtils;
import com.xbpsolutions.ceslauncher.ui.HomeActivity;
import com.xbpsolutions.ceslauncher.ui.widgets.ColorBox;
import com.xbpsolutions.ceslauncher.ui.widgets.ColorChooser;
import com.xbpsolutions.ceslauncher.ui.widgets.ColorChooser.OnBoxClickListner;
import com.xbpsolutions.ceslauncher.ui.widgets.TfTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private TfTextView txtTitleChooseTheme;
  private ColorBox colorBoxChoosenTheme;
  private ColorChooser colorChooser;
  private RelativeLayout relativeChooseTheme;
  private RelativeLayout relativeChangeLauncher;

  //this is settings

  public SettingsFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment SettingsFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static SettingsFragment newInstance(String param1, String param2) {
    SettingsFragment fragment = new SettingsFragment();
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
    return inflater.inflate(R.layout.fragment_settings, container, false);
  }

  @Override
  public void onResume() {
    super.onResume();
    colorBoxChoosenTheme.setCurrentColor(PrefUtils.getSelectedColor(getActivity()));
    colorBoxChoosenTheme.setup();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    colorBoxChoosenTheme = (ColorBox) view.findViewById(R.id.colorBoxChoosenTheme);
    colorChooser = (ColorChooser) view.findViewById(R.id.colorChooser);
    txtTitleChooseTheme = (TfTextView) view.findViewById(R.id.txtTitleChooseTheme);
    relativeChooseTheme = (RelativeLayout) view.findViewById(R.id.relativeChooseTheme);
    relativeChangeLauncher = (RelativeLayout) view.findViewById(R.id.relativeChangeLauncher);
    relativeChooseTheme.setOnClickListener(chooseThemeClickListner);
    relativeChangeLauncher.setOnClickListener(changeLauncherClickListner);

    colorChooser.setClickListner(new OnBoxClickListner() {
      @Override
      public void onBoxClicked(String selectedColor) {

        displayAlert(selectedColor);

      }
    });


  }


  private void displayAlert(final String selectedColor) {
    new AlertDialog.Builder(getActivity())
        .setTitle("Apply Theme?")
        .setMessage("Are you sure you want to apply this color?")
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            // continue with delete
            PrefUtils.setSelectedColor(getActivity(), selectedColor);
            colorBoxChoosenTheme.setCurrentColor(PrefUtils.getSelectedColor(getActivity()));
            colorBoxChoosenTheme.setup();
            ((HomeActivity) getActivity()).setThemeColor();
            dialog.dismiss();
            resetChooser();
          }
        })
        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            // do nothing
            dialog.dismiss();
            resetChooser();
          }
        })
        .show();

  }

  public void resetChooser() {
    colorChooser.setVisibility(View.GONE);
    colorBoxChoosenTheme.setVisibility(View.VISIBLE);
    txtTitleChooseTheme.setVisibility(View.VISIBLE);
  }

  private View.OnClickListener chooseThemeClickListner = new OnClickListener() {
    @Override
    public void onClick(View view) {

      if (!colorChooser.isShown()) {
        colorChooser.setVisibility(View.VISIBLE);
        colorBoxChoosenTheme.setVisibility(View.GONE);
        txtTitleChooseTheme.setVisibility(View.GONE);
        colorChooser.setup();

      }

    }
  };

  private View.OnClickListener changeLauncherClickListner = new OnClickListener() {
    @Override
    public void onClick(View view) {

      new AlertDialog.Builder(getActivity())
          .setTitle("Don't Go")
          .setMessage("Are you sure you want to move from CES?")
          .setPositiveButton("YES !!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
              Functions.resetPreferredLauncherAndOpenChooser(getActivity());

            }
          })
          .setNegativeButton("No, I like CES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
              // do nothing
              dialog.dismiss();
            }
          })
          .show();

    }
  };

}
