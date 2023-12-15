package com.example.kaizentranspo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kaizentranspo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SeatAdminView extends AppCompatActivity {
    private Button lastClickedButton;
    private Button backButton;
    private Button bookButton;
    private String price;
    private String destinationText;
    private String departure;
    private String selectedSeat;
    private String busNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);


        busNumber = getIntent().getStringExtra("Bus Number");
        seats();
        Log.i(TAG,busNumber);
        price = getIntent().getStringExtra("Price");
        TextView priceUI = findViewById(R.id.price);
        priceUI.setText(price);

        destinationText = getIntent().getStringExtra("Destination");

        TextView destination = findViewById(R.id.destination);
        destination.setText(destinationText);

        departure = getIntent().getStringExtra("Departure Time");
        TextView time = findViewById(R.id.departureTime);
        time.setText("Departure Time: " + departure);

        busNumber = getIntent().getStringExtra("Bus Number");
        TextView num = findViewById(R.id.bus_num_selection);
        num.setText(busNumber);


        // Back button
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), BusSelection.class);
                //startActivity(intent);
                finish();
            }
        });
        bookButton = findViewById(R.id.bookButton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastClickedButton != null) {
                    selectedSeat = ("seat" + lastClickedButton.getText());
                    Intent intent = new Intent(getApplicationContext(), AdminBookingView.class);
                    intent.putExtra("selectedSeat", selectedSeat);
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

            // Check if the document for the seat exists
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference seatCollection = db.collection("Buses").document(busNumber).collection("seats");
            String seatNumber = "seat" + i;

            seatCollection.document(seatNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            // Document exists, proceed with other checks
                            Boolean isTaken = document.getBoolean("isTaken");

                            if (document.contains("isTaken") && isTaken != null && isTaken) {
                                button.setBackgroundResource(R.drawable.booked_seat);
                            }else{
                                //else - haven't tried
                                button.setOnClickListener(null);
                            }
                        }
                    }
                }
            });
        }
    }


}
