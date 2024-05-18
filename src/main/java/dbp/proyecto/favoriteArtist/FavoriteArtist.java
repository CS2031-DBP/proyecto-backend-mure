package dbp.proyecto.favoriteArtist;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;

@Entity
public class FavoriteArtist {
    @EmbeddedId
    private FavoriteArtistId id;

    public FavoriteArtist(User user, Artist artist) {
        this.user = user;
        this.artist = artist;
        this.id = new FavoriteArtistId(user.getId(), artist.getId());
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
