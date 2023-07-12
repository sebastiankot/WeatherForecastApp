package kots.weatherforecastapp.weather;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherDataTest {

    @ParameterizedTest
    @CsvSource({"54, 2.25", "84, 3.5", "114, 4.75"})
    public void shouldReturnPositiveAveragePrecipitation(double precipitationSum, double expected) {
        // given
        WeatherData weatherData = generateWeatherData(precipitationSum);

        // when
        double precipitation = weatherData.getAveragePrecipitation();

        // then
        assertThat(precipitation).isEqualTo(expected);
    }

    private WeatherData generateWeatherData(double precipitationSum) {
        return new WeatherData(LocalDate.now(), precipitationSum, LocalTime.of(4, 52), LocalTime.of(21, 54));
    }
}