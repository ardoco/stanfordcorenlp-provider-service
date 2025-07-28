/* Licensed under MIT 2023-2025. */
package edu.kit.kastel.mcse.ardoco.stanfordnlp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(title = "ArDoCo: Stanford CoreNLP Provider Service", description = "Provides an interface to the Stanford CoreNLP library for text processing."), security = @SecurityRequirement(name = "basicAuth"))
@SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
@SpringBootApplication
public class StanfordCoreNlpProviderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StanfordCoreNlpProviderServiceApplication.class, args);
    }

}
