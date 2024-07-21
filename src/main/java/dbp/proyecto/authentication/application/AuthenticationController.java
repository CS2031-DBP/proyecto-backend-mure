package dbp.proyecto.authentication.application;

import dbp.proyecto.authentication.domain.AuthenticationService;
import dbp.proyecto.authentication.domain.GoogleAuthService;
import dbp.proyecto.authentication.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final GoogleAuthService googleAuthService;

    @PostMapping("/google")
    public ResponseEntity<GoogleAuthResponseDto> validateGoogleAuthToken(@RequestBody GoogleAuthRequestDto googleAuthRequestDto) {
        return ResponseEntity.ok(googleAuthService.validate(googleAuthRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto logInDTO) {
        return ResponseEntity.ok(authenticationService.login(logInDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponseDto> signin(@RequestBody SigninDto signInDTO) {
        return ResponseEntity.ok(authenticationService.signIn(signInDTO));
    }

    @PostMapping("/verify-password")
    public ResponseEntity<Boolean> verifyPassword(@RequestBody UserPasswordVerificationRequestDto request) {
        boolean isValid = authenticationService.verifyPassword(request.getUserId(), request.getPassword());
        return ResponseEntity.ok(isValid);
    }
}
