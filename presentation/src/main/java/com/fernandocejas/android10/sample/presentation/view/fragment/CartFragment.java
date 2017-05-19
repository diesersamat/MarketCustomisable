package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
    TextView totalTextview;
    @BindView(R.id.total_cardview)
    CardView totalCardView;
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
        productList.add(new ProductDescriptionModel("Brand 1", "https://www.iphones.ru/wp-content/uploads/2017/05/%D1%8B%D1%80%D1%89%D0%BA%D0%B5123-200x150.jpg", 0, 1213, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 2", "https://www.iphones.ru/wp-content/uploads/2017/05/201293_t7PkulYGZf_chet-200x132.jpg", 1, 113, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_1321.jpg", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_1321.jpg", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 4", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_2734-760x426.jpg", 3, 143.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 5", "https://www.iphones.ru/wp-content/uploads/2017/05/Hotmob-LINE-e1411960477590-200x127.jpg", 4, 121.3, "HUF"));
        setProductList(productList);
        //// TODO: 02/05/2017 демо значения

        background.setBackgroundColor(getAccentColor());
        totalCardView.setCardBackgroundColor(getPrimaryColor());
        proceedToCheckoutButton.setBackgroundColor(getPrimaryColor());
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
