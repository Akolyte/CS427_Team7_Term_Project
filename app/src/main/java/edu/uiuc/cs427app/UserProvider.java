package edu.uiuc.cs427app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

// Class to retrieve all user specific information from sharedPreferences
public class UserProvider {
    private final SharedPreferences sharedPreferences;
    private final String CITIES = "cities";

    // Constructor
    public UserProvider(Context ctx, String user) {
        this.sharedPreferences = ctx.getSharedPreferences(user, 0);
    }

    // Returns all cities associated with a user
    public Set<String> getCities() {
        return sharedPreferences.getStringSet(CITIES, new HashSet<>());
    }

    // Adds a city to the set of cities associated with a user
    public void addCity(String city) {
        Set<String> cities = getCities();
        cities.add(city);
        sharedPreferences
                .edit()
                .putStringSet(CITIES, cities)
                .commit();
    }

    // Adds a city to the set of cities associated with a user
    public void removeCity(String city) {
        Set<String> cities = getCities();
        cities.remove(city);
        sharedPreferences
                .edit()
                .putStringSet(CITIES, cities)
                .commit();
    }
}
