/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.provider.beautician.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.provider.beautician.R;
import com.provider.beautician.app.MyAndroidApp;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.listners.AlertDialogListener;
import com.provider.beautician.listners.RadioDialogListener;
import com.provider.beautician.listners.SpannableTextListener;
import com.provider.beautician.model.LoginDataModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

/**
 * This class represents all the common functions used throughout the application.
 */
public class CommonUtils {

    private static CustomProgressDialog customProgressDialog;

    public static String TAG = CommonUtils.class.getSimpleName();


    public static void initializeSSLContext(Context mContext){
        try {
            SSLContext.getInstance("TLSv1.2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            ProviderInstaller.installIfNeeded(mContext.getApplicationContext());
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public static boolean isGpsConnected(Context mContext){
        LocationManager manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE );
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public static String setTimeElapse(String dateString) {
        if (dateString != null) {
            String text = "";
            try {
                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                long date = simpleDateFormat.parse(dateString).getTime();


                if (date > 0) {
                    long difference = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis() - (date);

                    if (difference < 60000) {
                        text = "Few seconds Ago";
                    } else {
                        long diffSeconds = difference / 1000;
                        long diffMinutes = diffSeconds / 60;
                        long diffHours = diffMinutes / 60;
                        long diffDays = difference / (24 * 60 * 60 * 1000);
                        long diffMonths = diffDays / 30;
                        long diffYears = diffMonths / 12;
                        if (diffYears > 0) {
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd,yyyy", Locale.getDefault());
                            dateFormatter.setTimeZone(TimeZone.getDefault());
                            text = dateFormatter.format(new Date(date));

                        } else if (diffMonths > 0) {
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd", Locale.getDefault());
                            dateFormatter.setTimeZone(TimeZone.getDefault());
                            text = dateFormatter.format(new Date(date));

                        } else if (diffDays > 7) {
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd' at 'hh:mm a", Locale.getDefault());
                            dateFormatter.setTimeZone(TimeZone.getDefault());
                            text = dateFormatter.format(new Date(date));

                        } else if (diffDays > 1) {
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("E 'at' hh:mm a", Locale.getDefault());
                            dateFormatter.setTimeZone(TimeZone.getDefault());
                            text = dateFormatter.format(new Date(date));
                        } else if (diffDays == 1) {
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                            dateFormatter.setTimeZone(TimeZone.getDefault());
                            text = "Yesterday at " + dateFormatter.format(new Date(date));
                        } else if (diffHours > 11) {
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                            dateFormatter.setTimeZone(TimeZone.getDefault());
                            text = "Today at " + dateFormatter.format(new Date(date));
                        } else if (diffHours > 0) {
                            long minsLeft = 0;
                            if (false) {
                                minsLeft = diffMinutes - (diffHours * 60);
                            }
                            text = (diffHours + (diffHours == 1 ? " " + "hour" + " " : " " + "hours" + " ")) +
                                    (minsLeft > 0 ? minsLeft + (minsLeft == 1 ? " " + "minute" : " " + "minutes") : "");
                        } else if (diffMinutes > 0) {
                            text = diffMinutes == 1 ? diffMinutes + " " + "minute" : diffMinutes + " " + "minutes";
                        } else {
                            text = "Invalid";
                        }

                    }
                    return text;
                } else {

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return text;
        } else {
            return "";
        }
    }


    public static String getReadableTimeDifference(long difference, boolean showMins) {

        String timeago = "";
        try {
            long diffSeconds = difference / 1000;
            long diffMinutes = diffSeconds / 60;
            long diffHours = diffMinutes / 60;
            long diffDays = difference / (24 * 60 * 60 * 1000);
            long diffMonths = diffDays / 30;
            long diffYears = diffMonths / 12;
            if (diffYears > 0) {
                return diffYears == 1 ? diffYears + " " + "Year ago" : diffYears + " " + "years";
            } else if (diffMonths > 0) {
                return (diffMonths == 1 ? diffMonths + " " + "month" + " " : diffMonths + " " + "months" + " ");
            } else if (diffDays > 0) {
                return (diffDays == 1 ? diffDays + " " + "day" + " " : diffDays + " " + "days" + " ");
            } else if (diffHours > 0) {
                long minsLeft = 0;
                if (showMins) {
                    minsLeft = diffMinutes - (diffHours * 60);
                }
                return (diffHours + (diffHours == 1 ? " " + "hour" + " " : " " + "hours" + " ")) +
                        (minsLeft > 0 ? minsLeft + (minsLeft == 1 ? " " + "minute" : " " + "minutes") : "");
            } else if (diffMinutes > 0) {
                return diffMinutes == 1 ? diffMinutes + " " + "minute" : diffMinutes + " " + "minutes";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeago;
    }


    public static boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,7})$";

        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

    public static void showLoader(Context context) {
        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
            customProgressDialog = null;
        }

        customProgressDialog =   new CustomProgressDialog(context, R.layout.custom_progess_dialog);
        HorizontalDottedProgress progressBar = (HorizontalDottedProgress) customProgressDialog.findViewById(R.id.progressBar);
        customProgressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
    }

    public static void dismissLoader() {
        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
            customProgressDialog = null;
        }
    }

	public static void showSnackbar(final String string, final Context con, final View view) {
        ((Activity) con).runOnUiThread(new Runnable() {
			public void run() {
				Snackbar.make(view, string, Snackbar.LENGTH_LONG).show();
			}
		});
	}

    public static void showSnackbarWithoutView(final String string, final Context con) {
        ((Activity) con).runOnUiThread(new Runnable() {
            public void run() {

                Snackbar.make(((Activity) con).findViewById(android.R.id.content), string, Snackbar.LENGTH_LONG).show();

            }
        });
    }

    public static void showSnackbarWithoutView(final String string, final Context con, final int color) {
        ((Activity) con).runOnUiThread(new Runnable() {
            public void run() {

                Snackbar snackbar = Snackbar.make(((Activity) con).findViewById(android.R.id.content), string, Snackbar.LENGTH_LONG);
                View snakeBarView = snackbar.getView();
                TextView textView = (TextView) snakeBarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setTypeface(MyAndroidApp.getInstance().getRegularFont());

                if (color == 0){
                    snakeBarView.setBackgroundColor(ContextCompat.getColor(con,R.color.colorRed));
                    textView.setTextColor(ContextCompat.getColor(con,R.color.colorWhite));
                } else if (color == 1){
                    snakeBarView.setBackgroundColor(ContextCompat.getColor(con,R.color.colorGreen));
                    textView.setTextColor(ContextCompat.getColor(con,R.color.colorWhite));
                } else if (color == 2){
                    snakeBarView.setBackgroundColor(ContextCompat.getColor(con,R.color.colorDarkYellow));
                    textView.setTextColor(ContextCompat.getColor(con,R.color.colorWhite));
                }
                snackbar.show();
            }
        });
    }

	public static void showToast(final String string, final Context con) {
        ((Activity) con).runOnUiThread(new Runnable() {
			public void run() {
				View v = ((Activity) con).findViewById(android.R.id.content);
				if (v == null) {
					Toast.makeText(con, string, Toast.LENGTH_LONG).show();
				} else {
					showSnackbar(string, con, v);
				}
			}
		});
	}

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int emptyEditTextError(EditText[] edtTexts, String[] errorMsg) {
        int count = 0;
        for (int i = 0; i < edtTexts.length; i++) {
            edtTexts[i].setError(null);
            if (edtTexts[i].getText().toString().trim().length() == 0) {
                edtTexts[i].setError(errorMsg[i]);
                count++;
            }
        }
        return count;
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static String strCurrentCountryCode(Context context) {
        String strCountryCode = "";

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();
        //Toast.makeText(context, "NN"+countryCodeValue, Toast.LENGTH_SHORT).show();
        //strCountryCode = Locale.getDefault().getCountry() ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            strCountryCode = context.getResources().getConfiguration().getLocales().get(0).getCountry();
            Log.i("GetLocale", "=" + strCountryCode);
        } else {
            strCountryCode = Resources.getSystem().getConfiguration().locale.getCountry();
            //strCountryCode = context.getResources().getConfiguration().locale.getCountry();
            Log.i("GetLocale2", "=" + strCountryCode);

        }
        Log.i("GetCountryCodeNew", "=" + strCountryCode);
        if (strCountryCode.equals("")) {
            strCountryCode = "US";
        }

        return strCountryCode;
    }

    public static int getHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return height;
    }

    public static int getWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }

    public static int getTextSize(Activity activity) {
        int screenWidth = getWidth(activity);
        int screenHeight = getHeight(activity);
        int density = activity.getResources().getDisplayMetrics().densityDpi;
        if (screenWidth <= 240) {
            return screenWidth / density * 6;
        } else {
            return screenHeight / density * 35 / 10;
        }
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }


    public static String timeElapsed(String prettyTimeStr) {
        String timeElapsed = prettyTimeStr;
        if (timeElapsed.contains(" year ago")) {
            timeElapsed = timeElapsed.replace(" year ago", "Y");
        } else if (timeElapsed.contains(" years ago")) {
            timeElapsed = timeElapsed.replace(" years ago", "Y");
        } else if (timeElapsed.contains(" months ago")) {
            timeElapsed = timeElapsed.replace(" months ago", "M");
        } else if (timeElapsed.contains(" month ago")) {
            timeElapsed = timeElapsed.replace(" month ago", "M");
        } else if (timeElapsed.contains(" weeks ago")) {
            timeElapsed = timeElapsed.replace(" weeks ago", "W");
        } else if (timeElapsed.contains(" week ago")) {
            timeElapsed = timeElapsed.replace(" week ago", "W");
        } else if (timeElapsed.contains(" days ago")) {
            timeElapsed = timeElapsed.replace(" days ago", "d");
        } else if (timeElapsed.contains(" day ago")) {
            timeElapsed = timeElapsed.replace(" day ago", "d");
        } else if (timeElapsed.contains(" hours ago")) {
            timeElapsed = timeElapsed.replace(" hours ago", "h");
        } else if (timeElapsed.contains(" hour ago")) {
            timeElapsed = timeElapsed.replace(" hour ago", "h");
        } else if (timeElapsed.contains(" hours from now")) {
            timeElapsed = timeElapsed.replace(" hours from now", "h");
        } else if (timeElapsed.contains(" hour from now")) {
            timeElapsed = timeElapsed.replace(" hour from now", "h");
        } else if (timeElapsed.contains(" minutes from now")) {
            timeElapsed = timeElapsed.replace(" minutes from now", "m");
        } else if (timeElapsed.contains(" minute from now")) {
            timeElapsed = timeElapsed.replace(" minute from now", "m");
        } else if (timeElapsed.contains(" minutes ago")) {
            timeElapsed = timeElapsed.replace(" minutes ago", "m");
        }
        return timeElapsed;

    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static Drawable getDrawable(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getDrawable(context, id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    public static int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static int getDrawableImageByName(String name, Context mContext) {
        Resources resources = mContext.getResources();
        final int resourceId = resources.getIdentifier("com.pbtpleads/drawable:" + name, null, null);
        return resourceId;
    }

    public static void print(String string) {
        System.out.println(string);
    }


    /**
     * Returns network availability status.
     *
     * @param context - Application context.
     * @return - Network availability status.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        return info.isConnected();
    }


    public static Typeface setFont(Context context) {
        String fontPath = "fonts/HARLOWSI.ttf";
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        return tf;
    }

    public static void deleteDirectoryTree(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {

                if (child.getName().equals("picasso-cache")) {
                    deleteDirectoryTree(child);
                }
            }
        }
        fileOrDirectory.delete();
    }


    public static Bitmap decodeFile(File f){

        Bitmap b = null;

        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();

            int scale = 1;
            if (o.outHeight > 800 || o.outWidth >800) {
                scale = (int) Math.pow(2, (int) Math.ceil(Math.log(800 /
                        (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getPath(Uri uri, Context context) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage, float maxWidth, float maxHeight) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title" + " - " + Calendar.getInstance().getTime(), null);
        String finepath = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), CommonUtils.compressImage(String.valueOf(Uri.parse(path)), inContext, maxWidth, maxHeight), "Title" + " - " + Calendar.getInstance().getTime(), null);

        return Uri.parse(finepath);
    }

    public static Bitmap compressImage(String imageUri,Context mContext,float maxWidth,float maxHeight) {

        String filePath = getRealPathFromURI(imageUri,mContext);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        //float maxHeight = 1024.0f; //800.0f
        //float maxWidth = 768.0f;//800.0f
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }else if (orientation == 0 && getDeviceName().contains("Xiaomi")) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            }else if (orientation == 0 && getDeviceName().contains("Redmi")) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

            //write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return scaledBitmap;

    }

    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer+ " " + model;
        }
    }


    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "EYS/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");

    }

    private static String getRealPathFromURI(String contentURI,Context mContext) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = mContext.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static void setNoMorePostText(final Context context, TextView textView, final String start, String end, final Fragment fragment, final String fragText) {

        SpannableStringBuilder spanText = new SpannableStringBuilder();
        spanText.append(start);
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                // you can use custom color
            }
        }, spanText.length() - start.length(), spanText.length(), 0);

        spanText.append(" "+end);

        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ///open sign in page
               // ((LoginSignupActivity)context).switchContent(fragment,fragText);
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(ContextCompat.getColor(context,R.color.colorGreen));    // you can use custom color
            }
        }, spanText.length() - end.length(), spanText.length(), 0);

        spanText.setSpan(new android.text.style.StyleSpan(Typeface.BOLD),spanText.length() - end.length(), spanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spanText, TextView.BufferType.SPANNABLE);
    }

    public static void setTermsAndConditionText(final Context context, TextView textView, final String start, final String midOne, final String midTow) {
        String agree = context.getResources().getString(R.string.i_agree_to);
        SpannableStringBuilder spanText = new SpannableStringBuilder();
        spanText.append(start);

        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ///open sign in page
               // ((LoginSignupActivity)context).switchContent(fragment,fragText);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Constant.PRIVACY_POLICY_URL));
                context.startActivity(browserIntent);
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(ContextCompat.getColor(context,R.color.colorSkyBlue));    // you can use custom color
            }
        }, agree.length(), agree.length() + midOne.length() + 1, 0);

        Log.e("Register","start : " + (agree.length())+ " end : " + (agree.length() + midOne.length()));

        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ///open sign in page
               // ((LoginSignupActivity)context).switchContent(fragment,fragText);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Constant.TERMS_N_CONDITION_URL));
                context.startActivity(browserIntent);

            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(ContextCompat.getColor(context,R.color.colorSkyBlue));    // you can use custom color
            }
        }, spanText.length() - midTow.length(), spanText.length(), 0);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spanText, TextView.BufferType.SPANNABLE);
    }


    public static void addCircleToImage(final ImageView imageView, Context context){

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN );
        circularProgressDrawable.start();
        imageView.setBackground(circularProgressDrawable);

    }


    public static void loadImageWithGlide(final ImageView imageView, String url, Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN );
        circularProgressDrawable.start();

        Glide.with(context)
                .load(url)
                .placeholder(circularProgressDrawable)
                .error(R.mipmap.img_default)
                .into(imageView);
    }

    public static void loadRoundCornerImageWithGlide(final ImageView imageView, String url, final Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN );
        circularProgressDrawable.start();


        Glide.with(context).asBitmap().load(url).placeholder(circularProgressDrawable).centerCrop().error(R.mipmap.img_default).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(16);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static  void broadCastAladeen(Context context){

        Intent broadcast = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            broadcast.setAction("CALL_ALADEEN_RECEIVER");
            CommonUtils.sendImplicitBroadcast(context,broadcast);
        } else {
            broadcast.setAction("UPDATE_WATCH_ALADEEN");
            context.sendBroadcast(broadcast);
        }

    }

    public static void sendImplicitBroadcast(Context ctxt, Intent i) {
        PackageManager pm=ctxt.getPackageManager();
        List<ResolveInfo> matches=pm.queryBroadcastReceivers(i, 0);

        for (ResolveInfo resolveInfo : matches) {
            Intent explicit=new Intent(i);
            ComponentName cn=
                    new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName,
                            resolveInfo.activityInfo.name);
            explicit.setComponent(cn);
            ctxt.sendBroadcast(explicit);
        }
    }


    public static int compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime) {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();

        long diff           =   milliseconds2 - milliseconds1;
        int diffSeconds    =   (int) diff / 1000;

        // calculate hours minutes and seconds
        int hours = diffSeconds / 3600;
        int minutes = (diffSeconds % 3600) / 60;

        //seconds = (seconds % 3600) % 60;
        /*long diffMinutes    =   diff / (60 * 1000);
        long diffHours      =   diff / (60 * 60 * 1000);
        long diffDays       =   diff / (24 * 60 * 60 * 1000);*/

        return minutes;
    }

    public static String getJsonStringMember(JsonObject jsonObject, String member){
        return  jsonObject.get(member).getAsString();
    }

    public static long getTimeDifferenceFromMillis(long currentTime, long oldTime) {
        long diff           =   currentTime - oldTime;
        long diffMinutes    =   diff / (60 * 1000);
        long diffHours      =   diff / (60 * 60 * 1000);
        long diffDays       =   diff / (24 * 60 * 60 * 1000);

        return diffHours;
    }

    public static String gettingString(int resId,Context mContext){
        return mContext.getResources().getString(resId);
    }

    public static void setSpannableText(final Context context, TextView textView, String start, String mid, String end, final SpannableTextListener mListener){
        SpannableStringBuilder spanText = new SpannableStringBuilder();
        spanText.append(start);
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                // you can use custom color
                textPaint.setColor(ContextCompat.getColor(context,R.color.colorBlack));
                //textPaint.setTypeface(MyAndroidApp.getInstance().getPoppinsRegularFont());
            }
        }, spanText.length() - start.length(), spanText.length(), 0);

        spanText.append(mid);
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // you can use custom click
                mListener.clickHere();
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                // you can use custom color
                textPaint.setColor(ContextCompat.getColor(context,R.color.colorSkyBlue));
            }
        }, spanText.length() - mid.length(), spanText.length(), 0);

        spanText.append(" ");
        spanText.append(end);
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // you can use custom click

            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                // you can use custom color
                textPaint.setColor(ContextCompat.getColor(context,R.color.colorBlack));
            }
        }, spanText.length() - end.length(), spanText.length(), 0);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spanText, TextView.BufferType.SPANNABLE);
    }

    public static void displayToastAboveButton(Context mContext,View v, int messageId)
    {
        int xOffset = 0;
        int yOffset = 0;
        Rect gvr = new Rect();
        View parent = (View) v.getParent();
        int parentHeight = parent.getHeight();
        if (v.getGlobalVisibleRect(gvr))
        {
            View root = v.getRootView();
            int halfWidth = root.getRight() / 2;
            int halfHeight = root.getBottom() / 2;
            int parentCenterX = ((gvr.right - gvr.left) / 2) + gvr.left;
            int parentCenterY = ((gvr.bottom - gvr.top) / 2) + gvr.top;
            if (parentCenterY <= halfHeight)
            {
                yOffset = -(halfHeight - parentCenterY) - parentHeight;
            }
            else
            {
                yOffset = (parentCenterY - halfHeight) - parentHeight;
            }

            if (parentCenterX < halfWidth)
            {
                xOffset = -(halfWidth - parentCenterX);
            }

            if (parentCenterX >= halfWidth)
            {
                xOffset = parentCenterX - halfWidth;
            }
        }
        //Creating the LayoutInflater instance
        LayoutInflater li = ((Activity)mContext).getLayoutInflater();
        //Getting the View object as defined in the customtoast.xml file
        View layout = li.inflate(R.layout.customtoast,(ViewGroup)((Activity)mContext).findViewById(R.id.custom_toast_layout));
        TextView textView   =   layout.findViewById(R.id.custom_toast_message);
        textView.setText(messageId);
        Toast toast = Toast.makeText(mContext, messageId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, xOffset, yOffset);
        toast.setView(layout);
        toast.show();
    }

    public static void openRadioDialog(Context mContext,int position, final String[] items, final RadioDialogListener listener){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext,R.style.CustomDialogTheme);
        alertDialog.setSingleChoiceItems(items, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClick(which,items[which]);
                dialog.dismiss();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public static String getMonthName(int month){
        switch (month){
            case 1: return "Jan";
            case 2: return "Fab";
            case 3: return "Mar";
            case 4: return "Apr";
            case 5: return "May";
            case 6: return "Jun";
            case 7: return "Jul";
            case 8: return "Aug";
            case 9: return "Sep";
            case 10: return "Oct";
            case 11: return "Nov";
            case 12: return "Dec";
            default: return "";
        }
    }

    public static String getConvertDateTime(String dateTime,String inputFormat,String outputFormat){
        String formatDateTime ="";
        DateFormat df = new SimpleDateFormat(inputFormat,Locale.ENGLISH);
        //Date/time pattern of desired output date
        DateFormat outputformat = new SimpleDateFormat(outputFormat,Locale.ENGLISH);
        Date date = null;
        try{
            //Conversion of input String to date
            date= df.parse(dateTime);
            //old date format to new date format
            formatDateTime = outputformat.format(date);
            System.out.println(formatDateTime);
        }catch(ParseException pe){
            pe.printStackTrace();
        }
        return formatDateTime;
    }

    public static void loadCircleImageWithGlide(final ImageView imageView, String url,Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN );
        circularProgressDrawable.start();

        RequestOptions sharedOptions = new RequestOptions()
                .placeholder(circularProgressDrawable)
                .error(R.mipmap.ic_launcher_round)
                .transform(new CenterCrop(),new CircleCrop());

        Glide.with(context)
                .load(url)
                .apply(sharedOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public static void setUserDetails(Context mContext, LoginDataModel model){
        PreferenceConnector.writeString(mContext,PreferenceConnector.KEY_SALOON_ID,model.getId());
        PreferenceConnector.writeString(mContext,PreferenceConnector.KEY_USER_FIRST_NAME,model.getName());
        PreferenceConnector.writeString(mContext,PreferenceConnector.KEY_USER_LAST_NAME,model.getLastName());
        PreferenceConnector.writeString(mContext,PreferenceConnector.KEY_USER_EMAIL,model.getEmail());
        PreferenceConnector.writeString(mContext,PreferenceConnector.KEY_USER_PROFILE_IMAGE,model.getProfileImage());
        PreferenceConnector.writeString(mContext,PreferenceConnector.KEY_USER_PHONE,model.getPhone());
    }

    public static String getUserId(Context mContext){
        return PreferenceConnector.readString(mContext,PreferenceConnector.KEY_USER_ID,"1");
    }

    public static String getSaloonId(Context mContext) {
        return PreferenceConnector.readString(mContext, PreferenceConnector.KEY_SALOON_ID, "1");
    }

    public static String getStaffId(Context mContext) {
        return PreferenceConnector.readString(mContext, PreferenceConnector.KEY_STAFF_ID, "1");
    }

    public static String getUserFirstName(Context mContext){
        return PreferenceConnector.readString(mContext,PreferenceConnector.KEY_USER_FIRST_NAME,"");
    }

    public static String getUserLastName(Context mContext){
        return PreferenceConnector.readString(mContext,PreferenceConnector.KEY_USER_LAST_NAME,"");
    }

    public static String getUserEmail(Context mContext){
        return PreferenceConnector.readString(mContext,PreferenceConnector.KEY_USER_EMAIL,"");
    }

    public static String getUserProfile(Context mContext){
        return PreferenceConnector.readString(mContext,PreferenceConnector.KEY_USER_PROFILE_IMAGE,"");
    }

    public static String getUserMobile(Context mContext){
        return PreferenceConnector.readString(mContext,PreferenceConnector.KEY_USER_PHONE,"");
    }


    public static void showAlertDialog(String msg,int status,Context mContext){
        String title = CommonUtils.gettingString(R.string.caution,mContext);
        if (status==1){
            title = CommonUtils.gettingString(R.string.validation_failed,mContext);
        }else if (status==2){
            title = CommonUtils.gettingString(R.string.success,mContext);
        }
        AppDialogHelper.showAlertDialog(mContext, title, msg, new AlertDialogListener() {
            @Override
            public void onCloseBtnClick() { }});
    }

    public static String getFormattedDate(String strDate,String inputPattern, String outputPattern){
        String strDateTime = "";
        Calendar mCalendar = Calendar.getInstance();
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        try {
            Date date = inputFormat.parse(strDate);
            mCalendar.setTime(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern,Locale.ENGLISH);
            strDateTime = outputFormat.format(mCalendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDateTime;
    }

    public static String formatDate(Date date,String dateFormat){
        DateFormat format = new SimpleDateFormat(dateFormat,Locale.ENGLISH);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);
        return format.format(mCalendar.getTime());
    }

    public static String getColorCodeInString(Context context,int color){
        return "#" + Integer.toHexString(ContextCompat.getColor(context, color) & 0x00ffffff);
    }

    public static void clearPreferenceOnLogout(Context mContext) {
        String deviceToken = PreferenceConnector.readString(mContext, PreferenceConnector.DEVICE_TOKEN, "");
        boolean isFirst = PreferenceConnector.readBoolean(mContext, PreferenceConnector.KEY_IS_FIRST_TIME, false);
        Log.e(TAG, "deviceToken          === " + deviceToken);
        PreferenceConnector.cleanPrefrences(mContext);
        PreferenceConnector.writeString(mContext, PreferenceConnector.DEVICE_TOKEN, deviceToken);
        PreferenceConnector.writeBoolean(mContext, PreferenceConnector.KEY_IS_FIRST_TIME, isFirst);
     }

    public static void printKeyHash(Context context){
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
}
