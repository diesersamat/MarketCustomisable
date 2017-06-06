package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.ContactDetailModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.fernandocejas.android10.sample.presentation.view.activity.DefaultContactDetailsSelectionActivity.DEF_CONTACT;

public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.address)
    EditText address;
    //    @BindView(R.id.city)
//    EditText city;
//    @BindView(R.id.postal_code)
//    EditText postalCode;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.add_address_button)
    Button addAddress;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, AddAddressActivity.class);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_address_title);
        toolbar.setBackgroundColor(getAccentColor());
        toolbar.setTitleTextColor(getTextColor());
        progress.getIndeterminateDrawable().setColorFilter(getPrimaryColor(), PorterDuff.Mode.SRC_IN);

    }


    @OnClick(R.id.add_address_button)
    void onPlaceOrderClick() {
        progress.setVisibility(View.VISIBLE);
        addAddress.setVisibility(View.GONE);
        interactor.postContactDetail(phoneNumber.getText().toString(),
                address.getText().toString()).subscribe(new Subscriber<ContactDetailModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onErrorView();
            }

            @Override
            public void onNext(ContactDetailModel orderModel) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(DEF_CONTACT, orderModel.getId());
                editor.apply();
                finish();
            }
        });
    }

    void onErrorView() {
        progress.setVisibility(View.GONE);
        addAddress.setVisibility(View.VISIBLE);
    }
}
