package com.fernandocejas.android10.sample.presentation.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.BuildConfig;
import com.fernandocejas.android10.sample.presentation.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.btn_LoadData)
    Button btn_LoadData;
    @Bind(R.id.id)
    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        id.setText(BuildConfig.CUSTOMISABLE_APPLICATION_ID);
    }

    /**
     * Goes to the user list screen.
     */
    @OnClick(R.id.btn_LoadData)
    void navigateToUserList() {
        this.navigator.navigateToUserList(this);
    }
}
