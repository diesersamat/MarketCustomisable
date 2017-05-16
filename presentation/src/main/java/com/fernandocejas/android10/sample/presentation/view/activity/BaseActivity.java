package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.afollestad.aesthetic.Aesthetic;
import com.afollestad.aesthetic.AestheticActivity;
import com.fernandocejas.android10.sample.presentation.AndroidApplication;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.navigation.Navigator;

import javax.inject.Inject;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AestheticActivity {

    public static final String PRIMARY_COLOR = "PRIMARY_COLOR";
    public static final String ACCENT_COLOR = "ACCENT_COLOR";

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        additionalCreateOperations();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String primaryColor = preferences.getString(PRIMARY_COLOR, "");
        String accentColor = preferences.getString(ACCENT_COLOR, "");
        Aesthetic aesthetic = Aesthetic.get();
        if (!TextUtils.isEmpty(primaryColor)) {
            aesthetic = aesthetic.primaryColor(Color.parseColor(primaryColor));
        } else {
            aesthetic = aesthetic.primaryColorRes(R.color.md_indigo);
        }

        if (!TextUtils.isEmpty(accentColor)) {
            aesthetic = aesthetic.primaryColor(Color.parseColor(accentColor));
        } else {
            aesthetic = aesthetic.primaryColorRes(R.color.md_yellow);
        }
        aesthetic = aesthetic.isDark(true);
        aesthetic.apply();
    }

    /**
     * Get the Main Application component for dependency injection.
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    protected void additionalCreateOperations() {
        this.getApplicationComponent().inject(this);
    }
}
