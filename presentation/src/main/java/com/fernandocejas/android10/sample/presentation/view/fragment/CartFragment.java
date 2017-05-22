package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerCartFragmentComponent;
import com.fernandocejas.android10.sample.presentation.model.ProductDescriptionModel;
import com.fernandocejas.android10.sample.presentation.view.CartAndCheckoutView;
import com.fernandocejas.android10.sample.presentation.view.CartFragmentView;
import com.fernandocejas.android10.sample.presentation.view.adapter.CartListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartFragment extends BaseFragment implements CartFragmentView {

    @Inject
    CartListAdapter productListAdapter;

    @BindView(R.id.cart_recyclerview)
    RecyclerView cartRecyclerview;
    @BindView(R.id.bcg)
    View background;
    @BindView(R.id.total_textview)
    TextView totalTextView;
    @BindView(R.id.total_sum)
    TextView totalSum;
    @BindView(R.id.final_view)
    View finalView;
    @BindView(R.id.proceed_to_checkout_button)
    Button proceedToCheckoutButton;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        cartRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerview.setAdapter(productListAdapter);
        productListAdapter.setOnItemClickListener(this::openProductDescription);

        //// TODO: 02/05/2017 демо значения
        List<ProductDescriptionModel> productList = new ArrayList<>();
        productList.add(new ProductDescriptionModel("Brand 1", "http://lorempixel.com/400/400/", 0, 1213, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 2", "http://lorempixel.com/400/400/", 1, 113, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "http://lorempixel.com/400/400/", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "http://lorempixel.com/400/400/", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 4", "http://lorempixel.com/400/400/", 3, 143.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 5", "http://lorempixel.com/400/400/", 4, 121.3, "HUF"));
        setProductList(productList);
        //// TODO: 02/05/2017 демо значения

        background.setBackgroundColor(getBackgroundColor());
        finalView.setBackgroundColor(getAccentColor());
        proceedToCheckoutButton.setBackgroundColor(getPrimaryColor());
        proceedToCheckoutButton.setTextColor(getTextColor());
        totalTextView.setTextColor(getTextColor());
        totalSum.setTextColor(getTextColor());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(R.string.your_cart);
    }

    @Override
    protected void additionalCreateOperations() {
        DaggerCartFragmentComponent
                .builder()
                .view(this)
                .accentColor(getAccentColor())
                .primaryColor(getPrimaryColor())
                .textColor(getTextColor())
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    private void setProductList(List<ProductDescriptionModel> productList) {
        productListAdapter.setList(productList);
    }

    private void openProductDescription(ProductDescriptionModel productDescriptionModel) {
        navigator.navigateToProductDescription(getContext(), productDescriptionModel.getId());
    }

    @OnClick(R.id.proceed_to_checkout_button)
    void proceedToCheckout() {
        ((CartAndCheckoutView) getActivity()).navigateToCheckout();
    }
}
