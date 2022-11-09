package edu.uiuc.cs427app;

public class City {
    private String cityId;
    private String cityName;
    private double latitude;
    private double longitude;

    // Constructor for City Class
    public City(String cityId, String cityName, double latitude, double longitude) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Gets city
    public String getCityId() {
        return cityId;
    }

    // Sets cityid property for City class
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    // Returns city name
    public String getCityName() {
        return cityName;
    }

    // Sets city name property for City Class
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    // Gets latitude property of City Class
    public double getLatitude() {
        return latitude;
    }

    // Sets latitude property of City Class
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // Gets longitude property of City Class
    public double getLongitude() {
        return longitude;
    }

    // Sets longitude property of City Class
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Changes toString method of Class to get all of the properties of City in one string for parsing
    @Override
    public String toString() {
        return "City{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
