package kots.weatherforecastapp.client.openMeteo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DailyMeteoClientResponse(

        @JsonProperty("time")
        List<LocalDate> date,
        @JsonProperty("precipitation_sum")
        List<Double> precipitationSum,
        List<LocalDateTime> sunrise,
        List<LocalDateTime> sunset
) {

}
