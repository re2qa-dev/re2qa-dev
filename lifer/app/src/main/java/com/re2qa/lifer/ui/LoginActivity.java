package com.re2qa.lifer.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.re2qa.lifer.R;
import com.re2qa.lifer.databinding.ActivityLoginBinding;
import com.re2qa.lifer.viewModel.LoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private TextView forgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = ViewModelProviders.of(LoginActivity.this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(LoginActivity.this);
        binding.setLoginViewModel(loginViewModel);
        loginViewModel.getUser().observe(LoginActivity.this, loginUser -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
                binding.txtEmailAddress.setError("Enter an Email");
                binding.txtEmailAddress.requestFocus();
            } else if (!loginUser.isEmailValid()) {
                binding.txtEmailAddress.setError("Enter a Valid Email Address");
                binding.txtEmailAddress.requestFocus();
            } else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrPassword())) {
                binding.txtPassword.setError("Enter a Password");
                binding.txtPassword.requestFocus();
            } else if (!loginUser.isPasswordLengthGreaterThan5()) {
                binding.txtPassword.setError("Enter at least 6 Digit password");
                binding.txtPassword.requestFocus();
            }
            else {
                hideSoftKeyboard(this);
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        forgot_pass = findViewById(R.id.forgot_pass_txt);
        forgot_pass.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(i);
        });
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}