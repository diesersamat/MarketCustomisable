package com.fernandocejas.android10.sample.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.fernandocejas.android10.sample.presentation.view.activity.CartAndCheckoutActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.LoginActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.OrdersActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.ProductActivity;

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
        //// TODO: 14/04/2017
    }

    public void navigateToOrders(Context context) {
        if (context != null) {
            Intent intentToLaunch = OrdersActivity.getCallingIntent(context);
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

    public void navigateToPay(Context context, int orderId) {
        //// TODO: 24/05/2017  
    }
}
