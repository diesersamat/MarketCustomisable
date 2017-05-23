package com.fernandocejas.android10.sample.presentation.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CartItemModel extends RealmObject {
    @PrimaryKey
    private int id;
    private int count;
    private double price;
    private String name;
    private String photos;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }
}
