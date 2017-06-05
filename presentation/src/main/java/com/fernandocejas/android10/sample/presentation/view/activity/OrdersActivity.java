package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

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
import butterknife.OnClick;
import rx.Observer;

public class OrdersActivity extends BaseActivity {

    public static final String IS_PAYMENT_ENABLED = "IS_PAYMENT_ENABLED";


    @Inject
    OrderListAdapter adapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.orders_list)
    RecyclerView ordersList;
    @BindView(R.id.error_view)
    View errorView;
    @BindView(R.id.empty_view)
    View emptyView;
    @BindView(R.id.progress)
    ProgressBar progress;

    public static Intent getCallingIntent(Context context, boolean isPaymentEnabled) {
        Intent intent = new Intent(context, OrdersActivity.class);
        intent.putExtra(IS_PAYMENT_ENABLED, isPaymentEnabled);
        return intent;
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
            public void onPayClick(int categoryId, double totalPrice) {
                openPay(categoryId, totalPrice);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.all_orders);
        toolbar.setBackgroundColor(getAccentColor());
        toolbar.setTitleTextColor(getTextColor());
        ordersList.setBackgroundColor(getBackgroundColor());
        progress.getIndeterminateDrawable().setColorFilter(getPrimaryColor(), PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void additionalCreateOperations() {
        DaggerOrdersActivityComponent
                .builder()
                .view(this)
                .accentColor(getAccentColor())
                .primaryColor(getPrimaryColor())
                .textColor(getTextColor())
                .isPaymentAvailable(getIntent().getBooleanExtra(IS_PAYMENT_ENABLED, false))
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLoading();
    }

    private void startLoading() {
        progress.setVisibility(View.VISIBLE);
        interactor.getAllOrders().subscribe(new Observer<List<OrderModel>>() {
            @Override
            public void onCompleted() {
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                progress.setVisibility(View.GONE);
                showError();
            }

            @Override
            public void onNext(List<OrderModel> orderModels) {
                showOrdersList(orderModels);
            }

        });
    }

    private void openPay(int orderId, double totalPrice) {
        navigator.navigateToPay(this, orderId, totalPrice);
    }

    private void openDialog(List<OrderItemModel> models) {
        ItemsDialog itemsDialog = new ItemsDialog(this, models);
        itemsDialog.show();
    }

    private void showError() {
        ordersList.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    private void showOrdersList(List<OrderModel> orderModels) {
        ordersList.setVisibility(View.VISIBLE);
        if (orderModels == null || orderModels.isEmpty()) {
            showEmpty();
        } else {
            adapter.setList(orderModels);
        }
    }


    private void showEmpty() {
        errorView.setVisibility(View.GONE);
        ordersList.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.try_again_button)
    void onTryAgain() {
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        startLoading();
    }
}
