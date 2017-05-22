package com.fernandocejas.android10.sample.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class ProductModel extends RealmObject implements Parcelable {

    public static final Parcelable.Creator<ProductModel> CREATOR = new Parcelable.Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel source) {
            return new ProductModel(source);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };
    private String currency;
    private int id;
    private int count;
    private String photos;
    private boolean isInStock;
    private double price;
    private String name;
    private String description;

    public ProductModel() {
    }

    protected ProductModel(Parcel in) {
        this.id = in.readInt();
        this.photos = in.readString();
        this.isInStock = in.readByte() != 0;
        this.price = in.readDouble();
        this.name = in.readString();
        this.description = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return "RUB";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.photos);
        dest.writeByte(this.isInStock ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.price);
        dest.writeString(this.currency);
        dest.writeString(this.name);
        dest.writeString(this.description);
    }
}
