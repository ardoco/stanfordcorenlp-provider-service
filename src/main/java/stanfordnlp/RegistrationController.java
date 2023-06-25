package stanfordnlp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
//    private final DatabaseUserService databaseUserService;
//
//    RegistrationController(UserRepository userRepository) {
//        this.databaseUserService = new DatabaseUserService(userRepository);
//    }

    @GetMapping("/registration")
//    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> register() { //(@RequestParam String username, @RequestParam String password) {
        // password already encoded
//        databaseUserService.createUser(username, password);
        return ResponseEntity.ok("Microservice is healthy");
    }
}
