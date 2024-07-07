package dbp.proyecto.authentication.application;

import dbp.proyecto.authentication.domain.AuthenticationService;
import dbp.proyecto.authentication.dto.JwtAuthResponseDto;
import dbp.proyecto.authentication.dto.LoginDto;
import dbp.proyecto.authentication.dto.SigninDto;
import dbp.proyecto.authentication.dto.UserPasswordVerificationRequestDto;
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
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto logInDTO) {
        return ResponseEntity.ok(authenticationService.login(logInDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponseDto> signIn(@RequestBody SigninDto signInDTO) {
        return ResponseEntity.ok(authenticationService.signIn(signInDTO));
    }

    @PostMapping("/verify-password")
    public ResponseEntity<Boolean> verifyPassword(@RequestBody UserPasswordVerificationRequestDto request) {
        boolean isValid = authenticationService.verifyPassword(request.getUserId(), request.getPassword());
        return ResponseEntity.ok(isValid);
    }
}
