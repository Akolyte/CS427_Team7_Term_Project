package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private String username;
    private UserProvider userProvider;

    // Initializes Details Activity with information about the weather in city
    // associated with this activity.
    // Sets up UserProvider
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        userProvider = new UserProvider(this, username);
        userProvider.initializeTheme(userProvider, this);
        setContentView(R.layout.activity_details);
        setTitle(getString(R.string.app_name)+'-'+username);

        // Process the Intent payload that has opened this Activity and show the information accordingly
        String cityName = getIntent().getStringExtra("city").toString();
        String welcome = "Welcome to the "+cityName;
        String cityWeatherInfo = "Detailed information about the weather of "+cityName;

        // Initializing the GUI elements
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        TextView cityInfoMessage = findViewById(R.id.cityInfo);

        welcomeMessage.setText(welcome);
        cityInfoMessage.setText(cityWeatherInfo);
        // Get the weather information from a Service that connects to a weather server and show the results

        Button buttonMap = findViewById(R.id.mapButton);
        buttonMap.setOnClickListener(this);

    }

    // Handles onclick events for the Details Activity
    // Handles the  deleteLocationButton and map button
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deleteLocationButton:
                removeLocation();
                break;
            case R.id.mapButton:
                openMap();
                break;
        }
    }

    // Retrieves the city associated with this instance of the DetailsActivity
    // and removes it from the user's list of cities.
    // Redirects to the Main Activity when called.
    private void removeLocation() {
        String city = getIntent().getStringExtra("city").toString();
        userProvider.removeCity(city);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    // Opens the Map Activity
    private void openMap() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}

