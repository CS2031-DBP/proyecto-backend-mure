package dbp.proyecto.artist.domain;

import dbp.proyecto.artistSongs.ArtistSongs;
import dbp.proyecto.favoriteArtist.FavoriteArtist;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

//    @OneToMany(mappedBy = "artist")
//    private List<ArtistAlbums> albums;

    @OneToMany(mappedBy = "artist")
    private List<ArtistSongs> artist = new ArrayList<>();

    @Column(length = 1000)
    private String description;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    private Boolean verified;

    @OneToMany(mappedBy = "artist")
    private List<FavoriteArtist> favoriteArtists = new ArrayList<>();
}
