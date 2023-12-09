package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText editTextEmail,editTextPassword;
    Button buttonReg;
    Button buttonLogin;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    TextView textView;
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        editTextEmail=findViewById(R.id.registerUsername);
        editTextPassword=findViewById(R.id.registerPassword);
        buttonReg=findViewById(R.id.buttonRegister);
        buttonLogin=findViewById(R.id.buttonLogin);
        //progressBar=findViewById(R.id.progressBar);

        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        buttonReg.setOnClickListener(v -> {
            //progressBar.setVisibility(View.VISIBLE);

            String email = String.valueOf(editTextEmail.getText());
            String password = String.valueOf(editTextPassword.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Register.this, "Enter Email", Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);  // Hide progress bar
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);  // Hide progress bar
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        //progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            // User creation successful
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Account Created.", Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference=fStore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("Email",email);

                            userInfo.put("isUser","1");

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
        });
    }
}