package com.techflitter.testexam.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TechFlitter on 6/30/2017.
 */

public class TableBean extends RealmObject {

    @PrimaryKey
    private String id;
    private boolean isTableAvailable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isTableAvailable() {
        return isTableAvailable;
    }

    public void setTableAvailable(boolean tableAvailable) {
        isTableAvailable = tableAvailable;
    }
}
