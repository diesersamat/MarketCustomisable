package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.animation.Animator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerProductCategoryFragmentComponent;
import com.fernandocejas.android10.sample.presentation.model.CategoryModel;
import com.fernandocejas.android10.sample.presentation.model.ProductWrapperModel;
import com.fernandocejas.android10.sample.presentation.view.CircleAnimationUtil;
import com.fernandocejas.android10.sample.presentation.view.ProductCategoryView;
import com.fernandocejas.android10.sample.presentation.view.activity.ShopActivity;
import com.fernandocejas.android10.sample.presentation.view.adapter.ProductListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class ProductCategoryFragment extends BaseFragment implements ProductCategoryView {
    private static final String CATEGORY_MODEL = "CATEGORY_MODEL";
    private static final int COLUMN_COUNT = 2;

    @BindView(R.id.products_list)
    RecyclerView productsList;
    @BindView(R.id.error_view)
    View errorView;
    @BindView(R.id.progress)
    ProgressBar progress;

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
            public void onItemClicked(ProductWrapperModel categoryModel) {
                openProductDescription(categoryModel);
            }

            @Override
            public void onCartClicked(View addToCart, ProductWrapperModel productDescriptionModel) {
                String url = null;
                if (productDescriptionModel.getImages() != null) {
                    if (!productDescriptionModel.getImages().isEmpty()) {
                        if (productDescriptionModel.getImages().get(0) != null) {
                            url = productDescriptionModel.getImages().get(0).getUrl();
                        }
                    }
                }

                interactor.addProductToCart(productDescriptionModel.getProduct(), url);
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
        progress.getIndeterminateDrawable().setColorFilter(getPrimaryColor(), PorterDuff.Mode.SRC_IN);

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
        progress.setVisibility(View.VISIBLE);
        productsList.setVisibility(View.GONE);
        getInteractor().getCategoryListOfProducts(categoryModel.getId())
                .subscribe(new Observer<List<ProductWrapperModel>>() {
                    @Override
                    public void onNext(List<ProductWrapperModel> shopModel) {
                        setProductList(shopModel);
                        progress.setVisibility(View.GONE);
                        productsList.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        progress.setVisibility(View.GONE);
                        showError();
                    }
                });

    }

    private void showError() {
        productsList.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    private void setProductList(List<ProductWrapperModel> productList) {
        productListAdapter.setList(productList);
    }

    private void openProductDescription(ProductWrapperModel productDescriptionModel) {
        navigator.navigateToProductDescription(getContext(), productDescriptionModel.getProduct().getId());
    }

    @OnClick(R.id.try_again_button)
    void onTryAgain() {
        productsList.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        load();
    }
}
