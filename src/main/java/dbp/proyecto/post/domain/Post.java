package dbp.proyecto.post.domain;

import dbp.proyecto.content.Content;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Post extends Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    private String imageUrl;

    private String audioUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(Long id, String description, String imageUrl, String audioUrl) {
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.audioUrl = audioUrl;
    }

    public Post() {
    }

}