package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerOrdersActivityComponent;
import com.fernandocejas.android10.sample.presentation.model.OrderItemModel;
import com.fernandocejas.android10.sample.presentation.model.OrderModel;
import com.fernandocejas.android10.sample.presentation.view.ItemsDialog;
import com.fernandocejas.android10.sample.presentation.view.adapter.OrderListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

public class OrdersActivity extends BaseActivity {

    @Inject
    OrderListAdapter adapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.orders_list)
    RecyclerView ordersList;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, OrdersActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
        ordersList.setAdapter(adapter);
        adapter.setOnItemClickListener(new OrderListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(List<OrderItemModel> models) {
                openDialog(models);
            }

            @Override
            public void onPayClick(int categoryId) {
                openPay(categoryId);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.all_orders);
        toolbar.setBackgroundColor(getAccentColor());
        toolbar.setTitleTextColor(getTextColor());
        ordersList.setBackgroundColor(getBackgroundColor());
    }

    @Override
    protected void additionalCreateOperations() {
        DaggerOrdersActivityComponent
                .builder()
                .view(this)
                .accentColor(getAccentColor())
                .primaryColor(getPrimaryColor())
                .textColor(getTextColor())
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
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

    private void openPay(int orderId) {
        navigator.navigateToPay(this, orderId);
    }

    private void openDialog(List<OrderItemModel> models) {
        ItemsDialog itemsDialog = new ItemsDialog(this, models);
        itemsDialog.show();
    }

    private void showError() {
        //// TODO: 23/05/2017
    }

    private void showOrdersList(List<OrderModel> orderModels) {
        if (orderModels == null || orderModels.isEmpty()) {
            showEmpty();
        } else {
            adapter.setList(orderModels);
        }
    }


    private void showEmpty() {
        //// TODO: 23/05/2017
    }
}
