package stanfordnlp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/stanfordnlp/health")
    public ResponseEntity<String> healthCheck() {
        // no further checks needed for now
        return ResponseEntity.ok("Microservice is healthy");
    }
}
