package dbp.proyecto.events.SignIn;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SignInEvent extends ApplicationEvent {
    private final String email;
    private final String user;

    public SignInEvent(String email, String user) {
        super(email);
        this.email = email;
        this.user = user;
    }
}
