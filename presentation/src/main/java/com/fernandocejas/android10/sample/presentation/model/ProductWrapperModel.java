package com.fernandocejas.android10.sample.presentation.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductWrapperModel extends RealmObject {
    ProductModel product;
    @PrimaryKey

    private int id;

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
