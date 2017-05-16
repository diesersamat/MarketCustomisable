package com.fernandocejas.android10.sample.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ShopModel implements Parcelable {
    public static final Creator<ShopModel> CREATOR = new Creator<ShopModel>() {
        @Override
        public ShopModel createFromParcel(Parcel source) {
            return new ShopModel(source);
        }

        @Override
        public ShopModel[] newArray(int size) {
            return new ShopModel[size];
        }
    };
    List<CategoryModel> categoryModels;
    String primaryColor;
    String accentColor;
    String id;
    int themeId;
    boolean isDarkTheme;
    String name;

    public ShopModel() {
    }

    protected ShopModel(Parcel in) {
        this.categoryModels = in.createTypedArrayList(CategoryModel.CREATOR);
        this.primaryColor = in.readString();
        this.accentColor = in.readString();
        this.id = in.readString();
        this.themeId = in.readInt();
        this.isDarkTheme = in.readByte() != 0;
        this.name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryModel> getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(List<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        isDarkTheme = darkTheme;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.categoryModels);
        dest.writeString(this.primaryColor);
        dest.writeString(this.accentColor);
        dest.writeString(this.id);
        dest.writeInt(this.themeId);
        dest.writeByte(this.isDarkTheme ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
    }
}
