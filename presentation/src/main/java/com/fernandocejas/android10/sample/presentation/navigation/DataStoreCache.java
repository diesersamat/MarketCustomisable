package com.fernandocejas.android10.sample.presentation.navigation;

import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.model.ProductDescriptionModel;
import com.fernandocejas.android10.sample.presentation.model.ShopModel;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

public class DataStoreCache {

    @Inject
    public DataStoreCache() {
    }

    public Observable<ShopModel> getShopInfo(String appid) {
        RealmResults<ShopModel> all = getRealm().where(ShopModel.class).equalTo("id", appid).findAll();
        return Observable.from(getRealm().copyFromRealm(all));
    }

    public void saveShopInfo(ShopModel newsDTO) {
        getRealm().executeTransaction(realm ->
                realm.insertOrUpdate(newsDTO));
    }

    public void saveCategoriesList(List<CategoryModel> categoryModels) {
        getRealm().executeTransaction(realm ->
                realm.insertOrUpdate(categoryModels));
    }

    public Observable<List<CategoryModel>> getCategoriesList() {
        RealmResults<CategoryModel> all = getRealm().where(CategoryModel.class).findAll();
        List<CategoryModel> arrayListOfUnmanagedObjects = getRealm().copyFromRealm(all);
        if (arrayListOfUnmanagedObjects == null || arrayListOfUnmanagedObjects.isEmpty()) {
            return Observable.empty();
        } else {
            return Observable.just(arrayListOfUnmanagedObjects);
        }
    }

    public void saveProductsByCategory(List<ProductDescriptionModel> productDescriptionModels) {
        getRealm().executeTransaction(realm ->
                realm.insertOrUpdate(productDescriptionModels));
    }

    public Observable<List<ProductDescriptionModel>> getProductsByCategory(int categoryId) {
        RealmResults<ProductDescriptionModel> all = getRealm()
                .where(ProductDescriptionModel.class)
                .equalTo("categoryId", categoryId).findAll();
        List<ProductDescriptionModel> arrayListOfUnmanagedObjects = getRealm().copyFromRealm(all);

        if (arrayListOfUnmanagedObjects == null || arrayListOfUnmanagedObjects.isEmpty()) {
            return Observable.empty();
        } else {
            return Observable.just(arrayListOfUnmanagedObjects);
        }
    }

    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }
}
