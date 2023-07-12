package kots.weatherforecastapp.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kots.weatherforecastapp.weather.dto.WeatherDayDto;
import kots.weatherforecastapp.weather.dto.WeekWeatherDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.EXAMPLE_DATE;
import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.EXAMPLE_SUNRISE_TIME;
import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.EXAMPLE_SUNSET_TIME;
import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.LATITUDE;
import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.LONGITUDE;
import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.SIZE_LIST_DAILY_METEO;
import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.ZERO_PRECIPITATION_SUM;
import static kots.weatherforecastapp.weather.MeteoClientResponseDataHelper.createMeteoClientResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTest {

    private static final String WEATHER_ENDPOINT = "/api/weather";
    private static final String BASE_ENDPOINT_API = "https://example.apis";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldReceiveWeatherForLastWeek() throws Exception {
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(prepareExternalApiURI()))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(generateJSONOpenMeteoResponse()));

        // when & then
        String jsonResult = mockMvc.perform(get(generateWeatherEndpointWithParams()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        WeekWeatherDto weekWeatherDto = objectMapper.readValue(jsonResult, WeekWeatherDto.class);
        assertThat(weekWeatherDto.latitude()).isEqualTo(LATITUDE);
        assertThat(weekWeatherDto.longitude()).isEqualTo(LONGITUDE);
        assertThat(weekWeatherDto.weatherByDay()).hasSize(SIZE_LIST_DAILY_METEO);

        WeatherDayDto weatherDayDto = weekWeatherDto.weatherByDay().get(0);
        assertThat(weatherDayDto.date()).isEqualTo(EXAMPLE_DATE);
        assertThat(weatherDayDto.sunrise()).isEqualTo(EXAMPLE_SUNRISE_TIME);
        assertThat(weatherDayDto.sunset()).isEqualTo(EXAMPLE_SUNSET_TIME);
        assertThat(weatherDayDto.averageRainfall()).isEqualTo(ZERO_PRECIPITATION_SUM);
    }

    @ParameterizedTest
    @CsvSource({"123, 194", "-21, 84", "1, 342"})
    public void shouldReceiveBadRequestStatusWhenCoordinatesIsBad(double latitude, double longitude) throws Exception {
        mockMvc.perform(get(generateWeatherEndpointWithParams(latitude, longitude)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", Matchers.equalTo("Bound of coordinates")))
                .andExpect(jsonPath("$.detail", Matchers.equalTo("Coordinate boundaries: minimum is 0 and maximum is 180.")));
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 404, 400, 504})
    public void shouldReceiveInternalServerErrorStatusWhenTheExternalAPIEncountersAnError(int httpStatus) throws Exception {
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(prepareExternalApiURI()))
                .andRespond(withStatus(HttpStatus.valueOf(httpStatus)));

        // when & then
        mockMvc.perform(get(generateWeatherEndpointWithParams()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title", Matchers.equalTo("External Api Problem")));
    }


    private String generateJSONOpenMeteoResponse() throws JsonProcessingException {
        return objectMapper.writeValueAsString(createMeteoClientResponse());
    }

    private String generateWeatherEndpointWithParams() {
        return generateWeatherEndpointWithParams(LATITUDE, LONGITUDE);
    }

    private String generateWeatherEndpointWithParams(double latitude, double longitude) {
        return WEATHER_ENDPOINT + "?latitude=" + latitude + "&longitude=" + longitude;
    }

    private URI prepareExternalApiURI() {
        return prepareExternalApiURI(LATITUDE, LONGITUDE);
    }

    private URI prepareExternalApiURI(double latitude, double longitude) {
        return URI.create(BASE_ENDPOINT_API + "/forecast" +
                "?latitude=" + latitude +
                "&longitude=" + longitude + "" +
                "&daily=precipitation_sum,sunrise,sunset" +
                "&timezone=Europe/Berlin" +
                "&past_days=7" +
                "&forecast_days=0");
    }
}