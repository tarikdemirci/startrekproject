package io.tarik.startrekproject.klingon.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class KlingonService {
    public Optional<List<String>> translateToKlingon(String englishText) {
        return Optional.empty();
    }
}
