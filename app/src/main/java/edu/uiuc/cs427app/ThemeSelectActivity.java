package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ThemeSelectActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private String username;
    private UserProvider userProvider;
    private int check = 0;

    // Sets up the theme selection activity, as well as the drop down selection for themes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creates userProvider with username populated from LoginActivity
        username = getIntent().getStringExtra("username");
        userProvider = new UserProvider(this, username);

        // Sets up visuals for Activity
        userProvider.initializeTheme(userProvider, this);
        setContentView(R.layout.activity_theme_select);
        setTitle(getString(R.string.app_name)+'-'+username);

        // Sets up drop down menu using default spinner layout
        Spinner spinner = (Spinner) findViewById(R.id.buttonSelectTheme);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    // Moves to LoginActivity when "Confirm" button is selected
    @Override
    public void onClick(View view) {
        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        loginActivityIntent.putExtra("username",username);
        startActivity(loginActivityIntent);
    }

    // When a theme is selected, updates the preferred theme for the user and displays new theme
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
        if(++check > 1) {
            userProvider.selectTheme(position);
            userProvider.updateTheme(userProvider, this);
        }
    }

    // Empty method for when drop down menu is not being selected
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
