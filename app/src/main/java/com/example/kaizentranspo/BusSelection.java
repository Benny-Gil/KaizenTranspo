package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class BusSelection extends AppCompatActivity implements RecyclerViewInterface{

    ArrayList<BusList> bus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_selection);

        setUpBus();
        androidx.recyclerview.widget.RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        Bus_RecyclerViewAdapter adapter = new Bus_RecyclerViewAdapter(this, bus,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button ticketButton = findViewById(R.id.buttonTicket);

        ticketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ticket.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
    private void setUpBus() {

        /**Extracted data from strings.xml, if possible fetch data from database then store them here*/

        String[] busDestination = getResources().getStringArray(R.array.bus_destination);
        String[] busNumber = getResources().getStringArray(R.array.bus_number);
        String[] departureTime = getResources().getStringArray(R.array.time);
        String[] price = getResources().getStringArray(R.array.price);

        for (int i = 0; i < busDestination.length; i++) {
            bus.add(new BusList(busDestination[i],
                    departureTime[i],
                    busNumber[i],
                    price[i]));
        }
    }
    @Override
    public void onBusClick(int position) {
        Intent intent = new Intent(this, SeatSelection.class);
        intent.putExtra("Destination", bus.get(position).getDestination());
        intent.putExtra("Price", bus.get(position).getPrice());
        intent.putExtra("Bus Number", bus.get(position).getBusNumber());
        intent.putExtra("Departure Time", bus.get(position).getTime());
        startActivity(intent);
    }
}
