package edu.uiuc.cs427app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class UserProvider {
    private final SharedPreferences sharedPreferences;
    private final String CITIES = "cities";

    public UserProvider(Context ctx, String user) {
        this.sharedPreferences = ctx.getSharedPreferences(user, 0);
    }

    public Set<String> getCities() {
        return sharedPreferences.getStringSet(CITIES, new HashSet<>());
    }

    public void addCity(String city) {
        Set<String> cities = getCities();
        cities.add(city);
        sharedPreferences
                .edit()
                .putStringSet(CITIES, cities)
                .commit();
    }

    public void removeCity(String city) {
        Set<String> cities = getCities();
        cities.remove(city);
        sharedPreferences
                .edit()
                .putStringSet(CITIES, cities)
                .commit();
    }
}
