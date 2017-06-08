package com.fernandocejas.android10.sample.presentation.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ImageModel extends RealmObject {
    @PrimaryKey
    String id;
    String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
