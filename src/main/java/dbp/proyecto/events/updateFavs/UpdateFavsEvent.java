package dbp.proyecto.events.updateFavs;

import dbp.proyecto.post.domain.Post;
import dbp.proyecto.user.domain.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UpdateFavsEvent extends ApplicationEvent {
    private final User user;
    private final Post post;


    public UpdateFavsEvent(User user, Post post) {
        super(post);
        this.user = user;
        this.post = post;
    }
}
