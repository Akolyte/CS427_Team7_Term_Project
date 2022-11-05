package edu.uiuc.cs427app;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    private UserProvider userProvider;
    private String username;
    private String GOOGLE_MAPS_IFRAME_TEMPLATE = "<iframe src=\"https://maps.google.com/maps?q=%s,%s&t=&z=15&ie=UTF8&iwloc=&output=embed\"></iframe>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        userProvider = new UserProvider(this, username);
        userProvider.initializeTheme(userProvider, this);
        setContentView(R.layout.activity_map);


        initializeMapWebView();
    }

    private void initializeMapWebView() {
        WebView map = (WebView) findViewById(R.id.mapWebView);
        map.getSettings().setJavaScriptEnabled(true);
        map.getSettings().setBuiltInZoomControls(true);
        map.getSettings().setDisplayZoomControls(false);
        map.getSettings().setDomStorageEnabled(true);
        map.setWebViewClient(new WebViewClient());

        String html = buildGoogleMapsIframe();
        map.loadData(html, "text/html", null);
    }

    private String buildGoogleMapsIframe() {
        City city = getCity();
        return String.format(GOOGLE_MAPS_IFRAME_TEMPLATE, city.getLatitude(), city.getLongitude());
    }

    private City getCity() {
        String cityId = getIntent().getStringExtra("city").toString();
        City city = userProvider.getCityById(cityId);
        return city;
    }
}
