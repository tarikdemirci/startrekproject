package io.tarik.startrekproject.stapi.external;

import io.tarik.startrekproject.stapi.domain.character.CharacterBase;
import io.tarik.startrekproject.stapi.domain.character.CharacterBaseResponse;
import io.tarik.startrekproject.stapi.domain.character.CharacterFull;
import io.tarik.startrekproject.stapi.domain.character.CharacterFullResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class StapiClient {

    protected final RestTemplate restTemplate;

    @Value("${stapi.character.search.endpoint}")
    private String stapiCharacterSearchEndpoint;

    @Value("${stapi.character.endpoint}")
    private String stapiCharacterEndpoint;

    @Autowired
    public StapiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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

    public List<CharacterBase> getAllCharactersByName(String name) {
        CharacterBaseResponse characterBaseResponseFirstPage = searchCharacterByName(name, 0);

        Stream<CharacterBase> characterBaseStreamOfRemainingPages =
                IntStream.range(1, characterBaseResponseFirstPage.getPage().getTotalPages())
                    .parallel()
                    .mapToObj(pageNo -> searchCharacterByName(name, pageNo))
                    .flatMap(response -> response.getCharacters().stream());

        return Stream.concat(
                characterBaseResponseFirstPage.getCharacters().stream(),
                characterBaseStreamOfRemainingPages
        ).collect(Collectors.toList());
    }

    public CharacterFull getCharacterByUid(String uid) {
        return restTemplate.getForObject(
                stapiCharacterEndpoint,
                CharacterFullResponse.class,
                new HashMap<String, Object>() {{
                    put("uid", uid);
                }}
        ).getCharacter();
    }
}
