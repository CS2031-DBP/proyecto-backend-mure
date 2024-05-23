package dbp.proyecto.artist.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;

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

    @OneToMany(mappedBy = "artist")
    private List<ArtistAlbum> artistAlbums;

    @OneToMany(mappedBy = "artist")
    private List<ArtistSongs> songs;

    @Column(length = 1000)
    private String description;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    private Boolean verified;

    @OneToMany(mappedBy = "artist")
    private List<FavoriteArtist> favoriteArtists = new ArrayList<>();
}
