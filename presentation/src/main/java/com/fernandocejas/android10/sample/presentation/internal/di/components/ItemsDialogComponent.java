package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerFragment;
import com.fernandocejas.android10.sample.presentation.model.OrderItemModel;
import com.fernandocejas.android10.sample.presentation.view.ItemsDialog;

import java.util.List;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@PerFragment
public interface ItemsDialogComponent {
    void inject(ItemsDialog in);

    @Component.Builder
    interface Builder {
        Builder appComponent(ApplicationComponent appComponent);

        @BindsInstance
        Builder view(ItemsDialog view);

        @BindsInstance
        Builder primaryColor(@Named("primaryColor") int primaryColor);

        @BindsInstance
        Builder textColor(@Named("textColor") int textColor);

        @BindsInstance
        Builder accentColor(@Named("accentColor") int accentColor);

        @BindsInstance
        Builder list(List<OrderItemModel> models);

        ItemsDialogComponent build();

    }
}

