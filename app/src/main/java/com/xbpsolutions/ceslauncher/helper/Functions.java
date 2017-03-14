package com.xbpsolutions.ceslauncher.helper;

/**
 * @author jatin
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;


import com.xbpsolutions.ceslauncher.BuildConfig;
import com.xbpsolutions.ceslauncher.R;
import com.xbpsolutions.ceslauncher.ui.MockHome;
import com.xbpsolutions.ceslauncher.ui.widgets.TfTextView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {


    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    private static final String ProfilePicture = "metropolitan/profilePicture";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static void fireIntent(Context context, Class cls) {
        Intent i = new Intent(context, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void hideKeyPad(Context context, View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String toStr(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static int toLength(EditText editText) {
        return editText.getText().toString().trim().length();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void openInMap(Context context, double latitude, double longitude, String labelName) {
        String newUri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + labelName + ")";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newUri));
        context.startActivity(intent);
    }

    public static void makePhoneCall(Context context, String callNo) {
        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + callNo));
        context.startActivity(dialIntent);
    }

    public static String parseDate2(String inputDate, SimpleDateFormat outputFormat, SimpleDateFormat inputFormat) {

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(inputDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String getTimeFromDate(String date) {
        String time = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
        try {
            Date date1 = sdf1.parse(date);
            time = sdf2.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }


    public static String changeDateFormat(String date) {
        String output = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date date1 = sdf1.parse(date);
            output = sdf2.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            Functions.LogE("error", e.getMessage());
        }
        return output;
    }

    public interface OnToolbarClickListener {
        void onBack();
    }

    public static void LogE(String key, String value) {
        if (BuildConfig.DEBUG) {
            Log.e(key, value);
        }
    }

    public static void LogD(String key, String value) {
        if (BuildConfig.DEBUG) {
            Log.d(key, value);
        }
    }

    public static Typeface getFontType(Context _context, int typeValue) {

        Typeface typeface;

        if (typeValue == 1) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/light.ttf");

        } else if (typeValue == 2) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");

        } else if (typeValue == 3) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/semibold.ttf");

        } else if (typeValue == 4) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/bold.ttf");

        } else if (typeValue == 5) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/black.ttf");

        } else {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");
        }

        return typeface;
    }

    public static String NotificationDate(String notificationDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        SimpleDateFormat onlyDay = new SimpleDateFormat("dd", Locale.US);
        Date date = null;
        Date currentDate = new Date();
        try {
            date = sdf.parse(notificationDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int compare = getZeroTimeDate(date).compareTo(getZeroTimeDate(currentDate));

        if (compare == 0) {
            return "Today";
        } else {
            int isYesterday = getZeroTimeDate(date).compareTo(getZeroTimeDate(getYesterdayDate()));
            if (isYesterday == 0) {
                return "Yesterday";
            } else {
                return notificationDate;
            }
        }
    }

    private static Date getZeroTimeDate(Date input) {
        Date output = input;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(input);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        output = calendar.getTime();

        return output;
    }

    private static Date getYesterdayDate() {
        Date output = null;
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        output = calendar.getTime();

        return output;
    }

    public static void resetPreferredLauncherAndOpenChooser(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, MockHome.class);
        packageManager
                .setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP);

        Intent selector = new Intent(Intent.ACTION_MAIN);
        selector.addCategory(Intent.CATEGORY_HOME);
        selector.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(selector);

        packageManager
                .setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
                        PackageManager.DONT_KILL_APP);
    }

//
//    public static void loadImage(final ImageView imageView, String url, final Context context, boolean isRounded) {
//        try {
//            if (!isRounded) {
//                Glide.with(context).load(R.drawable.profile).asBitmap().centerCrop().into(imageView);
//            } else {
//                Glide.with(context).load(R.drawable.profile).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
//                        imageView.setImageDrawable(circularBitmapDrawable);
//                    }
//                });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void loadRoundedImage(String s, final Context context, final ImageView imageView, Uri imageUri) {
//        try {
//
//            Glide.clear(imageView);
//            Glide.with(context)
//                    .load(s)
//                    .asBitmap()
//                    .centerCrop()
//                    .error(R.drawable.ic_account_circle)
//                    .into(new BitmapImageViewTarget(imageView) {
//                @Override
//                protected void setResource(Bitmap resource) {
//                    RoundedBitmapDrawable circularBitmapDrawable =
//                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                    circularBitmapDrawable.setCircular(true);
//                    imageView.setImageDrawable(circularBitmapDrawable);
//                }
//            });
//
//        } catch (Exception e) {
//
//        }
//    }


//    public static void closeSession(Context context) {
//        PrefUtils.setLoggedIn(context, false);
//        Intent intent = new Intent(context, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(intent);
//        ((Activity) context).finish();
//    }
//
//    /*
//    1 password
//    2 new password
//    3 confirm password
//  */
//    public static boolean checkPassrordLength(Context context, int whichPassrord, String password) {
//        switch (whichPassrord) {
//            case 1:
//                if (password.trim().length() < context.getResources().getInteger(R.integer.pwd_min) || password.trim().length() > context.getResources().getInteger(R.integer.pwd_max)) {
//                    Functions.showToast(context, "Password should be minimum " + context.getResources().getInteger(R.integer.pwd_min) + " and maximum " + context.getResources().getInteger(R.integer.pwd_max) + " character long.");
//                    return false;
//                }
//                break;
//
//            case 2:
//                if (password.trim().length() < context.getResources().getInteger(R.integer.pwd_min) || password.trim().length() > context.getResources().getInteger(R.integer.pwd_max)) {
//                    Functions.showToast(context, "New Password should be minimum " + context.getResources().getInteger(R.integer.pwd_min) + " and maximum " + context.getResources().getInteger(R.integer.pwd_max) + " character long.");
//                    return false;
//                }
//                break;
//
//            case 3:
//                if (password.trim().length() < context.getResources().getInteger(R.integer.pwd_min) || password.trim().length() > context.getResources().getInteger(R.integer.pwd_max)) {
//                    Functions.showToast(context, "Confirm Password should be minimum " + context.getResources().getInteger(R.integer.pwd_min) + " and maximum " + context.getResources().getInteger(R.integer.pwd_max) + " character long.");
//                    return false;
//                }
//                break;
//        }
//        return true;
//    }

//    public static String getDeviceId(Context context) {
//        return Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//    }
//
//    public static void showUserDetails(final Context context) {
//        final AlertDialog dialog = new AlertDialog.Builder(context).create();
//        dialog.setTitle("User Details");
//        dialog.setMessage(PrefUtils.getUserFullProfileDetails(context).toString());
//        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialog.dismiss();
//            }
//        });
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//    }

//    public static String profilePicturePath() {
//
//        return Environment.getExternalStorageDirectory() + File.separator + ProfilePicture + File.separator;
//    }
//
//
//    public static String jsonString(Object obj) {
//        return "" + MyApplication.getGson().toJson(obj);
//    }
}
