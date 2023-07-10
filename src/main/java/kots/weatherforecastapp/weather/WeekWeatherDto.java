package kots.weatherforecastapp.weather;

import java.util.List;

record WeekWeatherDto(
        double latitude,
        double longitude,
        List<WeatherDayDto> weatherByDay
) {

}
