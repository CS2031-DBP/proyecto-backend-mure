package dbp.proyecto.tablasIntermedias.favoriteArtist;

import jakarta.persistence.Column;

import java.util.Objects;

public class FavoriteArtistId {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "artist_id")
    private Long artistId;

    public FavoriteArtistId(Long userId, Long artistId) {
        this.userId = userId;
        this.artistId = artistId;
    }

    public FavoriteArtistId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavoriteArtistId that = (FavoriteArtistId) o;

        if (!Objects.equals(userId, that.userId)) return false;
        return Objects.equals(artistId, that.artistId);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (artistId != null ? artistId.hashCode() : 0);
        return result;
    }
}
