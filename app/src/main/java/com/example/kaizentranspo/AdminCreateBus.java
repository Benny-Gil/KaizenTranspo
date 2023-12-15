package com.example.kaizentranspo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminCreateBus extends AppCompatActivity {
    Button create, bookingsButton;
    ImageButton close;
    EditText destination, departureTime, busNumber, price;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_bus);

        fStore = FirebaseFirestore.getInstance();
        close = findViewById(R.id.closeButton_add);
        create = findViewById(R.id.createButton_add);
        bookingsButton = findViewById(R.id.buttonBook);

        destination = findViewById(R.id.destination_admin_add);
        departureTime = findViewById(R.id.departureTime_admin_add);
        busNumber = findViewById(R.id.busNumber_admin_add);
        price = findViewById(R.id.price_admin_add);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override

                public void onClick(View v) {
                    // Get the values from the EditText fields
                    String destinationValue = destination.getText().toString();
                    String departureTimeValue = departureTime.getText().toString();
                    String busNumberValue = busNumber.getText().toString();
                    String priceValue = price.getText().toString();

                    // Check if any of the fields are empty
                    if (destinationValue.isEmpty() || departureTimeValue.isEmpty() || busNumberValue.isEmpty() || priceValue.isEmpty()) {
                        // Display a Toast indicating that fields are missing
                        Toast.makeText(AdminCreateBus.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        CollectionReference busesCollection = fStore.collection("Buses");

                        DocumentReference busDocument = busesCollection.document(busNumberValue);

                        Map<String, Object> busData = new HashMap<>();
                        busData.put("destination", destinationValue);
                        busData.put("time", departureTimeValue);
                        busData.put("busNumber", busNumberValue);
                        busData.put("price", priceValue);



                        busDocument.set(busData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Document successfully created
                                        // Now, create the "seats" collection inside the "Buses" document
                                        CollectionReference seatsCollection = busDocument.collection("seats");

                                        // Add seat documents (seat1 to seat49) with initial data
                                        for (int i = 1; i <= 49; i++) {
                                            DocumentReference seatDocument = seatsCollection.document("seat" + i);

                                            Map<String, Object> seatData = new HashMap<>();
                                            seatData.put("isTaken", false);
                                            seatData.put("reserverName", "");
                                            seatDocument.set(seatData);
                                        }
                                        Toast.makeText(AdminCreateBus.this, "Bus created successfully", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), AdminPage.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle the error
                                        Toast.makeText(AdminCreateBus.this, "Error creating document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                }


        });
        bookingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminPage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}