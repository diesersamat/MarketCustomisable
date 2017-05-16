package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.fragment.ProductFragment;

public class ProductActivity extends BaseActivity {

    public final static String PRODUCT_ID = "PRODUCT_ID";

    public static Intent getCallingIntent(Context context, int productId) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra(PRODUCT_ID, productId);
        return intent;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        int productId = getIntent().getIntExtra(PRODUCT_ID, Integer.MIN_VALUE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ProductFragment.newInstance(productId)).commit();
    }
}
