
package dbp.proyecto.events.updateFavs.likes;

import dbp.proyecto.user.domain.UserService;
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
    public void handleHelloEmailEvent(UpdateFavsEvent event) {
        userService.updateFavorites(event.getUser(), event.getPost());
    }
}
