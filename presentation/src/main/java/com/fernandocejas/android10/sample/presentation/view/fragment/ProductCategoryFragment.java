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
import com.fernandocejas.android10.sample.presentation.view.ProductCategoryView;
import com.fernandocejas.android10.sample.presentation.view.adapter.ProductListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductCategoryFragment extends BaseFragment implements ProductCategoryView {
    private final static String CATEGORY_MODEL = "CATEGORY_MODEL";
    private final static int COLUMN_COUNT = 2;

    @BindView(R.id.products_list)
    RecyclerView productsList;

    @Inject
    ProductListAdapter productListAdapter;

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

        //// TODO: 02/05/2017 демо значения
        List<ProductDescriptionModel> productList = new ArrayList<>();
        productList.add(new ProductDescriptionModel("Brand 1", "https://www.iphones.ru/wp-content/uploads/2017/05/%D1%8B%D1%80%D1%89%D0%BA%D0%B5123-200x150.jpg", 0, 1213, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 2", "https://www.iphones.ru/wp-content/uploads/2017/05/201293_t7PkulYGZf_chet-200x132.jpg", 1, 113, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_1321.jpg", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 4", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_2734-760x426.jpg", 3, 143.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 5", "https://www.iphones.ru/wp-content/uploads/2017/05/Hotmob-LINE-e1411960477590-200x127.jpg", 4, 121.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 1", "https://www.iphones.ru/wp-content/uploads/2017/05/%D1%8B%D1%80%D1%89%D0%BA%D0%B5123-200x150.jpg", 0, 1213, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 2", "https://www.iphones.ru/wp-content/uploads/2017/05/201293_t7PkulYGZf_chet-200x132.jpg", 1, 113, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_1321.jpg", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 4", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_2734-760x426.jpg", 3, 143.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 5", "https://www.iphones.ru/wp-content/uploads/2017/05/Hotmob-LINE-e1411960477590-200x127.jpg", 4, 121.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 1", "https://www.iphones.ru/wp-content/uploads/2017/05/%D1%8B%D1%80%D1%89%D0%BA%D0%B5123-200x150.jpg", 0, 1213, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 2", "https://www.iphones.ru/wp-content/uploads/2017/05/201293_t7PkulYGZf_chet-200x132.jpg", 1, 113, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_1321.jpg", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 4", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_2734-760x426.jpg", 3, 143.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 5", "https://www.iphones.ru/wp-content/uploads/2017/05/Hotmob-LINE-e1411960477590-200x127.jpg", 4, 121.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 1", "https://www.iphones.ru/wp-content/uploads/2017/05/%D1%8B%D1%80%D1%89%D0%BA%D0%B5123-200x150.jpg", 0, 1213, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 2", "https://www.iphones.ru/wp-content/uploads/2017/05/201293_t7PkulYGZf_chet-200x132.jpg", 1, 113, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_1321.jpg", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 4", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_2734-760x426.jpg", 3, 143.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 5", "https://www.iphones.ru/wp-content/uploads/2017/05/Hotmob-LINE-e1411960477590-200x127.jpg", 4, 121.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 1", "https://www.iphones.ru/wp-content/uploads/2017/05/%D1%8B%D1%80%D1%89%D0%BA%D0%B5123-200x150.jpg", 0, 1213, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 2", "https://www.iphones.ru/wp-content/uploads/2017/05/201293_t7PkulYGZf_chet-200x132.jpg", 1, 113, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_1321.jpg", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 4", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_2734-760x426.jpg", 3, 143.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 5", "https://www.iphones.ru/wp-content/uploads/2017/05/Hotmob-LINE-e1411960477590-200x127.jpg", 4, 121.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 1", "https://www.iphones.ru/wp-content/uploads/2017/05/%D1%8B%D1%80%D1%89%D0%BA%D0%B5123-200x150.jpg", 0, 1213, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 2", "https://www.iphones.ru/wp-content/uploads/2017/05/201293_t7PkulYGZf_chet-200x132.jpg", 1, 113, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_1321.jpg", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 4", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_2734-760x426.jpg", 3, 143.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 5", "https://www.iphones.ru/wp-content/uploads/2017/05/Hotmob-LINE-e1411960477590-200x127.jpg", 4, 121.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 1", "https://www.iphones.ru/wp-content/uploads/2017/05/%D1%8B%D1%80%D1%89%D0%BA%D0%B5123-200x150.jpg", 0, 1213, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 2", "https://www.iphones.ru/wp-content/uploads/2017/05/201293_t7PkulYGZf_chet-200x132.jpg", 1, 113, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_1321.jpg", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 4", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_2734-760x426.jpg", 3, 143.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 5", "https://www.iphones.ru/wp-content/uploads/2017/05/Hotmob-LINE-e1411960477590-200x127.jpg", 4, 121.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 1", "https://www.iphones.ru/wp-content/uploads/2017/05/%D1%8B%D1%80%D1%89%D0%BA%D0%B5123-200x150.jpg", 0, 1213, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 2", "https://www.iphones.ru/wp-content/uploads/2017/05/201293_t7PkulYGZf_chet-200x132.jpg", 1, 113, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 3", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_1321.jpg", 2, 113.43, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 4", "https://www.iphones.ru/wp-content/uploads/2017/05/IMG_2734-760x426.jpg", 3, 143.3, "HUF"));
        productList.add(new ProductDescriptionModel("Brand 5", "https://www.iphones.ru/wp-content/uploads/2017/05/Hotmob-LINE-e1411960477590-200x127.jpg", 4, 121.3, "HUF"));
        setProductList(productList);
        //// TODO: 02/05/2017 демо значения
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

    private void setProductList(List<ProductDescriptionModel> productList) {
        productListAdapter.setList(productList);
    }

    private void openProductDescription(ProductDescriptionModel productDescriptionModel) {
        navigator.navigateToProductDescription(getContext(), productDescriptionModel.getId());
    }
}
