
package com.fernandocejas.android10.sample.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.fernandocejas.android10.sample.presentation.view.activity.CartAndCheckoutActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.LoginActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.ProductActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.ShopActivity;

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
        // TODO: 14/04/2017
    }

    public void navigateToLogin(Context context) {
        if (context != null) {
            Intent intentToLaunch = LoginActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}
