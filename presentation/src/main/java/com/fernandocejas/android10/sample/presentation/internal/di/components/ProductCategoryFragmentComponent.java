package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerFragment;
import com.fernandocejas.android10.sample.presentation.view.ProductCategoryView;
import com.fernandocejas.android10.sample.presentation.view.fragment.ProductCategoryFragment;

import dagger.BindsInstance;
import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@PerFragment
public interface ProductCategoryFragmentComponent {
    void inject(ProductCategoryFragment in);

    @Component.Builder
    interface Builder {
        Builder appComponent(ApplicationComponent appComponent);

        @BindsInstance
        Builder view(ProductCategoryView view);


        ProductCategoryFragmentComponent build();
    }
}