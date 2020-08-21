package com.provider.beautician.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.ConnectivityReceiver;
import com.provider.beautician.helpers.LocaleHelper;

/**
 * Created by LENOVO on 11/22/2018.
 */

public class MyAndroidApp extends Application {
    public static MyAndroidApp singleton;
    private Typeface lightFont;
    private Typeface regularFont;
    private Typeface mediumFont;
    private Typeface boldFont;
    private Typeface lightItalicFont;
    public static synchronized MyAndroidApp getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    public Typeface getLightFont() {
        if (lightFont == null) {
            lightFont = Typeface.createFromAsset(getAssets(), "fonts/light.ttf");
        }
        return this.lightFont;
    }

    public Typeface getRegularFont() {
        if (regularFont == null) {
            regularFont = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        }
        return this.regularFont;
    }

    public Typeface getMediumFont() {
        if (mediumFont == null) {
            mediumFont = Typeface.createFromAsset(getAssets(), "fonts/medium.ttf");
        }
        return this.mediumFont;
    }

    public Typeface getLightItalicFont() {
        if (lightItalicFont == null) {
            lightItalicFont = Typeface.createFromAsset(getAssets(), "fonts/light_italic.ttf");
        }
        return this.mediumFont;
    }

    public Typeface getBoldFont() {
        if (boldFont == null) {
            boldFont = Typeface.createFromAsset(getAssets(), "fonts/bold.ttf");
        }
        return this.boldFont;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, Constant.LANGUAGE_CODE_EN));
    }
}
