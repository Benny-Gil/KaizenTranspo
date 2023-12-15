package com.example.kaizentranspo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button startButton;
    Button logoutButton;
    TextView textView;
    FirebaseUser user;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
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
            checkUserAccessLevel(user.getUid());

        });

        logoutButton.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), MainLog.class);
            startActivity(intent);
            finish();
        });
    }
    public void checkUserAccessLevel(String uid) {
        DocumentReference documentReference = fStore.collection("Users").document(uid);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            Log.d("Tag", "onSuccess: " + documentSnapshot.getData());

            if (documentSnapshot.getString("isAdmin") != null) {
                startActivity(new Intent(getApplicationContext(), AdminPage.class));
                finish();
            }
            if (documentSnapshot.getString("isUser") != null) {
                startActivity(new Intent(getApplicationContext(), BusSelection.class));
                finish();
            }
        });
    }
}
