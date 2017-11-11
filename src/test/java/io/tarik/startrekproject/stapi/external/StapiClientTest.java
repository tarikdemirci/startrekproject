package io.tarik.startrekproject.stapi.external;

import io.tarik.startrekproject.stapi.domain.character.CharacterBaseResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.junit.Assert.*;
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
}
