package dbp.proyecto.album.domain;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.song.domain.Song;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String titleNormalized;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @Pattern(regexp = "\\d{2}:\\d{2}:\\d{2}")
    private String totalDuration;

    private Integer songsCount;

    private String coverImage;

    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "album")
    private List<Song> songs = new ArrayList<>();

    public void calculateTotalDuration() {
        int totalSeconds = 0;

        for (Song song : songs) {
            totalSeconds += song.getDurationInSeconds();
        }

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        this.totalDuration = "%02d:%02d:%02d".formatted(hours, minutes, seconds);
    }

    public void calculateSongsCount() {
        this.songsCount = songs.size();
    }
}
