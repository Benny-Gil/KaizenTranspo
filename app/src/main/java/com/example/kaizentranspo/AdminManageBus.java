package com.example.kaizentranspo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AdminManageBus extends AppCompatActivity implements RecyclerViewInterface{
    ArrayList<BusList> bus = new ArrayList<>();
    Bus_RecyclerViewAdapter adapter;
    Button remove;
    ImageButton add;
    private ConstraintLayout busDetailsHolder;
    ImageButton close;
    TextView destination, price, busNumber, departureTime;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_bus);

        setUpBus();
        fStore=FirebaseFirestore.getInstance();
        androidx.recyclerview.widget.RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        adapter = new Bus_RecyclerViewAdapter(this, bus, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button buttonBook = findViewById(R.id.buttonBook);

        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
        buttonBook.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(), AdminPage.class);
            startActivity(intent);
            finish();
        });

        busDetailsHolder = findViewById(R.id.busDetailsHolder);
        busDetailsHolder.setVisibility(View.INVISIBLE);

        close = findViewById(R.id.closeButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busDetailsHolder.setVisibility(View.INVISIBLE);
            }
        });
        add = findViewById(R.id.addBusButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminCreateBus.class);
                startActivity(intent);
            }
        });

    }
/**
 *
 * Copied from the busSelection
 *
 * */
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
        remove = findViewById(R.id.removeButton);
        String busNum;
        busNum = bus.get(position).getBusNumber();
        busNumber = findViewById(R.id.busNumber_admin);
        busNumber.setText(busNum);

        busDetailsHolder.setVisibility(View.VISIBLE);

        destination = findViewById(R.id.destination_admin);
        price = findViewById(R.id.price_admin);
        departureTime = findViewById(R.id.departureTime_admin);

        destination.setText(bus.get(position).getDestination());
        price.setText(bus.get(position).getPrice());
        departureTime.setText(bus.get(position).getTime());

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference cf =fStore.collection("Buses");
                String busNumberToDelete = bus.get(position).getBusNumber();

                // Delete the document with the specified busNumber
                cf.document(busNumberToDelete)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Document successfully deleted
                                Toast.makeText(AdminManageBus.this, "Bus deleted successfully", Toast.LENGTH_SHORT).show();
                                busDetailsHolder.setVisibility(View.INVISIBLE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle any errors that occurred during the deletion
                                Toast.makeText(AdminManageBus.this, "Error deleting bus: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}

