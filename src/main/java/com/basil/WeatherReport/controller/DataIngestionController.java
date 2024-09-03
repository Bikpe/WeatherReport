package com.basil.WeatherReport.controller;

import com.basil.WeatherReport.config.WeatherApiConfig;
import com.basil.WeatherReport.model.WeatherData;
import com.basil.WeatherReport.model.WeatherResponse;
import com.basil.WeatherReport.service.DataDeliveryService;
import com.basil.WeatherReport.service.DataIngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.basil.WeatherReport.constant.Constants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weather/")
public class DataIngestionController {

    private final DataIngestionService dataIngestionService;
    private final DataDeliveryService dataDeliveryService;
    private final RestTemplate restTemplate;
    private final WeatherApiConfig weatherApiConfig;

    @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/ingest/{id}")
    public WeatherData ingestWeatherData(@PathVariable Long id) {
        log.info("Entered ingestWeatherData method");
        WeatherData weatherData = dataIngestionService.getWeatherDataById(id);
        log.info("Exited ingestWeatherData method");
        return weatherData;
    }

    @GetMapping("/fetch")
    public WeatherData fetchAndIngestWeatherData() {
        log.info("Entered fetchAndIngestWeatherData method");
        String url = weatherApiConfig.getApiUrl() + ACCESS_KEY + weatherApiConfig.getApiKey() + QUERY + weatherApiConfig.getQuery();
        WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);
        WeatherData weatherData = dataIngestionService.transformAndSaveWeatherData(weatherResponse);
        log.info("Exited fetchAndIngestWeatherData method with weatherData: {}", weatherData);
        return weatherData;
    }

    @GetMapping("/backfill")
    public void backfillData() {
        Long lastValidTimestamp = dataDeliveryService.getLastFetchedTimestamp();
        dataIngestionService.backfillDataGaps(lastValidTimestamp);
    }

    @Scheduled(initialDelay = ZERO_DELAY, fixedRate = ONE_HOUR)
    public void scheduledFetchAndIngestWeatherData() {
        fetchAndIngestWeatherData();
    }

    @Scheduled(initialDelay = ZERO_DELAY, fixedRate = ONE_HOUR)
    public void scheduledDataDelivery() {
        List<WeatherData> dataIncrements = dataDeliveryService.fetchDataIncrements();
        dataDeliveryService.sendDataToS3Bucket(dataIncrements);
    }
}