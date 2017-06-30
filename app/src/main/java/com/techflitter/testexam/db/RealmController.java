package com.techflitter.testexam.db;


import android.app.Activity;
import android.app.Application;

import com.techflitter.testexam.model.CustomerBean;
import com.techflitter.testexam.model.TableBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public void addCustomers(List<CustomerBean> customerBeanList) {
        for (CustomerBean b : customerBeanList) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(b);
            realm.commitTransaction();
        }

    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from CustomerBean.class
    public void clearAll() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

    }

    //find all objects in the CustomerBean.class
    public List<CustomerBean> getCustomers() {
        return realm.where(CustomerBean.class).findAll();
    }

    //query a single item with the given id
    public CustomerBean getCustomer(String id) {
        return realm.where(CustomerBean.class).equalTo("id", id).findFirst();
    }

    public List<TableBean> getTables() {
        return realm.where(TableBean.class).findAll();
    }

    public void addTable(List<TableBean> taleListList) {
        for (TableBean bean : taleListList) {
            realm.beginTransaction();
            realm.copyToRealm(bean);
            realm.commitTransaction();
        }

    }

    public void updateTableForUser(TableBean bean) {
        realm.beginTransaction();
        bean.setTableAvailable(false);
        realm.copyToRealmOrUpdate(bean);
        realm.commitTransaction();
    }
}
