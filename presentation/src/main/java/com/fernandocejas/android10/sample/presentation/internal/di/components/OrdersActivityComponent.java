package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerFragment;
import com.fernandocejas.android10.sample.presentation.view.activity.OrdersActivity;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@PerFragment
public interface OrdersActivityComponent {
    void inject(OrdersActivity in);

    @Component.Builder
    interface Builder {
        Builder appComponent(ApplicationComponent appComponent);

        @BindsInstance
        Builder view(OrdersActivity view);

        @BindsInstance
        Builder primaryColor(@Named("primaryColor") int primaryColor);

        @BindsInstance
        Builder textColor(@Named("textColor") int textColor);

        @BindsInstance
        Builder accentColor(@Named("accentColor") int accentColor);

        OrdersActivityComponent build();
    }
}
