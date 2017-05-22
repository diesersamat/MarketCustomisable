package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerProductComponent;
import com.fernandocejas.android10.sample.presentation.model.ProductModel;
import com.fernandocejas.android10.sample.presentation.presenter.ProductPresenter;
import com.fernandocejas.android10.sample.presentation.view.ProductView;
import com.fernandocejas.android10.sample.presentation.view.activity.ProductActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductFragment extends BaseFragment implements ProductView {

    @BindView(R.id.add_to_wishlist)
    Button addToWishlist;
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

    @Inject
    ProductPresenter presenter;


    @BindView(R.id.background)
    View background;
    @BindView(R.id.title_card)
    CardView titleCard;
    @BindView(R.id.desc_card)
    CardView descCard;

    public static ProductFragment newInstance(int productId) {
        ProductFragment productFragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ProductActivity.PRODUCT_ID, productId);
        productFragment.setArguments(bundle);
        return productFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.product_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_shopping_cart) {
            navigator.navigateToCart(getContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        addToWishlist.getBackground().setColorFilter(getAccentColor(), PorterDuff.Mode.MULTIPLY);
        addToCart.getBackground().setColorFilter(getAccentColor(), PorterDuff.Mode.MULTIPLY);
        priceButton.getBackground().setColorFilter(getPrimaryColor(), PorterDuff.Mode.MULTIPLY);
        toolbar.setBackgroundColor(getAccentColor());

        title.setTextColor(getTextColor());
        priceButton.setTextColor(getTextColor());
        description.setTextColor(getTextColor());
        addToCart.setTextColor(getTextColor());
        addToWishlist.setTextColor(getTextColor());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
        titleCard.setCardBackgroundColor(getAccentColor());
        descCard.setCardBackgroundColor(getAccentColor());
    }

    @Override
    public void onLoaded(ProductModel productInfo) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(productInfo.getName());
//        Glide.with(this).load(productInfo.getPhotos()).into(productImage);
        Glide.with(this).load("https://unsplash.it/200/").into(productImage);
        title.setText(productInfo.getName());
        priceButton.setText(String.format("%s%s", productInfo.getPrice(), productInfo.getCurrency()));
        description.setText(productInfo.getDescription());
    }

    @Override
    public void onError() {
        //// TODO: 19/05/2017  
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

}
