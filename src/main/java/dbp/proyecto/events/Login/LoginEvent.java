package dbp.proyecto.events.Login;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LoginEvent extends ApplicationEvent {
    private final String email;
    private final String user;

    public LoginEvent(String email, String user) {
        super(email);
        this.email = email;
        this.user = user;
    }
}