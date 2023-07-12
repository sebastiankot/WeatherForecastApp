package kots.weatherforecastapp.weather;

import kots.weatherforecastapp.client.openMeteo.OpenMeteoClient;
import kots.weatherforecastapp.client.openMeteo.dto.MeteoClientResponse;
import kots.weatherforecastapp.logging.WeatherLogger;
import kots.weatherforecastapp.weather.dto.WeatherDayDto;
import kots.weatherforecastapp.weather.dto.WeekWeatherDto;
import kots.weatherforecastapp.weather.exception.BoundOfCoordinatesValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class WeatherService {

    private final OpenMeteoClient openMeteoClient;
    private final WeatherLogger weatherLogger;

    WeekWeatherDto getLastWeekWeather(double latitude, double longitude) {
        validationCoordinates(latitude, longitude);
        MeteoClientResponse meteoClientResponse = openMeteoClient.weatherOfLastWeek(latitude, longitude);
        List<WeatherData> weatherData = WeatherMapper.mapToWeatherList(meteoClientResponse.daily());
        weatherLogger.saveDataRequest(latitude, longitude);
        return new WeekWeatherDto(latitude, longitude, getWeatherDayDtoList(weatherData));
    }

    private void validationCoordinates(double latitude, double longitude) {
        if(!validateCoordinatesRange(latitude) || !validateCoordinatesRange(longitude))
            throw new BoundOfCoordinatesValueException("Coordinate boundaries: minimum is 0 and maximum is 180.");
    }

    private boolean validateCoordinatesRange(double coordinates) {
        return coordinates > 0 && coordinates < 180;
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
