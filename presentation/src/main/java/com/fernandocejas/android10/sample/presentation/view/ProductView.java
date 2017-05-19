package com.fernandocejas.android10.sample.presentation.view;

import com.fernandocejas.android10.sample.presentation.model.ProductModel;

public interface ProductView {
    void onLoaded(ProductModel shopModel);

    void onError();
}
