package dbp.proyecto.favoriteArtist;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.user.User;
import jakarta.persistence.*;

@Entity
public class FavoriteArtist {
    @EmbeddedId
    private FavoriteArtistID id;

    public FavoriteArtist(User user, Artist artist) {
        this.user = user;
        this.artist = artist;
        this.id = id;
    }

    public FavoriteArtist() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("artistId")
    private Artist artist;


}
