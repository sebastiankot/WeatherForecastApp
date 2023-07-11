package kots.weatherforecastapp.weather;

import kots.weatherforecastapp.client.openMeteo.OpenMeteoClient;
import kots.weatherforecastapp.client.openMeteo.dto.MeteoClientResponse;
import kots.weatherforecastapp.logging.WeatherLogger;
import kots.weatherforecastapp.weather.dto.WeatherDayDto;
import kots.weatherforecastapp.weather.dto.WeekWeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class WeatherService {

    private final OpenMeteoClient openMeteoClient;
    private final WeatherLogger weatherLogger;

    WeekWeatherDto getLastWeekWeather(double latitude, double longitude) {
        MeteoClientResponse meteoClientResponse = openMeteoClient.weatherOfLastWeek(latitude, longitude);
        List<WeatherData> weatherData = WeatherMapper.mapToWeatherList(meteoClientResponse.daily());
        weatherLogger.saveDataRequest(latitude, longitude);
        return new WeekWeatherDto(latitude, longitude, getWeatherDayDtoList(weatherData));
    }

    private List<WeatherDayDto> getWeatherDayDtoList(List<WeatherData> weatherData) {
        return weatherData.stream()
                .map(this::mapToWeatherDayDto)
                .toList();
    }

    private WeatherDayDto mapToWeatherDayDto(WeatherData weather) {
        return new WeatherDayDto(
                weather.data(),
                weather.sunrise(),
                weather.sunset(),
                weather.getAveragePrecipitation()
        );
    }
}
