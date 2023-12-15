package com.example.kaizentranspo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Receipt extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    int counterValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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

        seat.setText(selectedSeat);

        thanks.setVisibility(View.INVISIBLE);


        // Retrieve the "Counter" value from Firestore It is like a reference number too keep track of tickets
        DocumentReference ticketReference = fStore.collection("TicketCounter").document("Counter");
        ticketReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    counterValue = document.getLong("Counter").intValue();
                } else {
                    // Handle the case where the document does not exist
                    counterValue = 0; // Set a default value if needed
                }
            } else {
                // Handle exceptions during the document retrieval
                counterValue = 0; // Set a default value if needed
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
        cancelButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), BusSelection.class);
            startActivity(intent1);
            finish();

        });
        confirmButton.setOnClickListener(v -> {
            cancelButton.setVisibility(View.INVISIBLE);
            confirmButton.setVisibility(View.INVISIBLE);
            thanks.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent12 = new Intent(getApplicationContext(), Ticket.class);
                    //adding ticket details to database
                    FirebaseUser user = mAuth.getCurrentUser();
                    DocumentReference ticketReference1 = fStore.collection("TicketCounter").document("Counter");
                    counterValue+=1;
                    DocumentReference documentReference = fStore.collection("Users").document(user.getUid()).collection("tickets").document(String.valueOf(counterValue));
                    DocumentReference seatReference = fStore.collection("Buses").document(busNumber).collection("seats").document(selectedSeat);

                    Map<String, Integer> counter = new HashMap<>();
                    counter.put("Counter", counterValue);
                    ticketReference1.set(counter);
                    Map<String, Object> ticketInfo = new HashMap<>();
                    ticketInfo.put("Bus Number", busNumber);
                    ticketInfo.put("Destination", destinationText);
                    ticketInfo.put("Departure", departure);
                    ticketInfo.put("Selected Seat", selectedSeat);

                    documentReference.set(ticketInfo);

                    Map<String, Object> seatUpdate = new HashMap<>();
                    seatUpdate.put("isTaken",true);
                    seatUpdate.put("reserverName",user.getEmail());
                    seatReference.set(seatUpdate);

                    startActivity(intent12);
                }
            }, 1500);
        });
    }
}
