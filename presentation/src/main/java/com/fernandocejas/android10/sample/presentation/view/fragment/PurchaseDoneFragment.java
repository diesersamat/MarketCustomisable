package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernandocejas.android10.sample.presentation.R;

import butterknife.ButterKnife;

public class PurchaseDoneFragment extends BaseFragment {

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_done, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
