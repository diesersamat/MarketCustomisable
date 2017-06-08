package com.fernandocejas.android10.sample.presentation.model;

import io.realm.RealmObject;

public class FieldModel extends RealmObject {
    String name;
    String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
