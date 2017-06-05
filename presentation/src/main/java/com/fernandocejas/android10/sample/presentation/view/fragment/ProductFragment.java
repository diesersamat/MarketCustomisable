package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.animation.Animator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerProductComponent;
import com.fernandocejas.android10.sample.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.presenter.ProductPresenter;
import com.fernandocejas.android10.sample.presentation.view.CircleAnimationUtil;
import com.fernandocejas.android10.sample.presentation.view.ProductView;
import com.fernandocejas.android10.sample.presentation.view.activity.ProductActivity;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductFragment extends BaseFragment implements ProductView {

    @BindView(R.id.add_to_cart)
    Button addToCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.product_image)
    ImageView productImage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price_button)
    Button priceButton;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.error_view)
    View errorView;
    @BindView(R.id.data_view)
    View dataView;
    @Inject
    ProductPresenter presenter;
    @BindView(R.id.background)
    View background;
    @BindView(R.id.title_card)
    CardView titleCard;
    @BindView(R.id.desc_card)
    CardView descCard;
    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.action_shopping_cart)
    ImageView actionShoppingCart;
    @BindView(R.id.counter)
    TextView counter;
    @BindView(R.id.progress)
    ProgressBar progress;
    private ProductModel productInfo;

    public static ProductFragment newInstance(int productId) {
        ProductFragment productFragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ProductActivity.PRODUCT_ID, productId);
        productFragment.setArguments(bundle);
        return productFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        background.setBackgroundColor(getBackgroundColor());
        addToCart.getBackground().setColorFilter(getAccentColor(), PorterDuff.Mode.MULTIPLY);
        priceButton.getBackground().setColorFilter(getPrimaryColor(), PorterDuff.Mode.MULTIPLY);
        toolbar.setBackgroundColor(getAccentColor());
        progress.getIndeterminateDrawable().setColorFilter(getPrimaryColor(), PorterDuff.Mode.SRC_IN);
        title.setTextColor(getTextColor());
        priceButton.setTextColor(getTextColor());
        description.setTextColor(getTextColor());
        addToCart.setTextColor(getTextColor());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        progress.setVisibility(View.VISIBLE);
        presenter.resume();
        dataView.setVisibility(View.GONE);
        titleCard.setCardBackgroundColor(getAccentColor());
        descCard.setCardBackgroundColor(getAccentColor());
        counter.setText(Integer.toString(interactor.getItemsNumberFromCart()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void onLoaded(ProductModel productInfo) {
        this.productInfo = productInfo;
        titleToolbar.setText(productInfo.getName());
//        Glide.with(this).load(productInfo.getPhotos()).into(productImage);
        Glide.with(this).load("https://unsplash.it/200/").into(productImage);
        title.setText(productInfo.getName());
        progress.setVisibility(View.GONE);
        dataView.setVisibility(View.VISIBLE);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(Currency.getInstance(productInfo.getCurrency()));
        String result = format.format(productInfo.getPrice());
        priceButton.setText(result);
        counter.setTextColor(getTextColor());
        description.setText(productInfo.getDescription());
    }

    @Override
    public void onError() {
        dataView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    @Override
    protected void additionalCreateOperations() {
        DaggerProductComponent.builder()
                .view(this)
                .productId(getArguments().getInt(ProductActivity.PRODUCT_ID, Integer.MIN_VALUE))
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @OnClick(R.id.price_button)
    void priceClick() {
        interactor.addProductToCart(productInfo);
        new CircleAnimationUtil().attachActivity(getActivity())
                .setTargetView(priceButton)
                .setMoveDuration(500)
                .setDestView(counter)
                .setAnimationListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        counter.setText("" + interactor.getItemsNumberFromCart());
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).startAnimation();
    }

    @OnClick(R.id.action_shopping_cart)
    void shoppingCartClick() {
        navigator.navigateToCart(getContext());
    }

    @OnClick(R.id.add_to_cart)
    void addToCartClick() {
        interactor.addProductToCart(productInfo);
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
                        counter.setText(String.valueOf(interactor.getItemsNumberFromCart()));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).startAnimation();
    }

    @OnClick(R.id.try_again_button)
    void onTryAgain() {
        dataView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        presenter.resume();
    }

}
