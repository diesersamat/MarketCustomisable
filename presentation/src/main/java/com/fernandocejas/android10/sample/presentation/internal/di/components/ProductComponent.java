package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerFragment;
import com.fernandocejas.android10.sample.presentation.view.ProductView;
import com.fernandocejas.android10.sample.presentation.view.fragment.ProductFragment;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;


@Component(dependencies = ApplicationComponent.class)
@PerFragment
public interface ProductComponent {
    void inject(ProductFragment in);

    @Component.Builder
    interface Builder {
        Builder appComponent(ApplicationComponent appComponent);

        @BindsInstance
        Builder view(ProductView view);

        @BindsInstance
        Builder productId(@Named("productId") int productId);

        ProductComponent build();
    }
}
