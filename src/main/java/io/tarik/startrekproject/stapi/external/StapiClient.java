package io.tarik.startrekproject.stapi.external;

import io.tarik.startrekproject.stapi.domain.character.CharacterBaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class StapiClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${stapi.character.search.endpoint}")
    private String stapiCharacterSearchEndpoint;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    protected CharacterBaseResponse searchCharacterByName(String name, int pageNumber) {
        return restTemplate.postForObject(
                stapiCharacterSearchEndpoint,
                new LinkedMultiValueMap<String, Object>() {{
                    add("name", name);
                }},
                CharacterBaseResponse.class,
                new HashMap<String, Object>() {{
                    put("pageNumber", pageNumber);
                }}
        );
    }
}
