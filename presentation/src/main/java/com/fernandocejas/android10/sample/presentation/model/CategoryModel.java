package com.fernandocejas.android10.sample.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CategoryModel extends RealmObject implements Parcelable {
    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel source) {
            return new CategoryModel(source);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };
    private String name;
    @PrimaryKey
    private int id;
    private int parentId;

    public CategoryModel() {
    }

    public CategoryModel(String name, int id) {
        this.name = name;
        this.id = id;
    }

    protected CategoryModel(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
        this.parentId = in.readInt();
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeInt(this.parentId);
    }
}
