/* Licensed under MIT 2023-2025. */
package stanfordnlp.corenlp;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.kit.kastel.mcse.ardoco.core.textproviderjson.converter.JsonConverter;
import edu.kit.kastel.mcse.ardoco.core.textproviderjson.dto.TextDto;
import edu.kit.kastel.mcse.ardoco.core.textproviderjson.error.InvalidJsonException;
import stanfordnlp.StanfordTestBase;
import stanfordnlp.TestUtil;

class TextControllerTest extends StanfordTestBase {

    static String address;
    static TestRestTemplate restTemplate;

    @BeforeAll
    static void setUp() {
        simpleWebServer.start();
        address = "http://" + simpleWebServer.getHost() + ":" + simpleWebServer.getMappedPort(8080);
        restTemplate = new TestRestTemplate();
    }

    @AfterAll
    static void tearDown() {
        simpleWebServer.stop();
    }

    @Test
    void testTextProcessing() throws InvalidJsonException, IOException {
        // unauthorized access
        String text0 = "The quick brown fox jumped over the lazy dog.";
        ResponseEntity<String> response0 = restTemplate.getForEntity(address + textEndpoint + "?text=" + text0, String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response0.getStatusCode());

        // authorized access
        String text1 = "Hello World!";
        ResponseEntity<String> response1 = restTemplate.withBasicAuth(username, password).getForEntity(address + textEndpoint + "?text=" + text1, String.class);
        Assertions.assertEquals(HttpStatus.OK, response1.getStatusCode());
        TextDto expected = JsonConverter.fromJsonString(TestUtil.getJsonExample());
        TextDto actual = JsonConverter.fromJsonString(response1.getBody());
        Assertions.assertEquals(expected, actual);
    }
}
