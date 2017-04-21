package com.soda.asynctaskloaderdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Author: yr
 * 17-4-21 下午5:45.
 */

public class Bakery extends BroadcastReceiver {
    final Baker mBaker;

    public static String CUSTOMER_ACTION = "com.example.asyncloaderdemo.new_customer" ;

    public Bakery(Baker baker) {
        mBaker = baker;
        IntentFilter filter = new IntentFilter(CUSTOMER_ACTION);
        baker.getContext().registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //通知面包师来客人了，要做面包了。
        mBaker.onContentChanged();
    }
}
