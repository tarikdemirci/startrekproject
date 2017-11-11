package io.tarik.startrekproject.stapi.external;

import io.tarik.startrekproject.stapi.domain.character.CharacterBaseResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StapiClientTest {

    @Autowired
    private StapiClient stapiClient;

    @Test
    public void searchCharacterByName() throws Exception {
        CharacterBaseResponse characterBaseResponse = stapiClient.searchCharacterByName("Uhura", 0);
        assertEquals(3, characterBaseResponse.getCharacterBase().size());
        assertEquals("CHMA0000068639", characterBaseResponse.getCharacterBase().get(0).getUid());
        assertEquals("Nyota Uhura", characterBaseResponse.getCharacterBase().get(0).getName());
    }
}
