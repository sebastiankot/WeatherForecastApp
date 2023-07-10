package kots.weatherforecastapp.client.openMeteo.dto;

public record MeteoClientResponse(
        double latitude,
        double longitude,
        DailyMeteoClientResponse daily
) {

}
