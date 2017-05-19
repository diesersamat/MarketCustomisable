package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.fernandocejas.android10.sample.presentation.AndroidApplication;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.navigation.Interactor;
import com.fernandocejas.android10.sample.presentation.navigation.Navigator;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;

import javax.inject.Inject;


/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {

    @Inject
    Interactor interactor;
    @Inject
    Navigator navigator;

    public int getPrimaryColor() {
        return ((BaseActivity) getActivity()).getPrimaryColor();
    }

    public int getAccentColor() {
        return ((BaseActivity) getActivity()).getAccentColor();
    }


    public static String getFragmentTag() {
        return BaseFragment.class.getName();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        additionalCreateOperations();
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getActivity().getApplication()).getApplicationComponent();
    }

    protected void additionalCreateOperations() {
        this.getApplicationComponent().inject(this);
    }

    protected Interactor getInteractor() {
        return interactor;
    }

}
