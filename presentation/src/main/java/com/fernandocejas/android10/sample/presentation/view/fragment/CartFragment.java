package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.CartAndCheckoutView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartFragment extends BaseFragment {


    @BindView(R.id.cart_recyclerview)
    RecyclerView cartRecyclerview;
    @BindView(R.id.total_textview)
    TextView totalTextview;
    @BindView(R.id.cart_error_textview)
    TextView cartErrorTextview;
    @BindView(R.id.proceed_to_checkout_button)
    Button proceedToCheckoutButton;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.proceed_to_checkout_button)
    void proceedToCheckout() {
        ((CartAndCheckoutView) getActivity()).navigateToCheckout();
    }
}
