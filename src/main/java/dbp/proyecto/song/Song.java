package dbp.proyecto.song;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.favoriteSong.FavoriteSong;
import dbp.proyecto.playlistSongs.PlaylistSongs;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String album;

    private Long likesNum;

    // TODO: Implementar tabla intermedia con artist

    private Date releaseDate;

    private String genre;

    private Duration duration;

    private Integer timesPlayed;

    private String coverImage;

    @OneToMany(mappedBy = "song")
    private List<PlaylistSongs> playlistSongs;

    @OneToMany(mappedBy = "song")
    private List<FavoriteSong> favoriteSongs;

    public Song() {
    }

    public Song(Long id, String title, String album, Long likesNum, List<Artist> artists, Date releaseDate, String genre, Duration duration, Integer timesPlayed, String coverImage) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.likesNum = likesNum;
        //this.artists = artists;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.duration = duration;
        this.timesPlayed = timesPlayed;
        this.coverImage = coverImage;
    }
}
