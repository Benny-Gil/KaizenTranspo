package com.example.kaizentranspo.classes;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

public class DatabaseResetter {

    private final FirebaseFirestore fStore;

    public DatabaseResetter() {
        // Initialize Firestore
        fStore = FirebaseFirestore.getInstance();
    }

    public void userTicketsReset() {
        CollectionReference collectionRef = fStore.collection("Users");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        userTicketsDelete(document.getId());
                    }
                }
            }
        });
    }

    public void busSeatReset() {
        CollectionReference collectionRef = fStore.collection("Buses");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        reset(document.getId());
                    }
                }
            }
        });
    }

    private void userTicketsDelete(String uid) {
        CollectionReference collectionRef = fStore.collection("Users").document(uid).collection("tickets");

        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    WriteBatch batch = fStore.batch();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        batch.delete(document.getReference());
                    }

                    batch.commit();
                }
            }
        });
    }


    private void reset(String busName) {
        CollectionReference seatsCollectionRef = fStore.collection("Buses").document(busName).collection("seats");
        seatsCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    WriteBatch batch = fStore.batch();

                    for (QueryDocumentSnapshot seatDocument : task.getResult()) {
                        if (seatDocument.contains("isTaken") && seatDocument.contains("reserverName")) {
                            // Create an update with the new value for isTaken
                            Map<String, Object> updateData = new HashMap<>();
                            updateData.put("isTaken", false);
                            updateData.put("reserverName", null);

                            // Add the update to the batch
                            batch.update(seatsCollectionRef.document(seatDocument.getId()), updateData);

                        }
                    }
                    batch.commit();
                }
            }
        });
    }

    public void ticketCounterReset() {
        DocumentReference documentReference = fStore.collection("TicketCounter").document("Counter");

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Integer> counterInfo = new HashMap<>();
                    counterInfo.put("Counter", 0);

                    documentReference.set(counterInfo);
                }
            }
        });
    }
}
