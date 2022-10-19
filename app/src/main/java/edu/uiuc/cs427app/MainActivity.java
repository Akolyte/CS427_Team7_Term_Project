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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private List<String> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities = new ArrayList<String>();
        cities.add("Champaign");
        cities.add("Chicago");
        cities.add("Los Angeles");
        cities.add("Dallas");

        for (String city : cities) {
            createCityLayout(city);
        }

        // Initializing the UI components
        // The list of locations should be customized per user (change the implementation so that
        // buttons are added to layout programmatically
//        Button buttonChampaign = findViewById(R.id.buttonChampaign);
//        Button buttonChicago = findViewById(R.id.buttonChicago);
//        Button buttonLA = findViewById(R.id.buttonLA);
//        Button buttonNew = findViewById(R.id.buttonAddLocation);
//
//        buttonChampaign.setOnClickListener(this);
//        buttonChicago.setOnClickListener(this);
//        buttonLA.setOnClickListener(this);
//        buttonNew.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String city = (String)view.getTag();
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("city", city);
        startActivity(intent);
//        switch (view.getId()) {
//            case R.id.buttonChampaign:
//                intent = new Intent(this, DetailsActivity.class);
//                intent.putExtra("city", "Champaign");
//                startActivity(intent);
//                break;
//            case R.id.buttonChicago:
//                intent = new Intent(this, DetailsActivity.class);
//                intent.putExtra("city", "Chicago");
//                startActivity(intent);
//                break;
//            case R.id.buttonLA:
//                intent = new Intent(this, DetailsActivity.class);
//                intent.putExtra("city", "Los Angeles");
//                startActivity(intent);
//                break;
//            case R.id.buttonAddLocation:
//                // Implement this action to add a new location to the list of locations
//                break;
//        }
    }

    private void createCityLayout(String city) {
        LinearLayout parentLayout = findViewById(R.id.parentLayout);


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

