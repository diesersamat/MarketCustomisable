package com.fernandocejas.android10.sample.presentation.view;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerItemsDialogComponent;
import com.fernandocejas.android10.sample.presentation.model.OrderItemModel;
import com.fernandocejas.android10.sample.presentation.navigation.Navigator;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;
import com.fernandocejas.android10.sample.presentation.view.adapter.ItemsDialogAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsDialog extends Dialog {

    private final List<OrderItemModel> models;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.view)
    View view;
    @Inject
    ItemsDialogAdapter adapter;
    @Inject
    Navigator navigator;
    private Activity activity;

    public ItemsDialog(Activity activity, List<OrderItemModel> models) {
        super(activity);
        this.activity = activity;
        this.models = models;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.items_dialog);
        ButterKnife.bind(this);
        inject();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        view.setBackgroundColor(getActivity().getBackgroundColor());
        adapter.setOnItemClickListener(productId -> navigator.navigateToProductDescription(activity, productId));
    }

    private BaseActivity getActivity() {
        return (BaseActivity) activity;
    }

    private void inject() {
        DaggerItemsDialogComponent
                .builder()
                .view(this)
                .list(models)
                .accentColor(getActivity().getAccentColor())
                .primaryColor(getActivity().getPrimaryColor())
                .textColor(getActivity().getTextColor())
                .appComponent(getActivity().getApplicationComponent())
                .build()
                .inject(this);
    }

}