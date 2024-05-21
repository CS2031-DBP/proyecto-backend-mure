package dbp.proyecto.story.domain;

import dbp.proyecto.content.Content;
import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Story extends Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Timestamp startDate;

    @Column(nullable = false)
    private Timestamp endDate;

    @Column(nullable = false)
    private String videoUrl;

    @Column(length = 200)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Story(String videoUrl, String text, User user, Song song) {
        super(song);
        this.startDate = new Timestamp(System.currentTimeMillis());
        this.endDate = new Timestamp(startDate.getTime() + 86400000);
        this.videoUrl = videoUrl;
        this.text = text;
        this.user = user;
    }

    public Story() {
    }
}
