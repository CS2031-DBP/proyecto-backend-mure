package dbp.proyecto.tablasIntermedias.artistAlbum;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.artist.domain.Artist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ArtistAlbum {
    @EmbeddedId
    private ArtistAlbumId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("artistId")
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("albumId")
    private Album album;
}
