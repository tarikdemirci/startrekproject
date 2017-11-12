package io.tarik.startrekproject.stapi.service;

import io.tarik.startrekproject.stapi.domain.character.CharacterSpecies;
import io.tarik.startrekproject.stapi.external.StapiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StapiService {
    @Autowired
    private StapiClient stapiClient;

    public Set<String> getSpeciesOfName(String name) {
        return stapiClient.getAllCharactersByName(name).stream()
                .map(characterBase -> stapiClient.getCharacterByUid(characterBase.getUid()))
                .parallel()
                .flatMap(characterFull -> characterFull.getCharacterSpecies().stream())
                .map(CharacterSpecies::getName)
                .collect(Collectors.toSet());
    }
}
