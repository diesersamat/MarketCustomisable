package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerFragment;
import com.fernandocejas.android10.sample.presentation.view.ShopActivityView;
import com.fernandocejas.android10.sample.presentation.view.activity.ShopActivity;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@PerFragment
public interface ShopActivityComponent {
    void inject(ShopActivity in);

    @Component.Builder
    interface Builder {
        Builder appComponent(ApplicationComponent appComponent);

        @BindsInstance
        Builder view(ShopActivityView view);


        @BindsInstance
        Builder backgroundColor(@Named("backgroundColor") int backgroundColor);
        @BindsInstance
        Builder textColor(@Named("textColor") int textColor);

        @BindsInstance
        Builder accentColor(@Named("accentColor") int accentColor);

        ShopActivityComponent build();
    }
}
