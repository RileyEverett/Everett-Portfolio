package edu.pdx.cs410j.riley34.airlineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class CreateFlight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flight);

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
        EditText flightNumber = findViewById(R.id.flight_number);
        intent.putExtra("flightNumber", flightNumber.getText().toString());
        EditText source = findViewById(R.id.source);
        intent.putExtra("source", source.getText().toString());
        EditText departDate = findViewById(R.id.depart_date);
        intent.putExtra("departDate", departDate.getText().toString());
        EditText departTime = findViewById(R.id.depart_time);
        intent.putExtra("departTime", departTime.getText().toString());
        Spinner departSuffix = findViewById(R.id.depart_suffix);
        intent.putExtra("departSuffix", departSuffix.getSelectedItem().toString());
        EditText dest = findViewById(R.id.dest);
        intent.putExtra("dest", dest.getText().toString());
        EditText arriveDate = findViewById(R.id.arrive_date);
        intent.putExtra("arriveDate", arriveDate.getText().toString());
        EditText arriveTime = findViewById(R.id.arrive_time);
        intent.putExtra("arriveTime", arriveTime.getText().toString());
        Spinner arriveSuffix = findViewById(R.id.arrive_suffix);
        intent.putExtra("arriveSuffix", arriveSuffix.getSelectedItem().toString());

        try {
            if (!flightNumber.getText().toString().equals("") && !source.getText().toString().equals("")
                    && !departDate.getText().toString().equals("") && !departTime.getText().toString().equals("")
                    && !departSuffix.getSelectedItem().toString().equals("") && !dest.getText().toString().equals("")
                    && !arriveDate.getText().toString().equals("") && !arriveTime.getText().toString().equals("")
                    && !arriveSuffix.getSelectedItem().toString().equals("")) {

                String testString =  source.getText().toString();

                Flight flight = new Flight(Integer.parseInt(flightNumber.getText().toString()),
                        source.getText().toString().toUpperCase(), departDate.getText().toString(),
                        departTime.getText().toString(), departSuffix.getSelectedItem().toString(),
                        dest.getText().toString().toUpperCase(), arriveDate.getText().toString(),
                        arriveTime.getText().toString(), arriveSuffix.getSelectedItem().toString());

                setResult(RESULT_OK, intent);
                finish();
            } else {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.textView2), "Please Fill In All Fields", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        } catch (IllegalArgumentException ex) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.textView2), Objects.requireNonNull(ex.getMessage()), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }
}
