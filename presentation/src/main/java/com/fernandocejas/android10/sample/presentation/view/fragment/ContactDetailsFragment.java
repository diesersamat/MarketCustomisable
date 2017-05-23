package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.activity.CartAndCheckoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactDetailsFragment extends BaseFragment {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.postal_code)
    EditText postalCode;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.cart_error_textview)
    TextView cartErrorTextView;
    @BindView(R.id.place_order)
    Button placeOrder;

    public static ContactDetailsFragment newInstance() {
        return new ContactDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(R.string.contact_details);
        placeOrder.setBackgroundColor(getPrimaryColor());
    }

    @OnClick(R.id.place_order)
    void onPlaceOrderClick() {
        ((CartAndCheckoutActivity) getActivity()).navigateToPayment();
    }

}
