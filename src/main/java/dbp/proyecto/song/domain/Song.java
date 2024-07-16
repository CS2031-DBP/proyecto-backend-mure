package dbp.proyecto.song.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String titleNormalized;

    @NotBlank
    private String genre;

    @NotNull
    private LocalDate releaseDate;

    @NotBlank
    private String duration;

    private String coverImageUrl;

    @NotNull
    @Min(0)
    private Integer likes;

    @NotNull
    @Min(0)
    private Integer timesPlayed;

    private String spotifyUrl;

    private String spotifyPreviewUrl;

    @ManyToMany(mappedBy = "songs")
    private List<Artist> artists;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "album_id")
    private Album album;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @ManyToMany(mappedBy = "favoriteSongs")
    private List<User> users = new ArrayList<>();

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
                ", coverImage='" + coverImageUrl + '\'' +
                ", likes=" + likes +
                ", timesPlayed=" + timesPlayed +
                '}';
    }

}
