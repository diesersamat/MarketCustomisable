package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerProductCategoryFragmentComponent;
import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.model.ProductDescriptionModel;
import com.fernandocejas.android10.sample.presentation.view.CircleAnimationUtil;
import com.fernandocejas.android10.sample.presentation.view.ProductCategoryView;
import com.fernandocejas.android10.sample.presentation.view.activity.ShopActivity;
import com.fernandocejas.android10.sample.presentation.view.adapter.ProductListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

public class ProductCategoryFragment extends BaseFragment implements ProductCategoryView {
    private final static String CATEGORY_MODEL = "CATEGORY_MODEL";
    private final static int COLUMN_COUNT = 2;

    @BindView(R.id.products_list)
    RecyclerView productsList;

    @Inject
    ProductListAdapter productListAdapter;
    private CategoryModel categoryModel;

    public static Fragment newInstance(CategoryModel categoryModel) {
        ProductCategoryFragment categoryFragment = new ProductCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CATEGORY_MODEL, categoryModel);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_category, container, false);
        ButterKnife.bind(this, view);
        productsList.setLayoutManager(new GridLayoutManager(getContext(), COLUMN_COUNT));
        productsList.setAdapter(productListAdapter);
        productListAdapter.setOnItemClickListener(new ProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(ProductDescriptionModel categoryModel) {
                openProductDescription(categoryModel);
            }

            @Override
            public void onCartClicked(View addToCart, ProductDescriptionModel productDescriptionModel) {
                interactor.addProductToCart(productDescriptionModel);
                new CircleAnimationUtil().attachActivity(getActivity())
                        .setTargetView(addToCart)
                        .setMoveDuration(500)
                        .setDestView(((ShopActivity) getActivity()).counter)
                        .setAnimationListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ((ShopActivity) getActivity()).updateCartNumber();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).startAnimation();
            }
        });
        categoryModel = getArguments().getParcelable(CATEGORY_MODEL);

        load();
        return view;
    }

    @Override
    protected void additionalCreateOperations() {
        DaggerProductCategoryFragmentComponent
                .builder()
                .view(this)
                .accentColor(getAccentColor())
                .primaryColor(getPrimaryColor())
                .textColor(getTextColor())
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    private void load() {
        getInteractor().getCategoryListOfProducts(categoryModel.getId())
                .subscribe(new Observer<List<ProductDescriptionModel>>() {
                    @Override
                    public void onNext(List<ProductDescriptionModel> shopModel) {
                        setProductList(shopModel);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showError();
                    }
                });

    }

    private void showError() {
        //// TODO: 27/05/2017  
    }

    private void setProductList(List<ProductDescriptionModel> productList) {
        productListAdapter.setList(productList);
    }

    private void openProductDescription(ProductDescriptionModel productDescriptionModel) {
        navigator.navigateToProductDescription(getContext(), productDescriptionModel.getId());
    }
}
