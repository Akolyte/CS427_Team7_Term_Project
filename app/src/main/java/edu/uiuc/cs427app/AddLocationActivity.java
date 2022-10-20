package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitNewLocation:
                addLocation();
                break;
        }
    }

    private void addLocation() {
        Set<String> cities = getSharedPreferences("chris", 0).getStringSet("cities", null);
        if (cities == null) {
            cities = new HashSet<>();
        }

        TextView text = (TextView)findViewById(R.id.cityInput);
        String newCity = text.getText().toString();

        cities.add(newCity);
        getSharedPreferences("chris", MODE_PRIVATE).edit().putStringSet("cities", cities).commit();


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
