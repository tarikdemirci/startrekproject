package io.tarik.startrekproject.klingon.external;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
public class PlqadMapClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper;

    @Value("${klingon.englishToPlqadMapping.file.name}")
    private String englishToPlqadMappingFile;

    @Autowired
    public PlqadMapClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Optional<Map<String, String>> getEnglishToPlqadMapping() {
        try {
            Resource resource = new ClassPathResource(new File("klingon", englishToPlqadMappingFile).getPath());
            InputStream resourceInputStream = resource.getInputStream();
            TypeReference<HashMap<String, String>> typeRef
                    = new TypeReference<HashMap<String, String>>() {};

            return Optional.of(objectMapper.readValue(resourceInputStream, typeRef));
        } catch (IOException e) {
            logger.error("Unable to get mapping from file: " + englishToPlqadMappingFile, e);
            return Optional.empty();
        }
    }
}
