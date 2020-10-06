package edu.pdx.cs410j.riley34.airlineapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class CreateAirline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_airline);

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
        EditText airlineName = findViewById(R.id.airline_name_editText);
        Intent intent = getIntent();
        intent.putExtra("airlineName", airlineName.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

}
