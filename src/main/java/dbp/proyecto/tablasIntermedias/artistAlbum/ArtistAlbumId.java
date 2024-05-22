package dbp.proyecto.tablasIntermedias.artistAlbum;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ArtistAlbumId implements Serializable {
    @Column(name = "artist_id")
    private Long artistId;

    @Column(name = "album_id")
    private Long albumId;

    public ArtistAlbumId() {
    }

    public ArtistAlbumId(Long artistId, Long albumId) {
        this.artistId = artistId;
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistAlbumId that = (ArtistAlbumId) o;

        if (!artistId.equals(that.artistId)) return false;
        return albumId.equals(that.albumId);
    }

    @Override
    public int hashCode() {
        int result = artistId.hashCode();
        result = 31 * result + albumId.hashCode();
        return result;
    }
}
