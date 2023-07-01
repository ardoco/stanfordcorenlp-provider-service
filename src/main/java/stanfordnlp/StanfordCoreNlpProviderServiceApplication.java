package stanfordnlp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(exclude = { SecurityAutoConfiguration.class })
public class StanfordCoreNlpProviderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StanfordCoreNlpProviderServiceApplication.class, args);
	}

}

