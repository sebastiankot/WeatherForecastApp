package kots.weatherforecastapp.client.openMeteo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DailyMeteoClientResponse(

        @JsonProperty("time")
        List<LocalDate> date,
        @JsonProperty("precipitation_sum")
        List<Double> precipitationSum,
        List<LocalTime> sunrise,
        List<LocalTime> sunset
) {

}
