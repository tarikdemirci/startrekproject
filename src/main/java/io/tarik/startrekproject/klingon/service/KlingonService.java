package io.tarik.startrekproject.klingon.service;

import io.tarik.startrekproject.klingon.external.PlqadMapClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class KlingonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PlqadMapClient plqadMapClient;

    @Autowired
    public KlingonService(PlqadMapClient plqadMapClient) {
        this.plqadMapClient = plqadMapClient;
    }

    private String translateCharacterToKlingon(char singleEnglishChar, Map<String, String> mapping) throws UntranslatableCharError {
        if (mapping.containsKey(String.valueOf(singleEnglishChar))) {
            return mapping.get(String.valueOf(singleEnglishChar));
        }

        String otherPossibility;
        if (Character.isLowerCase(singleEnglishChar)) {
            otherPossibility = String.valueOf(singleEnglishChar).toUpperCase();
        } else {
            otherPossibility = String.valueOf(singleEnglishChar).toLowerCase();
        }

        if (mapping.containsKey(otherPossibility)) {
            return mapping.get(otherPossibility);
        } else {
            throw new UntranslatableCharError("Char is not translatable: " + singleEnglishChar);
        }

    }

    public Optional<List<String>> translateToKlingon(String englishText) {
        Optional<Map<String, String>> mappingOptional = plqadMapClient.getEnglishToPlqadMapping();
        if (mappingOptional.isPresent()) {
            Map<String, String> mapping = mappingOptional.get();
            try {
                return Optional.of(englishText.chars()
                        .mapToObj(i -> (char) i)
                        .map(character -> translateCharacterToKlingon(character, mapping))
                        .collect(Collectors.toList()));
            } catch (UntranslatableCharError e) {
                logger.warn("Ignoring untranslatable text: " + englishText + ", " + e.getMessage());
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }

    }
}
