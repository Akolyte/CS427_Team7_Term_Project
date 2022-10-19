package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
