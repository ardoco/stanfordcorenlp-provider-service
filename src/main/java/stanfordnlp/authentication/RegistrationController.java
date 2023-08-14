package stanfordnlp.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private UserDetailsManager userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/stanfordnlp/registration")
    public String registerUser(@RequestBody RegistrationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (userDetailsService.userExists(username)) {
            return "User already exists";
        }

        String encodedPassword = passwordEncoder.encode(password);

        UserDetails newUser = User.withUsername(username)
                .password(encodedPassword)
                .roles("USER")
                .build();

        userDetailsService.createUser(newUser);

        return "User created successfully";
    }
}

