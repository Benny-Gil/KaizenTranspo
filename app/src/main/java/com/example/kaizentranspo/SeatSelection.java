package com.example.kaizentranspo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SeatSelection extends AppCompatActivity {

    private Button lastClickedButton;
    private Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        seats();
        bookButton = findViewById(R.id.bookButton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastClickedButton != null) {
                    System.out.println("Seat" + lastClickedButton.getText() + "");
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
        }
    }

}