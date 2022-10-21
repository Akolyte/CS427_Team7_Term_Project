package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener {
    private String username = "chris";
    private UserProvider userProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        userProvider = new UserProvider(this, username);
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
        TextView text = (TextView)findViewById(R.id.cityInput);
        String newCity = text.getText().toString();
        userProvider.addCity(newCity);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
