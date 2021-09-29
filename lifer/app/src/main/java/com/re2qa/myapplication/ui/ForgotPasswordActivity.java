package com.re2qa.myapplication.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.re2qa.myapplication.GlobalInstance;
import com.re2qa.myapplication.R;
import com.re2qa.myapplication.databinding.ActivityForgotPasswordBinding;
import com.re2qa.myapplication.viewModel.LoginViewModel;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityForgotPasswordBinding binding;
    private TextView login_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalInstance.getInstance().setContext(this);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        binding = DataBindingUtil.setContentView(ForgotPasswordActivity.this, R.layout.activity_forgot_password);

        binding.setLifecycleOwner(this);

        binding.setLoginViewModel(loginViewModel);

        loginViewModel.getUser().observe(this, loginUser -> {

            if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
                binding.txtEmailAddress.setError("Enter an Email");
                binding.txtEmailAddress.requestFocus();
            } else if (!loginUser.isEmailValid()) {
                binding.txtEmailAddress.setError("Enter a Valid Email Address");
                binding.txtEmailAddress.requestFocus();
            }
            else {
                Toast.makeText(this,"We have sent a password reset link to your registered email address. Please click the link to complete the reset process.",Toast.LENGTH_SHORT).show();
            }

        });

        login_back = findViewById(R.id.login_back_txt);
        login_back.setOnClickListener(v -> {
            Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }
}