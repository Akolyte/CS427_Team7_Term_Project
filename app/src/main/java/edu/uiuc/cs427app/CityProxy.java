package edu.uiuc.cs427app;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityProxy {
    private Context ctx;

    public CityProxy(Context ctx) {
        this.ctx = ctx;
    }

    public City getCityFromLocationString(String location) {
        LatLng latLng = getLatitudeLongitude(location);
        return buildCity(location, latLng);
    }

    private LatLng getLatitudeLongitude(String location) {
        try {
            Geocoder geocoder = new Geocoder(ctx);
            List<Address> addresses= geocoder.getFromLocationName(location, 5); // get the found Address Objects

            List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
            for(Address a : addresses){
                if(a.hasLatitude() && a.hasLongitude()){
                    ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                }
            }
            return ll.get(0);
        } catch (IOException e) {
            // handle the exception
            return new LatLng(0, 0);
        }
    }

    private City buildCity(String location, LatLng latLng) {
        return new City("0", location, latLng.latitude, latLng.longitude);
    }
}
