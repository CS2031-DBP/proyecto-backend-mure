package dbp.proyecto.album.domain;

import dbp.proyecto.tablasIntermedias.artistAlbum.ArtistAlbum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(length = 1500)
    String description;

    @Column(nullable = false)
    Timestamp duration;

    @OneToMany(mappedBy = "album")
    private List<ArtistAlbum> artistAlbums = new ArrayList<>();
}