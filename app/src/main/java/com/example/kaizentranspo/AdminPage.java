package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kaizentranspo.classes.DatabaseResetter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AdminPage extends AppCompatActivity implements RecyclerViewInterface {
    FirebaseAuth auth;
    FirebaseUser user;
    Bus_RecyclerViewAdapter adapter;
    Button resetButton;
    ArrayList<BusList> bus = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        auth=FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        setUpBus();
        androidx.recyclerview.widget.RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        adapter = new Bus_RecyclerViewAdapter(this, bus,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button manageBus = findViewById(R.id.manageBus);
        ImageButton homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        resetButton = findViewById(R.id.resetAllButton);
        resetButton.setOnClickListener(v -> {
            DatabaseResetter dr = new DatabaseResetter();
            dr.userTicketsReset();
            dr.busSeatReset();
            dr.ticketCounterReset();
        });


        manageBus.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AdminManageBus.class);
            startActivity(intent);
            finish();
        });

    }
    private void setUpBus() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference busesCollection = db.collection("Buses");
        busesCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int startPosition = bus.size();
                int itemCount = 0;

                for (QueryDocumentSnapshot document : task.getResult()) {
                    String busNumber = document.getString("busNumber");
                    String destination = document.getString("destination");
                    String price = document.getString("price");
                    String time = document.getString("time");

                    bus.add(new BusList(destination, time, busNumber, price));
                    itemCount++;
                }
                adapter.notifyItemRangeInserted(startPosition, itemCount);
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    String errorMessage = "Firestore query failed: " + exception.getMessage();
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, SeatAdminView.class);
        intent.putExtra("Destination",bus.get(position).getDestination());
        intent.putExtra("Price", bus.get(position).getPrice());
        intent.putExtra("Bus Number", bus.get(position).getBusNumber());
        intent.putExtra("Departure Time", bus.get(position).getTime());

        startActivity(intent);
    }
}