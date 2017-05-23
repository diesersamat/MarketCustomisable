package com.fernandocejas.android10.sample.presentation.internal.di.modules;

import com.fernandocejas.android10.sample.presentation.BuildConfig;
import com.fernandocejas.android10.sample.presentation.navigation.ApiInterface;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final String API_ROOT = BuildConfig.API_URL;

    private static OkHttpClient buildSslHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request build = chain.request().newBuilder()
                            .addHeader("applicationId", BuildConfig.CUSTOMISABLE_APPLICATION_ID)
                            .build();
                    return chain.proceed(build);
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return builder.build();
    }

    @Provides
    @Singleton
    ApiInterface provideApi(Gson gson) {
        final OkHttpClient okHttpClient = buildSslHttpClient();

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(API_ROOT)
                .client(okHttpClient)
                .build()
                .create(ApiInterface.class);
    }
}
