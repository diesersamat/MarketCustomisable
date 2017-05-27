package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerFragment;
import com.fernandocejas.android10.sample.presentation.view.activity.SearchActivity;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@PerFragment
public interface SearchActivityComponent {
    void inject(SearchActivity in);

    @Component.Builder
    interface Builder {
        Builder appComponent(ApplicationComponent appComponent);

        @BindsInstance
        Builder view(SearchActivity view);

        @BindsInstance
        Builder primaryColor(@Named("primaryColor") int primaryColor);

        @BindsInstance
        Builder textColor(@Named("textColor") int textColor);

        @BindsInstance
        Builder accentColor(@Named("accentColor") int accentColor);

        SearchActivityComponent build();
    }
}
