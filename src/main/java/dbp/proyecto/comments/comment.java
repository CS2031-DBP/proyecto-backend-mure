package dbp.proyecto.comments;

import dbp.proyecto.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private User user;

    private String comment;

    private Integer likes;

    public comment(Long id, User user, String comment, Integer likes) {
        this.id = id;
        this.user = user;
        this.comment = comment;
        this.likes = likes;
    }

    public comment() {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
