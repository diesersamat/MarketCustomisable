package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerShopActivityComponent;
import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.model.ShopModel;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.presenter.ShopPresenter;
import com.fernandocejas.android10.sample.presentation.view.ShopActivityView;
import com.fernandocejas.android10.sample.presentation.view.adapter.NavDrawerListAdapter;
import com.fernandocejas.android10.sample.presentation.view.fragment.ProductCategoryFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopActivity extends BaseActivity implements ShopActivityView {

    @BindView(R.id.counter)
    public TextView counter;
    @BindView(R.id.container)
    FrameLayout content;
    @BindView(R.id.nav_view)
    NavigationView leftDrawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_list)
    RecyclerView navList;
    @BindView(R.id.shop_title)
    TextView title;
    @BindView(R.id.title)
    TextView titleToolbar;
    @BindView(R.id.title_bcg)
    View titleBcg;
    @BindView(R.id.error_view)
    View errorView;
    @BindView(R.id.sign_in_button)
    Button signInButton;
    @BindView(R.id.sign_out_button)
    Button signOutButton;
    @BindView(R.id.logged_in_as)
    TextView loggedInAs;
    @BindView(R.id.progress)
    ProgressBar progress;
    @Inject
    NavDrawerListAdapter navDrawerListAdapter;
    @Inject
    ShopPresenter presenter;

    @BindView(R.id.action_search)
    ImageView actionSearch;
    @BindView(R.id.action_shopping_cart)
    ImageView actionShoppingCart;
    @BindView(R.id.action_orders)
    ImageView actionWishList;

    @Override
    public void onLoaded(ShopModel shopModel) {
        setCategoriesListToNavList(shopModel.getCategoryModels());
        switchFragment(shopModel.getCategoryModels().get(0));
        navDrawerListAdapter.setSelectedCategoryId(shopModel.getCategoryModels().get(0).getId());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ACCENT_COLOR, shopModel.getAccentColor());
        editor.putString(PRIMARY_COLOR, shopModel.getPrimaryColor());
        //// TODO: 06/06/2017
//        editor.putString(IS_DARK_ICONS, shopModel.getPrimaryColor());
//        editor.putString(BACKGROUND_COLOR, shopModel.getBackgroundColor());
//        editor.putString(TEXT_COLOR, shopModel.getPrimaryColor());
        editor.apply();

        title.setText(shopModel.getName());
        title.setTextColor(getTextColor());
        loggedInAs.setTextColor(getTextColor());
        titleBcg.setBackgroundColor(getPrimaryColor());
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        errorView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateCartNumber() {
        counter.setText(Integer.toString(interactor.getItemsNumberFromCart()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartNumber();
        updateUserInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleToolbar.setText("");

        navList.setLayoutManager(new LinearLayoutManager(this));
        navList.setAdapter(navDrawerListAdapter);
        navDrawerListAdapter.setOnItemClickListener(this::switchFragment);
        presenter.resume();
        progress.setVisibility(View.VISIBLE);
        content.setBackgroundColor(getBackgroundColor());
        toolbar.setBackgroundColor(getAccentColor());
        signInButton.getBackground().setColorFilter(getAccentColor(), PorterDuff.Mode.MULTIPLY);
        signOutButton.getBackground().setColorFilter(getAccentColor(), PorterDuff.Mode.MULTIPLY);
        signOutButton.setTextColor(getTextColor());
        signInButton.setTextColor(getTextColor());
        titleToolbar.setTextColor(getTextColor());
        counter.setTextColor(getTextColor());
        progress.getIndeterminateDrawable().setColorFilter(getPrimaryColor(), PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void additionalCreateOperations() {
        DaggerShopActivityComponent
                .builder()
                .view(this)
                .accentColor(getAccentColor())
                .backgroundColor(getBackgroundColor())
                .textColor(getTextColor())
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    private void updateUserInfo() {
        UserModel userInfoSync = interactor.getUserInfoSync();
        if (userInfoSync != null) {
            signInButton.setVisibility(View.GONE);
            loggedInAs.setVisibility(View.VISIBLE);
            loggedInAs.setText(getString(R.string.logged_in_as,
                    userInfoSync.getName(), userInfoSync.getSurame()));
            signOutButton.setVisibility(View.VISIBLE);
        } else {
            signInButton.setVisibility(View.VISIBLE);
            loggedInAs.setVisibility(View.GONE);
            signOutButton.setVisibility(View.GONE);
        }
    }

    private void switchFragment(CategoryModel categoryModel) {
        titleToolbar.setText(categoryModel.getName());
        String tag = String.valueOf(categoryModel.getId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ProductCategoryFragment.newInstance(categoryModel), tag)
                .addToBackStack(tag)
                .commit();
        drawerLayout.closeDrawers();
    }

    private void setCategoriesListToNavList(List<CategoryModel> categoryModels) {
        navDrawerListAdapter.setList(categoryModels);
    }

    @OnClick(R.id.cart_drawer)
    void cartDrawer() {
        navigator.navigateToCart(this);
        drawerLayout.closeDrawers();
    }

    @OnClick(R.id.all_orders_drawer)
    void allOrdersDrawer() {
        navigator.navigateToOrders(this, interactor.getShopInfoSync().isPaymentEnabled());
        drawerLayout.closeDrawers();
    }

    @OnClick(R.id.website_drawer)
    void websiteDrawer() {
        navigator.navigateToWebsite(this);
        drawerLayout.closeDrawers();
    }

    @OnClick(R.id.all_contact_data)
    void allContactData() {
        navigator.navigateToAddAddress(this);
        drawerLayout.closeDrawers();
    }

    @OnClick(R.id.action_search)
    void OnActionSearch() {
        navigator.navigateToSearchProducts(this);
    }

    @OnClick(R.id.action_shopping_cart)
    void OnActionShoppingCart() {
        navigator.navigateToCart(this);
    }

    @OnClick(R.id.action_orders)
    void OnActionWishList() {
        navigator.navigateToOrders(this, interactor.getShopInfoSync().isPaymentEnabled());
    }

    @OnClick(R.id.sign_out_button)
    void onSignOutClick() {
        interactor.signOut();
        updateUserInfo();
    }

    @OnClick(R.id.try_again_button)
    void onTryAgain() {
        errorView.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        presenter.resume();
    }

    @OnClick(R.id.sign_in_button)
    void onSignInRegister() {
        navigator.navigateToLogin(this);
    }
}
