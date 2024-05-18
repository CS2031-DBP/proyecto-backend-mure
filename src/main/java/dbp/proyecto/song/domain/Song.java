package dbp.proyecto.song.domain;

import dbp.proyecto.tablasIntermedias.favoriteSong.FavoriteSong;
import dbp.proyecto.tablasIntermedias.playlistSongs.PlaylistSongs;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String album;

    private Long likesNum;

    @ElementCollection
    private List<String> artists;

    private Date releaseDate;

    private String genre;

    private Duration duration;

    private Integer timesPlayed;

    private String coverImage;

    @OneToMany(mappedBy = "song")
    private List<PlaylistSongs> playlistSongs;

    @OneToMany(mappedBy = "song")
    private List<FavoriteSong> favoriteSongs;

    public Song(Long id, String title, String album, Long likesNum, List<String> artists, Date releaseDate, String genre, Duration duration, Integer timesPlayed, String coverImage) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.likesNum = likesNum;
        this.artists = artists;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.duration = duration;
        this.timesPlayed = timesPlayed;
        this.coverImage = coverImage;
    }

    public Song() {
    }

}
