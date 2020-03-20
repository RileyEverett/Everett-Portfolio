package edu.pdx.cs410j.riley34.airlineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;

public class FlightSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainActivity();
            }
        });

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton();
            }
        });
    }

    public void returnToMainActivity() {
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void submitButton() {
        TextView textWindow = findViewById(R.id.search_result);
        textWindow.setText("");

        ArrayList<String> flightStrings = getIntent().getStringArrayListExtra("flightStrings");
        if (flightStrings != null) {
            EditText source = findViewById(R.id.source);
            String sourceString = source.getText().toString().toUpperCase();
            EditText dest = findViewById(R.id.dest);
            String destString = dest.getText().toString().toUpperCase();

            if (sourceString.length() != 3 || destString.length() != 3) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_flight_search), "Airport Codes Must Be Three Letters Long", Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else if (!sourceString.matches("^[a-zA-Z]*$") || !destString.matches("^[a-zA-Z]*$")) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_flight_search), "Airport Codes Can Only Be Letters", Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else if (sourceString.equalsIgnoreCase(destString)) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_flight_search), "Airport Codes Must Be Different", Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {

                boolean flightFoundFlag = false;

                for (String flight : flightStrings) {
                    if (flight.contains(sourceString) && flight.contains(destString)) {
                        textWindow.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        textWindow.append("\n" + flight + "\n");
                        flightFoundFlag = true;
                    }
                }

                if (!flightFoundFlag) {
                    textWindow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textWindow.append("\n No Flights Found \n");
                }
            }
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_flight_search), "No Airline Selected", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

}
