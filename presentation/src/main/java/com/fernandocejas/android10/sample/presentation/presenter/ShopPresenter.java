package com.fernandocejas.android10.sample.presentation.presenter;

import com.fernandocejas.android10.sample.presentation.model.ShopModel;
import com.fernandocejas.android10.sample.presentation.navigation.Interactor;
import com.fernandocejas.android10.sample.presentation.view.ShopActivityView;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

public class ShopPresenter extends BasePresenter {

    private final ShopActivityView view;


    @Inject
    public ShopPresenter(Interactor interactor, ShopActivityView view) {
        super(interactor);
        this.view = view;
    }

    @Override
    public void resume() {
        Observable.zip(getInteractor().getShopInfo(),
                getInteractor().getCategoriesList(), (shopModel, categoryModels) -> {
                    shopModel.setCategoryModels(categoryModels);
                    return shopModel;
                })
                .subscribe(new Observer<ShopModel>() {
                    @Override
                    public void onNext(ShopModel shopModel) {
                        view.onLoaded(shopModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.onError();
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
