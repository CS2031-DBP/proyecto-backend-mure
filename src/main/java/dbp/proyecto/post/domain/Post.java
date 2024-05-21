package dbp.proyecto.post.domain;

import dbp.proyecto.content.Content;
import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public Post(String description, String imageUrl, String audioUrl, User user, Song song, Playlist playlist) {
        super(song, playlist);
        this.description = description;
        this.imageUrl = imageUrl;
        this.audioUrl = audioUrl;
        this.user = user;
    }

    public Post() {
    }
}
