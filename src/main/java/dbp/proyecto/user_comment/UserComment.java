package dbp.proyecto.user_comment;

import dbp.proyecto.comments.comment;
import dbp.proyecto.user.User;
import jakarta.persistence.*;

import javax.xml.stream.events.Comment;

@Entity
public class UserComment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = comment.class)
    @JoinColumn(name = "comment_id")
    private comment com;

    public UserComment() {}

    public UserComment(Long id, User user, comment com) {
        this.id = id;
        this.user = user;
        this.com = com;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public comment getCom() {
        return com;
    }

    public void setCom(comment com) {
        this.com = com;
    }
}
