package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.OrderModel;
import com.fernandocejas.android10.sample.presentation.view.activity.PaymentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class PaymentFragment extends BaseFragment {

    @BindView(R.id.webview)
    WebView webView;
    private int orderId;
    private double orderTotal;

    public static PaymentFragment newInstance(int orderId, double orderTotal) {
        PaymentFragment paymentFragment = new PaymentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PaymentActivity.ORDER_ID, orderId);
        bundle.putDouble(PaymentActivity.ORDER_TOTAL, orderTotal);
        paymentFragment.setArguments(bundle);
        return paymentFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        orderId = getArguments().getInt(PaymentActivity.ORDER_ID, Integer.MIN_VALUE);
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(R.string.payment);
        orderTotal = getArguments().getDouble(PaymentActivity.ORDER_ID, Double.MIN_VALUE);
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(R.string.payment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, view);

        //todo sort things out
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.contains("//sdfskfldk.ru")) {
                    paymentSuccess();
                    getActivity().finish();
                }
                if (url.contains("/error.xml?")) {
                    navigator.navigateToOrderFinish(getContext(), false);
                    getActivity().finish();
                }
            }
        });

        String accNumber = interactor.getShopInfoSync().getYandexMoneyAccount();
        String productName = "Order #" + orderId;
        webView.loadUrl("https://money.yandex.ru/quickpay/button-widget?" +
                "account=" + accNumber +
                "&quickpay=small" +
                "&any-card-payment-type=on" +
                "&button-text=02" +
                "&button-size=l" +
                "&button-color=green" +
                "&targets=" + productName +
                "&default-sum=" + orderTotal +
                "&successURL=sdfskfldk.ru");

        return view;
    }

    @OnClick({R.id.debug_skip_payment_success, R.id.debug_skip_payment_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.debug_skip_payment_success:
                paymentSuccess();
                break;
            case R.id.debug_skip_payment_error:
                navigator.navigateToOrderFinish(getContext(), false);
                getActivity().finish();
                break;
        }
    }

    private void paymentSuccess() {
        interactor.postOrderStatusPaid(orderId).subscribe(new Subscriber<OrderModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onErrorView();
            }

            @Override
            public void onNext(OrderModel orderModel) {
                navigator.navigateToOrderFinish(getContext(), true);
                getActivity().finish();
            }
        });
    }

    private void onErrorView() {
        navigator.navigateToOrderFinish(getContext(), false);
    }
}
