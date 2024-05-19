package dbp.proyecto.authentication.application;

import dbp.proyecto.authentication.domain.AuthenticationService;
import dbp.proyecto.authentication.dto.JwtAuthenticationResponseDTO;
import dbp.proyecto.authentication.dto.LogInDTO;
import dbp.proyecto.authentication.dto.SignInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponseDTO> login(@RequestBody LogInDTO logInDTO) {
        return ResponseEntity.ok(authenticationService.login(logInDTO));
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponseDTO> signIn(@RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok(authenticationService.signIn(signInDTO));
    }
}
