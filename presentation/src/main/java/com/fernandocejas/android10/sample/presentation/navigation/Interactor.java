package com.fernandocejas.android10.sample.presentation.navigation;

import com.fernandocejas.android10.sample.presentation.BuildConfig;
import com.fernandocejas.android10.sample.presentation.model.CartItemModel;
import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.model.OrderItemModel;
import com.fernandocejas.android10.sample.presentation.model.OrderModel;
import com.fernandocejas.android10.sample.presentation.model.ProductDescriptionModel;
import com.fernandocejas.android10.sample.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.model.ProductWrapperModel;
import com.fernandocejas.android10.sample.presentation.model.ShopModel;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

    private static final String ALL_ORDERS_TEST = "[\n" +
            "  {\n" +
            "    \"id\": 5,\n" +
            "    \"status\": 0,\n" +
            "    \"totalPrice\": 723,\n" +
            "    \"date\": \"2017-05-22T21:15:04.513\",\n" +
            "    \"orderItems\": [\n" +
            "      {\n" +
            "        \"id\": 4,\n" +
            "        \"productId\": 4,\n" +
            "        \"productName\": \"Green Day Disc\",\n" +
            "        \"productPrice\": 300,\n" +
            "        \"quantity\": 1\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 5,\n" +
            "        \"productId\": 5,\n" +
            "        \"productName\": \"LinkinPark\",\n" +
            "        \"productPrice\": 423,\n" +
            "        \"quantity\": 1\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 6,\n" +
            "    \"status\": 0,\n" +
            "    \"totalPrice\": 1446,\n" +
            "    \"date\": \"2017-05-22T21:33:59.643\",\n" +
            "    \"orderItems\": [\n" +
            "      {\n" +
            "        \"id\": 6,\n" +
            "        \"productId\": 4,\n" +
            "        \"productName\": \"Green Day Disc\",\n" +
            "        \"productPrice\": 300,\n" +
            "        \"quantity\": 2\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 7,\n" +
            "        \"productId\": 5,\n" +
            "        \"productName\": \"LinkinPark\",\n" +
            "        \"productPrice\": 423,\n" +
            "        \"quantity\": 2\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 1002,\n" +
            "    \"status\": 1,\n" +
            "    \"totalPrice\": 1446,\n" +
            "    \"date\": \"2017-05-23T00:13:47.657\",\n" +
            "    \"orderItems\": [\n" +
            "      {\n" +
            "        \"id\": 1002,\n" +
            "        \"productId\": 4,\n" +
            "        \"productName\": \"Green Day Disc\",\n" +
            "        \"productPrice\": 300,\n" +
            "        \"quantity\": 2\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 1003,\n" +
            "        \"productId\": 5,\n" +
            "        \"productName\": \"LinkinPark\",\n" +
            "        \"productPrice\": 423,\n" +
            "        \"quantity\": 2\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 1003,\n" +
            "    \"status\": 0,\n" +
            "    \"totalPrice\": 1446,\n" +
            "    \"date\": \"2017-05-23T00:16:01.14\",\n" +
            "    \"orderItems\": [\n" +
            "      {\n" +
            "        \"id\": 1004,\n" +
            "        \"productId\": 4,\n" +
            "        \"productName\": \"Green Day Disc\",\n" +
            "        \"productPrice\": 300,\n" +
            "        \"quantity\": 2\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 1005,\n" +
            "        \"productId\": 5,\n" +
            "        \"productName\": \"LinkinPark\",\n" +
            "        \"productPrice\": 423,\n" +
            "        \"quantity\": 2\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 1004,\n" +
            "    \"status\": 1,\n" +
            "    \"totalPrice\": 1446,\n" +
            "    \"date\": \"2017-05-23T00:13:47.657\"\n" +
            "  }\n" +
            "]";
    private final ApiInterface apiInterface;
    private final DataStoreCache dataStoreCache;

    @Inject
    public Interactor(ApiInterface apiInterface, DataStoreCache dataStoreCache) {
        this.apiInterface = apiInterface;
        this.dataStoreCache = dataStoreCache;
    }

    public Observable<ShopModel> getShopInfo() {
        return Observable.defer(() -> dataStoreCache.getShopInfo(BuildConfig.CUSTOMISABLE_APPLICATION_ID))
                .switchIfEmpty(apiInterface.getShopInfo(BuildConfig.CUSTOMISABLE_APPLICATION_ID)
                        .doOnNext(dataStoreCache::saveShopInfo))
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

    public Observable<ProductWrapperModel> getProductInfo(int productId) {
        return Observable.defer(() -> dataStoreCache.getProductInfo(productId))
                .switchIfEmpty(apiInterface.getProductInfo(productId)
                        .map(productWrapperModel -> {
                            productWrapperModel.setId(productId);
                            return productWrapperModel;
                        })
                        .doOnNext(dataStoreCache::saveProductInfo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void addProductToCart(ProductDescriptionModel product) {
        CartItemModel itemModel = new CartItemModel();
        itemModel.setId(product.getId());
        itemModel.setCount(1);
        itemModel.setName(product.getName());
        itemModel.setPhotos(product.getLinkToImage());
        itemModel.setPrice(product.getPrice());
        dataStoreCache.addProductToCart(itemModel);
    }

    public void addProductToCart(ProductModel product) {
        CartItemModel itemModel = new CartItemModel();
        itemModel.setId(product.getId());
        itemModel.setCount(1);
        itemModel.setName(product.getName());
        itemModel.setPhotos(product.getPhotos());
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

    public Observable<OrderModel> postOrder() {
        //// TODO: 25/05/2017  clear cart
        HashMap<String, Object> hashMap = new HashMap<>();
        ArrayList<OrderItemModel> orderItemModels = new ArrayList<>();
        List<CartItemModel> itemsFromCartSync = dataStoreCache.getItemsFromCartSync();
        for (CartItemModel itemModel : itemsFromCartSync) {
            orderItemModels.add(new OrderItemModel(itemModel.getId(),
                    itemModel.getCount()));
        }
        hashMap.put("OrderItems", orderItemModels);
        UserModel userInfoSync = dataStoreCache.getUserInfoSync();
        return apiInterface
                .sendOrder(userInfoSync == null ? "" : userInfoSync.getToken(), hashMap)
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
        Type listType = new TypeToken<ArrayList<OrderModel>>() {
        }.getType();

        List<OrderModel> yourClassList = new Gson().fromJson(ALL_ORDERS_TEST, listType);

        UserModel userInfoSync = dataStoreCache.getUserInfoSync();
        return apiInterface
                .getAllOrders(userInfoSync == null ? "" : userInfoSync.getToken())
                .onErrorReturn(throwable -> yourClassList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
