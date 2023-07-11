package kots.weatherforecastapp.weather.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record WeatherDayDto(
        LocalDate date,
        LocalTime sunrise,
        LocalTime sunset,
        double averageRainfall
) {

}
