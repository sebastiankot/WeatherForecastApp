package kots.weatherforecastapp.client.openMeteo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class OpenMeteoConfiguration {

    @Value("${open-meteo.pastDays}")
    private int pastDays = 7;
    @Value("${open-meteo.url}")
    private String url;
}
