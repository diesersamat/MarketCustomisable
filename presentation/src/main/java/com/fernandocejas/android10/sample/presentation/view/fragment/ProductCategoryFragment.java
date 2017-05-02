package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.fernandocejas.android10.sample.presentation.model.CategoryModel;

public class ProductCategoryFragment extends BaseFragment {
    private final static String CATEGORY_MODEL = "CATEGORY_MODEL";

    public static Fragment newInstance(CategoryModel categoryModel) {
        ProductCategoryFragment categoryFragment = new ProductCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CATEGORY_MODEL, categoryModel);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }
}
