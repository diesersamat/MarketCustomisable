package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerUserComponent;
import com.fernandocejas.android10.sample.presentation.view.CartAndCheckoutView;
import com.fernandocejas.android10.sample.presentation.view.fragment.CartFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.CheckoutFragment;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void navigateToPurchaseDone() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PurchaseDoneFragment.newInstance(), PurchaseDoneFragment.getFragmentTag())
                .addToBackStack(PurchaseDoneFragment.getFragmentTag())
                .commit();
        getSupportActionBar().setTitle("Purchase done");
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
        DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
