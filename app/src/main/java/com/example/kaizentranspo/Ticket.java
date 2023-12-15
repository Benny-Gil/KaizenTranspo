package com.example.kaizentranspo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.kaizentranspo.classes.TicketList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * This class is for the ticket page
 */
public class Ticket extends AppCompatActivity implements RecyclerViewInterface {
    ArrayList<TicketList> ticket = new ArrayList<>();
    Ticket_RecyclerViewAdapter adapter;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        setTicket();
        androidx.recyclerview.widget.RecyclerView recyclerView = findViewById(R.id.ticketrecyclerView);
        adapter = new Ticket_RecyclerViewAdapter(this, ticket, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button bookButton = findViewById(R.id.buttonBook);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BusSelection.class);

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

        FirebaseUser user = mAuth.getCurrentUser();
        CollectionReference collectionReference= fStore.collection("Users").document(user.getUid()).collection("tickets");
        collectionReference.get().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               int startPosition = ticket.size();
               int itemCount = 0;

               for (QueryDocumentSnapshot document : task.getResult()) {
                   String busDestination = document.getString("Destination");
                   String departure = document.getString("Departure");
                   String busNumber= document.getString("Bus Number");
                   String selectedSeat = document.getString("Selected Seat");

                   ticket.add(new TicketList(busDestination,departure,busNumber,selectedSeat));
                   itemCount++;
               }
               adapter.notifyItemRangeInserted(startPosition, itemCount);
           }else {
               Exception exception = task.getException();
           }
        });

    }
    @Override
    public void onClick(int position) {
        
    }
}
