package io.tarik.startrekproject.stapi.service;

import io.tarik.startrekproject.stapi.domain.character.CharacterSpecies;
import io.tarik.startrekproject.stapi.external.StapiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StapiService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StapiClient stapiClient;

    @Autowired
    public StapiService(StapiClient stapiClient) {
        this.stapiClient = stapiClient;
    }

    public Optional<Set<String>> getSpeciesOfName(String name) {
        try {
            return Optional.of(
                    stapiClient.getAllCharactersByName(name).stream()
                            .map(characterBase -> stapiClient.getCharacterByUid(characterBase.getUid()))
                            .parallel()
                            .flatMap(characterFull -> characterFull.getCharacterSpecies().stream())
                            .map(CharacterSpecies::getName)
                            .collect(Collectors.toSet())
            );
        } catch (ResourceAccessException exc) {
            if (exc.getCause() instanceof SocketTimeoutException) {
                logger.error("Timeout occurred during Stapi connection");
            } else {
                logger.error("Unable to fetch species of name", exc);
            }
            return Optional.empty();
        }
    }
}
