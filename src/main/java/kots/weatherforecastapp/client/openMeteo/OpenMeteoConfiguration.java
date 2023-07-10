package kots.weatherforecastapp.client.openMeteo;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Log4j2
@Configuration(proxyBeanMethods = false)
class OpenMeteoConfiguration {

    @Value("${open-meteo.url}")
    private String url;

    @Bean
    OpenMeteoClient openMeteoClient(WebClient.Builder builder) {
        var webClient = WebClientAdapter.forClient(builder.baseUrl(url)
                .defaultStatusHandler(HttpStatusCode::isError, handleExceptionWithApis())
                .build());
        return HttpServiceProxyFactory.builder()
                .clientAdapter(webClient)
                .build()
                .createClient(OpenMeteoClient.class);
    }

    private Function<ClientResponse, Mono<? extends Throwable>> handleExceptionWithApis() {
        return response -> response.bodyToMono(String.class).map(OpenMeteoException::new);
    }
}
