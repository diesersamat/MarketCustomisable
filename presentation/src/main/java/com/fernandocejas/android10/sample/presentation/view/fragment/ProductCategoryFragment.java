package com.fernandocejas.android10.sample.presentation.view.fragment;

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
import com.fernandocejas.android10.sample.presentation.model.ShopModel;
import com.fernandocejas.android10.sample.presentation.view.ProductCategoryView;
import com.fernandocejas.android10.sample.presentation.view.adapter.ProductListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
            public void onItemClicked(ProductDescriptionModel productDescriptionModel) {
                openProductDescription(productDescriptionModel);
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
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    private void load() {
        getInteractor().getCategoryListOfProducts(categoryModel.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

                    }
                });

    }

    private void onLoaded(ShopModel shopModel) {

    }

    private void setProductList(List<ProductDescriptionModel> productList) {
        productListAdapter.setList(productList);
    }

    private void openProductDescription(ProductDescriptionModel productDescriptionModel) {
        navigator.navigateToProductDescription(getContext(), productDescriptionModel.getId());
    }
}
