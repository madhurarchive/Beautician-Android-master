package com.provider.beautician.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.provider.beautician.constant.Constant;

public class MessageBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent(Constant.APP_NOTIFICATION_COMES));
    }
}



