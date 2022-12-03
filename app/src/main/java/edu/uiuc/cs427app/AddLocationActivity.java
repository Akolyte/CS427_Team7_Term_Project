package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

// Activity class used for location searching and adding
public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener {
    private String username;
    private UserProvider userProvider;
    private static final String TAG = AddLocationActivity.class.getSimpleName();
    private CityProxy cityProxy;

    // Sets up AddLocationActivity and constructs UserProvider
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        userProvider = new UserProvider(this, username);
        userProvider.initializeTheme(userProvider, this);
        setContentView(R.layout.activity_add_location);
        setTitle(getString(R.string.app_name)+'-'+username);
        this.cityProxy = new CityProxy(this);
    }

    // Handles onclick events associated with this Activity
    // For this activity there should only be one button to add a new location
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addLocationSubmit:
                addLocation();
                break;
        }
    }

    // Retrieves city information from thew new city form and stores it
    // Returns to Main Activity when complete
    private void addLocation() {
        TextView locationTextView = findViewById(R.id.locationInput);
        String location = locationTextView.getText().toString();
        City aCity = this.cityProxy.getCityFromLocationString(location);
        userProvider.addCity(aCity);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}
