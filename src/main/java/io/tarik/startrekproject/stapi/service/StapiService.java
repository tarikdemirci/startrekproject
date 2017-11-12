package io.tarik.startrekproject.stapi.service;

import io.tarik.startrekproject.stapi.domain.character.CharacterBase;
import io.tarik.startrekproject.stapi.domain.character.CharacterSpecies;
import io.tarik.startrekproject.stapi.external.StapiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
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
        String defaultErrorMessage = "Unable to fetch species of name";
        try {

            List<CharacterBase> characterBaseList = stapiClient.getAllCharactersByName(name);
            ForkJoinPool myPool = new ForkJoinPool(characterBaseList.size());

            return Optional.of(myPool.submit(() ->
                    characterBaseList.parallelStream()
                            .map(characterBase -> stapiClient.getCharacterByUid(characterBase.getUid()))
                    .flatMap(characterFull -> characterFull.getCharacterSpecies().stream())
                    .map(CharacterSpecies::getName)
                    .collect(Collectors.toSet())
            ).get());
        } catch (ResourceAccessException exc) {
            if (exc.getCause() instanceof SocketTimeoutException) {
                logger.error("Timeout occurred during Stapi connection");
            } else {
                logger.error(defaultErrorMessage, exc);
            }
            return Optional.empty();
        } catch (HttpClientErrorException exc) {
            if (exc.getRawStatusCode() == 403) {
                logger.error("Stapi request quota has been consumed. Please try again later.");
            } else {
                logger.error(defaultErrorMessage, exc);
            }
            return Optional.empty();
        } catch (ExecutionException exc) {
            if (exc.getCause() instanceof ResourceAccessException
                    && exc.getCause().getCause() instanceof SocketTimeoutException) {
                logger.error("Timeout occurred during Stapi connection");
            } else if (exc.getCause() instanceof HttpClientErrorException) {
                HttpClientErrorException httpClientErrorException = (HttpClientErrorException) exc.getCause();
                if (httpClientErrorException.getRawStatusCode() == 403) {
                    logger.error("Stapi request quota has been consumed. Please try again later.");
                } else {
                    logger.error(defaultErrorMessage, exc);
                }
            } else {
                logger.error(defaultErrorMessage, exc);
            }
            return Optional.empty();
        } catch (InterruptedException exc) {
            logger.error(defaultErrorMessage, exc);
            return Optional.empty();
        }
    }
}
