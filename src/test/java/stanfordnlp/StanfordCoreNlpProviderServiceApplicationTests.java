package stanfordnlp;

import io.github.ardoco.textproviderjson.converter.JsonConverter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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

	@Autowired
	private MockMvc mvc;

	@Test
	void testGetAnnotatedText() throws Exception {
		// registration
		String requestBody = "{\"username\": \"user1\", \"password\": \"password1\"}";
		mvc.perform(post("/registration")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(content().string("User created successfully"));

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("user1", "password1");
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
	void testUnsecuredRequest() throws Exception {
		mvc.perform(get("/health")
						.contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk());
	}

	@Test
	void testRegistration() throws Exception {
		String requestBody = "{\"username\": \"user1\", \"password\": \"password1\"}";
		mvc.perform(post("/registration")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(content().string("User created successfully"));

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("user1", "password1");
		mvc.perform(get("/stanfordnlp?text=This is Marie.\n")
						.contentType(MediaType.APPLICATION_JSON)
						.headers(headers))
				.andExpect(status().isOk());
	}


}
