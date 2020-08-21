package com.provider.beautician.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActSplash;
import com.provider.beautician.app.MyAndroidApp;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.PreferenceConnector;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;
    public static final String PUSH_NOTIFICATION = "pushNotification";

    @Override
    public void onNewToken(String refreshedToken) {
        super.onNewToken(refreshedToken);
        Log.e(TAG,refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        Log.e("here Firebase TOKEN", token);
        // TODO: Implement this method to send token to your app server.
        if(!PreferenceConnector.readString(this,PreferenceConnector.DEVICE_TOKEN,"").equalsIgnoreCase(token)) {
            PreferenceConnector.writeString(this,PreferenceConnector.DEVICE_TOKEN,token);
            Log.e("here Firebase TOKEN", PreferenceConnector.readString(this,PreferenceConnector.DEVICE_TOKEN,""));
        }
    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.e("Firebase","onMessageReceived "+remoteMessage.getNotification().getBody());
        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            System.out.println("Notification Payload "+remoteMessage.getData());
            Log.e("Firebase","Notification Payload "+remoteMessage.getData());
            handleDataMessage(remoteMessage);
        }

    }

    private void handleNotification(String message) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mSimpleDateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String timestamp = mSimpleDateFormater.format(calendar.getTime());

        // app is in foreground, broadcast the push message
        if (!NotificationUtils.isAppInBackgroundRunning(getApplicationContext())) {
            Intent pushNotification = new Intent(PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            Intent intent = new Intent(getApplicationContext(), ActSplash.class);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.showNotificationMessage(getString(R.string.app_name), message, timestamp, intent);

            Intent broadcast = new Intent();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                broadcast.setAction(Constant.APP_NOTIFICATION_RECEIVE);
                CommonUtils.sendImplicitBroadcast(this,broadcast);
            } else {
                broadcast.setAction(Constant.APP_NOTIFICATION_COMES);
                sendBroadcast(broadcast);
            }

        } else {
            // If the app is in background, firebase itself handles the notification
            Intent intent = new Intent(getApplicationContext(), ActSplash.class);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.showNotificationMessage(getString(R.string.app_name), message, timestamp, intent);
        }
    }

    private void handleDataMessage(RemoteMessage json) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mSimpleDateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String timestamp = mSimpleDateFormater.format(calendar.getTime());

            String title    =   json.getData().get("title");
            String message  =   json.getData().get("message");
            String imageUrl =   json.getData().get("image");
            String str_url  =   json.getData().get("url");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "timestamp: " + timestamp);
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "url: " + str_url);

            if (title == null){
                title = MyAndroidApp.getInstance().getResources().getString(R.string.app_name);
                Log.e(TAG, "title: " + title);
            }

            if (!NotificationUtils.isAppInBackgroundRunning(getApplicationContext())) {
                Intent broadcast = new Intent();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    broadcast.setAction(Constant.APP_NOTIFICATION_RECEIVE);
                    CommonUtils.sendImplicitBroadcast(this,broadcast);
                } else{
                    broadcast.setAction(Constant.APP_NOTIFICATION_COMES);
                    sendBroadcast(broadcast);
                }

            }

            // app is in background, show the notification in notification tray
            Intent resultIntent = new Intent(getApplicationContext(), ActSplash.class);
            resultIntent.putExtra("message", message);

            // check for image attachment
            if (TextUtils.isEmpty(imageUrl)) {
                showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
            } else {
                // image is present, show notification with image
                showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        }
    }


    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
        Log.e("timeStamp:", timeStamp);
    }

    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
        Log.e("timeStamp:", timeStamp);
    }
}
