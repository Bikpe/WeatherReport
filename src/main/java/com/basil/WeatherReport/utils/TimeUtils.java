package com.basil.WeatherReport.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static String convertEpochToHumanReadable(long epoch, String timezone) {
        return Instant.ofEpochSecond(epoch)
                .atZone(ZoneId.of(timezone))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getCurrentEpoch() {
        return String.valueOf(Instant.now().getEpochSecond());
    }
}