package kots.weatherforecastapp.client.openMeteo;

import kots.weatherforecastapp.client.openMeteo.dto.DailyMeteoClientResponse;
import kots.weatherforecastapp.client.openMeteo.dto.MeteoClientResponse;
import kots.weatherforecastapp.weather.MeteoClientResponseDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OpenMeteoClientImplTest {

    private static final String SUFFIX_ERROR = " when call to OpenMeteo endpoint";

    @InjectMocks
    private OpenMeteoClientImpl openMeteoClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OpenMeteoConfiguration openMeteoConfiguration;

    @Test
    public void shouldFetchMeteoClientResponse() {
        // given
        prepareMocksForOpenMeteoConfiguration();
        when(restTemplate.getForObject(anyString(), eq(MeteoClientResponse.class))).thenReturn(MeteoClientResponseDataHelper.createMeteoClientResponse());

        // when
        MeteoClientResponse meteoClientResponse = openMeteoClient.weatherOfLastWeek(MeteoClientResponseDataHelper.LATITUDE, MeteoClientResponseDataHelper.LONGITUDE);
        DailyMeteoClientResponse daily = meteoClientResponse.daily();

        // then
        assertThat(meteoClientResponse.latitude()).isEqualTo(MeteoClientResponseDataHelper.LATITUDE);
        assertThat(meteoClientResponse.longitude()).isEqualTo(MeteoClientResponseDataHelper.LONGITUDE);
        assertThat(daily.date().get(0)).isEqualTo(MeteoClientResponseDataHelper.EXAMPLE_DATE);
        assertThat(daily.sunrise().get(0)).isEqualTo(MeteoClientResponseDataHelper.EXAMPLE_SUNRISE_TIME);
        assertThat(daily.sunset().get(0)).isEqualTo(MeteoClientResponseDataHelper.EXAMPLE_SUNSET_TIME);
    }

    @ParameterizedTest
    @ValueSource(ints = {400, 403, 404, 500, 503, 504})
    public void shouldThrowAnExceptionWhenRemoteServerHaveError(int httpStatus) {
        // given
        prepareMocksForOpenMeteoConfiguration();
        when(restTemplate.getForObject(anyString(), eq(MeteoClientResponse.class))).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(httpStatus)));

        // when & then
        assertThrows(
                OpenMeteoException.class,
                () -> openMeteoClient.weatherOfLastWeek(MeteoClientResponseDataHelper.LATITUDE, MeteoClientResponseDataHelper.LONGITUDE),
                httpStatus + SUFFIX_ERROR
        );
    }

    private void prepareMocksForOpenMeteoConfiguration() {
        when(openMeteoConfiguration.getUrl()).thenReturn("http://sample.Url");
        when(openMeteoConfiguration.getPastDays()).thenReturn(7);
    }
}