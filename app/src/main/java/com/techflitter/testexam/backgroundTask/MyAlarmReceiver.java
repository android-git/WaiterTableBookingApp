package com.techflitter.testexam.backgroundTask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.techflitter.testexam.utils.Constant;

public class MyAlarmReceiver extends BroadcastReceiver {

    public static int REQUEST_CODE = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MyService.class);
        context.startService(i);
    }
}
