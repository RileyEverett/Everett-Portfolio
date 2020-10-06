package edu.pdx.cs410j.riley34.airlineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayFlights extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flights);

        displayFlightText();

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainActivity();
            }
        });
    }

    private void displayFlightText() {
        String flightText = getIntent().getStringExtra("printText");
        TextView printData = findViewById(R.id.print_data);
        printData.setText(flightText);
    }

    public void returnToMainActivity() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
