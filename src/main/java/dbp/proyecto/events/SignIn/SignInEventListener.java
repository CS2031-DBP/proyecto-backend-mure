/*
package dbp.proyecto.events.SignIn;

import dbp.proyecto.email.domain.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SignInEventListener {

    private final EmailService emailService;

    public SignInEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    @Async
    public void handleHelloEmailEvent(SignInEvent event) throws MessagingException {
        emailService.correoSingIn(event.getEmail(), event.getUser());
    }
}*/
