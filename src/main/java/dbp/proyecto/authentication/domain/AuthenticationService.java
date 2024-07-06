package dbp.proyecto.authentication.domain;

import dbp.proyecto.authentication.dto.JwtAuthResponseDto;
import dbp.proyecto.authentication.dto.LoginDto;
import dbp.proyecto.authentication.dto.SigninDto;
import dbp.proyecto.configuration.JwtService;
import dbp.proyecto.events.SignIn.SignInEvent;
import dbp.proyecto.exception.UserAlreadyExistException;
import dbp.proyecto.user.domain.Role;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public AuthenticationService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public JwtAuthResponseDto login(LoginDto logInDTO) {
        User user = userRepository.findByEmail(logInDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        if (!passwordEncoder.matches(logInDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        JwtAuthResponseDto response = new JwtAuthResponseDto();
        response.setToken(jwtService.generateToken(user));
        return response;
    }

    public JwtAuthResponseDto signIn(SigninDto signInDTO) {
        if (userRepository.findByEmail(signInDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("Email already exist");
        }
        applicationEventPublisher.publishEvent(new SignInEvent(signInDTO.getEmail(), signInDTO.getName()));
        User user = new User();
        user.setEmail(signInDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signInDTO.getPassword()));
        user.setName(signInDTO.getName());
        user.setBirthDate(signInDTO.getBirthdate());
        user.setCreatedAt(LocalDateTime.now());
        if (signInDTO.getIsAdmin()) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        userRepository.save(user);
        JwtAuthResponseDto response = new JwtAuthResponseDto();
        response.setToken(jwtService.generateToken(user));
        return response;
    }

    public boolean verifyPassword(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return passwordEncoder.matches(password, user.getPassword());
    }
}
