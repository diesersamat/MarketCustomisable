package com.fernandocejas.android10.sample.presentation.presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fernandocejas.android10.sample.presentation.BuildConfig;
import com.fernandocejas.android10.sample.presentation.model.ShopModel;
import com.fernandocejas.android10.sample.presentation.navigation.Interactor;
import com.fernandocejas.android10.sample.presentation.view.ShopActivityView;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity.ACCENT_COLOR;
import static com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity.PRIMARY_COLOR;

public class ShopPresenter extends BasePresenter {

    private final ShopActivityView view;


    @Inject
    public ShopPresenter(Interactor interactor, ShopActivityView view) {
        super(interactor);
        this.view = view;
    }

    @Override
    public void resume() {
        Observable.zip(getInteractor().getShopInfo(BuildConfig.CUSTOMISABLE_APPLICATION_ID),
                getInteractor().getCategoriesList(), (shopModel, categoryModels) -> {
                    shopModel.setCategoryModels(categoryModels);
                    return shopModel;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopModel>() {
                    @Override
                    public void onNext(ShopModel shopModel) {
                        view.onLoaded(shopModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onCompleted() {

                    }
                });

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
