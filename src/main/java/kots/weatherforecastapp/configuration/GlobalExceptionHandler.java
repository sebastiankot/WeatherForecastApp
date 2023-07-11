package kots.weatherforecastapp.configuration;

import kots.weatherforecastapp.client.openMeteo.OpenMeteoException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OpenMeteoException.class)
    ProblemDetail handleOpenMeteoException(OpenMeteoException exception) {
        log.error("OpenMeteo Error! Message:" +  exception.getMessage());
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Contact with administrator");
        problemDetail.setTitle("External Api Problem");
        return problemDetail;
    }
}
