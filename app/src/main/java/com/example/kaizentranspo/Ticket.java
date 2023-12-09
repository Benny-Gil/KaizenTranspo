package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;

public class Ticket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket);

        Button bookButton = findViewById(R.id.buttonBook);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), BusSelection.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private void setTicket() {

        /**fetch data from database then store them here-replace data for the arrays*/

        //example only
        String[] busDestination = {"HEllo",getIntent().getStringExtra("Destination")};
        String[] busNumber = {"HEllo",getIntent().getStringExtra("Bus Number")};
        String[] departureTime = {"HEllo",getIntent().getStringExtra("Departure Time")};
        String[] seatNumber = {"Test",getIntent().getStringExtra("Selected Seat")};

        for (int i = 0; i < seatNumber.length; i++) {
            ticket.add(new TicketList(busDestination[i],
                    departureTime[i],
                    busNumber[i],
                    seatNumber[i]));
        }
    }
    @Override
    public void onClick(int position) {

    }
}