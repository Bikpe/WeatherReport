package com.basil.WeatherReport.repository;

import com.basil.WeatherReport.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    @Query("SELECT wd FROM WeatherData wd WHERE wd.timestamp > :lastFetchedTimestamp")
    List<WeatherData> findDataIncrements(@Param("lastFetchedTimestamp") Long lastFetchedTimestamp);

    @Query("SELECT MAX(wd.timestamp) FROM WeatherData wd")
    Long findLastFetchedTimestamp();

    @Modifying
    @Transactional
    @Query("UPDATE WeatherData wd SET wd.timestamp = :timestamp WHERE wd.id = :id")
    void updateLastFetchedTimestamp(@Param("timestamp") Long timestamp, @Param("id") Long id);
}