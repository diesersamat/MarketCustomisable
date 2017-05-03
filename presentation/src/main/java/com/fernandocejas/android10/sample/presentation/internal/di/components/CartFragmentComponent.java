package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerFragment;
import com.fernandocejas.android10.sample.presentation.view.CartFragmentView;
import com.fernandocejas.android10.sample.presentation.view.fragment.CartFragment;

import dagger.BindsInstance;
import dagger.Component;


@Component(dependencies = ApplicationComponent.class)
@PerFragment
public interface CartFragmentComponent {
    void inject(CartFragment in);

    @Component.Builder
    interface Builder {
        Builder appComponent(ApplicationComponent appComponent);

        @BindsInstance
        Builder view(CartFragmentView view);


        CartFragmentComponent build();
    }
}
