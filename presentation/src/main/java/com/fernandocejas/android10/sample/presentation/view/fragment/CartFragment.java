package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerCartFragmentComponent;
import com.fernandocejas.android10.sample.presentation.model.CartItemModel;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.view.CartAndCheckoutView;
import com.fernandocejas.android10.sample.presentation.view.CartFragmentView;
import com.fernandocejas.android10.sample.presentation.view.adapter.CartListAdapter;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class CartFragment extends BaseFragment implements CartFragmentView {

    @Inject
    CartListAdapter productListAdapter;

    @BindView(R.id.cart_recyclerview)
    RecyclerView cartRecyclerview;
    @BindView(R.id.bcg)
    View background;
    @BindView(R.id.total_textview)
    TextView totalTextView;
    @BindView(R.id.total_sum)
    TextView totalSum;
    @BindView(R.id.final_view)
    View finalView;
    @BindView(R.id.proceed_to_checkout_button)
    Button proceedToCheckoutButton;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        cartRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerview.setAdapter(productListAdapter);
        productListAdapter.setOnItemClickListener(new CartListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(CartItemModel categoryModel) {
                openProductDescription(categoryModel);
            }

            @Override
            public void onItemAddClick(CartItemModel productDescriptionModel) {
                getInteractor().addProductToCart(productDescriptionModel);
                updateList();
            }

            @Override
            public void onItemRemoveClick(CartItemModel productDescriptionModel) {
                getInteractor().removeProductFromCart(productDescriptionModel);
                updateList();
            }
        });

        background.setBackgroundColor(getBackgroundColor());
        finalView.setBackgroundColor(getAccentColor());
        proceedToCheckoutButton.setBackgroundColor(getPrimaryColor());
        proceedToCheckoutButton.setTextColor(getTextColor());
        totalTextView.setTextColor(getTextColor());
        totalSum.setTextColor(getTextColor());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(R.string.your_cart);
        updateList();
    }

    @Override
    protected void additionalCreateOperations() {
        DaggerCartFragmentComponent
                .builder()
                .view(this)
                .accentColor(getAccentColor())
                .primaryColor(getPrimaryColor())
                .textColor(getTextColor())
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    private void updateList() {
        getInteractor().getItemsFromCart()
                .defaultIfEmpty(Collections.emptyList())
                .subscribe(new Observer<List<CartItemModel>>() {
                    @Override
                    public void onNext(List<CartItemModel> shopModel) {
                        setProductList(shopModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onErrorShow();
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    private void onErrorShow() {
        //// TODO: 23/05/2017
    }

    private void setProductList(List<CartItemModel> productList) {
        if (productList.isEmpty()) {
            showEmpty();
            proceedToCheckoutButton.setVisibility(View.INVISIBLE);
        } else {
            proceedToCheckoutButton.setVisibility(View.VISIBLE);
        }

        int summ = 0;
        for (CartItemModel itemModel : productList) {
            summ += (itemModel.getPrice() * itemModel.getCount());
        }

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(Currency.getInstance("RUB"));
        String result = format.format(summ);
        totalSum.setText(result);
        productListAdapter.setList(productList);
    }

    private void showEmpty() {
        //// TODO: 23/05/2017
    }

    private void openProductDescription(CartItemModel productDescriptionModel) {
        navigator.navigateToProductDescription(getContext(), productDescriptionModel.getId());
    }

    @OnClick(R.id.proceed_to_checkout_button)
    void proceedToCheckout() {
        UserModel userInfoSync = getInteractor().getUserInfoSync();
        if (userInfoSync == null) {
            navigator.navigateToLogin(getContext());
        } else {
            ((CartAndCheckoutView) getActivity()).navigateToCheckout();
        }
    }
}
