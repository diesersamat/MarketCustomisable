package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerDefaultContactComponent;
import com.fernandocejas.android10.sample.presentation.model.ContactDetailModel;
import com.fernandocejas.android10.sample.presentation.view.adapter.AddressListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class DefaultContactDetailsSelectionActivity extends BaseActivity {

    public static final String DEF_CONTACT = "DEF_CONTACT";


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.address_list)
    RecyclerView addressList;
    @BindView(R.id.error_view)
    View errorView;
    @BindView(R.id.empty_view)
    View emptyView;
    @BindView(R.id.new_address_button)
    Button newAddress;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.whole_layout)
    View wholeLayout;
    @BindView(R.id.bcg)
    View bcg;

    @Inject
    AddressListAdapter adapter;


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, DefaultContactDetailsSelectionActivity.class);
    }

    @Override
    protected void additionalCreateOperations() {
        DaggerDefaultContactComponent
                .builder()
                .view(this)
                .accentColor(getAccentColor())
                .primaryColor(getPrimaryColor())
                .textColor(getTextColor())
                .backgroundColor(getBackgroundColor())
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_contact_details_selection);
        ButterKnife.bind(this);
        addressList.setLayoutManager(new LinearLayoutManager(this));
        addressList.setAdapter(adapter);
        adapter.setOnItemClickListener(id -> {
            setDefAddress(id);
            startLoading();
            finish();
        });
        bcg.setBackgroundColor(getBackgroundColor());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.def_address_title);
        toolbar.setBackgroundColor(getAccentColor());
        toolbar.setTitleTextColor(getTextColor());
        progress.getIndeterminateDrawable().setColorFilter(getPrimaryColor(), PorterDuff.Mode.SRC_IN);
        newAddress.getBackground().setColorFilter(getAccentColor(), PorterDuff.Mode.MULTIPLY);
        newAddress.setTextColor(getTextColor());
        addressList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLoading();
    }

    private void setDefAddress(int id) {
        SharedPreferences preferences = getDefaultSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(DEF_CONTACT, id);
        editor.apply();
    }

    private SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void startLoading() {
        wholeLayout.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        interactor.getAllContactDetails(getDefaultSharedPreferences().getInt(DEF_CONTACT, Integer.MAX_VALUE))
                .subscribe(new Observer<List<ContactDetailModel>>() {
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
                    public void onNext(List<ContactDetailModel> orderModels) {
                        showAddressList(orderModels);
                    }

                });
    }

    private void showAddressList(List<ContactDetailModel> addressModels) {
        wholeLayout.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        if (addressModels == null || addressModels.isEmpty()) {
            showEmpty();
        } else {
            adapter.setList(addressModels);
        }
    }

    private void showError() {
        wholeLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    private void showEmpty() {
        errorView.setVisibility(View.GONE);
        wholeLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.try_again_button)
    void onTryAgain() {
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        startLoading();
    }

    @OnClick(R.id.add_address_button)
    void addAddressClick() {
        navigator.navigateToAddAddress(this);
    }

    @OnClick(R.id.new_address_button)
    void addAddressClick2() {
        navigator.navigateToAddAddress(this);
    }
}
