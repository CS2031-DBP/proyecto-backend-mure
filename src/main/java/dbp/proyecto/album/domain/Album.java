package dbp.proyecto.album.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

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

//    @ManyToMany
//    @JoinTable(
//            name = "artist_albums",
//            joinColumns = @JoinColumn(name = "album_id"),
//            inverseJoinColumns = @JoinColumn(name = "artist_id")
//    )
//    List<Artist> artists;
}