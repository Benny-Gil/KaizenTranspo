package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaizentranspo.admin.AdminPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private static final String ADMIN_CODE = "admin123";
    EditText editTextEmail, editTextPassword, adminEditText;
    ImageView adminIcon;
    Button buttonReg;
    Button buttonLogin;
    CheckBox checkBox;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        editTextEmail = findViewById(R.id.registerUsername);
        editTextPassword = findViewById(R.id.registerPassword);
        buttonReg = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
        checkBox = findViewById(R.id.adminCheckbox);
        adminEditText = findViewById(R.id.adminCode);
        adminIcon = findViewById(R.id.adminIcon);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // If the checkbox is checked, make the adminIcon and adminEditText visible
            if (isChecked) {
                adminIcon.setVisibility(View.VISIBLE);
                adminEditText.setVisibility(View.VISIBLE);
            } else {
                // If the checkbox is not checked, make them invisible
                adminIcon.setVisibility(View.INVISIBLE);
                adminEditText.setVisibility(View.INVISIBLE);
            }
        });


        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        buttonReg.setOnClickListener(v -> {

            String email = String.valueOf(editTextEmail.getText());
            String password = String.valueOf(editTextPassword.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Register.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            String enteredAdminCode = adminEditText.getText().toString();
            if (checkBox.isChecked() && enteredAdminCode.equals(ADMIN_CODE)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Register.this, "Account Created.", Toast.LENGTH_SHORT).show();
                                DocumentReference documentReference = fStore.collection("Users").document(user.getUid());

                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("email", email);
                                userInfo.put("isAdmin", "1");

                                documentReference.set(userInfo);

                                Intent intent = new Intent(getApplicationContext(), AdminBookingView.class);
                                startActivity(intent);
                                finish();
                            } else {
                                {
                                    // Check if the exception is a FirebaseAuthWeakPasswordException
                                    if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                        Toast.makeText(Register.this, "Weak password. Please choose a stronger password. (Atleast 6 Characters)", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle other authentication failures
                                        Toast.makeText(Register.this, "Authentication failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            } else if (!checkBox.isChecked()) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {

                            if (task.isSuccessful()) {
                                // User creation successful
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Register.this, "Account Created.", Toast.LENGTH_SHORT).show();
                                DocumentReference documentReference = fStore.collection("Users").document(user.getUid());
                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("Email", email);

                                userInfo.put("isUser", "1");

                                documentReference.set(userInfo);

                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Check if the exception is a FirebaseAuthWeakPasswordException
                                if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                    Toast.makeText(Register.this, "Weak password. Please choose a stronger password. (Atleast 6 Characters)", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Handle other authentication failures
                                    Toast.makeText(Register.this, "Authentication failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else{
                Toast.makeText(Register.this, "Incorrect admin code", Toast.LENGTH_SHORT).show();
            }
        });
    }
}