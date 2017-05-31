package com.fernandocejas.android10.sample.presentation.presenter;

import com.fernandocejas.android10.sample.presentation.model.ProductWrapperModel;
import com.fernandocejas.android10.sample.presentation.navigation.Interactor;
import com.fernandocejas.android10.sample.presentation.view.ProductView;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observer;
import rx.Subscription;

public class ProductPresenter extends BasePresenter {
    private final ProductView view;
    private final int productId;
    private Subscription subscription;

    @Inject
    protected ProductPresenter(Interactor interactor, ProductView view,
                               @Named("productId") int productId) {
        super(interactor);
        this.view = view;
        this.productId = productId;
    }

    @Override
    public void resume() {
        subscription = getInteractor().getProductInfo(productId)
                .subscribe(new Observer<ProductWrapperModel>() {
                    @Override
                    public void onNext(ProductWrapperModel shopModel) {
                        view.onLoaded(shopModel.getProduct());
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
        subscription.unsubscribe();
    }
}
