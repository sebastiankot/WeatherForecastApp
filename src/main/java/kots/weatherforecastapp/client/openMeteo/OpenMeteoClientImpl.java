package kots.weatherforecastapp.client.openMeteo;

import kots.weatherforecastapp.client.openMeteo.dto.MeteoClientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
@Component
@RequiredArgsConstructor
class OpenMeteoClientImpl implements OpenMeteoClient {

    private final RestTemplate restTemplate;
    private final OpenMeteoConfiguration meteoConfig;

    @Override
    public MeteoClientResponse weatherOfLastWeek(double latitude, double longitude) {
        try {
            return restTemplate.getForObject(generateUrl(latitude, longitude), MeteoClientResponse.class);
        } catch (HttpStatusCodeException e) {
            log.error("While call to OpenMeteo receive that status code: " + e.getStatusCode() + " | Error message: " + e.getMessage());
            throw new OpenMeteoException(e.getStatusCode().value() + " when call to OpenMeteo endpoint");
        }
    }

    private String generateUrl(double latitude, double longitude) {
        return UriComponentsBuilder.fromHttpUrl(meteoConfig.getUrl() + "/forecast")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("daily", "precipitation_sum,sunrise,sunset")
                .queryParam("timezone", "Europe/Berlin")
                .queryParam("past_days", meteoConfig.getPastDays())
                .queryParam("forecast_days", 0)
                .toUriString();
    }
}
