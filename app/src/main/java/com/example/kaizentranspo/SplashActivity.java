package com.example.kaizentranspo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay for a second and then start the MainLog
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainLog.class);
            startActivity(intent);
            finish();
        }, 1000);
    }
}
