package com.cs407.tasktumbler;

import android.content.BroadcastReceiver;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Task to do: " + intent.getStringExtra("reminder"), Toast.LENGTH_SHORT).show();
    }
}
