/* Licensed under MIT 2025. */
package stanfordnlp;

import java.nio.file.Path;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class StanfordTestBase {
    @Container
    protected static ProjectContainer simpleWebServer = new ProjectContainer().withExposedPorts(8080);

    protected final String username = "admin";
    protected final String password = "changeme";
    protected final String registrationEndpoint = "/stanfordnlp/registration";
    protected final String healthEndpoint = "/stanfordnlp/health";
    protected final String textEndpoint = "/stanfordnlp";

    protected static final class ProjectContainer extends GenericContainer<ProjectContainer> {
        public ProjectContainer() {
            super(new ImageFromDockerfile("localhost/testcontainers/stanfordcorenlp-provider-service", true) //
                    .withFileFromPath("./src", Path.of("./src")) //
                    .withFileFromPath("./pom.xml", Path.of("./pom.xml")) //
                    .withFileFromPath("./Dockerfile", Path.of("./Dockerfile")));
        }
    }
}
