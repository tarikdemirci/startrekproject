package io.tarik.startrekproject.stapi.service;

import io.tarik.startrekproject.stapi.domain.character.CharacterBase;
import io.tarik.startrekproject.stapi.domain.character.CharacterFull;
import io.tarik.startrekproject.stapi.domain.character.CharacterSpecies;
import io.tarik.startrekproject.stapi.external.StapiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StapiServiceTest {

    @InjectMocks
    private StapiService stapiService;

    @Mock
    private StapiClient stapiClient;

    @Test
    public void getSpeciesOfName() throws Exception {
        Mockito.when(stapiClient.getAllCharactersByName(anyString()))
                .thenReturn(Collections.singletonList(CharacterBase.builder().uid("A1").build()));

        Mockito.when(stapiClient.getCharacterByUid(anyString()))
                .thenReturn(CharacterFull.builder().characterSpecies(
                        CharacterSpecies.builder().name("Human").build())
                        .build());

        assertEquals(new HashSet<>(
                Collections.singletonList("Human")),
                stapiService.getSpeciesOfName("Uhura")
        );
    }

}
