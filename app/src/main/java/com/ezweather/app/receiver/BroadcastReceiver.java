package com.ezweather.app.receiver;

import android.content.Context;
import android.content.Intent;

import com.ezweather.app.service.AutoUpdateService;

/**
 * Created by Oniros on 2016/4/11.
 */
public class BroadcastReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startActivity(i);
    }
}
