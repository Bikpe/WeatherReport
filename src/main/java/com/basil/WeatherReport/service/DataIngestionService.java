package com.basil.WeatherReport.service;

import com.basil.WeatherReport.config.WeatherApiConfig;
import com.basil.WeatherReport.model.WeatherData;
import com.basil.WeatherReport.model.WeatherResponse;
import com.basil.WeatherReport.repository.WeatherDataRepository;
import com.basil.WeatherReport.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.basil.WeatherReport.constant.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataIngestionService {

    private final WeatherDataRepository weatherDataRepository;
    private final RestTemplate restTemplate;
    private final WeatherApiConfig weatherApiConfig;

    public WeatherData transformAndSaveWeatherData(WeatherResponse weatherResponse) {
        log.info("Entered transformAndSaveWeatherData method");
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(weatherResponse.getLocation().getName());
        weatherData.setCountry(weatherResponse.getLocation().getCountry());
        weatherData.setObservationTime(TimeUtils.convertEpochToHumanReadable(weatherResponse.getLocation().getLocaltime_epoch(), weatherResponse.getLocation().getTimezone_id()));
        weatherData.setTemperature(weatherResponse.getCurrent().getTemperature());
        weatherData.setWeatherCode(weatherResponse.getCurrent().getWeather_code());
        weatherData.setWeatherDescription(WeatherContextUtils.getWeatherDescription(weatherResponse.getCurrent().getWeather_code()));
        weatherData.setWindSpeed(weatherResponse.getCurrent().getWind_speed());
        weatherData.setWindDegree(weatherResponse.getCurrent().getWind_degree());
        weatherData.setWindDirection(WeatherContextUtils.getWindDirectionDescription(weatherResponse.getCurrent().getWind_degree()));
        weatherData.setPressure(weatherResponse.getCurrent().getPressure());
        weatherData.setHumidity(weatherResponse.getCurrent().getHumidity());
        weatherData.setCloudCover(weatherResponse.getCurrent().getCloudcover());
        weatherData.setFeelsLike(weatherResponse.getCurrent().getFeelslike());
        weatherData.setUvIndex(weatherResponse.getCurrent().getUv_index());
        weatherData.setVisibility(weatherResponse.getCurrent().getVisibility());
        weatherData.setDay(YES.equals(weatherResponse.getCurrent().getIs_day()));

        // Enrich data
        weatherData.setLandmarks(GeoContextUtils.getLandmarks(weatherResponse.getLocation().getName()));
        weatherData.setMapLink(GeoContextUtils.getMapLink(Double.parseDouble(weatherResponse.getLocation().getLat()), Double.parseDouble(weatherResponse.getLocation().getLon())));
        weatherData.setAqi(EnvironmentalDataUtils.getAirQualityIndex(weatherResponse.getLocation().getName()));
        weatherData.setPollenCount(EnvironmentalDataUtils.getPollenCount(weatherResponse.getLocation().getName()));
        weatherData.setHistoricalComparison(HistoricalDataUtils.getHistoricalWeatherComparison(weatherResponse.getLocation().getName(), weatherResponse.getLocation().getLocaltime()));
        weatherData.setShortTermForecast(PredictiveInsightsUtils.getShortTermForecast(weatherResponse.getLocation().getName()));
        weatherData.setSuggestedActivities(PredictiveInsightsUtils.suggestActivities(weatherResponse.getCurrent().getWeather_descriptions().get(0)));
        weatherData.setLocalEvents(CulturalInsightsUtils.getLocalEvents(weatherResponse.getLocation().getName()));
        weatherData.setLocalPractices(CulturalInsightsUtils.getLocalPractices(weatherResponse.getLocation().getName(), weatherResponse.getLocation().getLocaltime()));
        weatherData.setTravelAdvisories(TravelSafetyUtils.getTravelAdvisories(weatherResponse.getLocation().getName()));
        weatherData.setSafetyTips(TravelSafetyUtils.getSafetyTips(weatherResponse.getCurrent().getWeather_descriptions().get(0)));
        weatherData.setTimestamp(TimeUtils.getCurrentEpoch());

        weatherDataRepository.save(weatherData);
        log.info("Exited transformAndSaveWeatherData method with weatherData: {}", weatherData);
        return weatherData;
    }

    public WeatherData getWeatherDataById(Long id) {
        return weatherDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WeatherData not found for id: " + id));
    }

    public void backfillDataGaps(Long lastValidTimestamp) {
        Long currentTimestamp = Long.valueOf(TimeUtils.getCurrentEpoch());
        while (lastValidTimestamp < currentTimestamp) {
            String url = weatherApiConfig.getApiUrl() + ACCESS_KEY + weatherApiConfig.getApiKey() + QUERY + weatherApiConfig.getQuery() + "&timestamp=" + lastValidTimestamp;
            WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);
            transformAndSaveWeatherData(weatherResponse);
            lastValidTimestamp += ONE_HOUR; // Assuming data is fetched hourly
        }
    }
}