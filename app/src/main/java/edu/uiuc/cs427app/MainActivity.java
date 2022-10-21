package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import edu.uiuc.cs427app.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private final String SHOW_DETAILS = "SHOW DETAILS";

    private String username = "chris";
    private UserProvider userProvider;

    // Sets up the MainActivity and constructs the UserProvider
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userProvider = new UserProvider(this, username);

        Set<String> cities = userProvider.getCities();

        if (cities == null) {
            userProvider.addCity("Champaign");
            userProvider.addCity("Chicago");
            userProvider.addCity("Dallas");
            userProvider.addCity("Los Angeles");
        }

        cities = userProvider.getCities();
        for (String city : cities) {
            createCityLayout(city);
        }
    }

    // onClick handler for the MainActivity
    // Handles "Add A Location" button and all "Show Details" buttons for every city
    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.buttonAddLocation) {
            intent = new Intent(this, AddLocationActivity.class);
        } else {
            String city = (String)view.getTag();
            intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("city", city);
        }
        startActivity(intent);
    }

    // Creates and formats horizontal linear layout to house the city text and "Show Details" button
    private void createCityLayout(String city) {
        LinearLayout childLayout = new LinearLayout(this);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView text = createCityTextView(city);
        childLayout.addView(text);

        View spacer = new View(this);
        spacer.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));
        childLayout.addView(spacer);

        Button showDetailsButton = createShowDetailsButton(city);
        childLayout.addView(showDetailsButton);

        LinearLayout parentLayout = findViewById(R.id.citiesLayout);
        parentLayout.addView(childLayout);
    }

    // Creates the "Show Details" button with proper formatting
    private Button createShowDetailsButton(String city) {
        Button showDetailsButton = new MaterialButton(this);
        showDetailsButton.setText(SHOW_DETAILS);
        showDetailsButton.setOnClickListener(this);
        showDetailsButton.setTag(city);
        showDetailsButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        return showDetailsButton;
    }

    // Creates the text which display the city name with proper formatting
    private TextView createCityTextView(String city) {
        TextView text = new MaterialTextView(this);
        text.setText(city);
        text.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        return text;
    }
}

