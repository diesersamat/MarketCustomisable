package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.activity.CartAndCheckoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentFragment extends BaseFragment {

    @BindView(R.id.webview)
    WebView webView;

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
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
                    ((CartAndCheckoutActivity) getActivity()).navigateToPurchaseDone();
                }
                if (url.contains("/error.xml?")) {
                    ((CartAndCheckoutActivity) getActivity()).navigateToCheckout();
                }
            }
        });

        String accNumber = "410011354575504";
        String productName = "%D0%9D%D0%B0%D0%B7%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5+%D1%82%D0%BE%D0%B2%D0%B0%D1%80%D0%B0";
        int sum = 1;
        webView.loadUrl("https://money.yandex.ru/quickpay/button-widget?" +
                "account=" + accNumber +
                "&quickpay=small" +
                "&any-card-payment-type=on" +
                "&button-text=02" +
                "&button-size=l" +
                "&button-color=green" +
                "&targets=" + productName +
                "&default-sum=" + sum +
                "&successURL=sdfskfldk.ru");

        return view;
    }
}
