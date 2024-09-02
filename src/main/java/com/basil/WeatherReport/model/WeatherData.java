package com.basil.WeatherReport.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "weather_data")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String country;
    private String observationTime;
    private int temperature;
    private int weatherCode;
    private String weatherDescription;
    private int windSpeed;
    private int windDegree;
    private String windDirection;
    private int pressure;
    private int humidity;
    private int cloudCover;
    private int feelsLike;
    private int uvIndex;
    private int visibility;
    private boolean isDay;
    private String landmarks;
    private String mapLink;
    private int aqi;
    private int pollenCount;
    private String historicalComparison;
    private String shortTermForecast;
    private String suggestedActivities;
    private String localEvents;
    private String localPractices;
    private String travelAdvisories;
    private String safetyTips;
    private String timestamp;
}