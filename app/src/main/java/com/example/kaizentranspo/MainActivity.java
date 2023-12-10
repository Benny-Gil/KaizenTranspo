package com.example.kaizentranspo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button startButton;
    Button logoutButton;
    TextView textView;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();
        startButton = findViewById(R.id.getStartedButton);
        logoutButton=findViewById(R.id.logoutButton);
        textView=findViewById(R.id.userName);
        user = auth.getCurrentUser();
        if(user==null){

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else {
            textView.setText(user.getEmail());
        }

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BusSelection.class);
            startActivity(intent);
            finish();

        });

        logoutButton.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), MainLog.class);
            startActivity(intent);
            finish();
        });


    }
}
