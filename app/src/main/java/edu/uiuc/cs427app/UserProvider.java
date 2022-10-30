package edu.uiuc.cs427app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// Class to retrieve all user specific information from sharedPreferences
public class UserProvider {
    private final SharedPreferences sharedPreferences;
    private final String CITIES = "cities";
    private final String THEME = "theme";
    private String username;

    // Constructor
    public UserProvider(Context ctx, String user) {
        this.sharedPreferences = ctx.getSharedPreferences(user, 0);
        this.username = user;
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

    // Returns the integer value of the theme selected by the user
    public int getTheme(){
        return sharedPreferences.getInt(THEME, 0);
    }


    // Assigns the given integer value as the preferred theme of the user
    public void selectTheme(int themeInt){
        sharedPreferences
                .edit()
                .putInt(THEME, themeInt)
                .commit();
    }

    // Restarts activity and sets the theme based on the users preference
    public void updateTheme(UserProvider userProvider, Activity activity){
        int themeID = userProvider.getTheme();
        activity.finish();
        Intent intent = new Intent(activity, activity.getClass());
        intent.putExtra("username",this.username);
        activity.startActivity(intent);
        switch (themeID) {
            case 1:
                activity.setTheme(R.style.Theme_UIUC);
                break;
            case 2:
                activity.setTheme(R.style.Theme_UArizona);
                break;
            case 3:
                activity.setTheme(R.style.Theme_LSU);
                break;
        }
    }
    
    // At the beginning of an activity, sets the theme based on the user's preference
    public void initializeTheme(UserProvider userProvider, Activity activity){
        int themeID = userProvider.getTheme();
        switch (themeID) {
            default:
            case 1:
                activity.setTheme(R.style.Theme_UIUC);
                break;
            case 2:
                activity.setTheme(R.style.Theme_UArizona);
                break;
            case 3:
                activity.setTheme(R.style.Theme_LSU);
                break;
        }
    }

}
