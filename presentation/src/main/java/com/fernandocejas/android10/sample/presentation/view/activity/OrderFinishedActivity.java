package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.fragment.PurchaseDoneFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.PurchaseErrorFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFinishedActivity extends BaseActivity {

    public static final String IS_SUCCESS = "IS_SUCCESS";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getCallingIntent(Context context, boolean isSuccess) {
        Intent intent = new Intent(context, OrderFinishedActivity.class);
        intent.putExtra(IS_SUCCESS, isSuccess);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_finished);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(getAccentColor());
        toolbar.setTitleTextColor(getTextColor());

        boolean isSuccess = getIntent().getBooleanExtra(IS_SUCCESS, false);
        if (isSuccess) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PurchaseDoneFragment.newInstance()).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PurchaseErrorFragment.newInstance()).commit();
        }
    }
}
