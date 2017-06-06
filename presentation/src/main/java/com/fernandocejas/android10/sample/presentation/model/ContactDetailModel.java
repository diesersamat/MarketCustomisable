package com.fernandocejas.android10.sample.presentation.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ContactDetailModel extends RealmObject {

    @PrimaryKey
    int id;
    int CustomerId;
    String address;
    String phone;
    boolean isDefault;

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
