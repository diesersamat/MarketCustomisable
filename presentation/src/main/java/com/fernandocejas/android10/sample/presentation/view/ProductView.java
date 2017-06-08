package com.fernandocejas.android10.sample.presentation.view;

import com.fernandocejas.android10.sample.presentation.model.ProductWrapperModel;

public interface ProductView {
    void onLoaded(ProductWrapperModel shopModel);

    void onError();
}
