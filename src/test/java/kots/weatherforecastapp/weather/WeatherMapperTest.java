package kots.weatherforecastapp.weather;

import kots.weatherforecastapp.client.openMeteo.dto.DailyMeteoClientResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherMapperTest {

    @Test
    public void shouldMapDailyMeteoClientResponseToWeatherDataList() {
        // given
        DailyMeteoClientResponse dailyMeteoClientResponse = MeteoClientResponseDataHelper.createDailyMeteoClientResponse();

        // when
        List<WeatherData> weatherDataList = WeatherMapper.mapToWeatherList(dailyMeteoClientResponse);
        WeatherData firstOfList = weatherDataList.get(0);

        // then
        assertThat(weatherDataList).hasSize(MeteoClientResponseDataHelper.SIZE_LIST_DAILY_METEO);
        assertThat(firstOfList.data()).isEqualTo(MeteoClientResponseDataHelper.EXAMPLE_DATE);
        assertThat(firstOfList.sunset()).isEqualTo(MeteoClientResponseDataHelper.EXAMPLE_SUNSET_TIME);
        assertThat(firstOfList.sunrise()).isEqualTo(MeteoClientResponseDataHelper.EXAMPLE_SUNRISE_TIME);
        assertThat(firstOfList.precipitationSum()).isEqualTo(MeteoClientResponseDataHelper.ZERO_PRECIPITATION_SUM);
    }
}