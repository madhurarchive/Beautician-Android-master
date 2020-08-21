/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.provider.beautician.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.provider.beautician.R;
import com.provider.beautician.app.MyAndroidApp;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.fragment.FragAddNewClient;
import com.provider.beautician.fragment.FragClientProfile;
import com.provider.beautician.fragment.FragClients;
import com.provider.beautician.fragment.FragHome;
import com.provider.beautician.fragment.FragMenu;
import com.provider.beautician.fragment.FragNotification;
import com.provider.beautician.fragment.FragSales;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.ConnectivityReceiver;
import com.provider.beautician.helpers.PreferenceConnector;
import com.provider.beautician.listners.NoInternetDialogListener;
import com.provider.beautician.navdrawer.FragmentDrawer;
import java.util.List;
import static com.provider.beautician.R.id.drawer_layout;


/**
 * TODO
 */
public class ActHome extends BaseActivity implements ConnectivityReceiver.ConnectivityReceiverListener,View.OnClickListener{

    private DrawerLayout                mDrawerLayout;
    private ImageView                   imgIconBack;
    private FragmentDrawer              drawerFragment;
    private Toolbar                     toolbar;
    private ActionBarDrawerToggle       toggle;
    private LinearLayout                layoutBottom;
    private Context                     mContext;
    private ConnectivityReceiver        mConnectivityReceiver;
    private String                      loginUserId;
    private ImageView                   imgCalender,imgBookmark,imgUserDetail,imgNotification,imgHamburger;
    private boolean                     doubleBackToExitPressedOnce = false;

    public String TAG = ActHome.class.getSimpleName();

    BroadcastReceiver mMessageBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("Receiver", "Broadcast received: " + intent.getAction());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.act_home);

        mContext    = this;
        loginUserId = PreferenceConnector.readString(ActHome.this, PreferenceConnector.KEY_USER_ID, "");

        initToolbar();
        setUpNavigationDrawer();
        setFonts();
        displayView(0);

        Log.e(ActHome.class.getSimpleName(), "TOKEN === " + PreferenceConnector.readString(mContext, PreferenceConnector.DEVICE_TOKEN, ""));

    }

    private void initToolbar() {
        //toolbar         = (Toolbar) findViewById(R.id.toolbar);
        /*layoutBack      = (LinearLayout) toolbar.findViewById(R.id.layoutBack);
        imgIconBack     = (ImageView) toolbar.findViewById(R.id.img_icon_back);
        lblHeaderTitle  = (TextView) toolbar.findViewById(R.id.lblHeaderTitle);*/

        layoutBottom    =   findViewById(R.id.layout_bottom_root);
        imgCalender     =   findViewById(R.id.layout_bottom_imgCalender);
        imgBookmark     =   findViewById(R.id.layout_bottom_imgBookmark);
        imgUserDetail   =   findViewById(R.id.layout_bottom_imgUserIcon);
        imgNotification =   findViewById(R.id.layout_bottom_imgNotification);
        imgHamburger    =   findViewById(R.id.layout_bottom_imgHamburger);

        imgCalender.setOnClickListener(this);
        imgBookmark.setOnClickListener(this);
        imgUserDetail.setOnClickListener(this);
        imgNotification.setOnClickListener(this);
        imgHamburger.setOnClickListener(this);
    }

    private void setUpNavigationDrawer() {
        mDrawerLayout   = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment  = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(drawer_layout), toolbar);
        drawerFragment.setDrawerListener(new FragmentDrawer.FragmentDrawerListener() {
            @Override
            public void onDrawerItemSelected(View view, int position) {
                displayView(position);
            }
        });
        /*layoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });*/
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
    }

    public void displayView(int position) {
        switch (position) {
            case 0:
                ///MUST REPLACE HERE:::::::::::::
                switchContent(new FragHome(), FragHome.class.getSimpleName(), false);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                //orders
                break;
            case 6:
                break;
        }
    }

    public void setUpToolBar(Fragment fragment, String title,boolean isHide) {
        Log.e("ActHome", fragment + "======");
        layoutBottom.setVisibility(View.VISIBLE);
        if (isHide){
            layoutBottom.setVisibility(View.GONE);
        }
    }

    private void setFonts() {
//        lblHeaderTitle.setTypeface(MyAndroidApp.getInstance().getRegularFont());
    }

    public void switchContent(Fragment fragment, String tag, boolean isBackToStack) {
        CommonUtils.hideKeyboard(mContext);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction switch_Ft = fragmentManager.beginTransaction();
        if (isBackToStack) {
            switch_Ft.replace(R.id.container_body, fragment,tag).addToBackStack(tag).commit();
        } else {
            switch_Ft.replace(R.id.container_body, fragment, tag).commit();
        }

    }

    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else if (getVisibleFragment() instanceof FragAddNewClient || getVisibleFragment() instanceof FragClientProfile){
            clearBackStack();
            switchContent(new FragClients(), FragClients.class.getSimpleName(), false);
        }else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            CommonUtils.hideKeyboard(mContext);
        }else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getResources().getString(R.string.app_exit_msg), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }else {
            super.onBackPressed();
        }
    }

    private void updateTokenTimer() {
        Log.e("", "==== updateTokenTimer ======");
        boolean isSendRequest = false;
        long updateTokenTime = PreferenceConnector.readLong(mContext, PreferenceConnector.KEY_TIME, 0);
        if (updateTokenTime == 0) {
            System.out.println("=====Dealparse==========" + updateTokenTime + "======difference====");
            PreferenceConnector.writeLong(mContext, PreferenceConnector.KEY_TIME, System.currentTimeMillis());
            isSendRequest = true;
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            long difference = currentTimeMillis - updateTokenTime;
            long seconds = difference / 1000;
            System.out.println(currentTimeMillis + "=====Dealparse==========" + updateTokenTime + "======difference====" + difference + "========" + seconds);

            if (seconds > Constant.FCM_UPDATE_TIMER_INTERVAL) {
                PreferenceConnector.writeLong(mContext, PreferenceConnector.KEY_TIME, currentTimeMillis);
                isSendRequest = true;
            }
        }

        //isSendRequest = true;
        if (isSendRequest) {
            System.out.println("===== isSendRequest ======");
            try {
                String strDeviceToken, strUserID;
                strDeviceToken = PreferenceConnector.readString(mContext, PreferenceConnector.DEVICE_TOKEN, "");
                strUserID = PreferenceConnector.readString(mContext, PreferenceConnector.KEY_USER_ID, "");

                Log.e("strDeviceToken", "====" + strDeviceToken);
                Log.e("DEVICE_TYPE_ANDROID", "====" + Constant.DEVICE_TYPE_ANDROID);

                if (CommonUtils.isConnectingToInternet(mContext)) {
                    System.out.println("======= body api call =========");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateTokenTimer();
        registerReceiver(mMessageBroadcastReceiver, new IntentFilter(Constant.APP_NOTIFICATION_COMES));
        registerConnectivityReciever();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageBroadcastReceiver);
        unregisterReceiver(mConnectivityReceiver);
    }

    private void registerConnectivityReciever() {
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        mConnectivityReceiver = new ConnectivityReceiver();
        this.registerReceiver(mConnectivityReceiver, intentFilter);
        MyAndroidApp.getInstance().setConnectivityListener(this);
    }


    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {

                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        Log.e("network_status ", "============" + isConnected);
        if (!isConnected) {
            AppDialogHelper.showNoInternetDialog(mContext, new NoInternetDialogListener() {
                @Override
                public void onCloseBtnClick() {
                    System.exit(0);
                }

                @Override
                public void onRetryBtnClick() {
                    boolean isConnected = ConnectivityReceiver.isConnected();
                    Log.e("ActHome", "connected ===================" + isConnected);

                    if (isConnected) {
                        AppDialogHelper.dismissNoInternetDialog();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_bottom_imgCalender:
                imgCalender.setImageResource(R.mipmap.img_file_icon);
                imgBookmark.setImageResource(R.mipmap.img_page_icon);
                imgUserDetail.setImageResource(R.mipmap.img_contect_icon);
                imgNotification.setImageResource(R.mipmap.img_bell_icon);
                imgHamburger.setImageResource(R.mipmap.img_mores_icon);
                CommonUtils.displayToastAboveButton(mContext,imgCalender,R.string.home);
                switchContent(new FragHome(), FragHome.class.getSimpleName(), false);
                break;
            case R.id.layout_bottom_imgBookmark:
                imgCalender.setImageResource(R.mipmap.img_file_icon_v2);
                imgBookmark.setImageResource(R.mipmap.img_pages_icon_v2);
                imgUserDetail.setImageResource(R.mipmap.img_contect_icon);
                imgNotification.setImageResource(R.mipmap.img_bell_icon);
                imgHamburger.setImageResource(R.mipmap.img_mores_icon);
                CommonUtils.displayToastAboveButton(mContext,imgBookmark,R.string.sales);
                switchContent(new FragSales(), FragSales.class.getSimpleName(), false);
                break;
            case R.id.layout_bottom_imgUserIcon:
                imgCalender.setImageResource(R.mipmap.img_file_icon_v2);
                imgBookmark.setImageResource(R.mipmap.img_page_icon);
                imgUserDetail.setImageResource(R.mipmap.img_contect_sel);
                imgNotification.setImageResource(R.mipmap.img_bell_icon);
                imgHamburger.setImageResource(R.mipmap.img_mores_icon);
                CommonUtils.displayToastAboveButton(mContext,imgUserDetail,R.string.clients);
                switchContent(new FragHome(), FragHome.class.getSimpleName(), false);
                //switchContent(new FragClients(), FragClients.class.getSimpleName(), false);
                break;
            case R.id.layout_bottom_imgNotification:
                imgCalender.setImageResource(R.mipmap.img_file_icon_v2);
                imgBookmark.setImageResource(R.mipmap.img_page_icon);
                imgUserDetail.setImageResource(R.mipmap.img_contect_icon);
                imgNotification.setImageResource(R.mipmap.img_bell_sel_icon);
                imgHamburger.setImageResource(R.mipmap.img_mores_icon);
                CommonUtils.displayToastAboveButton(mContext,imgNotification,R.string.notifications);
                switchContent(new FragNotification(), FragNotification.class.getSimpleName(), false);
                break;
            case R.id.layout_bottom_imgHamburger:
                imgCalender.setImageResource(R.mipmap.img_file_icon_v2);
                imgBookmark.setImageResource(R.mipmap.img_page_icon);
                imgUserDetail.setImageResource(R.mipmap.img_contect_icon);
                imgNotification.setImageResource(R.mipmap.img_bell_icon);
                imgHamburger.setImageResource(R.mipmap.img_more_icon_v2);
                CommonUtils.displayToastAboveButton(mContext,imgHamburger,R.string.menu);
                switchContent(new FragMenu(), FragMenu.class.getSimpleName(), false);
                break;
        }
    }
}
