package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

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
    public static final String BACKGROUND_COLOR = "BACKGROUND_COLOR";
    public static final String TEXT_COLOR = "TEXT_COLOR";
    public static final String IS_DARK_ICONS = "IS_DARK_ICONS";

    @Inject
    Navigator navigator;
    private int primaryColor;
    private int accentColor;
    private int backgroundColor;
    private int textColor;
    private boolean isDarkIcons;

    public boolean isDarkIcons() {
        return isDarkIcons;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

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
        String backgroundColor = preferences.getString(BACKGROUND_COLOR, "");
        String textColor = preferences.getString(TEXT_COLOR, "");
        isDarkIcons = preferences.getBoolean(IS_DARK_ICONS, false);

        if (!TextUtils.isEmpty(primaryColor)) {
            this.primaryColor = Color.parseColor(primaryColor);
        } else {
            this.primaryColor = getResources().getColor(R.color.primary_default);
        }

        if (!TextUtils.isEmpty(accentColor)) {
            this.accentColor = Color.parseColor(accentColor);
        } else {
            this.accentColor = getResources().getColor(R.color.accent_default);
        }

        if (!TextUtils.isEmpty(accentColor)) {
            this.backgroundColor = Color.parseColor(backgroundColor);
        } else {
            this.backgroundColor = getResources().getColor(R.color.background_default);
        }

        if (!TextUtils.isEmpty(accentColor)) {
            this.textColor = Color.parseColor(textColor);
        } else {
            this.textColor = getResources().getColor(R.color.text_default);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getPrimaryColor());
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
