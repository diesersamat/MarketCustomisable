package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerFragment;
import com.fernandocejas.android10.sample.presentation.view.activity.DefaultContactDetailsSelectionActivity;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;


@Component(dependencies = ApplicationComponent.class)
@PerFragment
public interface DefaultContactComponent {
    void inject(DefaultContactDetailsSelectionActivity in);

    @Component.Builder
    interface Builder {
        Builder appComponent(ApplicationComponent appComponent);

        @BindsInstance
        Builder view(DefaultContactDetailsSelectionActivity view);

        @BindsInstance
        Builder primaryColor(@Named("primaryColor") int primaryColor);

        @BindsInstance
        Builder textColor(@Named("textColor") int textColor);

        @BindsInstance
        Builder accentColor(@Named("accentColor") int accentColor);

        DefaultContactComponent build();
    }
}
