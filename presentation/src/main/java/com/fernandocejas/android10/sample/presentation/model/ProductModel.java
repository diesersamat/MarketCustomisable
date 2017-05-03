package com.fernandocejas.android10.sample.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModel implements Parcelable {

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
    private int id;
    private String photos;
    private boolean isInStock;
    private double price;
    private String currency;
    private String title;
    private String description;

    public ProductModel() {
    }

    protected ProductModel(Parcel in) {
        this.id = in.readInt();
        this.photos = in.readString();
        this.isInStock = in.readByte() != 0;
        this.price = in.readDouble();
        this.currency = in.readString();
        this.title = in.readString();
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
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        dest.writeString(this.title);
        dest.writeString(this.description);
    }
}
