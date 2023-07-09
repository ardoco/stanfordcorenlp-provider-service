package stanfordnlp;

import io.github.ardoco.textproviderjson.converter.JsonConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class StanfordCoreNlpProviderServiceApplicationTests {
	Logger logger = LoggerFactory.getLogger(StanfordCoreNlpProviderServiceApplicationTests.class);

	@Autowired
	private MockMvc mvc;

	@Test
	void testGetAnnotatedText() throws Exception {
		String username = System.getenv("USERNAME");
		String password = System.getenv("PASSWORD");
		if(username == null || password == null) {
			logger.warn("Environment variables USERNAME and PASSWORD are not set. Skipping test.");
			return;
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);
		mvc.perform(get("/stanfordnlp?text=This is Marie.\n")
						.contentType(MediaType.APPLICATION_JSON)
						.headers(headers))
				.andExpect(status().isOk())
				.andExpect(content().json(JsonConverter.toJsonString(TestUtil.getExpectedDto())));
	}

	@Test
	void testUnauthorizedRequest() throws Exception {
		mvc.perform(get("/stanfordnlp?text=This is Marie.\n)")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}


	@Test
	void testRegistration() throws Exception {
		String username = System.getenv("USERNAME");
		String password = System.getenv("PASSWORD");
		if(username == null || password == null) {
			logger.warn("Environment variables USERNAME and PASSWORD are not set. Skipping test.");
			return;
		}
		// Test if admin can register a user
		HttpHeaders adminHeaders = new HttpHeaders();
		adminHeaders.setBasicAuth(username, password);
		String requestBody = "{\"username\": \"user1\", \"password\": \"password1\"}";
		mvc.perform(post("/registration")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody)
						.headers(adminHeaders))
				.andExpect(status().isOk())
				.andExpect(content().string("User created successfully"));

		// Test if user can access the service
		HttpHeaders userHeaders = new HttpHeaders();
		userHeaders.setBasicAuth("user1", "password1");
		mvc.perform(get("/stanfordnlp?text=This is Marie.\n")
						.contentType(MediaType.APPLICATION_JSON)
						.headers(userHeaders))
				.andExpect(status().isOk());

		// Test if user can't register a user
		String userRequestBody = "{\"username\": \"user2\", \"password\": \"password2\"}";
		mvc.perform(post("/registration")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody)
						.headers(userHeaders))
				.andExpect(status().isForbidden());
	}


}
