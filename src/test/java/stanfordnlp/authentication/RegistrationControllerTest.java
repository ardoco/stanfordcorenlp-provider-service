package stanfordnlp.authentication;

import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.PullPolicy;

class RegistrationControllerTest {

    String username = "defaultadmin";
    String password = "defaultpassword";
    String registrationEndpoint = "/registration";
    String healthEndpoint = "/health";
    static String address;
    static TestRestTemplate restTemplate;

    @ClassRule
    public static GenericContainer simpleWebServer = new GenericContainer("stanfordnlp-microservice")
            .withExposedPorts(8080)
            .withImagePullPolicy(PullPolicy.defaultPolicy());

    @BeforeAll
    static void beforeAll() {
        simpleWebServer.start();
        address = "http://"
                + simpleWebServer.getContainerIpAddress()
                + ":" + simpleWebServer.getMappedPort(8080);
        restTemplate = new TestRestTemplate();
    }

    @Test
    void testRegistration() {
        String newUsername = "user1";
        String newPassword = "password1";

        // unauthorized access
        HttpHeaders headers0 = new HttpHeaders();
        headers0.setContentType(MediaType.APPLICATION_JSON);
        String requestBody0 = "{\"username\": \"" + newUsername + "\", \"password\": \"" + newPassword + "\"}";
        HttpEntity<String> request0 = new HttpEntity<>(requestBody0, headers0);
        ResponseEntity<String> response0 =  restTemplate.postForEntity(address + registrationEndpoint, request0, String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response0.getStatusCode());

        // register new user
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_JSON);
        String requestBody1 = "{\"username\": \"" + newUsername + "\", \"password\": \"" + newPassword + "\"}";
        HttpEntity<String> request1 = new HttpEntity<>(requestBody1, headers1);
        ResponseEntity<String> response1 =  restTemplate.withBasicAuth(username, password).postForEntity(address + registrationEndpoint, request1, String.class);
        Assertions.assertEquals(HttpStatus.OK, response1.getStatusCode());
        Assertions.assertEquals("User created successfully", response1.getBody());

        // check if user is registered
        String response2 = restTemplate.withBasicAuth(newUsername, newPassword).getForObject(address + healthEndpoint, String.class);
        Assertions.assertEquals("Microservice is healthy", response2);

        // check if user can't register new users
        HttpHeaders headers3 = new HttpHeaders();
        headers3.setContentType(MediaType.APPLICATION_JSON);
        String requestBody3 = "{\"username\": \"user2\", \"password\": \"password2\"}";
        HttpEntity<String> request3 = new HttpEntity<>(requestBody3, headers3);
        ResponseEntity<String> response3 =  restTemplate.withBasicAuth(newUsername, newPassword).postForEntity(address + registrationEndpoint, request3, String.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response3.getStatusCode());

        // check if user with same credentials can't be registered again
        HttpHeaders headers4 = new HttpHeaders();
        headers4.setContentType(MediaType.APPLICATION_JSON);
        String requestBody4 = "{\"username\": \"" + newUsername + "\", \"password\": \"" + newPassword + "\"}";
        HttpEntity<String> request4 = new HttpEntity<>(requestBody4, headers4);
        ResponseEntity<String> response4 =  restTemplate.withBasicAuth(username, password).postForEntity(address + registrationEndpoint, request4, String.class);
        Assertions.assertEquals(HttpStatus.OK, response4.getStatusCode());
        Assertions.assertEquals("User already exists", response4.getBody());
    }
}
