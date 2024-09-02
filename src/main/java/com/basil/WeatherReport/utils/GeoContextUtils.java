package com.basil.WeatherReport.utils;

public class GeoContextUtils {
    public static String getLandmarks(String city) {
        // Add logic to fetch landmarks
        return "India Gate, Red Fort"; // Example
    }

    public static String getMapLink(double lat, double lon) {
        return "https://www.google.com/maps?q=" + lat + "," + lon;
    }
}