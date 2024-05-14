package dbp.proyecto.post.domain;

import dbp.proyecto.content.Content;
import dbp.proyecto.user.User;
import jakarta.persistence.*;


@Entity
public class Post extends Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String imageurl;

    private String audioUrl;

    @ManyToOne
    private User user;

    public Post(Long id, String description, String imageurl, String audioUrl) {
        this.id = id;
        this.description = description;
        this.imageurl = imageurl;
        this.audioUrl = audioUrl;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}