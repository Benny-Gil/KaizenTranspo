package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AdminCreateBus extends AppCompatActivity {
    Button create, bookingsButton;
    ImageButton close;
    EditText destination, departureTime, busNumber, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_bus);

        close = findViewById(R.id.closeButton_add);
        create = findViewById(R.id.createButton_add);
        bookingsButton = findViewById(R.id.buttonBook_add);

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

            }
        });
        bookingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}