package dbp.proyecto.events.updateFavs.dislikes;

import dbp.proyecto.post.domain.Post;
import dbp.proyecto.user.domain.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UpdateFavsEventDL extends ApplicationEvent {
    private final User user;
    private final Post post;


    public UpdateFavsEventDL(User user, Post post) {
        super(post);
        this.user = user;
        this.post = post;
    }
}
