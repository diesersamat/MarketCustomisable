package com.fernandocejas.android10.sample.presentation.navigation;

import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.model.ProductDescriptionModel;
import com.fernandocejas.android10.sample.presentation.model.ShopModel;
import com.fernandocejas.android10.sample.presentation.model.UserModel;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Interactor {

    private final ApiInterface apiInterface;
    private final DataStoreCache dataStoreCache;

    @Inject
    public Interactor(ApiInterface apiInterface, DataStoreCache dataStoreCache) {
        this.apiInterface = apiInterface;
        this.dataStoreCache = dataStoreCache;
    }

    public Observable<ShopModel> getShopInfo(String appId) {
        return Observable.defer(() -> dataStoreCache.getShopInfo(appId))
                .switchIfEmpty(apiInterface.getShopInfo(appId)
                        .doOnNext(dataStoreCache::saveShopInfo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<CategoryModel>> getCategoriesList() {
//        return apiInterface.getCategoriesList();
        return Observable.defer(dataStoreCache::getCategoriesList)
                .switchIfEmpty(apiInterface.getCategoriesList()
                        .doOnNext(dataStoreCache::saveCategoriesList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<ProductDescriptionModel>> getCategoryListOfProducts(int categoryId) {
        return Observable.defer(() -> dataStoreCache.getProductsByCategory(categoryId))
                .switchIfEmpty(apiInterface.getProductsByCategory(categoryId).map(productDescriptionModels -> {
                    for (ProductDescriptionModel mod : productDescriptionModels) {
                        mod.setCategoryId(categoryId);
                    }
                    return productDescriptionModels;
                })
                        .doOnNext(dataStoreCache::saveProductsByCategory))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
