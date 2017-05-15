package com.fernandocejas.android10.sample.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.presentation.navigation.Interactor;
import com.fernandocejas.android10.sample.presentation.view.UserDetailsView;

import javax.inject.Inject;

public class ShopPresenter extends BasePresenter {

    private UserDetailsView viewDetailsView;

    @Inject
    public ShopPresenter(Interactor interactor) {
        super(interactor);
    }

    public void setView(@NonNull UserDetailsView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
//        this.getUserDetailsUseCase.dispose();
        this.viewDetailsView = null;
    }

    /**
     * Initializes the presenter by showing/hiding proper views
     * and retrieving user details.
     */
    public void initialize(int userId) {
        this.hideViewRetry();
        this.showViewLoading();
//        this.getUserDetails(userId);
    }

    private void getShopDetails(int shopId) {
//        getInteractor().getShopDetails()
    }

    private void showViewLoading() {
        this.viewDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.viewDetailsView.hideLoading();
    }

    private void showViewRetry() {
        this.viewDetailsView.showRetry();
    }

    private void hideViewRetry() {
        this.viewDetailsView.hideRetry();
    }

//    private void showErrorMessage(ErrorBundle errorBundle) {
//        String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(),
//                errorBundle.getException());
//        this.viewDetailsView.showError(errorMessage);
//    }

    private void showUserDetailsInView(User user) {
//        final UserModel userModel = this.userModelDataMapper.transform(user);
//        this.viewDetailsView.renderUser(userModel);
    }

//    private final class UserDetailsObserver extends DefaultObserver<User> {
//
//        @Override
//        public void onComplete() {
//            UserDetailsPresenter.this.hideViewLoading();
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            UserDetailsPresenter.this.hideViewLoading();
//            UserDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
//            UserDetailsPresenter.this.showViewRetry();
//        }
//
//        @Override
//        public void onNext(User user) {
//            UserDetailsPresenter.this.showUserDetailsInView(user);
//        }
//    }
}
