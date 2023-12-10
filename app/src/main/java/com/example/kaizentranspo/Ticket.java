package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.kaizentranspo.classes.TicketList;

import java.util.ArrayList;

public class Ticket extends AppCompatActivity implements RecyclerViewInterface {
    ArrayList<TicketList> ticket = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket);

        setTicket();
        androidx.recyclerview.widget.RecyclerView recyclerView = findViewById(R.id.ticketrecyclerView);
        Ticket_RecyclerViewAdapter adapter = new Ticket_RecyclerViewAdapter(this, ticket, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button bookButton = findViewById(R.id.buttonBook);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BusSelection.class);

                /**TO BE REMOVED, WAITING FOR DATABASE INTEGRATION*/

                intent.putExtra("Selected Seat", getIntent().getStringExtra("Selected Seat"));
                intent.putExtra("Destination", getIntent().getStringExtra("Destination"));
                intent.putExtra("Departure Time",  getIntent().getStringExtra("Departure Time"));
                intent.putExtra("Bus Number", getIntent().getStringExtra("Bus Number"));

                /**TO BE REMOVED, WAITING FOR DATABASE INTEGRATION*/



                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
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
