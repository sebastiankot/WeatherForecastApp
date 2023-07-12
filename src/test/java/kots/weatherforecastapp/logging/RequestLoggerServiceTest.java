package kots.weatherforecastapp.logging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RequestLoggerServiceTest {

    private static final double LATITUDE = 5.0;
    private static final double LONGITUDE = 25.0;

    @InjectMocks
    private RequestLoggerService loggerService;

    @Mock
    private WeatherRequestRepository weatherRequestRepository;

    @Test
    public void shouldSaveDataParamsRequest() {
        // given
        // when
        loggerService.saveDataRequest(LATITUDE, LONGITUDE);

        // then
        verify(weatherRequestRepository).save(any(WeatherRequest.class));
    }

    @Test
    public void verifiedArgumentsToSave() {
        // given
        // when
        loggerService.saveDataRequest(LATITUDE, LONGITUDE);

        // then
        verify(weatherRequestRepository).save(argThat(weatherRequest ->
                weatherRequest.getLongitude() == LONGITUDE &&
                weatherRequest.getLatitude() == LATITUDE &&
                weatherRequest.getRequestTime() != null
        ));
    }
}