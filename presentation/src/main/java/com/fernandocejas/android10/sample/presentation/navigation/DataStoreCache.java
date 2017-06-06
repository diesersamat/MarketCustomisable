package com.fernandocejas.android10.sample.presentation.navigation;

import com.fernandocejas.android10.sample.presentation.model.CartItemModel;
import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.model.ContactDetailModel;
import com.fernandocejas.android10.sample.presentation.model.ProductDescriptionModel;
import com.fernandocejas.android10.sample.presentation.model.ProductWrapperModel;
import com.fernandocejas.android10.sample.presentation.model.ShopModel;
import com.fernandocejas.android10.sample.presentation.model.UserModel;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

public class DataStoreCache {

    @Inject
    public DataStoreCache() {
    }

    public ContactDetailModel getDefaultContactDetailsAsync(int id) {
        RealmResults<ContactDetailModel> all = getRealm().where(ContactDetailModel.class)
                .equalTo("id", id).findAll();
        if (all.isEmpty()) {
            return null;
        }
        return getRealm().copyFromRealm(all).get(0);
    }

    public void saveContactDetail(List<ContactDetailModel> contactDetailModels) {
        RealmResults<ContactDetailModel> delete = getRealm().where(ContactDetailModel.class).findAll();
        getRealm().executeTransaction(realm -> delete.deleteAllFromRealm());
        getRealm().executeTransaction(realm ->
                realm.insertOrUpdate(contactDetailModels));
    }


    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    void clearCart() {
        RealmResults<CartItemModel> all = getRealm().where(CartItemModel.class).findAll();
        getRealm().executeTransaction(realm -> all.deleteAllFromRealm());
    }

    List<CartItemModel> getItemsFromCartSync() {
        RealmResults<CartItemModel> all = getRealm().where(CartItemModel.class).findAll();
        return getRealm().copyFromRealm(all);
    }

    void signOut() {
        RealmResults<UserModel> rows = getRealm().where(UserModel.class).findAll();
        getRealm().executeTransaction(realm -> rows.deleteAllFromRealm());
    }

    UserModel getUserInfoSync() {
        RealmResults<UserModel> all = getRealm().where(UserModel.class).findAll();
        if (all.isEmpty()) {
            return null;
        } else {
            return getRealm().copyFromRealm(all).get(0);
        }
    }

    void saveUserInfo(UserModel userModel) {
        getRealm().executeTransaction(realm ->
                realm.insertOrUpdate(userModel));
    }

    ShopModel getShopInfoSync(String appId) {
        RealmResults<ShopModel> all = getRealm().where(ShopModel.class).equalTo("id", appId).findAll();
        return getRealm().copyFromRealm(all).get(0);
    }

    Observable<List<CartItemModel>> getItemsFromCart() {
        RealmResults<CartItemModel> all = getRealm().where(CartItemModel.class).findAll();
        List<CartItemModel> arrayListOfUnmanagedObjects = getRealm().copyFromRealm(all);
        if (arrayListOfUnmanagedObjects == null || arrayListOfUnmanagedObjects.isEmpty()) {
            return Observable.empty();
        } else {
            return Observable.just(arrayListOfUnmanagedObjects);
        }
    }

    void removeProductFromCart(CartItemModel itemModel) {
        RealmResults<CartItemModel> rows = getRealm()
                .where(CartItemModel.class)
                .equalTo("id", itemModel.getId())
                .findAll();
        if (rows.isEmpty()) {
            //do nothing
        } else {
            CartItemModel model = rows.get(0);
            if (model.getCount() == 1) {
                getRealm().executeTransaction(realm -> rows.deleteAllFromRealm());
            } else {
                getRealm().executeTransaction(realm -> model.setCount(model.getCount() - 1));
            }
        }
    }

    Observable<ShopModel> getShopInfo(String appid) {
        RealmResults<ShopModel> all = getRealm().where(ShopModel.class).equalTo("id", appid).findAll();
        return Observable.from(getRealm().copyFromRealm(all));
    }

    void saveShopInfo(ShopModel newsDTO) {
        getRealm().executeTransaction(realm ->
                realm.insertOrUpdate(newsDTO));
    }

    void saveCategoriesList(List<CategoryModel> categoryModels) {
        getRealm().executeTransaction(realm ->
                realm.insertOrUpdate(categoryModels));
    }

    Observable<List<CategoryModel>> getCategoriesList() {
        RealmResults<CategoryModel> all = getRealm().where(CategoryModel.class).findAll();
        List<CategoryModel> arrayListOfUnmanagedObjects = getRealm().copyFromRealm(all);
        if (arrayListOfUnmanagedObjects == null || arrayListOfUnmanagedObjects.isEmpty()) {
            return Observable.empty();
        } else {
            return Observable.just(arrayListOfUnmanagedObjects);
        }
    }

    void saveProductsByCategory(List<ProductDescriptionModel> productDescriptionModels) {
        getRealm().executeTransaction(realm ->
                realm.insertOrUpdate(productDescriptionModels));
    }

    Observable<List<ProductDescriptionModel>> getProductsByCategory(int categoryId) {
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

    Observable<ProductWrapperModel> getProductInfo(int productId) {
        RealmResults<ProductWrapperModel> all = getRealm()
                .where(ProductWrapperModel.class)
                .equalTo("id", productId).findAll();
        return Observable.from(getRealm().copyFromRealm(all));
    }

    void saveProductInfo(ProductWrapperModel productModel) {
        getRealm().executeTransaction(realm ->
                realm.insertOrUpdate(productModel));
    }

    void addProductToCart(CartItemModel itemModel) {
        RealmResults<CartItemModel> all = getRealm().where(CartItemModel.class).equalTo("id", itemModel.getId()).findAll();
        if (all.isEmpty()) {
            getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(itemModel));
        } else {
            CartItemModel itemModelFromDB = all.get(0);
            getRealm().executeTransaction(realm -> itemModelFromDB.setCount(itemModelFromDB.getCount() + 1));
        }
    }
}
