
package dbp.proyecto.events.updateFavs;

import dbp.proyecto.email.domain.EmailService;
import dbp.proyecto.user.domain.UserService;
import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UpdateFavsListener {

    private final UserService userService;

    public UpdateFavsListener(UserService userService) {
        this.userService = userService;
    }


    @EventListener
    @Async
    public void handleHelloEmailEvent(UpdateFavsEvent event) throws MessagingException {
        userService.updateFavorites(event.getUser(), event.getPost());
    }
}
