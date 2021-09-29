package com.re2qa.myapplication.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.re2qa.myapplication.GlobalInstance;
import com.re2qa.myapplication.R;
import com.re2qa.myapplication.databinding.ActivityLoginBinding;
import com.re2qa.myapplication.viewModel.LoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private TextView forgot_pass;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalInstance.getInstance().setContext(this);
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
                GlobalInstance.getInstance().setPreferences("Email",loginUser.getStrEmailAddress());
                GlobalInstance.getInstance().setPreferences("Pass",loginUser.getStrPassword());
            }

        });

        forgot_pass = findViewById(R.id.forgot_pass_txt);
        forgot_pass.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, com.re2qa.myapplication.ui.ForgotPasswordActivity.class);
            startActivity(i);
        });

        loginBtn = findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, com.re2qa.myapplication.ui.MainActivity.class);
            startActivity(i);
        });
    }
}