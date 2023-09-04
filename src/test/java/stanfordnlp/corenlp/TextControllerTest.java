package stanfordnlp.corenlp;

import edu.kit.kastel.mcse.ardoco.core.textproviderjson.converter.JsonConverter;
import edu.kit.kastel.mcse.ardoco.core.textproviderjson.dto.TextDto;
import edu.kit.kastel.mcse.ardoco.core.textproviderjson.error.InvalidJsonException;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import stanfordnlp.TestUtil;

import java.io.IOException;
import java.nio.file.Path;

public class TextControllerTest {


    String username = "admin";
    String password = "changeme";
    String textEndpoint = "/stanfordnlp";
    static String address;
    static TestRestTemplate restTemplate;

    @ClassRule
    public static GenericContainer<?> simpleWebServer = new GenericContainer<>(new ImageFromDockerfile("localhost/testcontainers/stanfordcorenlp-provider-service", true) //
            .withFileFromPath("./src", Path.of("./src")) //
            .withFileFromPath("./pom.xml", Path.of("./pom.xml")) //
            .withFileFromPath("./Dockerfile", Path.of("./Dockerfile")) //
    ).withExposedPorts(8080);

    @BeforeAll
    static void setUp() {
        simpleWebServer.start();
        address = "http://" + simpleWebServer.getContainerIpAddress() + ":" + simpleWebServer.getMappedPort(8080);
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
