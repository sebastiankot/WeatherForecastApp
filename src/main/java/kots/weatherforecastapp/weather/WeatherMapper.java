package kots.weatherforecastapp.weather;

import kots.weatherforecastapp.client.openMeteo.dto.DailyMeteoClientResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

class WeatherMapper {

    static List<WeatherData> mapToWeatherList(DailyMeteoClientResponse dailyMeteoClientResponse) {
        List<LocalDate> date = dailyMeteoClientResponse.date();
        List<Double> precipitationSum = dailyMeteoClientResponse.precipitationSum();
        List<LocalTime> sunrise = dailyMeteoClientResponse.sunrise();
        List<LocalTime> sunset = dailyMeteoClientResponse.sunset();

        return IntStream.range(0, date.size())
                .mapToObj(i -> new WeatherData(
                        date.get(i),
                        precipitationSum.get(i),
                        sunrise.get(i),
                        sunset.get(i)
                ))
                .toList();
    }
}
