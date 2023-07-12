package kots.weatherforecastapp.configuration;

import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Setter(value = AccessLevel.PROTECTED)
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "rest.connection")
class RestTemplateConfig {

    private Duration connectionRequestTimeout = Duration.ofSeconds(20);
    private Duration readTimeout = Duration.ofSeconds(30);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(connectionRequestTimeout)
                .setReadTimeout(readTimeout)
                .build();
    }
}
