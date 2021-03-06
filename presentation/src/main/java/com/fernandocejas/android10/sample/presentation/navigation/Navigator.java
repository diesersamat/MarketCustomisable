package com.fernandocejas.android10.sample.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import com.fernandocejas.android10.sample.presentation.view.activity.AddAddressActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.CartAndCheckoutActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.DefaultContactDetailsSelectionActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.LoginActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.OrderFinishedActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.OrdersActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.PaymentActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.ProductActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.SearchActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }

    public void navigateToCart(Context context) {
        if (context != null) {
            Intent intentToLaunch = CartAndCheckoutActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToProductDescription(Context context, int productId) {
        if (context != null) {
            Intent intentToLaunch = ProductActivity.getCallingIntent(context, productId);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToSearchProducts(Context context) {
        if (context != null) {
            Intent intentToLaunch = SearchActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToOrders(Context context, boolean isPaymentEnabled) {
        if (context != null) {
            Intent intentToLaunch = OrdersActivity.getCallingIntent(context, isPaymentEnabled);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToLogin(Context context) {
        if (context != null) {
            Intent intentToLaunch = LoginActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToWebsite(Context context) {
        //// TODO: 23/05/2017
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://webapplication120170408081622.azurewebsites.net/"));
        context.startActivity(browserIntent);
    }

    public void navigateToPay(Context context, int orderId, double orderTotal) {
        if (context != null) {
            Intent intentToLaunch = PaymentActivity.getCallingIntent(context, orderId, orderTotal);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToOrderFinish(Context context, boolean isSuccess) {
        if (context != null) {
            Intent intentToLaunch = OrderFinishedActivity.getCallingIntent(context, isSuccess);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToContactDetailsActivity(Context context) {
        if (context != null) {
            Intent intentToLaunch = DefaultContactDetailsSelectionActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToAddAddress(Context context) {
        if (context != null) {
            Intent intentToLaunch = AddAddressActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}
