package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
   EditText editTextEmail,editTextPassword;
    Button buttonLogin;
    Button buttonRegister;
    FirebaseAuth mAuth;

    TextView textView;
    FirebaseFirestore fStore;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),BusSelection.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fStore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.registerUsername);
        editTextPassword=findViewById(R.id.registerPassword);
        buttonLogin=findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
            finish();
        });
        buttonLogin.setOnClickListener(v -> {

            //deleteeee
            startActivity(new Intent(getApplicationContext(),BusSelection.class));


            String email,password;
            email=String.valueOf(editTextEmail.getText());
            password=String.valueOf(editTextPassword.getText());

            if(TextUtils.isEmpty(email)){
                Toast.makeText(Login.this,"Enter email",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(Login.this,"Enter Password",Toast.LENGTH_SHORT).show();

            }
            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
            checkUserAccessLevel(authResult.getUser().getUid());
            }).addOnFailureListener(e -> Toast.makeText(Login.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show());

                    });


    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference documentReference= fStore.collection("Users").document(uid);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            Log.d("Tag","onSuccess: " + documentSnapshot.getData());

            if(documentSnapshot.getString("isAdmin") != null){
                startActivity(new Intent(getApplicationContext(),AdminPage.class));
                finish();
            }
            if(documentSnapshot.getString("isUser")!=null){
                startActivity(new Intent(getApplicationContext(),BusSelection.class));
                finish();
            }
        });
    }
}