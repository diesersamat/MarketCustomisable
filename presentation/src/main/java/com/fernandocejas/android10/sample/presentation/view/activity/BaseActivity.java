package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.fernandocejas.android10.sample.presentation.AndroidApplication;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.navigation.Navigator;

import javax.inject.Inject;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String PRIMARY_COLOR = "PRIMARY_COLOR";
    public static final String ACCENT_COLOR = "ACCENT_COLOR";

    @Inject
    Navigator navigator;
    private int primaryColor;
    private int accentColor;

    public int getPrimaryColor() {
        return primaryColor;
    }

    public int getAccentColor() {
        return accentColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String primaryColor = preferences.getString(PRIMARY_COLOR, "");
        String accentColor = preferences.getString(ACCENT_COLOR, "");
        if (!TextUtils.isEmpty(primaryColor)) {
            this.primaryColor = Color.parseColor(primaryColor);
        } else {
            this.primaryColor = getResources().getColor(R.color.md_indigo);
        }

        if (!TextUtils.isEmpty(accentColor)) {
            this.accentColor = Color.parseColor(accentColor);
        } else {
            this.accentColor = getResources().getColor(R.color.md_yellow);
        }
        additionalCreateOperations();

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
