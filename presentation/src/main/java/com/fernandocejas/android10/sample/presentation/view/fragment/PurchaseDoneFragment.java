package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernandocejas.android10.sample.presentation.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PurchaseDoneFragment extends BaseFragment {


    public static PurchaseDoneFragment newInstance() {
        return new PurchaseDoneFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_done, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(R.string.purchase_done);
    }

    @OnClick(R.id.all_orders)
    void allOrderClick() {
        navigator.navigateToOrders(getContext());
    }
}
