package io.tarik.startrekproject.klingon.external;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlqadMapClientTest {
    @InjectMocks
    private PlqadMapClient plqadMapClient;

    @Test
    public void getEnglishToPlqadMapping() throws Exception {
        Map<String, String> mapping = plqadMapClient.getEnglishToPlqadMapping();
        assertEquals("0xF8E5", mapping.get("u"));
        assertEquals("0xF8D6", mapping.get("h"));
        assertEquals("0xF8E1", mapping.get("r"));
        assertEquals("0xF8D0", mapping.get("a"));
    }

}