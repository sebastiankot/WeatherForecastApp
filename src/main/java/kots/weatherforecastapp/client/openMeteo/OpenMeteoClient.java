package kots.weatherforecastapp.client.openMeteo;

import kots.weatherforecastapp.client.openMeteo.dto.MeteoClientResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface OpenMeteoClient {

    @GetExchange("/forecast?latitude={latitude}&longitude={longitude}&daily=precipitation_sum,sunrise,sunset&timezone=Europe/Berlin&past_days=7&forecast_days=0")
    MeteoClientResponse weatherOfLastWeek(@PathVariable double latitude, @PathVariable double longitude);
}
