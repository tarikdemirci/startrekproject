package io.tarik.startrekproject.stapi.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StapiServiceTest {

    @Autowired
    private StapiService stapiService;

    @Test
    public void getSpeciesOfName() throws Exception {
        assertEquals(new HashSet<>(
                Collections.singletonList("Human")),
                stapiService.getSpeciesOfName("Uhura")
        );
    }

}
