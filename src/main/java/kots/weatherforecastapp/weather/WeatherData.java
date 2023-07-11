package kots.weatherforecastapp.weather;

import java.time.LocalDate;
import java.time.LocalTime;

record WeatherData(
        LocalDate data,
        double precipitationSum,
        LocalTime sunrise,
        LocalTime sunset
) {

    double getAveragePrecipitation() {
        return precipitationSum == 0 ? 0 : precipitationSum / 24;
    }
}
