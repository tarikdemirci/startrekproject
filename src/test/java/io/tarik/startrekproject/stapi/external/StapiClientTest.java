package io.tarik.startrekproject.stapi.external;

import io.tarik.startrekproject.stapi.domain.character.CharacterBase;
import io.tarik.startrekproject.stapi.domain.character.CharacterBaseResponse;
import io.tarik.startrekproject.stapi.domain.character.CharacterFull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StapiClientTest {

    @Autowired
    private StapiClient stapiClient;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(stapiClient.restTemplate);
    }

    @Test
    public void searchCharacterByName() throws Exception {
        byte[] responseBody = readAllBytes(get("src", "test", "resources", "mock", "response",
                "stapi.character.search.uhura.response.json"));

        mockServer.expect(anything())
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        CharacterBaseResponse characterBaseResponse = stapiClient.searchCharacterByName("Uhura", 0);

        mockServer.verify();
        assertEquals(3, characterBaseResponse.getCharacters().size());
        assertEquals("CHMA0000068639", characterBaseResponse.getCharacters().get(0).getUid());
        assertEquals("Nyota Uhura", characterBaseResponse.getCharacters().get(0).getName());
    }

    @Test
    public void getAllCharactersByName() throws Exception {
        mockServer
                .expect(times(3), anything())
                .andExpect(method(HttpMethod.POST))
                .andRespond(clientHttpRequest -> {
                    String query = clientHttpRequest.getURI().getQuery();
                    String pageNumber = String.valueOf(query.charAt(query.length() - 1));
                    byte[] responseBody = readAllBytes(get("src", "test", "resources", "mock", "response",
                            "stapi.character.search.ed.response.page." + pageNumber + ".json"));
                    return withSuccess(responseBody, MediaType.APPLICATION_JSON)
                            .createResponse(clientHttpRequest);
                });

        List<CharacterBase> characters = stapiClient.getAllCharactersByName("ed");

        mockServer.verify();
        assertEquals(121, characters.size());
        assertEquals("CHMA0000069273", characters.get(0).getUid());
        assertEquals("F. Sepulveda", characters.get(0).getName());
        assertEquals("CHMA0000005581", characters.get(120).getUid());
        assertEquals("Edward Jellico", characters.get(120).getName());
    }

    @Test
    public void getCharacterByUid() throws Exception {
        byte[] responseBody = readAllBytes(get("src", "test", "resources", "mock", "response",
                "stapi.character.uhura.response.json"));

        mockServer.expect(anything())
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        CharacterFull character = stapiClient.getCharacterByUid("CHMA0000115364");

        mockServer.verify();
        assertEquals("CHMA0000115364", character.getUid());
        assertEquals("Nyota Uhura", character.getName());
        assertEquals(1, character.getCharacterSpecies().size());
        assertEquals("SPMA0000026314", character.getCharacterSpecies().get(0).getUid());
        assertEquals("Human", character.getCharacterSpecies().get(0).getName());
    }
}
