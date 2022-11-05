package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;
import java.util.concurrent.ExecutionException;

import org.json.*;


public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private String username;
    private UserProvider userProvider;
    TextView weatherText;
    String key;
    String j2;
    String j1;

    // Initializes Details Activity with information about the weather in city
    // associated with this activity.
    // Sets up UserProvider
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        userProvider = new UserProvider(this, username);
        userProvider.initializeTheme(userProvider, this);
        setContentView(R.layout.activity_details);
        setTitle(getString(R.string.app_name)+'-'+username);

        // Process the Intent payload that has opened this Activity and show the information accordingly
        String cityName = getIntent().getStringExtra("city").toString();
        String welcome = "Welcome to the "+cityName;
        String cityWeatherInfo = "Detailed information about the weather of "+cityName;

        // Initializing the GUI elements
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        //TextView cityInfoMessage = findViewById(R.id.cityInfo);

        welcomeMessage.setText(welcome);
        //cityInfoMessage.setText(cityWeatherInfo);
        weatherText = (TextView) findViewById(R.id.cityInfo);
        readURL task = new readURL();
        task.execute("https://dataservice.accuweather.com/locations/v1/cities/search?q="+cityName+"&apikey=58ccmwU7fOT14BhHzHx6HzOKtLeSNA6O");
        // Get the weather information from a Service that connects to a weather server and show the results
    }

    // Handles onclick events for the Details Activity
    // Handles the deleteLocationButton
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deleteLocationButton:
                removeLocation();
                break;
        }
    }

    // Retrieves the city associated with this instance of the DetailsActivity
    // and removes it from the user's list of cities.
    // Redirects to the Main Activity when called.
    private void removeLocation() {
        String city = getIntent().getStringExtra("city").toString();
        userProvider.removeCity(city);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);

    }

    public class readURL extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            String data = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream input = urlConnection.getInputStream();
                InputStreamReader inputReader = new InputStreamReader(input);
                int info = inputReader.read();
                while (info != -1) {
                    char current = (char) info;
                    data += current;
                    info = inputReader.read();
                }
                return data;
            }
            catch (Exception e) {
                e.printStackTrace();
                return "URL READ FAILED";

            }
        }

        @Override
        protected void onPostExecute(String urlInfo) {

            try {
                JSONArray keyArr = new JSONArray(urlInfo);
                key=keyArr.getJSONObject(0).getString("Key");
                try{
                    readURL weather = new readURL();
                    j2 = weather.execute("https://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=58ccmwU7fOT14BhHzHx6HzOKtLeSNA6O&language=en-us&details=true").get();
                    JSONArray weatherArray = new JSONArray(j2);
                    for (int i = 0; i<weatherArray.length(); i++){
                        JSONObject weatherPart = weatherArray.getJSONObject(i);
                        weatherText.setText("Weather: "+weatherPart.getString("WeatherText")+ "\n"
                                + "Temperature: "+weatherPart.getJSONObject("Temperature").getJSONObject("Imperial").getString("Value")+" F"+"\n"
                                +"RelativeHumidity: "+weatherPart.getString("RelativeHumidity")+"\n"
                                +"UVIndex: "+weatherPart.getString("UVIndexText"));
                    }
                }catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(urlInfo);

        }
    }
}





