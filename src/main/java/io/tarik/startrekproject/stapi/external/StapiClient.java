package io.tarik.startrekproject.stapi.external;

import io.tarik.startrekproject.stapi.domain.character.CharacterBaseResponse;
import org.springframework.stereotype.Component;

@Component
public class StapiClient {
    protected CharacterBaseResponse searchCharacterByName(String name, int pageNumber) {
        return new CharacterBaseResponse();
    }
}
