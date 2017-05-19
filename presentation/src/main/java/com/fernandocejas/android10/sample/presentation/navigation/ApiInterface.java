package com.fernandocejas.android10.sample.presentation.navigation;

import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.model.ProductDescriptionModel;
import com.fernandocejas.android10.sample.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.model.ProductWrapperModel;
import com.fernandocejas.android10.sample.presentation.model.ShopModel;
import com.fernandocejas.android10.sample.presentation.model.UserModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiInterface {

    @GET("Applications/{appId}")
    Observable<ShopModel> getShopInfo(@Path("appId") String appId);

    @GET("products/categories")
    Observable<List<CategoryModel>> getCategoriesList();

    @GET("products/categories/{categoryId}")
    Observable<List<ProductDescriptionModel>> getProductsByCategory(@Path("categoryId") int categoryId);

    @GET("Customers/Auth")
    Observable<UserModel> auth(@Body HashMap<String, Object> body);

    @GET("Products/{productId}")
    Observable<ProductWrapperModel> getProductInfo(@Path("productId") int productId);
}
