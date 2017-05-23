package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.OrderModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

public class OrdersActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.orders_list)
    RecyclerView ordersList;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, OrdersActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.all_orders);
        toolbar.setBackgroundColor(getAccentColor());
        toolbar.setTitleTextColor(getTextColor());
        ordersList.setBackgroundColor(getBackgroundColor());
    }

    @Override
    protected void onResume() {
        super.onResume();
        interactor.getAllOrders().subscribe(new Observer<List<OrderModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                showError();
            }

            @Override
            public void onNext(List<OrderModel> orderModels) {
                showOrdersList(orderModels);
            }

        });
    }

    private void showError() {
        //// TODO: 23/05/2017
    }

    private void showOrdersList(List<OrderModel> orderModels) {
        if (orderModels == null || orderModels.isEmpty()) {
            showEmpty();
        } else {

        }
    }

    private void showEmpty() {
        //// TODO: 23/05/2017
    }
}
