package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener {
    private String username = "chris";
    private UserProvider userProvider;

    // Sets up AddLocationActivity and constructs UserProvider
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        userProvider = new UserProvider(this, username);
        userProvider.initializeTheme(userProvider, this);
        setContentView(R.layout.activity_add_location);
        setTitle(getString(R.string.app_name)+'-'+username);
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
        TextView text = (TextView)findViewById(R.id.cityInput);
        String newCity = text.getText().toString();
        userProvider.addCity(newCity);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}
