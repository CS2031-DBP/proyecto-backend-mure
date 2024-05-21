package dbp.proyecto.events.Login;

import dbp.proyecto.email.domain.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LoginEventListener {

    private final EmailService emailService;

    public LoginEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    @Async
    public void handleHelloEmailEvent(LoginEvent event) throws MessagingException {
        emailService.correoLogin(event.getEmail(), event.getUser());
    }
}