package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminBookingView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking_view);

        Intent intent = getIntent();
        String selectedSeat = intent.getStringExtra("selectedSeat");

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView seat = findViewById(R.id.seatNumber_receipt);

        String price = getIntent().getStringExtra("Price");
        TextView priceText = findViewById(R.id.price_receipt);
        priceText.setText(price);

        String destinationText = getIntent().getStringExtra("Destination");
        TextView destination = findViewById(R.id.destination_receipt);
        destination.setText(destinationText);

        String departure = getIntent().getStringExtra("Departure Time");
        TextView departureTime = findViewById(R.id.departureTime_receipt);
        departureTime.setText(departure);

        String busNumber = getIntent().getStringExtra("Bus Number");
        System.out.println(busNumber);
        TextView busNum = findViewById(R.id.busNumber_receipt);
        busNum.setText(busNumber);

        seat.setText(selectedSeat);

        /**
         *
         * USERNAMEE
         *
         * */

        TextView userName = findViewById(R.id.userName);
        userName.setText("hello");
    }
}