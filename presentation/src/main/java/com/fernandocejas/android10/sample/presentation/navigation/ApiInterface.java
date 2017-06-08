package com.fernandocejas.android10.sample.presentation.navigation;

import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.model.ContactDetailModel;
import com.fernandocejas.android10.sample.presentation.model.OrderModel;
import com.fernandocejas.android10.sample.presentation.model.ProductWrapperModel;
import com.fernandocejas.android10.sample.presentation.model.ShopModel;
import com.fernandocejas.android10.sample.presentation.model.UserModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiInterface {

    @GET("Applications/{appId}")
    Observable<ShopModel> getShopInfo(@Path("appId") String appId);

    @GET("products/categories")
    Observable<List<CategoryModel>> getCategoriesList();

    @GET("products/categories/{categoryId}")
    Observable<List<ProductWrapperModel>> getProductsByCategory(@Path("categoryId") int categoryId);

    @GET("products/search/{string}")
    Observable<List<ProductWrapperModel>> searchProducts(@Path("string") String searchString);

    @POST("Customers/Auth")
    Observable<UserModel> auth(@Body HashMap<String, Object> body);

    @GET("Products/{productId}")
    Observable<ProductWrapperModel> getProductInfo(@Path("productId") int productId);

    @POST("customers/new")
    Observable<UserModel> registerUser(@Body HashMap<String, Object> body);


    @GET("Orders")
    Observable<List<OrderModel>> getAllOrders(@Header("token") String token);

    @POST("Orders")
    Observable<OrderModel> sendOrder(@Header("token") String token,
                                     @Body HashMap<String, Object> body);

    @POST("Orders/ChangeStatus")
    Observable<OrderModel> changeOrderStatus(@Header("token") String token,
                                             @Body HashMap<String, Object> body);

    @GET("customers/GetContactDetails")
    Observable<List<ContactDetailModel>> getAllContactDetails(@Header("token") String token);

    @POST("customers/newContactDetail")
    Observable<ContactDetailModel> postContactDetail(@Header("token") String token,
                                                     @Body HashMap<String, Object> body);
}
