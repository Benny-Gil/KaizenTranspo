package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import com.example.kaizentranspo.AdminManageBus;
import com.example.kaizentranspo.BusList;
import com.example.kaizentranspo.Bus_RecyclerViewAdapter;
import com.example.kaizentranspo.MainActivity;
import com.example.kaizentranspo.R;
import com.example.kaizentranspo.RecyclerViewInterface;
import com.example.kaizentranspo.SeatAdminView;
import com.example.kaizentranspo.Ticket;
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

        Button ticketButton = findViewById(R.id.buttonTicket);

        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
        //did not renamed
        ticketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminManageBus.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
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
            }
        });
    }


    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, SeatAdminView.class);

        intent.putExtra("Bus Number", bus.get(position).getBusNumber());

        //just added
        intent.putExtra("Price", bus.get(position).getPrice());
        intent.putExtra("Bus Number", bus.get(position).getBusNumber());
        intent.putExtra("Departure Time", bus.get(position).getTime());

        startActivity(intent);
    }
}