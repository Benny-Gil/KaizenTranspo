package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainLog extends AppCompatActivity {
    Button buttonLogin;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log);

        buttonLogin = findViewById(R.id.loginButton);
        buttonRegister = findViewById(R.id.registerButton);

        buttonLogin.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();

        });
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
            finish();
        });

    }
}