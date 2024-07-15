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
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher applicationEventPublisher;

    public JwtAuthResponseDto login(LoginDto logInDto) {
        User user = userRepository.findByEmail(logInDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        if (!passwordEncoder.matches(logInDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        JwtAuthResponseDto response = new JwtAuthResponseDto();
        response.setToken(jwtService.generateToken(user));
        return response;
    }

    public JwtAuthResponseDto signIn(SigninDto signinDto) {
        if (userRepository.findByEmail(signinDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("Email already exist");
        }

        applicationEventPublisher.publishEvent(new SignInEvent(signinDto.getEmail(), signinDto.getName()));
        User user = new User();
        user.setEmail(signinDto.getEmail());
        user.setPassword(passwordEncoder.encode(signinDto.getPassword()));
        user.setName(signinDto.getName());
        user.setBirthDate(signinDto.getBirthdate());
        user.setCreatedAt(LocalDateTime.now());
        user.setNickname(signinDto.getNickname());
        user.setNicknameNormalized(Normalizer.normalize(signinDto.getNickname(), Normalizer.Form.NFC));

        if (signinDto.getIsAdmin()) {
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
