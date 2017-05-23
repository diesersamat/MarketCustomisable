package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;

public class LoginActivity extends BaseActivity {

    boolean isSomethingShowing = false;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.login_form)
    ScrollView loginView;
    @BindView(R.id.fname_register)
    EditText fnameRegister;
    @BindView(R.id.sname_register)
    EditText snameRegister;
    @BindView(R.id.email_register)
    EditText emailRegister;
    @BindView(R.id.password_register)
    EditText passwordRegister;
    @BindView(R.id.phone_register)
    EditText phoneRegister;
    @BindView(R.id.register_button)
    Button emailRegisterButton;
    @BindView(R.id.email_sign_in)
    EditText emailSignIn;
    @BindView(R.id.password_sign_in)
    EditText passwordSignIn;
    @BindView(R.id.sign_in_button)
    Button emailSignInButton;
    @BindView(R.id.fname_register_view)
    TextInputLayout fnameRegisterView;
    @BindView(R.id.sname_register_view)
    TextInputLayout snameRegisterView;
    @BindView(R.id.email_register_view)
    TextInputLayout emailRegisterView;
    @BindView(R.id.password_register_view)
    TextInputLayout passwordRegisterView;
    @BindView(R.id.phone_register_view)
    TextInputLayout phoneRegisterView;
    @BindView(R.id.email_sign_in_view)
    TextInputLayout emailSignInView;
    @BindView(R.id.password_sign_in_view)
    TextInputLayout passwordSignInView;
    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @BindView(R.id.register_fields)
    LinearLayout registerFields;
    @BindView(R.id.login_fields)
    LinearLayout loginFields;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_welcome)
    TextView textWelcome;
    @BindView(R.id.main_view)
    LinearLayout mainView;

    private Subscription subscription;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public void onBackPressed() {
        if (isSomethingShowing) {
            resetToMainState();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.login_signup_title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainView.setBackgroundColor(getBackgroundColor());
        toolbar.setBackgroundColor(getAccentColor());
        toolbar.setTitleTextColor(getTextColor());
        emailSignInButton.setBackgroundColor(getAccentColor());
        emailRegisterButton.setBackgroundColor(getPrimaryColor());
        textWelcome.setTextColor(getTextColor());
        textWelcome.setText(getResources().getString(R.string.please_sign_in, interactor.getShopInfoSync().getName()));
        emailRegister.getBackground().setColorFilter(getPrimaryColor(), PorterDuff.Mode.DST_IN);
        emailSignIn.getBackground().setColorFilter(getPrimaryColor(), PorterDuff.Mode.DST_IN);
        phoneRegister.getBackground().setColorFilter(getPrimaryColor(), PorterDuff.Mode.DST_IN);
        passwordRegister.getBackground().setColorFilter(getPrimaryColor(), PorterDuff.Mode.DST_IN);
        passwordSignIn.getBackground().setColorFilter(getPrimaryColor(), PorterDuff.Mode.DST_IN);
        fnameRegister.getBackground().setColorFilter(getPrimaryColor(), PorterDuff.Mode.DST_IN);
        snameRegister.getBackground().setColorFilter(getPrimaryColor(), PorterDuff.Mode.DST_IN);
    }

    private void attemptLogin() {
        if (subscription != null) {
            return;
        }

        // Reset errors.
        emailSignIn.setError(null);
        passwordSignIn.setError(null);

        // Store values at the time of the login attempt.
        String email = emailSignIn.getText().toString();
        String password = passwordSignIn.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            passwordSignIn.setError(getString(R.string.error_invalid_password));
            focusView = passwordSignIn;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailSignIn.setError(getString(R.string.error_field_required));
            focusView = emailSignIn;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailSignIn.setError(getString(R.string.error_invalid_email));
            focusView = emailSignIn;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            subscription = interactor.authUser(email, password).subscribe(getObserver());
        }
    }

    private void attemptRegister() {
        if (subscription != null) {
            return;
        }

        // Reset errors.
        emailRegister.setError(null);
        passwordRegister.setError(null);
        fnameRegister.setError(null);
        snameRegister.setError(null);
        phoneRegister.setError(null);

        // Store values at the time of the login attempt.
        String email = emailRegister.getText().toString();
        String password = passwordRegister.getText().toString();
        String fname = fnameRegister.getText().toString();
        String sname = snameRegister.getText().toString();
        String phone = phoneRegister.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            passwordRegister.setError(getString(R.string.error_invalid_password));
            focusView = passwordRegister;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailRegister.setError(getString(R.string.error_field_required));
            focusView = emailRegister;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailRegister.setError(getString(R.string.error_invalid_email));
            focusView = emailRegister;
            cancel = true;
        }

        if (TextUtils.isEmpty(fname)) {
            fnameRegister.setError(getString(R.string.error_field_required));
            focusView = fnameRegister;
            cancel = true;
        }

        if (TextUtils.isEmpty(sname)) {
            snameRegister.setError(getString(R.string.error_field_required));
            focusView = snameRegister;
            cancel = true;
        }
        if (TextUtils.isEmpty(sname)) {
            phoneRegister.setError(getString(R.string.error_field_required));
            focusView = phoneRegister;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            subscription = interactor.registerUser(email,
                    password, fname, sname, phone).subscribe(getRegisterObserver());
        }
    }

    private Observer<UserModel> getRegisterObserver() {
        return new Observer<UserModel>() {
            @Override
            public void onCompleted() {
                subscription = null;
                showProgress(false);
            }

            @Override
            public void onError(Throwable e) {
                passwordRegister.setError(getString(R.string.error_incorrect_password));
                passwordRegister.requestFocus();
            }

            @Override
            public void onNext(UserModel userModel) {
                if (TextUtils.isEmpty(userModel.getToken())) {
                    onError(new IllegalStateException("Token not received"));
                } else {
                    //success
                    finish();
                }
            }
        };
    }

    private void resetToMainState() {
        registerFields.setVisibility(View.GONE);
        emailSignInButton.setVisibility(View.VISIBLE);
        loginFields.setVisibility(View.GONE);
        emailRegisterButton.setVisibility(View.VISIBLE);
        isSomethingShowing = false;
    }

    private Observer<UserModel> getObserver() {
        return new Observer<UserModel>() {
            @Override
            public void onCompleted() {
                subscription = null;
                showProgress(false);
            }

            @Override
            public void onError(Throwable e) {
                passwordSignIn.setError(getString(R.string.error_incorrect_password));
                passwordSignIn.requestFocus();
            }

            @Override
            public void onNext(UserModel userModel) {
                if (TextUtils.isEmpty(userModel.getToken())) {
                    onError(new IllegalStateException("Token not received"));
                } else {
                    //success
                    finish();
                }
            }
        };
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private void showProgress(final boolean show) {
        loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        loginView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.sign_in_button)
    void onSignInClick() {
        if (!isSomethingShowing) {
            isSomethingShowing = true;
            loginFields.setVisibility(View.VISIBLE);
            emailRegisterButton.setVisibility(View.GONE);
            loginView.post(() -> loginView.fullScroll(View.FOCUS_DOWN));
        } else {
            attemptLogin();
        }
    }

    @OnClick(R.id.register_button)
    void onRegisterClick() {
        if (!isSomethingShowing) {
            isSomethingShowing = true;
            registerFields.setVisibility(View.VISIBLE);
            emailSignInButton.setVisibility(View.GONE);
            loginView.post(() -> loginView.fullScroll(View.FOCUS_DOWN));
        } else {
            attemptRegister();
        }
    }
}

