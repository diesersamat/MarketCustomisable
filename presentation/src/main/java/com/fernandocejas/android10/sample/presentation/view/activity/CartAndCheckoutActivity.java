package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.CartAndCheckoutView;
import com.fernandocejas.android10.sample.presentation.view.fragment.CartFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.CheckoutFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.PaymentFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.PurchaseDoneFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAndCheckoutActivity extends BaseActivity implements CartAndCheckoutView {

    private static final String CART_TAG = "CART_TAG";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, CartAndCheckoutActivity.class);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void navigateToCheckout() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, CheckoutFragment.newInstance(), CheckoutFragment.getFragmentTag())
                .addToBackStack(CheckoutFragment.getFragmentTag())
                .commit();
        getSupportActionBar().setTitle("Checkout");
    }

    @Override
    public void navigateToPayment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PaymentFragment.newInstance(), PaymentFragment.getFragmentTag())
                .addToBackStack(PaymentFragment.getFragmentTag())
                .commit();
        getSupportActionBar().setTitle("Payment");
    }

    @Override
    public void navigateToPurchaseDone() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PurchaseDoneFragment.newInstance(), PurchaseDoneFragment.getFragmentTag())
                .commit();
        getSupportActionBar().setTitle("Purchase Done!");
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_and_checkout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Your Cart");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, CartFragment.newInstance(), CartFragment.getFragmentTag()).commit();

    }

    @Override
    protected void additionalCreateOperations() {
//        DaggerUserComponent.builder()
//                .applicationComponent(getApplicationComponent())
//                .activityModule(getActivityModule())
//                .build();
    }
}
