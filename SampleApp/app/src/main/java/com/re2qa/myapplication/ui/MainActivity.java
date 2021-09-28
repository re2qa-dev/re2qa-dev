package com.re2qa.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.re2qa.myapplication.GlobalInstance;
import com.re2qa.myapplication.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalInstance.getInstance().setContext(this);
        TextView email = findViewById(R.id.lblEmailAnswer);
        TextView pass = findViewById(R.id.lblPasswordAnswer);

        email.setText(GlobalInstance.getInstance().getPreferences("Email"));
        pass.setText(GlobalInstance.getInstance().getPreferences("Pass"));

        Button loginBtn = findViewById(R.id.btnLogout);
        loginBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        });

    }

}
