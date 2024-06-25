
package dbp.proyecto.events.updateFavs.dislikes;

import dbp.proyecto.user.domain.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UpdateFavsListenerDL {

    private final UserService userService;

    public UpdateFavsListenerDL(UserService userService) {
        this.userService = userService;
    }


    @EventListener
    @Async
    public void handleHelloEmailEvent(UpdateFavsEventDL event) {
        userService.updateFavoritesDL(event.getUser(), event.getPost());
    }
}
