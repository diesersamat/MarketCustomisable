package com.fernandocejas.android10.sample.presentation.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductWrapperModel extends RealmObject {
    ProductModel product;
    RealmList<FieldModel> fields;
    RealmList<ImageModel> images;
    private int categoryId;
    @PrimaryKey
    private int id;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public RealmList<ImageModel> getImages() {
        return images;
    }

    public void setImages(RealmList<ImageModel> images) {
        this.images = images;
    }

    public RealmList<FieldModel> getFields() {
        return fields;
    }

    public void setFields(RealmList<FieldModel> fields) {
        this.fields = fields;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }
}
