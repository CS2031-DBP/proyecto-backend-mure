package dbp.proyecto.favoriteArtist;

import jakarta.persistence.Column;

public class FavoriteArtistID {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "artist_id")
    private int artistId;

    public FavoriteArtistID(int userId, int artistId) {
        this.userId = userId;
        this.artistId = artistId;
    }

    public FavoriteArtistID() {
    }
}
