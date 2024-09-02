package com.basil.WeatherReport.utils;

public class WeatherContextUtils {
    public static String getWeatherDescription(int weatherCode) {
        // Add logic to map weather codes to descriptions
        return "Haze"; // Example
    }

    public static String getWindDirectionDescription(int windDegree) {
        // Add logic to map wind degrees to directions
        return "West-Northwest"; // Example
    }

    public static String getUvIndexDescription(int uvIndex) {
        // Add logic to describe UV index
        return "High"; // Example
    }
}