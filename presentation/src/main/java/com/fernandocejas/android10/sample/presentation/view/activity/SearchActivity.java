package com.fernandocejas.android10.sample.presentation.view.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerSearchActivityComponent;
import com.fernandocejas.android10.sample.presentation.model.ProductDescriptionModel;
import com.fernandocejas.android10.sample.presentation.view.CircleAnimationUtil;
import com.fernandocejas.android10.sample.presentation.view.adapter.ProductListAdapter;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class SearchActivity extends BaseActivity {
    private final static int COLUMN_COUNT = 2;


    @BindView(R.id.search_edit_text)
    EditText searchEditText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.products_list)
    RecyclerView productsList;
    @BindView(R.id.counter)
    TextView counter;

    @Inject
    ProductListAdapter productListAdapter;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.action_shopping_cart)
    ImageView actionShoppingCart;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @OnClick({R.id.delete, R.id.action_shopping_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete:
                searchEditText.setText("");
                break;
            case R.id.action_shopping_cart:
                navigator.navigateToCart(this);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartNumber();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productsList.setLayoutManager(new GridLayoutManager(this, COLUMN_COUNT));
        productsList.setAdapter(productListAdapter);
        toolbar.setBackgroundColor(getAccentColor());
        productListAdapter.setOnItemClickListener(new ProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(ProductDescriptionModel categoryModel) {
                openProductDescription(categoryModel);
            }

            @Override
            public void onCartClicked(View addToCart, ProductDescriptionModel productDescriptionModel) {
                interactor.addProductToCart(productDescriptionModel);
                new CircleAnimationUtil().attachActivity(getActivity())
                        .setTargetView(addToCart)
                        .setMoveDuration(500)
                        .setDestView(counter)
                        .setAnimationListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                updateCartNumber();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).startAnimation();
            }
        });

        RxTextView.textChanges(searchEditText)
                .filter(s -> s.length() > 2 || s.length() == 0)
                .debounce(500, TimeUnit.MILLISECONDS)
                .switchMap(charSequence -> interactor.searchForItems(charSequence.toString()))
                .subscribe(getObserver());
    }

    @Override
    protected void additionalCreateOperations() {
        DaggerSearchActivityComponent
                .builder()
                .view(this)
                .accentColor(getAccentColor())
                .primaryColor(getPrimaryColor())
                .textColor(getTextColor())
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    private Activity getActivity() {
        return this;
    }

    private void updateCartNumber() {
        counter.setText("" + interactor.getItemsNumberFromCart());
    }

    @NonNull
    private Observer<List<ProductDescriptionModel>> getObserver() {
        return new Observer<List<ProductDescriptionModel>>() {
            @Override
            public void onNext(List<ProductDescriptionModel> value) {
                setProductList(value);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                showError();
            }
        };
    }

    private void showError() {
        //// TODO: 27/05/2017
    }


    private void showEmpty() {
        //// TODO: 27/05/2017
    }

    private void setProductList(List<ProductDescriptionModel> productList) {
        productListAdapter.setList(productList);
    }

    private void openProductDescription(ProductDescriptionModel productDescriptionModel) {
        navigator.navigateToProductDescription(this, productDescriptionModel.getId());
    }
}