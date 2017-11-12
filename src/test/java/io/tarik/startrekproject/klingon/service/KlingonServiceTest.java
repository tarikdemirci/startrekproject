package io.tarik.startrekproject.klingon.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KlingonServiceTest {

    @Autowired
    private KlingonService klingonService;

    @Test
    public void translateToKlingon() throws Exception {
        Optional<List<String>> optionalKlingonText = klingonService.translateToKlingon("Uhura");
        assertTrue("No result for 'Uhura'", optionalKlingonText.isPresent());

        List<String> klingonText = optionalKlingonText.get();
        assertEquals(
                Arrays.asList("0xF8E5", "0xF8D6", "0xF8E5", "0xF8E1", "0xF8D0"),
                klingonText
        );
    }

    @Test
    public void translateToKlingon_nameWithSpace() throws Exception {
        Optional<List<String>> optionalKlingonText = klingonService.translateToKlingon("Nyota Uhura");
        assertTrue("No result", optionalKlingonText.isPresent());

        List<String> klingonText = optionalKlingonText.get();
        assertEquals(
                Arrays.asList("0xF8DB", "0xF8E8", "0xF8DD", "0xF8E3", "0xF8D0", "0x0020", "0xF8E5", "0xF8D6", "0xF8E5", "0xF8E1", "0xF8D0"),
                klingonText
        );
    }
}
