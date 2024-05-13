package dbp.proyecto.comment;

import dbp.proyecto.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private User user;

    private String comment;

    private Integer likes;

    public Comment() {}

    public Comment(Long id, User user, String comment, Integer likes) {
        this.id = id;
        this.user = user;
        this.comment = comment;
        this.likes = likes;
    }
}
