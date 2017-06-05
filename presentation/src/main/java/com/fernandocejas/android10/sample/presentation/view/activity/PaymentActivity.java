package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.fragment.PaymentFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends BaseActivity {


    public static final String ORDER_ID = "ORDER_ID";
    public static final String ORDER_TOTAL = "ORDER_TOTAL";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getCallingIntent(Context context, int productId, double orderTotal) {
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(ORDER_ID, productId);
        intent.putExtra(ORDER_TOTAL, orderTotal);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(getAccentColor());

        int orderId = getIntent().getIntExtra(ORDER_ID, Integer.MIN_VALUE);
        double orderTotal = getIntent().getDoubleExtra(ORDER_TOTAL, Double.MIN_VALUE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PaymentFragment.newInstance(orderId, orderTotal)).commit();
    }
}
