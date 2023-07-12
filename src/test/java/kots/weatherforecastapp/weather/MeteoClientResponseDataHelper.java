package kots.weatherforecastapp.weather;

import kots.weatherforecastapp.client.openMeteo.dto.DailyMeteoClientResponse;
import kots.weatherforecastapp.client.openMeteo.dto.MeteoClientResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MeteoClientResponseDataHelper {

    public static final LocalDate EXAMPLE_DATE = LocalDate.of(2024, 5, 14);
    public static final LocalTime EXAMPLE_SUNRISE_TIME = LocalTime.of(12, 40);
    public static final LocalTime EXAMPLE_SUNSET_TIME = LocalTime.of(21, 22);
    public static final double ZERO_PRECIPITATION_SUM = 0.0;
    public static final int SIZE_LIST_DAILY_METEO = 2;
    public static final double LONGITUDE = 10.0;
    public static final double LATITUDE = 5.0;

    public static MeteoClientResponse createMeteoClientResponse() {
        return new MeteoClientResponse(LATITUDE, LONGITUDE, createDailyMeteoClientResponse());
    }

    public static DailyMeteoClientResponse createDailyMeteoClientResponse() {
        return new DailyMeteoClientResponse(
                List.of(EXAMPLE_DATE, LocalDate.now().minusDays(1)),
                List.of(ZERO_PRECIPITATION_SUM, 5.0),
                List.of(EXAMPLE_SUNRISE_TIME, LocalTime.NOON),
                List.of(EXAMPLE_SUNSET_TIME, LocalTime.now())
        );
    }
}
