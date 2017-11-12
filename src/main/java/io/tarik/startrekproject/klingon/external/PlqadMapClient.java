package io.tarik.startrekproject.klingon.external;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlqadMapClient {
    public Map<String, String> getEnglishToPlqadMapping() {
        return new HashMap<>();
    }
}
