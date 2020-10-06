package edu.pdx.cs410j.riley34.airlineapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SwitchAirline extends AppCompatActivity {

    static final int SWITCH_AIRLINE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_airline);

        getDropDownNames();

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
        Intent intent = getIntent();
        Spinner airlineSpinner = findViewById(R.id.airline_spinner);
        String selectedAirline = airlineSpinner.getSelectedItem().toString();
        intent.putExtra("selectedAirline", selectedAirline);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void getDropDownNames() {
        ArrayList<String> airlineNames = getIntent().getStringArrayListExtra("airlineNames");
        if (airlineNames != null) {
            Spinner airlineSpinner = findViewById(R.id.airline_spinner);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, airlineNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            airlineSpinner.setAdapter(dataAdapter);
        }
    }

}


