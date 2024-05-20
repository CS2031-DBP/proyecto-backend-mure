package dbp.proyecto.tablasIntermedias.artistSongs;

import dbp.proyecto.artist.domain.Artist;

import dbp.proyecto.song.domain.Song;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ArtistSongs {
    @EmbeddedId
    private ArtistSongId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("artistId")
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("songId")
    private Song song;
}
