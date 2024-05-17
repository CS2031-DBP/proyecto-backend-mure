package dbp.proyecto.artistAlbum;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.artist.domain.Artist;
import jakarta.persistence.*;

@Entity
public class ArtistAlbum {
    @EmbeddedId
    private ArtistAlbumId id;

    public ArtistAlbum() {
    }

    public ArtistAlbum(Artist artist, Album album) {
        this.artist = artist;
        this.album = album;
        this.id = new ArtistAlbumId(artist.getId(), album.getId());
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("artistId")
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("albumId")
    private Album album;
}
