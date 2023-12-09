package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Receipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        Intent intent = getIntent();
        String selectedSeat = intent.getStringExtra("selectedSeat");

        Button confirmButton = findViewById(R.id.confirmButton);
        Button cancelButton = findViewById(R.id.cancelButton);
        TextView thanks = findViewById(R.id.thankyou);




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

        seat.setText("Seat #" +selectedSeat);


        thanks.setVisibility(View.INVISIBLE);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButton.setVisibility(View.INVISIBLE);
                confirmButton.setVisibility(View.INVISIBLE);
                thanks.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), Ticket.class);

                        /**TO BE REMOVED*/

                        intent.putExtra("Selected Seat", "Seat #"+selectedSeat);
                        intent.putExtra("Destination", destinationText);
                        intent.putExtra("Departure Time", departure);
                        intent.putExtra("Bus Number", busNumber);

                        /**TO BE REMOVED*/

                        /**
                         * Try to pass these variables to database then retrieve in the Ticket class
                         *
                         * selectedSeat
                         * destinationText
                         * departure
                         * busNumber
                         *
                         * */


                        startActivity(intent);
                    }
                }, 2000);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
