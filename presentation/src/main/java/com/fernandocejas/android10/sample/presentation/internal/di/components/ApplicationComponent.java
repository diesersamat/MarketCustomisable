
package com.fernandocejas.android10.sample.presentation.internal.di.components;

import android.content.Context;

import com.fernandocejas.android10.sample.presentation.internal.di.modules.ApiModule;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ApplicationModule;
import com.fernandocejas.android10.sample.presentation.navigation.Interactor;
import com.fernandocejas.android10.sample.presentation.navigation.Navigator;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseActivity);

    //Exposed to sub-graphs.
    Context context();

    Navigator navigator();

    Interactor getInteractor();

}
