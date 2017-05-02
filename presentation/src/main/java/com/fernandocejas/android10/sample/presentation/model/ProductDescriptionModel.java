package com.fernandocejas.android10.sample.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductDescriptionModel implements Parcelable {
    public static final Parcelable.Creator<ProductDescriptionModel> CREATOR = new Parcelable.Creator<ProductDescriptionModel>() {
        @Override
        public ProductDescriptionModel createFromParcel(Parcel source) {
            return new ProductDescriptionModel(source);
        }

        @Override
        public ProductDescriptionModel[] newArray(int size) {
            return new ProductDescriptionModel[size];
        }
    };
    private String title;
    private String linkToImage;
    private int id;
    private int price;
    private String currency;

    public ProductDescriptionModel(String title, String linkToImage, int id, int price, String currency) {

        this.title = title;
        this.linkToImage = linkToImage;
        this.id = id;
        this.price = price;
        this.currency = currency;
    }

    protected ProductDescriptionModel(Parcel in) {
        this.title = in.readString();
        this.linkToImage = in.readString();
        this.id = in.readInt();
        this.price = in.readInt();
        this.currency = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkToImage() {
        return linkToImage;
    }

    public void setLinkToImage(String linkToImage) {
        this.linkToImage = linkToImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.linkToImage);
        dest.writeInt(this.id);
        dest.writeInt(this.price);
        dest.writeString(this.currency);
    }
}
