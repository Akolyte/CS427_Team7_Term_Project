package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener {
    private String username;
    private City city;
    private UserProvider userProvider;
    private AutocompleteSupportFragment autocompleteFragment;
    private static final String TAG = AddLocationActivity.class.getSimpleName();
    // Sets up AddLocationActivity and constructs UserProvider
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        userProvider = new UserProvider(this, username);
        userProvider.initializeTheme(userProvider, this);
        setContentView(R.layout.activity_add_location);
        setTitle(getString(R.string.app_name)+'-'+username);
        initializeAutocompleteFragment();
    }

    // Handles onclick events associated with this Activity
    // For this activity there should only be one button to add a new location
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitNewLocation:
                addLocation();
                break;
        }
    }

    // Retrieves city information from thew new city form and stores it
    // Returns to Main Activity when complete
    private void addLocation() {
        userProvider.addCity(city);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    // Initialize an autocompleteFragment which can return city name predictions
    // when user inputs a string
    private void initializeAutocompleteFragment() {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.app_key));
        }
        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));


        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                city = new City(place.getId(), place.getName(), place.getLatLng().latitude, place.getLatLng().longitude);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }
            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

}
