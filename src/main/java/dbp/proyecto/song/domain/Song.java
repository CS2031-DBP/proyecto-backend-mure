package dbp.proyecto.song.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.post.domain.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String genre;

    @NotNull
    private LocalDate releaseDate;

    @NotBlank
    private String duration;

    private String coverImage;

    @NotNull
    @Min(0)
    private Integer likes;

    @NotNull
    @Min(0)
    private Integer timesPlayed;

    @ManyToMany(mappedBy = "songs")
    private Set<Artist> artists = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "album_id")
    private Album album;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    public int getDurationInSeconds() {
        String[] parts = duration.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        return minutes * 60 + seconds;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration='" + duration + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", likes=" + likes +
                ", timesPlayed=" + timesPlayed +
                '}';
    }
}
