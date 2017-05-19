package com.fernandocejas.android10.sample.presentation.view;

public interface CartAndCheckoutView {
    void navigateToCheckout();

    void navigateToPayment();

    void navigateToPurchaseDone();

    int getAccentColor();

    int getPrimaryColor();

}
