package kots.weatherforecastapp.logging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class RequestLoggerService implements  WeatherLogger {

    private final WeatherRequestRepository weatherRequestRepository;

    @Override
    public void saveDataRequest(double latitude, double longitude) {
        weatherRequestRepository.save(WeatherRequest.of(null, LocalDateTime.now(), latitude, longitude));
    }
}
