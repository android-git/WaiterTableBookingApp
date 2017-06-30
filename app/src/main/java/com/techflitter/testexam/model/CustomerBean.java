package com.techflitter.testexam.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CustomerBean extends RealmObject {

    @PrimaryKey
    private String id;
    private String customerFirstName;
    private String customerLastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
}
