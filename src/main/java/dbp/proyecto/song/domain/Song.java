package dbp.proyecto.song.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.tablasIntermedias.artistSongs.ArtistSongs;
import dbp.proyecto.tablasIntermedias.favoriteSong.FavoriteSong;
import dbp.proyecto.tablasIntermedias.playlistSongs.PlaylistSongs;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Album album;

    private Long likesNum;

    private Date releaseDate;

    private String genre;

    private String duration;

    private Integer timesPlayed;

    private String coverImage;

    @OneToMany(mappedBy = "song")
    private List<PlaylistSongs> playlistSongs = new ArrayList<>();

    @OneToMany(mappedBy = "song")
    private List<FavoriteSong> favoriteSongs = new ArrayList<>();

    @OneToMany(mappedBy = "song")
    private List<ArtistSongs> artists = new ArrayList<>();

}
