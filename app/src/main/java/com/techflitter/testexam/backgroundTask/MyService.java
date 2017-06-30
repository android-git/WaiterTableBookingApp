package com.techflitter.testexam.backgroundTask;

import android.app.IntentService;
import android.app.usage.NetworkStatsManager;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.techflitter.testexam.AppManger;
import com.techflitter.testexam.db.RealmController;
import com.techflitter.testexam.utils.CommonUtils;
import com.techflitter.testexam.utils.Constant;

public class MyService extends IntentService {

    public MyService() {
        super("MyService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (CommonUtils.isNetwork(getApplicationContext())) {
            Log.d("mytag","Avilable");
            RealmController.with(new AppManger()).clearAll();

            Intent ii = new Intent();
            ii.setAction(Constant.UPDATE_ACTION);
            sendBroadcast(ii);
        }
    }

}
