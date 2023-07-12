package kots.weatherforecastapp.client.openMeteo;

import kots.weatherforecastapp.client.openMeteo.dto.MeteoClientResponse;

public interface OpenMeteoClient {

    MeteoClientResponse weatherOfLastWeek(double latitude, double longitude);
}
