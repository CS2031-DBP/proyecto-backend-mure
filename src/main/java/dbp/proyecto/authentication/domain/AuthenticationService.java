package dbp.proyecto.authentication.domain;

import dbp.proyecto.authentication.dto.JwtAuthenticationResponseDTO;
import dbp.proyecto.authentication.dto.LogInDTO;
import dbp.proyecto.authentication.dto.SignInDTO;
import dbp.proyecto.configuration.JwtService;
import dbp.proyecto.exception.UniqueResourceAlreadyExist;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtAuthenticationResponseDTO login(LogInDTO logInDTO) {
        User user = userRepository.findByEmail(logInDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        if (!passwordEncoder.matches(logInDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return new JwtAuthenticationResponseDTO(jwtService.generateToken(user));
    }
    public JwtAuthenticationResponseDTO signIn(SignInDTO signInDTO) {
        if (userRepository.findByEmail(signInDTO.getEmail()).isPresent()) {
            throw new UniqueResourceAlreadyExist("Email already exist");
        }
        User user = new User();
        user.setEmail(signInDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signInDTO.getPassword()));
        user.setName(signInDTO.getName());
        user.setRole(signInDTO.getRole());
        userRepository.save(user);
        return new JwtAuthenticationResponseDTO(jwtService.generateToken(user));
    }

}
