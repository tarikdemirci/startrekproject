package io.tarik.startrekproject.klingon.external;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlqadMapClientTest {
    @Autowired
    private PlqadMapClient plqadMapClient;

    @Test
    public void getEnglishToPlqadMapping() throws Exception {
        Optional<Map<String, String>> optionalMapping = plqadMapClient.getEnglishToPlqadMapping();
        assertTrue(optionalMapping.isPresent());

        Map<String, String> mapping = optionalMapping.get();
        assertEquals("0xF8E5", mapping.get("u"));
        assertEquals("0xF8D6", mapping.get("H"));
        assertEquals("0xF8E1", mapping.get("r"));
        assertEquals("0xF8D0", mapping.get("a"));
    }

}