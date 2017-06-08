package com.fernandocejas.android10.sample.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductDescriptionModel extends RealmObject implements Parcelable {
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
    private String name;
    private String linkToImage;
    @PrimaryKey
    private int id;
    private int count;
    private double price;
    private double salePrice;
    private String saleId;
    private String currency;
    private int categoryId;
    private RealmList<ImageModel> images;

    public ProductDescriptionModel() {
    }

    public ProductDescriptionModel(String title, String linkToImage, int id, double price, String currency) {
        this.name = title;
        this.linkToImage = linkToImage;
        this.id = id;
        this.price = price;
        this.currency = currency;
    }

    protected ProductDescriptionModel(Parcel in) {
        this.name = in.readString();
        this.linkToImage = in.readString();
        this.id = in.readInt();
        this.price = in.readDouble();
        this.currency = in.readString();
    }

    public RealmList<ImageModel> getImages() {
        return images;
    }

    public void setImages(RealmList<ImageModel> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return "RUB";
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
        dest.writeString(this.name);
        dest.writeString(this.linkToImage);
        dest.writeInt(this.id);
        dest.writeDouble(this.price);
        dest.writeString(this.currency);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
