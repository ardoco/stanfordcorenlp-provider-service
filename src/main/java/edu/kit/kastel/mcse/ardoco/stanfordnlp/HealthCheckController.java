/* Licensed under MIT 2023. */
package edu.kit.kastel.mcse.ardoco.stanfordnlp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    private static final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    @GetMapping("/stanfordnlp/health")
    public ResponseEntity<String> healthCheck() {
        logger.info("Getting and answering health request");
        // no further checks needed for now
        return ResponseEntity.ok("Microservice is healthy");
    }
}
