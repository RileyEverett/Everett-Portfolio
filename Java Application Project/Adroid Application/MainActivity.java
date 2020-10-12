package edu.pdx.cs410j.riley34.airlineapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import edu.pdx.cs410J.ParserException;

public class MainActivity extends AppCompatActivity {

    //request codes
    static final int CREATE_AIRLINE_REQUEST_CODE = 1;
    static final int SWITCH_AIRLINE_REQUEST_CODE = 2;
    static final int CREATE_FLIGHT_REQUEST_CODE = 3;
    static final int DISPLAY_FLIGHT_REQUEST_CODE = 4;
    static final int FLIGHT_SEARCH_REQUEST_CODE = 5;
    static final int READ_ME_REQUEST_CODE = 6;


    private Collection<Airline> airlines;
    private Airline currentSelectedAirline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        airlines = new ArrayList<>();
        currentSelectedAirline = null;

        Button createAirlineButton = findViewById(R.id.create_airline);
        createAirlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAirlineActivity();
            }
        });

        Button switchAirlineButton = findViewById(R.id.switch_airlines);
        switchAirlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchAirlineActivity();
            }
        });

        Button createFlightButton = findViewById(R.id.add_flight);
        createFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFlightActivity();
            }
        });

        Button displayFlightsButton = findViewById(R.id.display_all);
        displayFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayFlightsActivity();
            }
        });

        Button flightSearchButton = findViewById(R.id.flight_search);
        flightSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flightSearchActivity();
            }
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton();
            }
        });

        Button loadButton = findViewById(R.id.load_button);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadButton();
            }
        });

        FloatingActionButton readMeButton = findViewById(R.id.readme_Button);
        readMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readMeButton();
            }
        });


    }

    private void readMeButton() {
        Intent intent = new Intent(this, readMe.class);
        startActivityForResult(intent, READ_ME_REQUEST_CODE);
    }

    public void createAirlineActivity() {
        Intent intent = new Intent(this, CreateAirline.class);
        startActivityForResult(intent, CREATE_AIRLINE_REQUEST_CODE);
    }

    public void switchAirlineActivity() {
        Intent intent = new Intent(this, SwitchAirline.class);
        ArrayList<String> airlineNames = getAirlineNames();
        if (airlineNames != null) {
            intent.putStringArrayListExtra("airlineNames", airlineNames);
            startActivityForResult(intent, SWITCH_AIRLINE_REQUEST_CODE);
        }
    }

    public void createFlightActivity() {
        if (currentSelectedAirline != null) {
            Intent intent = new Intent(this, CreateFlight.class);
            startActivityForResult(intent, CREATE_FLIGHT_REQUEST_CODE);
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.airline_name), "No Airline Selected", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    public void DisplayFlightsActivity() {
        if (currentSelectedAirline != null) {
            Intent intent = new Intent(this, DisplayFlights.class);
            PrettyPrinter printer = new PrettyPrinter("null");
            String printText = printer.commandLinePrint(currentSelectedAirline);
            intent.putExtra("printText", printText);
            startActivityForResult(intent, DISPLAY_FLIGHT_REQUEST_CODE);
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.airline_name), "No Airline Selected", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    public void flightSearchActivity() {
        Intent intent = new Intent(this, FlightSearch.class);
        ArrayList<String> flightList = getFlightStrings();
        if (flightList != null) {
            intent.putStringArrayListExtra("flightStrings", flightList);
            startActivityForResult(intent, FLIGHT_SEARCH_REQUEST_CODE);
        }
    }

    public void saveButton() {
        try {
            XmlDumper dumper = new XmlDumper("null");
            ArrayList<String> airlineNames = new ArrayList<>();
            Object[] airlineList = airlines.toArray();
            boolean fileWrittenFlag = false;
            String xmlData;
            String filename = "";
            File currentFile;
            int index = 0;

            for (Object airline : airlineList) {
                Airline currentAirline = (Airline) airline;

                if (!airlineNames.contains(currentAirline.getName())) {
                    airlineNames.add(currentAirline.getName());
                    xmlData = dumper.dumpToString(currentAirline);

                    filename = "airlineApp" + index + ".xml";
                    currentFile = new File(getFilesDir(), filename);

                    FileOutputStream fileOutputStream = new FileOutputStream(currentFile);
                    fileOutputStream.write(xmlData.getBytes());
                    fileOutputStream.close();

                    index++;
                    fileWrittenFlag = true;
                }
            }

            if (fileWrittenFlag) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.airline_name), "Data Has Been Saved", Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.airline_name), "No Data To Save", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

        } catch (IOException e) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.airline_name), "No Data To Save", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }

    public void loadButton() {
        try {
            airlines = new ArrayList<>();
            currentSelectedAirline = null;

            XmlParser parser = new XmlParser("null");
            Airline currentAirline = null;
            ArrayList<String> airlineNames = new ArrayList<>();
            boolean fileReadFlag = false;
            int index = 0;

            String fileName = "airlineApp" + index + ".xml";
            File currentFile = new File(getFilesDir(), fileName);

            if (currentFile.exists()) {

                while (currentFile.exists()) {
                    InputStream inputStream = new FileInputStream(currentFile);
                    currentAirline = parser.parseStream(inputStream);

                    if (!airlineNames.contains(currentAirline.getName())) {
                        airlineNames.add(currentAirline.getName());
                        airlines.add(currentAirline);

                        index++;
                        fileName = "airlineApp" + index + ".xml";
                        currentFile = new File(getFilesDir(), fileName);
                        fileReadFlag = true;
                    } else {
                        boolean delete = currentFile.delete();
                        index++;
                    }
                }

                if (fileReadFlag) {
                    currentSelectedAirline = currentAirline;
                    TextView currentAirlineText = this.<TextView>findViewById(R.id.airline_name);
                    currentAirlineText.setText(currentSelectedAirline.getName());
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.airline_name), "Data Loaded From File", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }


            } else {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.airline_name), "No Data Found", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        } catch (ParserException | IOException e) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.airline_name), "No Data Found", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }


    private ArrayList<String> getAirlineNames() {
        ArrayList<String> names = new ArrayList<>();
        Object[] airlineList = airlines.toArray();
        if (airlineList.length > 0) {
            for (Object airline : airlineList) {
                Airline currentAirline = (Airline) airline;
                names.add(currentAirline.getName());
            }
            return names;
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.airline_name), "No Airlines Available, Please Create One", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return null;
        }
    }

    private ArrayList<String> getFlightStrings() {
        ArrayList<String> flights = new ArrayList<>();
        if (currentSelectedAirline != null) {
            Object[] flightList = currentSelectedAirline.getFlights().toArray();
            for (Object flight : flightList) {
                Flight currentFlight = (Flight) flight;
                flights.add(currentFlight.toString());

            }
            return flights;
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.airline_name), "No Airline Selected", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_AIRLINE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String airlineName = data.getStringExtra("airlineName");
                if (airlineName != null) {
                    Airline airline = new Airline(airlineName);
                    airlines.add(airline);
                    currentSelectedAirline = airline;
                    TextView currentAirlineText = findViewById(R.id.airline_name);
                    currentAirlineText.setText(airlineName);
                }
            }
        } else if (requestCode == SWITCH_AIRLINE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String airlineName = data.getStringExtra("selectedAirline");
                if (airlineName != null) {
                    Object[] airlineList = airlines.toArray();
                    for (Object airline : airlineList) {
                        Airline currentAirline = (Airline) airline;
                        if (currentAirline.getName().equalsIgnoreCase(airlineName)) {
                            currentSelectedAirline = currentAirline;
                            TextView currentAirlineText = findViewById(R.id.airline_name);
                            currentAirlineText.setText(airlineName);
                        }
                    }
                }
            }
        } else if (requestCode == CREATE_FLIGHT_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String flightNumber = data.getStringExtra("flightNumber");
                String source = data.getStringExtra("source");
                String departDate = data.getStringExtra("departDate");
                String departTime = data.getStringExtra("departTime");
                String departSuffix = data.getStringExtra("departSuffix");
                String dest = data.getStringExtra("dest");
                String arriveDate = data.getStringExtra("arriveDate");
                String arriveTime = data.getStringExtra("arriveTime");
                String arriveSuffix = data.getStringExtra("arriveSuffix");
                if (flightNumber != null && source != null && departDate != null &&
                        departTime != null && departSuffix != null && dest != null &&
                        arriveDate != null && arriveTime != null && arriveSuffix != null) {
                    Flight flight = new Flight(Integer.parseInt(flightNumber),
                            source.toUpperCase(), departDate, departTime, departSuffix,
                            dest.toUpperCase(), arriveDate, arriveTime, arriveSuffix);
                    currentSelectedAirline.addFlight(flight);
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
