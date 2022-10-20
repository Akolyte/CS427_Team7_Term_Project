package edu.uiuc.cs427app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import edu.uiuc.cs427app.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private String username = "chris";
    private String preferencesKey = "cities";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Set<String> cities = getSharedPreferences(username, 0).getStringSet(preferencesKey, null);

        if (cities == null) {
            cities = new HashSet<>();
            cities.add("Champaign");
            cities.add("Chicago");
            cities.add("Los Angeles");
            cities.add("Dallas");
            getSharedPreferences(username, MODE_PRIVATE).edit().putStringSet(preferencesKey, cities).commit();
        }

        cities = getSharedPreferences(username, 0).getStringSet(preferencesKey, null);
        for (String city : cities) {
            createCityLayout(city);
        }
    }

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

    private void createCityLayout(String city) {
        LinearLayout parentLayout = findViewById(R.id.citiesLayout);

        LinearLayout childLayout = new LinearLayout(this);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(50)));

        TextView text = createCityTextView(city);
        childLayout.addView(text);
        Button showDetailsButton = createShowDetailsButton(city);
        childLayout.addView(showDetailsButton);
        parentLayout.addView(childLayout);
    }

    private Button createShowDetailsButton(String city) {
        Button showDetailsButton = new Button(this);
        showDetailsButton.setText("SHOW DETAILS");
        showDetailsButton.setOnClickListener(this);
        showDetailsButton.setTag(city);
        showDetailsButton.setLayoutParams(new LinearLayout.LayoutParams(
                dpToPx(45),
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        ));

        return showDetailsButton;
    }

    private TextView createCityTextView(String city) {
        TextView text = new TextView(this);
        text.setText(city);
        text.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        return text;
    }

    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density + + 0.5f);
    }
}

