package io.tarik.startrekproject.stapi.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Component
public class StapiRestTemplate {

    @Value("${stapi.connect.timeout.seconds}")
    private int stapiConnectTimeoutSeconds;

    @Value("${stapi.read.timeout.seconds}")
    private int stapiReadTimeoutSeconds;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout((int) TimeUnit.SECONDS.toMillis(stapiConnectTimeoutSeconds))
                .setReadTimeout((int)TimeUnit.SECONDS.toMillis(stapiReadTimeoutSeconds)).build();
    }
}
