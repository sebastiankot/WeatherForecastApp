package kots.weatherforecastapp.weather.dto;

import java.util.List;

public record WeekWeatherDto(
        double latitude,
        double longitude,
        List<WeatherDayDto> weatherByDay
) {

}
