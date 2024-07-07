package dbp.proyecto.artist.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.song.domain.Song;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String nameNormalized;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Boolean verified;

    @Size(max = 500)
    private String description;

    @NotBlank
    @NotNull
    private String imageUrl;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "artist_songs",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song> songs = new ArrayList<>();

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
