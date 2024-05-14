package dbp.proyecto.artist.domain;

import dbp.proyecto.artistSongs.ArtistSongs;
import dbp.proyecto.favoriteArtist.FavoriteArtist;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @OneToMany(mappedBy = "artist")
    private List<FavoriteArtist> favoriteArtists = new ArrayList<>();

    @OneToMany(mappedBy = "artist")
    private List<ArtistSongs> artist = new ArrayList<>();

    String description;

    Date birthDate;

    Boolean verified;


}
