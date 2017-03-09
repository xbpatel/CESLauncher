package com.xbpsolutions.ceslauncher.helper;

import android.content.Context;

/**
 * Created by ishan on 24-02-2017.
 */
public class PrefUtils {

  public static String LOGGED_IN = "login";
  public static String USER_SELECTED_COLOR = "selected_color";


  public static void setLoggedIn(Context ctx, boolean value) {
    Prefs.with(ctx).save(LOGGED_IN, value);
  }

  public static boolean isUserLoggedIn(Context ctx) {
    return Prefs.with(ctx).getBoolean(LOGGED_IN, false);
  }

  public static void setSelectedColor(Context ctx, String value) {
    Prefs.with(ctx).save(USER_SELECTED_COLOR, value);
  }

  public static String getSelectedColor(Context ctx) {
    return Prefs.with(ctx).getString(USER_SELECTED_COLOR, "");
  }


}
