package com.fernandocejas.android10.sample.presentation.presenter;

import com.fernandocejas.android10.sample.presentation.navigation.Interactor;

public abstract class BasePresenter implements Presenter {

    private Interactor interactor;


    protected BasePresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    protected Interactor getInteractor() {
        return interactor;
    }
}
