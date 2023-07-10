package kots.weatherforecastapp.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/weather")
class WeatherController {

    @GetMapping
    List<WeekWeatherDto> lastWeekWeather(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
        return List.of(
                new WeekWeatherDto(latitude, longitude, List.of(new WeatherDayDto(LocalDate.now(), LocalDateTime.now(), LocalDateTime.now(), "53,2mm"))),
                new WeekWeatherDto(latitude, longitude, List.of(new WeatherDayDto(LocalDate.now().minusDays(1), LocalDateTime.now(), LocalDateTime.now(), "53,2mm"))),
                new WeekWeatherDto(latitude, longitude, List.of(new WeatherDayDto(LocalDate.now().minusDays(2), LocalDateTime.now(), LocalDateTime.now(), "53,2mm")))
        );
    }
}
