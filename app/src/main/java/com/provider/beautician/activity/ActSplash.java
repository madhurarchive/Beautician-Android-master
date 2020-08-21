package com.provider.beautician.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.provider.beautician.R;
import com.provider.beautician.helpers.PreferenceConnector;

public class ActSplash extends BaseActivity {

    private Context mContext;
    private static final int SPLASH_TIME_OUT = 2000;
    private String langCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_splash);

        mContext    =   this;
        langCode    =   PreferenceConnector.readString(mContext,PreferenceConnector.LANG_CODE,"");
        PreferenceConnector.writeString(mContext,PreferenceConnector.LANG_CODE,"en");

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PreferenceConnector.readBoolean(mContext,PreferenceConnector.KEY_IS_LOGIN,false)) {
                    Intent intent = new Intent(mContext, ActHome.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(mContext, ActLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
