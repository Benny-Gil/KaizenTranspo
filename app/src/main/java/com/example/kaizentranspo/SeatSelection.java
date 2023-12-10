package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SeatSelection extends AppCompatActivity {

    private Button lastClickedButton;
    private Button bookButton;
    private String price;
    private String destinationText;
    private String departure;
    private String date;
    private String selectedSeat;
    private String busNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        seats();

        price = getIntent().getStringExtra("Price");
        TextView priceUI = findViewById(R.id.price);
        priceUI.setText(price);

        destinationText = getIntent().getStringExtra("Destination");

        TextView destination = findViewById(R.id.destination);
        destination.setText(destinationText);

        departure = getIntent().getStringExtra("Departure Time");
        TextView time =  findViewById(R.id.departureTime);
        time.setText("Departure Time: "+departure);

        busNumber = getIntent().getStringExtra("Bus Number");
        TextView num = findViewById(R.id.bus_num_selection);
        num.setText(busNumber);


        bookButton = findViewById(R.id.bookButton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastClickedButton != null) {
                    selectedSeat = ("Seat #" + lastClickedButton.getText());
                    Intent intent=new Intent(getApplicationContext(), Receipt.class);
                    intent.putExtra("selectedSeat",selectedSeat);
                    intent.putExtra("Destination", destinationText);
                    intent.putExtra("Departure Time", departure);
                    intent.putExtra("Price", price);
                    intent.putExtra("Bus Number", busNumber);
                    startActivity(intent);
                }
            }
        });
    }

    private void setButtonClickListener(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookButton.setVisibility(View.VISIBLE);
                if (lastClickedButton != null) {
                    lastClickedButton.setBackgroundResource(R.drawable.available_seat);
                }
                if (lastClickedButton == button) {
                    lastClickedButton = null;
                    bookButton.setVisibility(View.INVISIBLE);
                } else {
                    button.setBackgroundResource(R.drawable.selected_seat_color);
                    lastClickedButton = button;
                }
            }
        });
    }
    private void seats() {
        for (int i = 1; i <= 49; i++) {
            int resId = getResources().getIdentifier("seat" + i, "id", getPackageName());
            Button button = findViewById(resId);
            setButtonClickListener(button);
        }
    }
}
