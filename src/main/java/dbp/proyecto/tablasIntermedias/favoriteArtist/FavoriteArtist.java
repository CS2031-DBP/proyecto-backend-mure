package dbp.proyecto.tablasIntermedias.favoriteArtist;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class FavoriteArtist {
    @EmbeddedId
    private FavoriteArtistId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("artistId")
    private Artist artist;


}
