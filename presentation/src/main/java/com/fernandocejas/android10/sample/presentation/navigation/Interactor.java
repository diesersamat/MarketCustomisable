package com.fernandocejas.android10.sample.presentation.navigation;

import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.model.ProductDescriptionModel;
import com.fernandocejas.android10.sample.presentation.model.ShopModel;
import com.fernandocejas.android10.sample.presentation.model.UserModel;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class Interactor {

    private final ApiInterface apiInterface;

    @Inject
    public Interactor(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public Observable<ShopModel> getShopInfo(String appId) {
        return apiInterface.getShopInfo(appId);
    }

    public Observable<List<CategoryModel>> getCategoriesList() {
        return apiInterface.getCategoriesList();
    }


    public Observable<List<ProductDescriptionModel>> getCategoryListOfProducts(int categoryId) {
        return apiInterface.getProductsByCategory(categoryId);
    }

//    public Observable<ProductModel> getProductInfo(int productId) {
//
//    }
//
//
//    public Observable<OrderModel> postOrder() {
//
//    }

    public Observable<UserModel> authUser(String email, String pass) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("password", pass);
        return apiInterface.auth(hashMap);
    }
}
