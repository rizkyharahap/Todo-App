package com.haphap.todoapp.Feature.User;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.haphap.todoapp.BaseActivity;
import com.haphap.todoapp.R;
import com.haphap.todoapp.ViewModel.UserViewModel;
import com.haphap.todoapp.Model.User;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LOGIN";

    @BindView(R.id.login_activity_email)
    TextInputEditText etEmail;
    @BindView(R.id.login_activity_password)
    TextInputEditText etPassword;
    @BindView(R.id.login_activity_button)
    Button btnLogin;
    @BindView(R.id.login_activity_register_text)
    TextView tvRegister;
    @BindView(R.id.login_progress_bar_layout)
    LinearLayout linearLayout;

    private UserViewModel viewModel;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

        handler = new Handler();
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        if (getSharedPrefence().getUserId() != -1) {
            for (User loggedUser : viewModel.getallUsers()) {
                if (loggedUser.getId().equals(getSharedPrefence().getUserId())) {
                    openMainActivity(LoginActivity.this, loggedUser);
                    finish();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_activity_button :
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
                login();
                break;
            case R.id.login_activity_register_text :
                gotoRegisterActivity(LoginActivity.this);
                break;
        }
    }

    private void login() {
        String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString().trim();

        if (!validateEmailPassword(email, password)) {
            hideProgressbar();
        } else {
            showProgressbar();

            for (User user:viewModel.getallUsers()) {
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    getSharedPrefence().setLoggedUserId(user.getId());
                    Log.d(TAG, "register: SharedPref LoggedIn UserId " + getSharedPrefence().getUserId());
                    openMainActivityonDelay(user);
                    return;
                }
            }

            handler.postDelayed(() -> {
                hideProgressbar();
                showToast("Wrong email or password");
            }, 2000);
        }


    }

    private void openMainActivityonDelay(final User user) {
        handler.postDelayed(() -> {
            showProgressbar();
            openMainActivity(LoginActivity.this, user);
            finish();
        }, 2000);
    }


    private boolean validateEmailPassword(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Required");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Required");
            return false;
        } else if (password.length() < 6) {
            etPassword.setError("Password too short");
            return false;
        }
        return true;
    }

    private void showProgressbar() {
        linearLayout.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);
    }

    private void hideProgressbar() {
        linearLayout.setVisibility(View.GONE);
        btnLogin.setEnabled(true);
    }

}
