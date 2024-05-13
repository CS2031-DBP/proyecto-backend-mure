package dbp.proyecto.post;

import dbp.proyecto.content.Content;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Post extends Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String imageurl;

    private String audioUrl;

    public Post() {}

    public Post(Long id, String description, String imageurl, String audioUrl) {
        this.id = id;
        this.description = description;
        this.imageurl = imageurl;
        this.audioUrl = audioUrl;
    }
}
