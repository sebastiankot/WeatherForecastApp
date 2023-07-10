package kots.weatherforecastapp.weather;

import java.time.LocalDate;
import java.time.LocalDateTime;

record WeatherDayDto(
        LocalDate date,
        LocalDateTime sunrise,
        LocalDateTime sunset,
        String averageRainfall
) {

}
