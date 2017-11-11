package io.tarik.startrekproject.stapi.service;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class StapiService {

    public Set<String> getSpeciesOfName(String name) {
        return Collections.emptySet();
    }
}
