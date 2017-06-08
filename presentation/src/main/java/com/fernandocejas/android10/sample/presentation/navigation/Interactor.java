package com.fernandocejas.android10.sample.presentation.navigation;

import com.fernandocejas.android10.sample.presentation.BuildConfig;
import com.fernandocejas.android10.sample.presentation.model.CartItemModel;
import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.model.ContactDetailModel;
import com.fernandocejas.android10.sample.presentation.model.OrderItemModel;
import com.fernandocejas.android10.sample.presentation.model.OrderModel;
import com.fernandocejas.android10.sample.presentation.model.ProductDescriptionModel;
import com.fernandocejas.android10.sample.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.model.ProductWrapperModel;
import com.fernandocejas.android10.sample.presentation.model.ShopModel;
import com.fernandocejas.android10.sample.presentation.model.UserModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

    public Observable<ShopModel> getShopInfo() {
//        return Observable.defer(() -> dataStoreCache.getShopInfo(BuildConfig.CUSTOMISABLE_APPLICATION_ID))
//                .switchIfEmpty(apiInterface.getShopInfo(BuildConfig.CUSTOMISABLE_APPLICATION_ID)
//                        .doOnNext(dataStoreCache::saveShopInfo))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
        return apiInterface.getShopInfo(BuildConfig.CUSTOMISABLE_APPLICATION_ID)
                .doOnNext(dataStoreCache::saveShopInfo)
                .onErrorResumeNext(Observable.defer(() -> dataStoreCache.getShopInfo(BuildConfig.CUSTOMISABLE_APPLICATION_ID)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public ShopModel getShopInfoSync() {
        return dataStoreCache.getShopInfoSync(BuildConfig.CUSTOMISABLE_APPLICATION_ID);
    }

    public UserModel getUserInfoSync() {
        return dataStoreCache.getUserInfoSync();
    }

    public Observable<List<CategoryModel>> getCategoriesList() {
//        return Observable.defer(dataStoreCache::getCategoriesList)
//                .switchIfEmpty(apiInterface.getCategoriesList()
//                        .doOnNext(dataStoreCache::saveCategoriesList))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
        return apiInterface.getCategoriesList()
                .doOnNext(dataStoreCache::saveCategoriesList)
                .onErrorResumeNext(Observable.defer(dataStoreCache::getCategoriesList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<ProductDescriptionModel>> getCategoryListOfProducts(int categoryId) {
//        return Observable.defer(() -> dataStoreCache.getProductsByCategory(categoryId))
//                .switchIfEmpty(apiInterface.getProductsByCategory(categoryId).map(productDescriptionModels -> {
//                    for (ProductDescriptionModel mod : productDescriptionModels) {
//                        mod.setCategoryId(categoryId);
//                    }
//                    return productDescriptionModels;
//                })
//                        .doOnNext(dataStoreCache::saveProductsByCategory))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());

        return apiInterface.getProductsByCategory(categoryId).map(productDescriptionModels -> {
            for (ProductDescriptionModel mod : productDescriptionModels) {
                mod.setCategoryId(categoryId);
            }
            return productDescriptionModels;
        })
                .doOnNext(dataStoreCache::saveProductsByCategory)
                .onErrorResumeNext(Observable.defer(() -> dataStoreCache.getProductsByCategory(categoryId)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ProductDescriptionModel>> searchForItems(String searchString) {
        return apiInterface.searchProducts(searchString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ProductWrapperModel> getProductInfo(int productId) {
//        return Observable.defer(() -> dataStoreCache.getProductInfo(productId))
//                .switchIfEmpty(apiInterface.getProductInfo(productId)
//                        .map(productWrapperModel -> {
//                            productWrapperModel.setId(productId);
//                            return productWrapperModel;
//                        })
//                        .doOnNext(dataStoreCache::saveProductInfo))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());

        return apiInterface.getProductInfo(productId)
                .map(productWrapperModel -> {
                    productWrapperModel.setId(productId);
                    return productWrapperModel;
                })
                .doOnNext(dataStoreCache::saveProductInfo)
                .onErrorResumeNext(Observable.defer(() -> dataStoreCache.getProductInfo(productId)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void addProductToCart(ProductDescriptionModel product) {
        CartItemModel itemModel = new CartItemModel();
        itemModel.setId(product.getId());
        itemModel.setCount(1);
        itemModel.setName(product.getName());
        itemModel.setPrice(product.getPrice());
        dataStoreCache.addProductToCart(itemModel);


        if (product.getImages() != null) {
            if (!product.getImages().isEmpty()) {
                if (product.getImages().get(0) != null) {
                    itemModel.setPhotos(product.getImages().get(0).getUrl());
                }
            }
        }
    }

    public void addProductToCart(ProductModel product, String url) {
        CartItemModel itemModel = new CartItemModel();
        itemModel.setId(product.getId());
        itemModel.setCount(1);
        itemModel.setName(product.getName());
        itemModel.setPhotos(url);
        itemModel.setPrice(product.getPrice());
        dataStoreCache.addProductToCart(itemModel);
    }

    public void addProductToCart(CartItemModel product) {
        dataStoreCache.addProductToCart(product);
    }

    public void removeProductFromCart(CartItemModel itemModel) {
        dataStoreCache.removeProductFromCart(itemModel);
    }

    public Observable<List<CartItemModel>> getItemsFromCart() {
        return Observable.defer(dataStoreCache::getItemsFromCart);
    }


    public Observable<UserModel> authUser(String email, String pass) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("password", md5(pass));
        return apiInterface.auth(hashMap)
                .doOnNext(dataStoreCache::saveUserInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<UserModel> registerUser(String email, String pass, String fname,
                                              String sname, String phone) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("password", md5(pass));
        hashMap.put("fname", fname);
        hashMap.put("sname", sname);
        hashMap.put("Phone", phone);
        return apiInterface.registerUser(hashMap)
                .doOnNext(dataStoreCache::saveUserInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void signOut() {
        dataStoreCache.signOut();
    }

    public int getItemsNumberFromCart() {
        List<CartItemModel> itemsFromCartSync = dataStoreCache.getItemsFromCartSync();
        if (itemsFromCartSync == null || itemsFromCartSync.isEmpty()) {
            return 0;
        } else {
            int count = 0;
            for (CartItemModel itemModel : itemsFromCartSync) {
                count += itemModel.getCount();
            }
            return count;
        }
    }

    public Observable<OrderModel> postOrder(int contactDetailId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        ArrayList<OrderItemModel> orderItemModels = new ArrayList<>();
        List<CartItemModel> itemsFromCartSync = dataStoreCache.getItemsFromCartSync();
        for (CartItemModel itemModel : itemsFromCartSync) {
            orderItemModels.add(new OrderItemModel(itemModel.getId(),
                    itemModel.getCount()));
        }
        hashMap.put("OrderItems", orderItemModels);
        hashMap.put("ContactDetailId", contactDetailId);
        UserModel userInfoSync = dataStoreCache.getUserInfoSync();
        return apiInterface
                .sendOrder(userInfoSync == null ? "" : userInfoSync.getToken(), hashMap)
                .doOnCompleted(this::clearCart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<OrderModel> postOrderStatusPaid(int orderId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", orderId);
        hashMap.put("status", 1);
        UserModel userInfoSync = dataStoreCache.getUserInfoSync();
        return apiInterface
                .changeOrderStatus(userInfoSync == null ? "" : userInfoSync.getToken(), hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<OrderModel>> getAllOrders() {
        UserModel userInfoSync = dataStoreCache.getUserInfoSync();
        return apiInterface
                .getAllOrders(userInfoSync == null ? "" : userInfoSync.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ContactDetailModel>> getAllContactDetails(int id) {
        UserModel userInfoSync = dataStoreCache.getUserInfoSync();
        return apiInterface
                .getAllContactDetails(userInfoSync == null ? "" : userInfoSync.getToken())
                .map(contactDetailModels -> {
                    for (ContactDetailModel cont :
                            contactDetailModels) {
                        if (cont.getId() == id) {
                            cont.setDefault(true);
                        } else {
                            cont.setDefault(false);
                        }
                    }
                    return contactDetailModels;
                })
                .doOnNext(dataStoreCache::saveContactDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ContactDetailModel> postContactDetail(String phone, String address) {
        UserModel userInfoSync = dataStoreCache.getUserInfoSync();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("address", address);
        return apiInterface
                .postContactDetail(userInfoSync == null ? "" : userInfoSync.getToken(), hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public ContactDetailModel getDefaultContactDetailsAsync(int id) {
        return dataStoreCache.getDefaultContactDetailsAsync(id);
    }

    private void clearCart() {
        dataStoreCache.clearCart();
    }

    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
