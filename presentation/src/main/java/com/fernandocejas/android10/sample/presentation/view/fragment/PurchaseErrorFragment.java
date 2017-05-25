package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PurchaseErrorFragment extends BaseFragment {


    @BindView(R.id.webview)
    TextView webview;
    @BindView(R.id.all_orders)
    Button allOrders;
    @BindView(R.id.done)
    Button done;
    @BindView(R.id.all_view)
    LinearLayout allView;

    public static PurchaseErrorFragment newInstance() {
        return new PurchaseErrorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_error, container, false);
        ButterKnife.bind(this, view);
        allOrders.setTextColor(getTextColor());
        done.setTextColor(getTextColor());
        allView.setBackgroundColor(getBackgroundColor());
        webview.setTextColor(getTextColor());
        done.setBackgroundColor(getPrimaryColor());
        allOrders.setBackgroundColor(getPrimaryColor());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.purchase_error_title);
    }

    @OnClick(R.id.all_orders)
    void allOrderClick() {
        navigator.navigateToOrders(getContext());
    }

    @OnClick(R.id.done)
    void onDoneClick() {
        getActivity().onBackPressed();
    }
}
