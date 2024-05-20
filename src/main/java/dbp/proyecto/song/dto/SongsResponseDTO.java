package dbp.proyecto.song.dto;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.tablasIntermedias.artistSongs.ArtistSongs;
import dbp.proyecto.tablasIntermedias.favoriteSong.FavoriteSong;
import dbp.proyecto.tablasIntermedias.playlistSongs.PlaylistSongs;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.Duration;
import java.util.Date;
import java.util.List;


@Data
public class SongsResponseDTO {
    private String title;
    private Album album;
    private Long likesNum;
    private Date releaseDate;
    private Duration duration;
    private String coverImage;
    private List<ArtistSongs> artists;
}

