package edu.uiuc.cs427app;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

//    private final String LATITUDE = "32.7767° N";
//    private final String LONGITUDE = "96.7970° W";
    private UserProvider userProvider;
    private String username;

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

        String html = "<iframe src=\"https://maps.google.com/maps?q=32.7767,96.7970&t=&z=15&ie=UTF8&iwloc=&output=embed\"></iframe>";
        map.loadData(html, "text/html", null);
    }
}
