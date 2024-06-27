package dbp.proyecto.artist.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.song.domain.Song;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Boolean verified;

    @Size(max = 500)
    private String description;

    @OneToMany(mappedBy = "artist")
    private Set<Album> albums = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "artist_songs",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private Set<Song> songs = new HashSet<>();

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", verified=" + verified +
                ", description='" + description + '\'' +
                '}';
    }
}
