package com.fernandocejas.android10.sample.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerShopActivityComponent;
import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.presenter.ShopPresenter;
import com.fernandocejas.android10.sample.presentation.view.ShopActivityView;
import com.fernandocejas.android10.sample.presentation.view.adapter.NavDrawerListAdapter;
import com.fernandocejas.android10.sample.presentation.view.fragment.ProductCategoryFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopActivity extends BaseActivity implements ShopActivityView {

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

    @Inject
    NavDrawerListAdapter navDrawerListAdapter;
    @Inject
    ShopPresenter presenter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_search:
                navigator.navigateToSearchProducts(this);
                return true;
            case R.id.action_shopping_cart:
                navigator.navigateToCart(this);
                return true;
            case R.id.action_wish_list:
                navigator.navigateToWishList(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        leftDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
        navList.setLayoutManager(new LinearLayoutManager(this));
        navList.setAdapter(navDrawerListAdapter);
        navDrawerListAdapter.setOnItemClickListener(new NavDrawerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(CategoryModel categoryModel) {
                switchFragment(categoryModel);
            }
        });

        //// TODO: 02/05/2017 демо значения
        List<CategoryModel> categoryModels = new ArrayList<>();
        categoryModels.add(new CategoryModel("Automobile", 2));
        categoryModels.add(new CategoryModel("Phones", 3));
        categoryModels.add(new CategoryModel("Display", 4));
        categoryModels.add(new CategoryModel("Mega", 5));
        setCategoriesListToNavList(categoryModels);
        switchFragment(categoryModels.get(0));
        navDrawerListAdapter.setSelectedCategoryId(categoryModels.get(0).getId());
        //// TODO: 02/05/2017 демо значения
    }

    @Override
    protected void additionalCreateOperations() {
        DaggerShopActivityComponent
                .builder()
                .view(this)
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    private void switchFragment(CategoryModel categoryModel) {
        toolbar.setTitle(categoryModel.getName());
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
}
