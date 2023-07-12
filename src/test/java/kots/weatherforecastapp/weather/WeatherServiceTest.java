package kots.weatherforecastapp.weather;

import kots.weatherforecastapp.client.openMeteo.OpenMeteoClient;
import kots.weatherforecastapp.logging.WeatherLogger;
import kots.weatherforecastapp.weather.dto.WeatherDayDto;
import kots.weatherforecastapp.weather.dto.WeekWeatherDto;
import kots.weatherforecastapp.weather.exception.BoundOfCoordinatesValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.EXAMPLE_DATE;
import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.EXAMPLE_SUNRISE_TIME;
import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.EXAMPLE_SUNSET_TIME;
import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.createMeteoClientResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private OpenMeteoClient openMeteoClient;

    @Mock
    private WeatherLogger weatherLogger;

    @Test
    public void shouldFetchWeekWeatherDto() {
        // given
        when(openMeteoClient.weatherOfLastWeek(5.0, 5.0)).thenReturn(createMeteoClientResponse());

        // when
        WeekWeatherDto weekWeather = weatherService.getLastWeekWeather(5.0, 5.0);

        // then
        verify(weatherLogger, times(1)).saveDataRequest(5.0, 5.0);
        assertThat(weekWeather).isNotNull();
        assertThat(weekWeather.latitude()).isEqualTo(5.0);
        assertThat(weekWeather.longitude()).isEqualTo(5.0);
        assertThat(weekWeather.weatherByDay()).hasSize(2);

        WeatherDayDto anyDay = weekWeather.weatherByDay().get(0);
        assertThat(anyDay.date()).isEqualTo(EXAMPLE_DATE);
        assertThat(anyDay.sunset()).isEqualTo(EXAMPLE_SUNSET_TIME);
        assertThat(anyDay.sunrise()).isEqualTo(EXAMPLE_SUNRISE_TIME);
        assertThat(anyDay.averageRainfall()).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource({"5.1, 193", "-2.1, 24", "84, -240"})
    public void shouldThrowAnExceptionWhenParametersAreBoundOfValues(double latitude, double longitude) {
        assertThrows(BoundOfCoordinatesValueException.class, () -> weatherService.getLastWeekWeather(latitude, longitude));
    }
}