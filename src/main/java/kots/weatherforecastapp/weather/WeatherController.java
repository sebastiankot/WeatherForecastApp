package kots.weatherforecastapp.weather;

import kots.weatherforecastapp.weather.dto.WeekWeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/weather")
@RequiredArgsConstructor
class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    WeekWeatherDto lastWeekWeather(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
        return weatherService.getLastWeekWeather(latitude, longitude);
    }
}
