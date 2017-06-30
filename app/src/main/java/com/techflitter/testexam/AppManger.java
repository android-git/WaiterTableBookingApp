package com.techflitter.testexam;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class AppManger extends Application {

    public static AppManger appManger;

    @Override
    public void onCreate() {
        super.onCreate();
        appManger=this;
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static AppManger getAppManger() {
        return appManger;
    }
}
